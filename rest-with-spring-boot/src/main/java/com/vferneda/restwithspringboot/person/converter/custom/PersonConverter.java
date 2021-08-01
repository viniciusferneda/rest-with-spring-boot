package com.vferneda.restwithspringboot.person.converter.custom;

import com.vferneda.restwithspringboot.person.data.model.Person;
import com.vferneda.restwithspringboot.person.data.vo.v2.PersonVOV2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PersonConverter {

    public PersonVOV2 convertEntityToVO(Person person) {
        final PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setAddress(person.getAddress());
        vo.setBirthDay(LocalDate.now());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());
        return vo;
    }

    public Person convertVOToEntity(PersonVOV2 person) {
        final Person entity = new Person();
        entity.setId(person.getId());
        entity.setAddress(person.getAddress());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        return entity;
    }
}
