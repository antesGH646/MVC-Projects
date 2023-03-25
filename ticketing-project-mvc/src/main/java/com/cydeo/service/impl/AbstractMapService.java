package com.cydeo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapService<T,ID> {

    //stores data that comes from the data generator, simulates database
    protected Map<ID,T> map = new HashMap<>();

    //will add the type e.g. role or user into the map object
    T save(ID id,T t){
        map.put(id,t);
        return t;
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

    void update(ID id,T t){
        map.put(id,t);
    }
}
