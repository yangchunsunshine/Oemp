package com.wb.component.computer.billManage.util;


/**
 * RespPageDto 分页响应参数基类
 * Created by wyf on 2015/8/29.
 */
@SuppressWarnings("serial")
public class RespPageDto extends RespDto {
    //total number of records for the query
    //总记录数
    private Long records;
    //当前页码数 current page of the query
	private Integer page; 
	//总页数  total pages for the query
	private Integer total;   
    //每页请求条数
	@Deprecated
	private Integer pageSize;
	
    static RespPageDto getSuccess(boolean success, Object data) {
        RespPageDto respPageDto = new RespPageDto();
        respPageDto.setSuccess(success);
        respPageDto.setRows(data);
        return respPageDto;
    }
    
    public static  RespPageDto getSuccess(Object data, ReqPageDto pageDto) {
        RespPageDto respPageDto = new RespPageDto();
        respPageDto.setSuccess(true);
        respPageDto.setPage(pageDto.getPage());
        respPageDto.setRows(data);
        respPageDto.setRecords(pageDto.getTotalRecords());
        respPageDto.setPageSize(pageDto.getRows());
        return respPageDto;
    }

	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
        if (getRecords() % getPageSize() == 0) {
            total = (int) (getRecords() / getPageSize());
        } else {
            total = (int) (getRecords() / getPageSize() + 1);
        }
        return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
