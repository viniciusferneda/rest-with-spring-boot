package com.vferneda.restwithspringboot.person.services;

import com.vferneda.restwithspringboot.exception.ResourceNotFoundException;
import com.vferneda.restwithspringboot.person.converter.DozerConverter;
import com.vferneda.restwithspringboot.person.converter.custom.PersonConverter;
import com.vferneda.restwithspringboot.person.data.model.Person;
import com.vferneda.restwithspringboot.person.data.vo.v1.PersonVO;
import com.vferneda.restwithspringboot.person.data.vo.v2.PersonVOV2;
import com.vferneda.restwithspringboot.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonConverter converter;

    public PersonVO create(PersonVO personVO) {
        var person = DozerConverter.parseObject(personVO, Person.class);
        return DozerConverter.parseObject(this.repository.save(person), PersonVO.class);
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        var entity = converter.convertVOToEntity(person);
        var vo = converter.convertEntityToVO(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        final Person entity = DozerConverter.parseObject(findById(person.getKey()), Person.class);
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerConverter.parseObject(this.repository.save(entity), PersonVO.class);
    }

    @Transactional
    public PersonVO disabledPerson(Long id) {
        this.repository.disabledPerson(id);

        return DozerConverter.parseObject(
                this.repository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("No records found for this ID!")),
                PersonVO.class);
    }

    public boolean delete(Long id) {
        final Person entity = DozerConverter.parseObject(findById(id), Person.class);
        this.repository.delete(entity);
        return Boolean.TRUE;
    }

    public PersonVO findById(Long id) {
        return DozerConverter.parseObject(
                this.repository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("No records found for this ID!")),
                PersonVO.class);
    }

    public Page<PersonVO> findAll(Pageable pageable) {
        final var page = repository.findAll(pageable);
        return page.map(this::convertToPersonVO);
    }

    private PersonVO convertToPersonVO(Person person) {
        return DozerConverter.parseObject(person, PersonVO.class);
    }

    public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
        final var page = repository.findPersonByName(firstName, pageable);
        return page.map(this::convertToPersonVO);
    }

}
