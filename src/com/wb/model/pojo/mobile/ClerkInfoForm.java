package com.wb.model.pojo.mobile;

public class ClerkInfoForm
{
    private String partId;
    
    private String clerkName;
    
    private String clerkTel;
    
    private Integer clerkState;
    
    private Integer page;
    
    private Integer rows;
    
    public String getClerkName()
    {
        return clerkName;
    }
    
    public void setClerkName(String clerkName)
    {
        this.clerkName = clerkName;
    }
    
    public String getClerkTel()
    {
        return clerkTel;
    }
    
    public void setClerkTel(String clerkTel)
    {
        this.clerkTel = clerkTel;
    }
    
    public Integer getClerkState()
    {
        return clerkState;
    }
    
    public void setClerkState(Integer clerkState)
    {
        this.clerkState = clerkState;
    }
    
    public Integer getPage()
    {
        return page;
    }
    
    public void setPage(Integer page)
    {
        this.page = page;
    }
    
    public Integer getRows()
    {
        return rows;
    }
    
    public void setRows(Integer rows)
    {
        this.rows = rows;
    }
    
    /**
     * 获取 partId
     * 
     * @return 返回 partId
     */
    public String getPartId()
    {
        return partId;
    }
    
    /**
     * 设置 partId
     * 
     * @param 对partId进行赋值
     */
    public void setPartId(String partId)
    {
        this.partId = partId;
    }

}
