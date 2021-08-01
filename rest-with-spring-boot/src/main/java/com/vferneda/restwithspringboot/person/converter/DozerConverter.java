package com.vferneda.restwithspringboot.person.converter;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerConverter {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        final List<D> destinationObjects = new ArrayList<>();
        for (Object obj : origin) {
            destinationObjects.add(parseObject(obj, destination));
        }
        return destinationObjects;
    }

}
