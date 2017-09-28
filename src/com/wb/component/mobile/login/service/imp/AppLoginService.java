/*
 * 文 件 名:  LoginService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-4-12
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.mobile.login.service.imp;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wb.component.mobile.login.service.IAppLoginService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.mobile.BizAppDownLoad;

/**
 * 登陆接口
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("appLoginService")
public class AppLoginService extends BaseDao implements IAppLoginService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(AppLoginService.class);
    
    /**
     * 重载方法
     * 
     * @param orgId
     * @return
     */
    @Override
    public String getLastVersion()
    {
        String version = null;
        try
        {
            version = (String)sqlMapClient.queryForObject("getLastVersion");
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("获取版本号:", e, true));
            return "error";
        }
        return version;
    }
    
    /**
     * 重载方法
     * 
     * @return
     */
    @Override
    public BizAppDownLoad downLoadApp()
    {
        BizAppDownLoad appVersion = null;
        try
        {
            appVersion = (BizAppDownLoad)sqlMapClient.queryForObject("downLoadOempApk");
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return appVersion;
    }

}
