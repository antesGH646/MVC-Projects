package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
    }

    /**
     * Adding teachers endpoint
     * @return a list of teachers
     */
    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        List<TeacherDTO> teacherDTOs = teacherService.findAll();
        return teacherDTOs;
    }

    /**
     * Creating an endpoint for students
     * responding message: "Students are successfully retrieved"
     * code: 200
     * success: true
     * student data
     * Objects of ResponseWrapper will be serialized to Json fetched with student data
     */
    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> getAllStudents() {
        return ResponseEntity.ok(new ResponseWrapper("Students are successfully retrieved",
                studentService.findAll()));
    }

    /**
     * Creating an endpoint for parents
     * responding message: "Parents are successfully retrieved"
     * code: 202
     * success: true
     * parents data
     */
    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> getAllParents() {
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Parents are successfully retrieved",
                HttpStatus.ACCEPTED.value(), parentService.findAll());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Parent", "Returned")
                .body(responseWrapper);
    }

}
