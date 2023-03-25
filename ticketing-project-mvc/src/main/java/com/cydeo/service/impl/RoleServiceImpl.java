package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//used with services; it combines @Component, @Retention, @Documented
public class RoleServiceImpl extends AbstractMapService<RoleDTO,Long> implements RoleService {


    //adds the type of role into the map, and returns the added type of role
    @Override
    public RoleDTO save(RoleDTO object) {
        return super.save(object.getId(), object);
    }

    //stores a list of roles and returns the list
    @Override
    public List<RoleDTO> findAll() {
        return super.findAll();
    }

    //deletes the type of role by id and returns it
    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void update(RoleDTO role) {
        super.update(role.getId(), role);
    }

    //finds the type of role by id and returns it
    @Override
    public RoleDTO findById(Long id) {
        return super.findById(id);
    }
}
