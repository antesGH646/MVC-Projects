package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    public SchoolController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
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

}
