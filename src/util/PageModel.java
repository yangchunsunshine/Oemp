package util;

import java.util.List;

/***
 * 封装分页信息
 * */
public class PageModel {
	//结果集
	private List list;
	//查询记录数
	private int totalRecord;
	//当前第几页
	private int pageNum;
	//每页多少条数据
	private int pageSize;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	//获得总页数
	public int getTotalPages(){
		
		return (totalRecord+pageSize-1)/pageSize ;
	}
	//取得首页
	public int getTopPageNo(){
		return 1;
	}
	//取得尾页
	public int getBottomPageNo(){
		return getTotalPages();
	}
	//取得上一页
	public int getPreviousPageNo(){
		if(this.pageNum<=1){
			return 1;
		}
		return pageNum-1;
	}
	
	//取得下一页
	public int getNextPageNo(){
		if(this.pageNum>=getBottomPageNo()){
			return getBottomPageNo();
		}
		return pageNum+1;
	}
	
}
