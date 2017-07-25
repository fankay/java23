package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.TaskExample;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

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
    public void saveNewTask(Task task) {
        task.setCreateTime(new Date());
        task.setDone(false);
        taskMapper.insert(task);
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
    public void delTask(Task task) {
        taskMapper.deleteByPrimaryKey(task.getId());
    }
}
