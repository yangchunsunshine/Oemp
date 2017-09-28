/**  
 * @Title: Certificate.java
 * @Package com.ctj.component
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 付天有 
 * @date 2014-7-22 下午01:14:14
 * @version V1.0  
 */
package com.wb.component.mobile.common.certificate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.wb.framework.commonUtil.encrypt.MD5;
import com.wb.framework.nestLogger.NestLogger;

/**
 * @ClassName: Certificate
 * @Description: TODO http通信认证证书
 * @author 付天有
 * @date 2014-7-22 下午01:14:14
 */
public class Certificate implements Serializable
{
    
    private String sessionID;
    
    private Integer OrgID;
    
    private Integer MemberID;
    
    private String imei;
    
    private String signture;
    
    public Integer getOrgID()
    {
        return OrgID;
    }
    
    public void setOrgID(Integer orgID)
    {
        OrgID = orgID;
    }
    
    public Integer getMemberID()
    {
        return MemberID;
    }
    
    public void setMemberID(Integer memberID)
    {
        MemberID = memberID;
    }
    
    public String getSessionID()
    {
        return sessionID;
    }
    
    public void setSessionID(String sessionID)
    {
        this.sessionID = sessionID;
    }
    
    public String getImei()
    {
        return imei;
    }
    
    public void setImei(String imei)
    {
        this.imei = imei;
    }
    
    public String getSignture()
    {
        return signture;
    }
    
    public void setSignture(String signture)
    {
        this.signture = signture;
    }
    
    public String signture()
    {
        MD5 md5 = new MD5();
        this.signture = md5.encrypt(toByteArray());
        return signture;
    }
    
    public boolean checkSignture()
    {
        MD5 md5 = new MD5();
        String temp = signture;
        setSignture(null);
        String newSignture = md5.encrypt(toByteArray());
        if (temp.equals(newSignture))
        {
            setSignture(temp);
            return true;
        }
        return false;
    }
    
    public byte[] toByteArray()
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream out = new ObjectOutputStream(buffer);
            out.writeObject(this);
            return buffer.toByteArray();
        }
        catch (IOException e)
        {
            NestLogger.showException(e);
        }
        
        return null;
    }
}
