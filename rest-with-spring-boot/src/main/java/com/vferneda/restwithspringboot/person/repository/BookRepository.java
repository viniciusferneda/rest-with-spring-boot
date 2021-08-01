package com.vferneda.restwithspringboot.person.repository;

import com.vferneda.restwithspringboot.person.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}