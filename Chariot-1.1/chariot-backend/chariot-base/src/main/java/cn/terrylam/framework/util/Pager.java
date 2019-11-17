package cn.terrylam.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 *
 * @param <T>
 */
public class Pager<T> {

    private Integer curPage = 1;
    private Integer pageSize = 10;
    private Integer totalRow;
    private Integer totalPage;
    private Integer startIndex;
    private List<T> result = new ArrayList<>();
    private Boolean isMore;

    public Pager() {
    }

    public Pager(Integer curPage, Integer pageSize, Integer totalRow) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = (this.totalRow + this.pageSize - 1) / this.pageSize;
        this.startIndex = (this.curPage - 1) * this.pageSize;
        this.isMore = this.curPage < this.totalPage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Boolean getMore() {
        return isMore;
    }

    public void setMore(Boolean more) {
        isMore = more;
    }
}
