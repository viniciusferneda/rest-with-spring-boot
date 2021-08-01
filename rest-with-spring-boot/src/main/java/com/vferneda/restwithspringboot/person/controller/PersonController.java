package com.vferneda.restwithspringboot.person.controller;

import com.vferneda.restwithspringboot.person.data.vo.v1.PersonVO;
import com.vferneda.restwithspringboot.person.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@CrossOrigin
@Api(value = "Person Endpoint",
        description = "Description for person",
        tags = {"PersonEndpoint"})
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PagedResourcesAssembler assembler;

    @ApiOperation(value = "Find all people recorded")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        final Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        final Page<PersonVO> persons = this.personService.findAll(pageable);
        persons.stream()
                .forEach(per -> per.add(
                        linkTo(methodOn(PersonController.class).findById(per.getKey())).withSelfRel()
                        )
                );

        final PagedModel<?> pagedModel = assembler.toModel(persons);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @ApiOperation(value = "Find Person By Name")
    @GetMapping(value = "/findPersonByName/{firstName}",
            produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findPersonByName(
            @PathVariable(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        final Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        final Page<PersonVO> persons = this.personService.findPersonByName(firstName, pageable);
        persons.stream()
                .forEach(per -> per.add(
                        linkTo(methodOn(PersonController.class).findById(per.getKey())).withSelfRel()
                        )
                );

        final PagedModel<?> pagedModel = assembler.toModel(persons);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    //@CrossOrigin(origins = {"http://localhost:8080", "http://www.vferneda.com"})
    @GetMapping(value = "/{id}",
            produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO findById(@PathVariable("id") Long id) {
        final PersonVO personVO = this.personService.findById(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO create(@RequestBody PersonVO person) {
        final PersonVO personVO = personService.create(person);
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO update(@RequestBody PersonVO person) {
        final PersonVO personVO = personService.update(person);
        personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
        return personVO;
    }

    @PatchMapping(value = "/{id}",
            produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO disabledPerson(@PathVariable("id") Long id) {
        final PersonVO personVO = this.personService.disabledPerson(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.personService.delete(id));
    }

}
