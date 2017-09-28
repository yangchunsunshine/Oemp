package com.wb.model.pojo.computer;

import java.util.Date;

public class SuperintendentQueryForm
{
    
    // 管理人员ID
    private Integer mngId;
    
    // 记账人员姓名
    private String clerkName;
    
    // 记账人员电话
    private String clerkTel;
    
    // 记账人员在线状态
    private Integer clerkState;
    
    // 记账期间
    private Date curDate;
    
    // 页数
    private Integer page;
    
    // 每页记录数
    private Integer rows;
    
    // 所有记录数
    private Long records;
    
    // 开始行数
    @SuppressWarnings("unused")
    private Integer startRow;
    
    // 结束行数
    @SuppressWarnings("unused")
    private Integer endRow;
    
    // 关联标识
    private Integer isAccept;
    
    // 额外属性3个
    private Integer arg1;
    
    private Integer arg2;
    
    private Integer arg3;
    
    private String str1;
    
    private String str2;
    
    private String str3;
    
    private String partId;
    
    // 统计信息
    private TrendDataDto trendData;
    
    // add by zhouww date:2016-10-14 desc:员工本人进入组织架构页面查询的应该是员工本人的数据查询的时候需要添加员工id进行查询，如果是管理员查询管理员下所有员工
    
    private String userMemberId ;
    
    public String getUserMemberId() {
		return userMemberId;
	}

	public void setUserMemberId(String userMemberId) {
		this.userMemberId = userMemberId;
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

    public Integer getMngId()
    {
        return mngId;
    }
    
    public void setMngId(Integer mngId)
    {
        this.mngId = mngId;
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
    
    public Integer getStartRow()
    {
        return (page - 1) * rows;
    }
    
    public void setStartRow(Integer startRow)
    {
        this.startRow = startRow;
    }
    
    public Integer getEndRow()
    {
        return page * rows;
    }
    
    public void setEndRow(Integer endRow)
    {
        this.endRow = endRow;
    }
    
    public Long getRecords()
    {
        return records;
    }
    
    public void setRecords(Long records)
    {
        this.records = records;
    }
    
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
    
    public Date getCurDate()
    {
        return curDate;
    }
    
    public void setCurDate(Date curDate)
    {
        this.curDate = curDate;
    }
    
    public Integer getIsAccept()
    {
        return isAccept;
    }
    
    public void setIsAccept(Integer isAccept)
    {
        this.isAccept = isAccept;
    }
    
    public Integer getArg1()
    {
        return arg1;
    }
    
    public void setArg1(Integer arg1)
    {
        this.arg1 = arg1;
    }
    
    public Integer getArg2()
    {
        return arg2;
    }
    
    public void setArg2(Integer arg2)
    {
        this.arg2 = arg2;
    }
    
    public Integer getArg3()
    {
        return arg3;
    }
    
    public void setArg3(Integer arg3)
    {
        this.arg3 = arg3;
    }
    
    public String getStr1()
    {
        return str1;
    }
    
    public void setStr1(String str1)
    {
        this.str1 = str1;
    }
    
    public String getStr2()
    {
        return str2;
    }
    
    public void setStr2(String str2)
    {
        this.str2 = str2;
    }
    
    public String getStr3()
    {
        return str3;
    }
    
    public void setStr3(String str3)
    {
        this.str3 = str3;
    }
    
    public TrendDataDto getTrendData()
    {
        return trendData;
    }
    
    public void setTrendData(TrendDataDto trendData)
    {
        this.trendData = trendData;
        this.records = (Long)trendData.getCount();
    }
}
