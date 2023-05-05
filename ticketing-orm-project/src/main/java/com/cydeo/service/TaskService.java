package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id); //to find a task by id
    List<TaskDTO> findAllTasks(UserDTO manager);
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(TaskDTO dto);
}
