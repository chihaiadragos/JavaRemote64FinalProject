package com.sda64.javaremote64finalproject.mapper;

import com.sda64.javaremote64finalproject.exception.EntityNotFoundException;

public interface Mapper <E, D>{
    D convertToDto(E entity);
    E convertToEntity(D dto) throws EntityNotFoundException;
}
