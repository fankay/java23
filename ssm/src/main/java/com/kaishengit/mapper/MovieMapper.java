package com.kaishengit.mapper;

import com.kaishengit.entity.Movie;

import java.util.List;
import java.util.Map;

/**
 * Created by fankay on 2017/7/14.
 */
public interface MovieMapper {

    List<Movie> findAll();

    void save(Movie movie);

    void delById(Integer id);

    Movie findById(Integer id);

    void update(Movie movie);

    List<Movie> findByParam(Map<String, Object> searchParam);
}
