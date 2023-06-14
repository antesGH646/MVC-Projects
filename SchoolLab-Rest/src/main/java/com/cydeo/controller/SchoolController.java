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
    public SchoolController(TeacherService teacherService,
                            StudentService studentService,
                            ParentService parentService,
                            AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }

    /**
     * Adding teachers endpoint
     *NB.
     *   1) Note that Controllers' methods work with DTOs
     *   2) Spring serializes the DTO's fields into Json objects and returns it.
     *   3) Though the Controller method looks fine, but the famous StackOverFlowError
     *      or infinite recursion may occur during serialization when there is bidirectional
     *      relationship between two or more DTOs . Similar the circular dependency
     *      issue that occurs in ORM or Springboot (we use @Lazy annotation to solve the issues),
     *      Spring creates json objects from the fields inside the DTO, therefore the methods will
     *      call each other causing infinite recursion depleting the memory. To break this chain
     *      use the  @JsonManagedReference annotation which means I do not want the specified DTO
     *      when during serializing. You may place this annotation in either of the DTOs. Spring will
     *      serialize all the fields of the related DTOs once, but the DTOs will not call each other infinitely.
     *      When you use @JsonManagedReference annotation more than once, in related DTOs it is a good practice to
     *      specify the values meaning marking where the DTO field is. It requires some kind of unique values.
     *   4) sensitive DTO fields such as password fields, null fields, and unknown fields should not
     *      be serialized to Json or exposed or be invisible. Use the @JsonIgnore() annotation for IDs
     *      and the  @JsonProperty() annotation for passwords, etc. On the class level can use
     *      the @JsonInclude(JsonInclude.Include.NON_NULL) and the @JsonIgnoreProperties(ignoreUnknown = true)
     *      annotations.
     *
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
