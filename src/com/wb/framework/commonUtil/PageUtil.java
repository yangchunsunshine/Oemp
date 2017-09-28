package com.wb.framework.commonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 分页支持对象,包含当前页面数据和相关信息 如：页面总数、起始页号、前后页等信息 注：页号起始位置为 1、数据起始位置为 0
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressWarnings("rawtypes")
public class PageUtil
{
    private Adapter adapter = new Adapter();
    
    private PageInfo pageInfo = new PageInfo();
    
    private int pageIndex = pageInfo.getPage();// 当前页号
    
    private int pageSize = pageInfo.getRows();// 每页的记录数
    
    private int totalCount = 0;// 总记录数
    
    private int totalPageCount = 0;// 总页数
    
    private List records = null;// 记录集合
    
    public static class Adapter
    {
        
        /**
         * 数据
         */
        private String data = "rows";
        
        /**
         * 当前页
         */
        private String currPage = "page";
        
        /**
         * 总页数
         */
        private String totalPages = "total";
        
        /**
         * 总记录数
         */
        private String totalRecords = "records";
        
        /**
         * 获取 dataName
         * 
         * @return 返回 dataName
         */
        public String getDataName()
        {
            return data;
        }
        
        /**
         * 设置 dataName
         * 
         * @param 对dataName进行赋值
         */
        public void setDataName(String data)
        {
            this.data = data;
        }
        
        /**
         * 获取 currPage
         * 
         * @return 返回 currPage
         */
        public String getCurrPage()
        {
            return currPage;
        }
        
        /**
         * 设置 currPage
         * 
         * @param 对currPage进行赋值
         */
        public void setCurrPage(String currPage)
        {
            this.currPage = currPage;
        }
        
        /**
         * 获取 totalPages
         * 
         * @return 返回 totalPages
         */
        public String getTotalPages()
        {
            return totalPages;
        }
        
        /**
         * 设置 totalPages
         * 
         * @param 对totalPages进行赋值
         */
        public void setTotalPages(String totalPages)
        {
            this.totalPages = totalPages;
        }
        
        /**
         * 获取 totalRecords
         * 
         * @return 返回 totalRecords
         */
        public String getTotalRecords()
        {
            return totalRecords;
        }
        
        /**
         * 设置 totalRecords
         * 
         * @param 对totalRecords进行赋值
         */
        public void setTotalRecords(String totalRecords)
        {
            this.totalRecords = totalRecords;
        }
    }
    
    public static class PageInfo
    {
        
        /**
         * <默认构造函数>
         */
        public PageInfo()
        {
            super();
        }
        
        /**
         * <默认构造函数>
         */
        public PageInfo(int page, int rows)
        {
            super();
            this.page = page;
            this.rows = rows;
        }
        
        /**
         * <默认构造函数>
         */
        public PageInfo(int page, int rows, String sidx, String sord)
        {
            super();
            this.page = page;
            this.rows = rows;
            this.sidx = sidx;
            this.sord = sord;
        }
        
        /**
         * 第几页
         */
        private int page;
        
        /**
         * 每页多少条
         */
        private int rows;
        
        /**
         * 排序
         */
        private String sidx;
        
        /**
         * 排序规则
         */
        private String sord;
        
        /**
         * 获取 page
         * 
         * @return 返回 page
         */
        public int getPage()
        {
            return page;
        }
        
        /**
         * 设置 page
         * 
         * @param 对page进行赋值
         */
        public void setPage(int page)
        {
            this.page = page;
        }
        
        /**
         * 获取 rows
         * 
         * @return 返回 rows
         */
        public int getRows()
        {
            return rows;
        }
        
        /**
         * 设置 rows
         * 
         * @param 对rows进行赋值
         */
        public void setRows(int rows)
        {
            this.rows = rows;
        }
        
        /**
         * 获取 sidx
         * 
         * @return 返回 sidx
         */
        public String getSidx()
        {
            return sidx;
        }
        
