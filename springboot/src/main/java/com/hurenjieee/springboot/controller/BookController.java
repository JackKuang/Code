package com.hurenjieee.springboot.controller;


import com.hurenjieee.springboot.entity.Book;
import com.hurenjieee.springboot.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 书籍 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @GetMapping("/list")
    public List<Book> listBooks() {
        return bookService.list();
    }
}
