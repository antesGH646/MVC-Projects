package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService extends CrudService<TaskDTO, Long> {

    //to find all the tasks assigned to a manager
    List<TaskDTO> findTasksByManager(UserDTO manager);

    //to find all tasks based on the status (completed, In progress, or Open)
    List<TaskDTO> findAllTasksByStatus(Status status);

    List<TaskDTO> findAllTasksByStatusIsNot(Status status);

    void updateStatus(TaskDTO task);
}
