package com.tfzhang.quiz.model;


import java.util.List;

public class PageBean{
    private int total;
    private List<User> rows;

    public PageBean() {
    }

    public PageBean(int total, List<User> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<User> getRows() {
        return rows;
    }

    public void setRows(List<User> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}


