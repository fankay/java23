package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Movie;
import com.kaishengit.mapper.MovieMapper;
import com.kaishengit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fankay on 2017/7/14.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> findAll() {
        return movieMapper.findAll();
    }

    @Override
    public void save(Movie movie) {
        movieMapper.save(movie);
    }

    @Override
    public void delById(Integer id) {
        movieMapper.delById(id);
    }

    @Override
    public Movie findById(Integer id) {
        return movieMapper.findById(id);
    }

    @Override
    public void edit(Movie movie) {
        movieMapper.update(movie);
    }

    @Override
    public PageInfo<Movie> pageByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        List<Movie> movieList = movieMapper.findAll();
        return new PageInfo<>(movieList);
    }

    @Override
    public PageInfo<Movie> findByParam(String title, String daoyan,Float min,Float max, Integer pageNo) {
        Map<String,Object> searchParam = new HashMap<>();
        searchParam.put("title",title);
        searchParam.put("daoyan",daoyan);
        searchParam.put("min",min);
        searchParam.put("max",max);
        searchParam.put("pageNum",pageNo);
        searchParam.put("pageSize",10);
        List<Movie> movieList = movieMapper.findByParam(searchParam);
        return new PageInfo<>(movieList);
    }
}
