package com.kaishengit.dto;

import java.util.List;

/**
 * DataTables结果转换类
 * Created by fankay on 2017/7/18.
 */
public class DataTableResult<T> {

    private String draw;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<T> data;

    public DataTableResult(String draw, Long recordsTotal, Long recordsFiltered, List<T> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
