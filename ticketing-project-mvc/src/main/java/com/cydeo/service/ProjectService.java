package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;

import java.util.List;

public interface ProjectService extends CrudService<ProjectDTO, String> {
    void complete(ProjectDTO byId);

    //to store list of uncompleted projects
    List<ProjectDTO> findAllNonCompletedProjects();

    //To store list of projects assigned to the manager
    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager);

}
