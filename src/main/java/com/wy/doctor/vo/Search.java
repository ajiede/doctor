package com.wy.doctor.vo;

import lombok.Data;

@Data
public class Search {

    private int currentPage;
    private int pageSize;
    // 总条数
    private int total;
    private String sort;
    private String direction;
    private String keyword;

    public void initSearch() {
        if (this.getCurrentPage() == 0) {
            this.setCurrentPage(1);
        }
        if (this.getPageSize() == 0) {
            this.setPageSize(10);
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
