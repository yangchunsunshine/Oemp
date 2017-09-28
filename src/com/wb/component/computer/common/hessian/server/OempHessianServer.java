/*
 * 文 件 名:  OempHessianServer.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.common.hessian.server;

import org.springframework.web.multipart.MultipartFile;

import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.cusManage.MntCustomContract;
import com.wb.model.pojo.computer.CustomPayVo;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface OempHessianServer
{
    MntMember getMngInfo(String tel);
    
    MntMember getMngInfoByContract(String tel);
    
    int createCustomContractInfo(String bizId, String orgCode, String orgName, String telphone, String feeMonth, String bookFee, String accBegin, String accEnd, String contractType);
    
    int createCustomContractInfo(MntCustomContract entity, MultipartFile file, String userId);
    //支付前校验是否已交费
    String beforePayValidate(String str);
    //生成缴费记录
    boolean createMntExpenseDetail(String str);

    MntMember getPayInfo(String memberId);

}
