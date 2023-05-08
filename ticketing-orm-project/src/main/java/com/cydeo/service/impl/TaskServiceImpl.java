package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    /**
     * This method finds a task from the db by its id
     * Without first finding the task you cannot update the task.
     * First the fetched task must be converted into DTO object
     * Since findById() method return Optional must make it Optional
     * If the task is found convert it to DTO object and return it
     * otherwise throw make it to throw exception or return null
     * @param id Long
     * @return a task
     */
    @Override
    public TaskDTO findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()) {
            return taskMapper.convertToDTO(task.get());
        }
        return null;
    }

    /**
     * This method return a list of tasks to show up in the UI
     * First get all the tasks from db
     * Second convert it to DTO then display it
     * @return a list of tasks
     */
    @Override
    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * This method an assigned task from the UI into the db
     * First convert the UI fetched data(dto objects) into entity object
     * Second save it into the database through the repository
     * Third set the task status to open, and to current date
     * because the status object does not come from the UI form
     * Make sure to declare id in the TaskDTO and the ProjectDTO
     * (private Long id), otherwise it will not save a task
     * with unmatched task or project
     * JpaRepository provides save() method or can create it in the services.
     *
     * @param dto TaskDTO
     */
    @Override
    public void save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);
    }

    /**
     * This make update a task in the db
     * First you need to convert the updated data into entity object
     * Second store in the db through the repository
     * Need to capture the status and the date and set or assign the updated task
     * @param dto TaskDTO
     */
    @Override
    public void update(TaskDTO dto) {
        Optional<Task> task = taskRepository.findById(dto.getId());
        Task convertedTask = taskMapper.convertToEntity(dto);
        if(task.isPresent()) {
            convertedTask.setId(task.get().getId());//to avoid duplicated record
            convertedTask.setTaskStatus(task.get().getTaskStatus());//assign the status
            convertedTask.setAssignedDate(task.get().getAssignedDate());//assign the date
            taskRepository.save(convertedTask);//save the updated task
        }
    }

    /**
     * This method soft deletes a task
     * First find the task from the db
     *
     * @param id Long
     */
    @Override
    public void delete(Long id) {
        //Since findById() returns Optional, the Task object must be Optional
        Optional<Task> foundTask = taskRepository.findById(id);
        if (foundTask.isPresent()) {
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }
    }

    @Override
    public int totalUncompletedTasks(String projectCode) {
        return taskRepository.totalUncompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTasks(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }
}
