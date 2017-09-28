package com.wb.component.computer.billManage.util;
/**
 * ReqPageDto分页参数基类
 * Created by wyf on 2015/8/29.
 */
public class ReqPageDto
{
	//每页查询条数
    private Integer rows;
    //查询第几页
    private Integer page;
    //limit的第一个参数
    @Deprecated
    private Integer start;
    @Deprecated
    //分页总记录数接收参数
    private Long totalRecords;
    
    public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getStart()
    {
        start = (getPage()-1)*getRows();
//        start = start<0?0:start;
        return start;
    }
    
    public void setStart(Integer start)
    {
        this.start = start;
    }
    
    public Integer getRows()
    {
        return null == rows ? 0 : rows;
    }
    
    public void setRows(Integer rows)
    {
        this.rows = rows;
    }
    
    public Integer getPage()
    {
        return null == page ? 0 : page;
    }
    
    public void setPage(Integer page)
    {
        this.page = page;
    }
    
}
