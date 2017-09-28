package com.wb.model.pojo.computer;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EnterpriseQueryForm
{
    
    // 创建人
    private Integer creator;
    
    // 企业ID
    private String orgId;
    
    // 企业名称
    private String orgName;
    
    // 企业编码
    private String orgCode;
    
    // 企业用户姓名
    private String fbUserName;
    
    // 企业用户电话
    private String userTelphone;
    
    // 企业执照注册号
    private String lisence;
    
    // 企业认证状态
    private Integer authState;
    
    // 默认是否可用状态
    private Integer isDeleted;
    
    // 全部状态标识
    private Integer isAllSelect;
    
    // 起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    
    // 结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    
    private String onDate;
    
    private String offDate;
    
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
    
    // 排序列
    private String sidx;
    
    // 排序方式
    private String sord;
    
    // 结账进度
    private Double settleProcess;
    
    // 报税进度
    private Double taxProcess;
    
    // 统计信息
    private TrendDataDto trendData;
    
    // 额外属性3个
    private Integer arg1;
    
    private Integer arg2;
    
    private Integer arg3;
    
    public String getOrgId()
    {
        return orgId;
    }
    
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }
    
    public String getOrgName()
    {
        return orgName;
    }
    
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }
    
    public String getFbUserName()
    {
        return fbUserName;
    }
    
    public void setFbUserName(String fbUserName)
    {
        this.fbUserName = fbUserName;
    }
    
    public String getUserTelphone()
    {
        return userTelphone;
    }
    
    public void setUserTelphone(String userTelphone)
    {
        this.userTelphone = userTelphone;
    }
    
    public String getLisence()
    {
        return lisence;
    }
    
    public void setLisence(String lisence)
    {
        this.lisence = lisence;
    }
    
    public Integer getAuthState()
    {
        return authState;
    }
    
    public void setAuthState(Integer authState)
    {
        this.authState = authState;
    }
    
    public Integer getIsDeleted()
    {
        return isDeleted;
    }
    
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }
    
    public Integer getIsAllSelect()
    {
        return isAllSelect;
    }
    
    public void setIsAllSelect(Integer isAllSelect)
    {
        // if(isAllSelect.equals(authState)){
        // this.isAllSelect = 0;
        // }else{
        // this.isAllSelect = 1;
        // }
        this.isAllSelect = isAllSelect;
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
    
    public String getOrgCode()
    {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }
    
    public Integer getCreator()
    {
        return creator;
    }
    
    public void setCreator(Integer creator)
    {
        this.creator = creator;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    
    public Date getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
    
    public String getSidx()
    {
        return sidx;
    }
    
    public void setSidx(String sidx)
    {
        this.sidx = sidx;
    }
    
    public String getSord()
    {
        return sord;
    }
    
    public void setSord(String sord)
    {
        this.sord = sord;
    }
    
    public Double getSettleProcess()
    {
        if (settleProcess > 1)
        {
            return 1.0;
        }
        else if (settleProcess < 0)
        {
            return 0.0;
        }
        else
        {
            return settleProcess;
        }
    }
    
    public void setSettleProcess(Double settleProcess)
    {
        this.settleProcess = settleProcess;
    }
    
    public Double getTaxProcess()
    {
        if (taxProcess > 1)
        {
            return 1.0;
        }
        else if (taxProcess < 0)
        {
            return 0.0;
        }
        else
        {
            return taxProcess;
        }
    }
    
    public void setTaxProcess(Double taxProcess)
    {
        this.taxProcess = taxProcess;
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
    
    public TrendDataDto getTrendData()
    {
        return trendData;
    }
    
    public void setTrendData(TrendDataDto trendData)
    {
        this.trendData = trendData;
        this.records = (Long)trendData.getCount();
    }
    
    public String getOnDate()
    {
        return onDate;
    }
    
    public void setOnDate(String onDate)
    {
        this.onDate = onDate;
    }
    
    public String getOffDate()
    {
        return offDate;
    }
    
    public void setOffDate(String offDate)
    {
        this.offDate = offDate;
    }
}
