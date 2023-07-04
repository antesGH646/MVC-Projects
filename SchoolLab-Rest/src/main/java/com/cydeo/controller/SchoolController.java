package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     *      annotations. If a DTO field is not serialized it means it won't show up with the API response.
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
     * The following additional objects are added beside the default json objects.
     * The objects are serialized in the ResponseWrapper so have to be serialized
     * to Json to send them with student data.
     *    responding message: "Students are successfully retrieved"
     *    code: 200
     *    success: true
     *    student data
     *  ResponseEntity helps to customize the added objects along with data you want.
     */
    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> getAllStudents() {
        return ResponseEntity.ok(new ResponseWrapper("Students are successfully retrieved",
                studentService.findAll()));
    }

    /**
     * Creating an endpoint for parents
     * The following additional objects are added beside the default json objects.
     * The objects are serialized in the ResponseWrapper so have to be serialized
     * to Json to send them with parent data.
     *      responding message: "Parents are successfully retrieved"
     *      code: 202
     *      success: true
     *      parents data
     */
    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> getAllParents() {
        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Parents are successfully retrieved",
                HttpStatus.ACCEPTED.value(), parentService.findAll());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)//202 response
                .header("Parent", "Returned")
                .body(responseWrapper);
    }

    /**
     * Creating an endpoint to fetch an Address information by id
     *  e.g /address/1
     *  The following additional objects are added beside the default json objects
     *         return status code 200
     *         message: "Address is successfully retrieved."
     *         success: true
     *         and address information
     *
     */
    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAllAddresses(@PathVariable("id") Long id) throws Exception {
        //ok() is accepting the custom objects(ResponseWrapper), to serialize them in the json object response
        return ResponseEntity.ok(new ResponseWrapper(true, "Addresses are successfully retrieved",
                HttpStatus.OK.value(), addressService.findById(id)));
    }

    /**
     * - Creating an endpoint to update individual address information
     *   and directly returning the updated address
     *       - For the path of the endpoint must use @PathVariable
     *       - Since you have to send a request body, need to use
     *         the @RequestBody for the DTO
     *       - If the id does not exit, the method should throw un exception
     * - To display the current temperature and country flag with the json response
     *   the method requires consuming an external API
     *   To consume external api
     *      1) add feign client dependency
     *      2) add @EnableFeignClient annotation in your runner class
     *      3) create feign client interface method
     *      4) convert the json response into DTOs (may use online converters)
     *          and add them to the dto package
     */
    @PutMapping("/address/{id}")
    public AddressDTO updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) throws Exception {
        addressDTO.setId(id);
        return  addressService.update(addressDTO);
    }

    /**
     *  Creating teachers endpoint to create teacher
     *  return Http status 201
     *  custom header "teacherId","idCreated"
     *  responseWrapper("Teacher is created",teacherInfo)
     *  To return a customized response always use the ResponseEntity<ResponseWrapper>
     *  as a return type and pass the DTO as a parameter
     *  The @Valid annotation tells Spring that the TeacherDTO fields will be validated
     *  according to annotations placed on the top of them.
     * @param teacherDTO TeacherDTO
     * @return Customized json response
     */
    @PostMapping("/teachers")
    public ResponseEntity<ResponseWrapper> creatTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        //wants to return TeachDTO
        TeacherDTO teacher = teacherService.createTeacher(teacherDTO);
        //wants to return customized responses in addition to the TeacherDTO (the ResponseWrapper)
        ResponseWrapper responseWrapper = new ResponseWrapper(true,"Teacher is created."
                ,HttpStatus.CREATED.value(),teacher);
        //returning custom headers with the json response
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("teacherId",String.valueOf(teacher.getId()))
                .body(responseWrapper);
    }
}
