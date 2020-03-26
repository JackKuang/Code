package com.hurenjieee.springboot.controller;


import com.hurenjieee.springboot.entity.Author;
import com.hurenjieee.springboot.entity.Book;
import com.hurenjieee.springboot.service.IAuthorService;
import com.hurenjieee.springboot.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 作者 前端控制器
 * </p>
 *
 * @author Jack
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    IAuthorService authorService;

    @GetMapping("/list")
    public List<Author> listBooks() {
        return authorService.list();
    }
}
