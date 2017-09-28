package com.wb.model.pojo.computer;

/**
 * @ClassName: PageProxyDto
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 王磊
 * @date 2015-6-18 下午01:53:21
 */
public class PageProxyDto
{
    private int total;
    
    private int page;
    
    private Long records;
    
    private Object rows;
    
    private TrendDataDto trendData;
    
    public PageProxyDto(Integer page, Long records, Integer size, Object rows)
    {
        this.total = (int)((records == null ? 0 : records) - 1) / size + 1;
        this.page = page;
        this.records = records;
        this.rows = rows;
    }
    
    public PageProxyDto(Integer page, Long records, Integer size, Object rows, TrendDataDto trendData)
    {
        this.total = (int)((records == null ? 0 : records) - 1) / size + 1;
        this.page = page;
        this.records = records;
        this.rows = rows;
        this.trendData = trendData;
    }
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public int getPage()
    {
        return page;
    }
    
    public void setPage(int page)
    {
        this.page = page;
    }
    
    public Long getRecords()
    {
        return records;
    }
    
    public void setRecords(Long records)
    {
        this.records = records;
    }
    
    public Object getRows()
    {
        return rows;
    }
    
    public void setRows(Object rows)
    {
        this.rows = rows;
    }
    
    public TrendDataDto getTrendData()
    {
        return trendData;
    }
    
    public void setTrendData(TrendDataDto trendData)
    {
        this.trendData = trendData;
    }
}
