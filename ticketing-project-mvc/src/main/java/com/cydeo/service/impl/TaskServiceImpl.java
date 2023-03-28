package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO, Long> implements TaskService {

    @Override
    public TaskDTO save(TaskDTO object) {
        // To handle exception, whenever the status is null, it will set/open a new status
        if (object.getTaskStatus() == null) {
            object.setTaskStatus(Status.OPEN);
        }
       // To handle exception, whenever the date is null, it will set into a new date
        if (object.getAssignedDate() == null) {
            object.setAssignedDate(LocalDate.now());
        }
        /*
        After clicking the Save button, if the ID is null, it will not display it in the table.
        It will throw exception. To handle it, whenever the ID is null it will set into a new id
        */
        if (object.getId() == null) {
            object.setId(UUID.randomUUID().getMostSignificantBits());
        }
        return super.save(object.getId(), object);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void update(TaskDTO object) {

        TaskDTO foundTask = findById(object.getId());

        object.setTaskStatus(foundTask.getTaskStatus());
        object.setAssignedDate(foundTask.getAssignedDate());

        super.update(object.getId(), object);

    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findTasksByManager(UserDTO manager) {
        return findAll().stream()
                .filter(task -> task.getProject().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {
        return findAll().stream()
                .filter(task -> task.getTaskStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
        return findAll().stream()
                .filter(task -> !task.getTaskStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        findById(task.getId()).setTaskStatus(task.getTaskStatus());
        update(task);
    }
}
