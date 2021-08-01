package com.vferneda.restwithspringboot.person.services;

import com.vferneda.restwithspringboot.exception.ResourceNotFoundException;
import com.vferneda.restwithspringboot.person.converter.DozerConverter;
import com.vferneda.restwithspringboot.person.data.model.Book;
import com.vferneda.restwithspringboot.person.data.vo.v1.BookVO;
import com.vferneda.restwithspringboot.person.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public BookVO create(BookVO book) {
        var entity = DozerConverter.parseObject(book, Book.class);
        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        return vo;
    }

    public List<BookVO> findAll() {
        return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
    }

    public BookVO findById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return DozerConverter.parseObject(entity, BookVO.class);
    }

    public BookVO update(BookVO book) {
        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
        return vo;
    }

    public void delete(Long id) {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}
