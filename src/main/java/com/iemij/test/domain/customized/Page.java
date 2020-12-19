package com.iemij.test.domain.customized;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class Page<T> {

    public static final Page<?> DEFAULT = new Page<Object>();

    private int pageNo = 1;
    private int pageSize = 20;
    private int totalCount;
    private List<T> result;
    private String orderBy;
    private String order;
    /*private Integer offset;
    private Map otherResult;*/

    public Page() {

    }

    public Page(String orderBy) {
        this.orderBy = orderBy;
    }

    public Page(String orderBy, String order) {
        this.orderBy = orderBy;
        this.order = order;
    }

    public Page(String orderBy, String order, int pageSize) {
        this.orderBy = orderBy;
        this.order = order;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        if (totalCount < 0) {
            return -1;
        }

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
