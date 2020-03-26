package com.hurenjieee.springboot.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.hurenjieee.springboot.entity.Author;
import com.hurenjieee.springboot.mapper.AuthorMapper;
import com.hurenjieee.springboot.service.IAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作者 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2020-03-26
 */
@Service
@DS("mysql_2")
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

}
