package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creating an abstract class that implements the generic methods
 * defined in the CrudService
 * @param <T> T
 * @param <ID> ID
 */
public abstract class AbstractMapService<T,ID> {

    //stores data that comes from the data generator that simulates the database
    protected Map<ID,T> map = new HashMap<>();

    //will add the type e.g. role or user into the map object
    T save(ID id,T object){
        map.put(id,object);
        return object;
    }

    //stores a list of the types e.g. roles or users
    List<T> findAll(){
        return new ArrayList<>(map.values());
    }

    //finds a type e.g. role or user by passing the id
    T findById(ID id){
        return map.get(id);
    }

    //deletes the type e.g. role or user by id (id could be Long or username)
    void deleteById(ID id){
        map.remove(id);
    }

    void update(ID id,T object){
        map.put(id,object);
    }
}
