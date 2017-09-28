/*
 * 文 件 名:  OempAccHessain.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-6-27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.hessian.server;

import java.util.Map;

import com.wb.model.entity.computer.MntMember;
import com.wb.model.pojo.computer.CustomPayVo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  郝洋
 * @version  [版本号, 2016-6-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface OempAccHessian
{
    /**
     * 
     * 根据实体企业ID获取代帐公司信息(实体企业必须与代帐公司存在合同关系,且关系生效才有结果显示)
     * 
     * @param bookId
     * @return
     * @see [类、类#方法、类#成员]
     */
    MntMember findMngRecommendByOrgId(int orgID);
    
    Map<String, Boolean> getPayInfo(String orgId, String mngId);
    
    boolean customPay(int orgId, int userId, CustomPayVo vo);
    
    Map<String, Object> getMonthPayInfo(String orgId, String mngId, String year);
}