        /**
         * 设置 sidx
         * 
         * @param 对sidx进行赋值
         */
        public void setSidx(String sidx)
        {
            this.sidx = sidx;
        }
        
        /**
         * 获取 sord
         * 
         * @return 返回 sord
         */
        public String getSord()
        {
            return sord;
        }
        
        /**
         * 设置 sord
         * 
         * @param 对sord进行赋值
         */
        public void setSord(String sord)
        {
            this.sord = sord;
        }
    }
    
    public void setAdapter(Adapter adapter)
    {
        this.adapter = adapter;
    }
    
    public void setPageInfo(PageInfo pageInfo)
    {
        this.pageInfo = pageInfo;
    }

    /**
     * 
     * <默认构造函数>
     */
    public PageUtil(List records, int totalCount, int pageIndex, int pageSize)
    {
        this.totalPageCount = totalCount / pageSize;
        this.totalPageCount = (totalCount > this.totalPageCount * pageSize) ? this.totalPageCount + 1 : totalPageCount;
        this.setRecords(records);
        this.setTotalCount(totalCount);
        this.setPageIndex(pageIndex);
        this.setPageSize(pageSize);
    }

    /**
     * 
     * 返回构建好的结果集
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> initResult()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(adapter.getCurrPage(), this.getPageIndex());
        map.put(adapter.getTotalPages(), this.getTotalPageCount());
        map.put(adapter.getTotalRecords(), this.getTotalCount());
        map.put(adapter.getDataName(), this.getRecords());
        return map;
    }
    
    /**
     * 获取总页数
     * 
     * @return int 总页数
     */
    public int getTotalPageCount()
    {
        return this.totalPageCount;
    }

    /**
     * 获取起始查询行数
     * 
     * @param pageIndex 从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据的位置
     */
    public static int getStartOfPage(int pageIndex, int pageSize)
    {
        return (pageIndex - 1 > 0 ? (pageIndex - 1) * pageSize : 0);
    }
    
    /**
     * 返回下一页 页号 如果没有后一页 则返回最后一页页号
     * 
     * @return int下一页 页号
     */
    public int getNextIndex()
    {
        return getPageIndex() - 1 < 1 ? 1 : getPageIndex() - 1;
    }
    
    /**
     * 获取上一页页号如果没有上一页 则返回当前页号(也就是 第一页)
     * 
     * @return int 上一页页号
     */
    public int getPreviousIndex()
    {
        return getPageIndex() + 1 > getTotalPageCount() ? getTotalPageCount() : getPageIndex() + 1;
    }
    
    // -------------------------------------------------------------------------
    // setter and getter
    // -------------------------------------------------------------------------
    /**
     * 获取每页记录数
     * 
     * @return int 每页记录数
     */
    public int getPageSize()
    {
        return pageSize;
    }
    
    /**
     * 设置每页记录数
     * 
     * @param pageSize 每页记录数
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    /**
     * 获取当前页面结果集
     * 
     * @return List 集合
     */
    public List getRecords()
    {
        return records;
    }
    
    /**
     * 设置当前页面结果集
     * 
     * @param records List
     */
    public void setRecords(List records)
    {
        this.records = records;
    }
    
    /**
     * 获取当前页面号码
     * 
     * @return Int 当前页号
     */
    public int getPageIndex()
    {
        return this.pageIndex;
    }
    
    /**
     * 设置当前页号
     * 
     * @param pageIndex int 页号
     */
    public void setPageIndex(int pageIndex)
    {
        if (pageIndex <= 1)
        {
            this.pageIndex = 1;
        }
        else
        {
            this.pageIndex = getTotalPageCount() > pageIndex ? pageIndex : getTotalPageCount();
        }
    }
    
    /**
     * 获取记录总数
     * 
     * @return int 记录总数
     */
    public int getTotalCount()
    {
        return totalCount;
    }
    
    /**
     * 设置记录总数
     * 
     * @param totalCount int 记录总数
     */
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
}
