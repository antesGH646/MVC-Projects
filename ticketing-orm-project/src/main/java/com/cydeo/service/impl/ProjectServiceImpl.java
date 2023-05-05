package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;//to fetch projects from the database
    private final ProjectMapper projectMapper;//used to convert the fetched projects into DTOs

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * This fetches a single Project from the database
     * required to show up in the UI.
     * First the project is fetched from database through the repository
     * Second return the fetched project to the DTO, it means the data must be converted into DTO objects
     * Third display the DTO objects into the UI through the Controller and thymeleaf
     * @param code String
     * @return a ProjectDTO found by its code
     */
    @Override
    public ProjectDTO getByProjectCode(String code) {
       Project project = projectRepository.findByProjectCode(code);//called an abstract method
        return projectMapper.convertToDTO(project);
    }

    /**
     * This method returns a list of projects required to show up on the UI table. How?
     * First get all the projects are fetched from the database through the repository level
     * Second return the fetched projects to the DTO, it means the data must be converted to DTO
     * Third display the DTO objects into the UI through the Controller and thymeleaf
     * @return list of ProjectDTO objects
     */
    @Override
    public List<ProjectDTO> listAllProjects() {
        //A list created to fetch projects from the db using the ProjectRepository
        List<Project> projectList = projectRepository.findAll();//find all is coming from the JpaRepository
        //convert the fetched data into DTO then return them as a list
        return projectList.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    /**
     * This method grab user's UI entry and saves the data into the database. How?
     * database interacts only with the entity objects,
     * First convert the DTO objects into entity objects
     * Second call the repository to save the converted data,
     * Jpa provides ready save() method
     * @param dto ProjectDTO object
     */
    @Override
    public void save(ProjectDTO dto) {
        //when a project is created, the status must be set to open
        dto.setProjectStatus(Status.OPEN);
        //convert the project into entity, then save them
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {

    }

    @Override
    public void delete(String code) {

    }

    @Override
    public void complete(String projectCode) {

    }
}





















