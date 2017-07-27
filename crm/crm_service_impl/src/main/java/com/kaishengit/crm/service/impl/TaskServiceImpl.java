package com.kaishengit.crm.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import static com.cronutils.model.field.expression.FieldExpressionFactory.*;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.TaskExample;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.quartz.jobs.WeixinNotifyJob;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 根据账号ID查找对应的待办任务
     * @param accountId
     * @param showAll
     * @return
     */
    @Override
    public List<Task> findTaskByAccountId(Integer accountId, boolean showAll) {
        return taskMapper.findByAccountId(accountId,showAll);
    }

    /**
     * 新增任务
     * @param task
     */
    @Override
    @Transactional
    public void saveNewTask(Task task) {
        task.setCreateTime(new Date());
        task.setDone(false);
        taskMapper.insert(task);

        //判断是否有提醒时间，如果有则需要添加动态定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put("to",task.getAccountId());
            dataMap.put("message",task.getTitle());

            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            //jobDetail
            JobDetail jobDetail = JobBuilder.newJob(WeixinNotifyJob.class)
                    .withIdentity(new JobKey("account:"+task.getAccountId(),"weixinGroup"))
                    .setJobData(dataMap).build();
            //Trigger CRON 2017-09-23 12 : 55 - 0 55 12 23 09 ? 2017-2017

            //字符串日期格式转换为JodaTime的DateTime类对象
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());
            //根据日期生成cron表达式
            Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                    .withYear(on(dateTime.getYear()))
                    .withMonth(on(dateTime.getMonthOfYear()))
                    .withDoM(on(dateTime.getDayOfMonth()))
                    .withHour(on(dateTime.getHourOfDay()))
                    .withMinute(on(dateTime.getMinuteOfHour()))
                    .withSecond(on(dateTime.getSecondOfMinute()))
                    .withDoW(questionMark())
                    .instance();

            String cronExpression = cron.asString();
            System.out.println("cronExpression:" + cronExpression);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (SchedulerException ex) {
                throw  new ServiceException("添加定时任务异常",ex);
            }

        }
    }

    /**
     * 根据客户ID查找未完成的待办事项
     * @param id
     * @return
     */
    @Override
    public List<Task> findUnDoneTaskByCustId(Integer id) {
        TaskExample example = new TaskExample();
        example.createCriteria().andCustIdEqualTo(id).andDoneEqualTo(false);
        example.setOrderByClause("finish_time asc");
        return taskMapper.selectByExample(example);
    }

    /**
     * 根据ID查找对应的待办事项
     * @param id
     * @return
     */
    @Override
    public Task findById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改Task对象
     * @param task
     */
    @Override
    public void updateTask(Task task) {
        taskMapper.updateByPrimaryKey(task);
    }

    /**
     * 删除待办对象
     * @param task
     */
    @Override
    @Transactional
    public void delTask(Task task) {
        taskMapper.deleteByPrimaryKey(task.getId());
        //判断删除的Task是否有提醒时间，如果有则删除定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("account:" + task.getAccountId(), "weixinGroup"));
            } catch (SchedulerException ex) {
                throw new ServiceException("删除调度器任务异常",ex);
            }
        }
    }
}
