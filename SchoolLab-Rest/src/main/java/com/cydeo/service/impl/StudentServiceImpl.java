package com.cydeo.service.impl;

import com.cydeo.dto.StudentDTO;
import com.cydeo.entity.Student;
import com.cydeo.exception.NotFoundException;
import com.cydeo.util.MapperUtil;
import com.cydeo.repository.StudentRepository;
import com.cydeo.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MapperUtil mapperUtil;

    public StudentServiceImpl(StudentRepository studentRepository, MapperUtil mapperUtil) {
        this.studentRepository = studentRepository;
        this.mapperUtil = mapperUtil;
    }

    /**
     * The fetched objects has to be mapped into DTO objects first
     * before returning them as a list. MapperUtil a convert() method that
     * converts entities into DTOs and vice-versa.
     * @return a list of mapped DTO objects
     */
    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(student -> mapperUtil.convert(student, new StudentDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Long id) throws Exception {
        Student foundStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Student Found!"));
        return mapperUtil.convert(foundStudent, new StudentDTO());
    }

}