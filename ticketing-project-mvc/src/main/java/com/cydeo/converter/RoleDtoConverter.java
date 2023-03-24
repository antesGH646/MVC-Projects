package com.cydeo.converter;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
//Converter interface asks 2 thing, what into what, String into RoleDTO
public class RoleDtoConverter implements Converter<String, RoleDTO> {

    RoleService roleService;//required to call the findById() method

    //must be injected using constructor
    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Converts String format number from HTML into Long
     * When you are working with Thymeleaf you need converter
     * @param source String
     * @return Long
     */
    @Override
    public RoleDTO convert(String source) {
        return roleService.findById(Long.parseLong(source));
    }
}
