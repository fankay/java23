package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Movie;

import java.util.List;

/**
 * Created by fankay on 2017/7/14.
 */
public interface MovieService {

    List<Movie> findAll();

    void save(Movie movie);

    void delById(Integer id);

    Movie findById(Integer id);

    void edit(Movie movie);

    PageInfo<Movie> pageByPageNo(Integer pageNo);

    PageInfo<Movie> findByParam(String title, String daoyan,Float min,Float max, Integer pageNo);
}
