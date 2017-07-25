package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    /**
     * 待办任务首页
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping
    public String home(@RequestParam(required = false,defaultValue = "")String show,
                       HttpSession httpSession, Model model) {
        Account account = getCurrUser(httpSession);
        boolean showAll = "all".equals(show) ? true : false;
        List<Task> taskList = taskService.findTaskByAccountId(account.getId(),showAll);

        model.addAttribute("taskList",taskList);
        return "task/home";
    }

    /**
     * 新增任务
     */
    @GetMapping("/new")
    public String newTask() {
        return "task/new";
    }

    @PostMapping("/new")
    public String newTask(Task task, RedirectAttributes redirectAttributes) {
        taskService.saveNewTask(task);
        redirectAttributes.addFlashAttribute("message","新增任务成功");
        return "redirect:/task";
    }

    /**
     * 修改待办事项的状态 已完成 | 未完成
     */
    @GetMapping("/{id:\\d+}/state/{state}")
    public String changeTaskState(@PathVariable Integer id,@PathVariable String state,HttpSession httpSession) {
        Task task = taskService.findById(id);
        Account account = getCurrUser(httpSession);

        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        //根据state变量决定对象的状态
        if("done".equals(state)) {
            task.setDone(true);
        } else {
            task.setDone(false);
        }

        taskService.updateTask(task);
        return "redirect:/task";
    }

    /**
     * 删除待办任务
     */
    @GetMapping("/{id:\\d+}/del")
    public String delTask(@PathVariable Integer id,HttpSession httpSession) {
        Task task = taskService.findById(id);
        Account account = getCurrUser(httpSession);

        if(task == null) {
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())) {
            throw new ForbiddenException();
        }

        taskService.delTask(task);
        return "redirect:/task";
    }

}
