package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController( ProjectService projectService) {
        this.projectService = projectService;

    }

    /**
     * Return a list of all projects
     * @return ResponseWrapper
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper> getProjects() {
        //getting the list from UserService
        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projectDTOList, HttpStatus.OK));
    }

    /**
     * Return a user by project code
     * the response code is the json payload or body code
     * not the status code
     * @return ResponseWrapper
     */
    @GetMapping("/{code}")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String projectCode) {
        //getting the list from UserService
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved",projectDTO, HttpStatus.OK));
    }

    /**
     * ProjectDTO is fetched by PostMan then captured by the ProjectService
     * @param projectDTO project
     * @return ResponseWrapper mapped project object
     */
    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Projects is successfully created",HttpStatus.CREATED));
    }

    /**
     * ProjectDTO is fetched by PostMan then captured by the ProjectService
     * @param projectDTO project
     * @return ResponseWrapper mapped project object
     */

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated",HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully deleted",HttpStatus.OK));
       // return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper("Project is successfully deleted",HttpStatus.OK));
    }

    @GetMapping("/manager/project-status")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
       List<ProjectDTO> projects = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved",projects, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> completedManagerProjects(@PathVariable("projectCode") String code) {
        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully completed",HttpStatus.OK));
    }
}
