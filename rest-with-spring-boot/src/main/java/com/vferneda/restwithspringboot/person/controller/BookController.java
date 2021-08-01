package com.vferneda.restwithspringboot.person.controller;

import com.vferneda.restwithspringboot.person.data.vo.v1.BookVO;
import com.vferneda.restwithspringboot.person.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = "BookEndpoint")
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Find all books")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<BookVO> findAll() {
        final List<BookVO> books = bookService.findAll();
        books.stream()
                .forEach(boo -> boo.add(
                        linkTo(methodOn(BookController.class).findById(boo.getKey())).withSelfRel()
                        )
                );
        return books;
    }

    @ApiOperation(value = "Find a specific book by your ID")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO findById(@PathVariable("id") Long id) {
        final BookVO bookVO = bookService.findById(id);
        bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Create a new book")
    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO create(@RequestBody BookVO book) {
        final BookVO bookVO = bookService.create(book);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Update a specific book")
    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO update(@RequestBody BookVO book) {
        final BookVO bookVO = bookService.update(book);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    @ApiOperation(value = "Delete a specific book by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }

}