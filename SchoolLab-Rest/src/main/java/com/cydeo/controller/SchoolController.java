package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;
    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
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

    /**
     * creating an endpoint for individual Address information
     *         /address/1
     *         return status code 200
     *         message: "Address is successfully retrieved."
     *         success: true
     *         and address information
     */
    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAllAddresses(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(new ResponseWrapper(true, "Addresses are successfully retrieved",
                HttpStatus.OK.value(), addressService.findById(id)));
    }

    /**
     * creating an endpoint to update individual address information
     * and directly returning the updated address
     */
}
