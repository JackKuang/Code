package com.hurenjieee.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.hurenjieee.springboot.entity.Book;
import com.hurenjieee.springboot.mapper.BookMapper;
import com.hurenjieee.springboot.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 书籍 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2020-03-26
 */
@Service
@DS("mysql_1")
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
