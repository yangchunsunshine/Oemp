package com.wb.component.computer.common.job;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wb.component.computer.customerManage.service.ICustomerManageService;

/**
 * 
 * 通用JOB
 * 
 * @author 郝洋
 * @version [版本号, 2016-5-9]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
public class CommonJob
{
    
    private static final Logger log = Logger.getLogger(CommonJob.class);
    

    /**
     * 客户管理Service层实例
     */
    @Autowired
    @Qualifier("customerManageService")
    private ICustomerManageService customerManageService;
    
    /**
     * 
     * 催费JOB
     * 
     * @see [类、类#方法、类#成员]
     */
    @Scheduled(cron = "0 0 11 1 * ?")
    public void feeNoticeCycle()
    {
        List<Map<String, Object>> orgInfoList = customerManageService.getAutoSendfeeOrgInfo();
        for (Map<String, Object> orgInfoMap : orgInfoList)
        {
            String orgId = orgInfoMap.get("orgId").toString();
            String orgName = orgInfoMap.get("orgName").toString();
            int mngId = (Integer)orgInfoMap.get("orgName");
            Map<String, Object> feeResult = customerManageService.saveFeeNoticeInfoByOrgId(mngId, orgId, orgName);
            if (feeResult.size() > 0)
            {
                log.debug("[orgId:" + feeResult.get("orgId"));
                log.debug("|cusName:" + feeResult.get("cusName"));
                log.debug("|tel:" + feeResult.get("tel"));
                log.debug("|statusCode:" + feeResult.get("statusCode"));
                log.debug("|mess:" + feeResult.get("mess") + "]");
            }
        }
    }
}