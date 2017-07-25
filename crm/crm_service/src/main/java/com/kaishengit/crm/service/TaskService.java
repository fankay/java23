package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findTaskByAccountId(Integer accountId, boolean showAll);

    void saveNewTask(Task task);

    List<Task> findUnDoneTaskByCustId(Integer id);

    Task findById(Integer id);

    void updateTask(Task task);

    void delTask(Task task);
}
