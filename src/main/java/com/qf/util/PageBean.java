package com.qf.util;

import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 17:15
 */
public class PageBean<T> {
    private int pageIndex;
    private int pageSize;
    private int pageCount;
    private long totalCount;
    private List<T> pageData;
    private int start;
    private int end;

    public PageBean() {
    }

    public PageBean(int pageIndex, int pageSize, long totalCount, List<T> pageData) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageData = pageData;
        if(totalCount%pageSize==0) {
            this.pageCount = (int)totalCount/pageSize;
        }else{
            this.pageCount = (int)(totalCount/pageSize)+1;
        }
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount=pageCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    public void setStartAndEnd(){
        this.start=this.pageIndex-3;
        this.end = this.pageIndex+3;
        if(start<=0){
            this.start=1;
        }
        if(end>pageCount){
            this.end=pageCount;
        }
    }
}
