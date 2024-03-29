package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service//autowiring the ProjectService
public class ProjectServiceImpl extends AbstractMapService<ProjectDTO, String> implements ProjectService {

    private final TaskService taskService;

    public ProjectServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO save(ProjectDTO code) {
        //code.setProjectStatus(Status.OPEN);//whenever getting a project set it to OPEN
        if(code.getProjectStatus() == null) {//to handle exception if the project is null
            code.setProjectStatus(Status.OPEN);//whenever getting a project set it to OPEN
        }
        return super.save(code.getProjectCode(),code);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }

    /**
     * This method finds the manager by passing the username
     * @param id String, must pass the username
     * @return returns the manager found by the username or id
     */
    @Override
    public ProjectDTO findById(String id) {
        return super.findById(id);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public void update(ProjectDTO objectName) {
        //handling a null object in the UI display
        ProjectDTO newProject = findById(objectName.getProjectCode());
        if(objectName.getProjectStatus() == null) {
            objectName.setProjectStatus(newProject.getProjectStatus());
        }
        super.update(objectName.getProjectName(),objectName);
    }

    @Override
    public void complete(ProjectDTO byId) {
        byId.setProjectStatus(Status.COMPLETE);
        super.save(byId.getProjectCode(), byId);//save it into the map
    }

    /**
     * This method filters out a given manager, and counts stores them in a list
     * the uncompleted projects assigned to the manager.
     * @return list of assigned projects
     */
    @Override
    public List<ProjectDTO> findAllNonCompletedProjects() {
        return findAll().stream()
                .filter(project -> !project.getProjectStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    /**
     * There are separate UI tables (use list, task list, and project status). This method is connecting
     * this tables to get data and return the list.
     * This method gets all the projects, filters out the projects that belong
     * to a given manager, from each project creates a new ProjectDTO (builds all args constructor)
     * then finds the tasks, counts the tasks of a project based on status, then returns the project.
     * Stores all the projects assigned to a manager and returns the lists.
     * @param manager UserDTO
     * @return list of assigned projects
     */
    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {

        List<ProjectDTO> projectList = findAll().stream()
                        .filter(project -> project.getAssignedManager().equals(manager))
                        .map( project -> {
                            List<TaskDTO> taskList = taskService.findTasksByManager(manager);
                            int completeTaskCounts = (int)taskList.stream()
                                    .filter(t ->  t.getProject().equals(project) && t.getTaskStatus() == Status.COMPLETE)
                                    .count();
                            int unfinishedTaskCounts = (int)taskList.stream()
                                    .filter(t ->  t.getProject().equals(project) && t.getTaskStatus() != Status.COMPLETE)
                                    .count();
                            project.setCompleteTaskCounts(completeTaskCounts);
                            project.setUnfinishedTaskCounts(unfinishedTaskCounts);
                            return project;
                        })
                        .collect(Collectors.toList());
        return projectList;
    }
}
