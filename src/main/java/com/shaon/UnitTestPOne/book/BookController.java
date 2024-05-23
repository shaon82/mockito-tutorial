package com.shaon.UnitTestPOne.book;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/get-list")
    public List<Book> getList() {
        return bookRepository.findAll();
    }

    @GetMapping("/find-by/{id}")
    public Book findById(@PathVariable Long id) {
        return bookRepository.findById(id).get();
    }

    @PostMapping("/save/book")
    public Book saveBook(@RequestBody Book book){
        Book book1 = bookRepository.save(book);
        return book1;
    }
}
