package com.wb.component.computer.customerManage.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntOrgtax;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;
import com.wb.model.entity.computer.cusManage.MntCustomContract;
import com.wb.model.entity.computer.cusManage.MntCustomInfo;
import com.wb.model.entity.computer.cusManage.MntCustomTaxInfo;
import com.wb.model.pojo.computer.CustomFollowInfo;
import com.wb.model.pojo.computer.CustomInfoVo;
import com.wb.model.pojo.computer.CustomPayVo;
import com.wb.model.pojo.mobile.login.LoginVo;

/**
 * 客户管理Service层接口
 * 
 * @author 姓名 工号
 * @version [版本号, 2016-3-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 姓名 工号
 * @version [版本号, 2016-4-15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ICustomerManageService
{
    
    /**
     * 创建客户
     * 
     * @param vo 客户信息POJO类
     * @param userId 系统登录人
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    String createCustom(CustomInfoVo vo, String userId)
        throws Exception;
    
    /**
     * 编辑客户信息
     * 
     * @param vo 客户信息POJO类
     * @param cusId 客户编号
     * @param userId 系统登录人
     * @return int
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int updateCustom(CustomInfoVo vo, String cusId, String userId)
        throws Exception;
    
    /**
     * 验证公司名称是否重复
     * 
     * @param orgName 公司名称
     * @param cmd 判断是更新还是新增
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkOrgNameRepeat(String orgName, String cmd);
    
    /**
     * 验证公司编号是否重复
     * 
     * @param orgNum 公司名称
     * @param cmd 判断是更新还是新增
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkOrgNumRepeat(String orgNum, String cmd, String ownerId);
    
    /**
     * 添加税务信息
     * 
     * @param vo 客户信息POJO类
     * @param cusId 客户ID
     * @see [类、类#方法、类#成员]
     */
    void addTaxInfo(CustomInfoVo vo, String cusId);
    
    /**
     * ID编码生成器
     * 
     * @param userId 系统登录人
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String idCreater(String userId);
    
    /**
     * 属性名过滤器
     * 
     * @param fieldName 属性名
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean fieldNameFilter(String fieldName);
    
    /**
     * 文件上传方法
     * 
     * @param vo 客户信息POJO类
     * @param cusId 客户ID
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    void fileUpLoad(CustomInfoVo vo, String cusId)
        throws IOException;
    
    /**
     * 获取公司信息列表
     * 
     * @param cmd 查询方式（BY_EMP,BY_DEP）
     * @param id 查询ID
     * @param orgId 公司ID
     * @param companySearchName 编号或客户名称
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    PageUtil getCompanyInfo(String cmd, String conType, String disFlag, String id, int orgId, String companySearchName, PageInfo info);
    
    /**
     * 获取跟进信息
     * 
     * @param userId 系统登录人
     * @param cusId 客户ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    PageUtil getFollowInfo(String cusId, PageInfo info);
    
    /**
     * 获取公司名称
     * 
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String getCusInfo(String cusId);
    /**
     * 获取公司信息
     * 
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    MntCustomInfo getCustomInfo(String cusId);
    /**
     * 获取公司名称
     * 
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    BizOrganization getCustomName(String cusId);
    /**
     * 保存跟进信息
     * 
     * @param vo 客户跟进信息POJO类
     * @param userId 系统登录人
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean saveFollowInfo(CustomFollowInfo vo, String userId);
    
    /**
     * 删除跟进信息
     * 
     * @param followId 跟进ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean deleteFollowInfo(String followId);
    
    /**
     * 编辑跟进信息
     * 
     * @param followId 跟进ID
     * @param vo 客户跟进信息POJO类
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean updateFollowInfo(String followId, CustomFollowInfo vo);
    
    /**
     * 获取客户信息POJO类对象
     * 
     * @param cusId 客户ID
     * @return MntCostomInfo
     * @see [类、类#方法、类#成员]
     */
    CustomInfoVo getCustomInfoVo(String cusId);
    
    /**
     * 获取附件信息实体类
     * 
     * @param cusId 客户ID
     * @return MntCustomAttachment
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getAttachmentInfoVo(String cusId);
    
    /**
     * 获取税务信息实体类
     * 
     * @param cusId 客户ID
     * @return MntCustomTaxInfo
     * @see [类、类#方法、类#成员]
     */
    List<MntCustomTaxInfo> getTaxInfoVo(String cusId);
    
    /**
     * 根据文件ID删除单条文件信息记录
     * 
     * @param fileId 文件ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean deleteFileById(String fileId);
    
    /**
     * 附件下载
     * 
     * @param fileId 文件ID
     * @return ResponseEntity<byte[]>
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    ResponseEntity<byte[]> download(String fileId)
        throws IOException;
    
    /**
     * 创建公司信息
     * 
     * @param vo 客户信息POJO类
     * @param userId 系统登录人
     * @param cusId 客户ID
     * @see [类、类#方法、类#成员]
     */
    void createOffice(CustomInfoVo vo, String userId, String cusId);
    
    /**
     * 更新公司信息
     * 
     * @param vo 客户信息POJO类
     * @param userId 系统登录人
     * @param cusId 客户ID
     * @throws ParseException ParseException
     * @see [类、类#方法、类#成员]
     */
    void updateOffice(CustomInfoVo vo, String userId, String cusId)
        throws ParseException;
    
    /**
     * 获取缴费信息
     * 
     * @param cmd BY_DEP,BY_EMP
     * @param queryId 查询所用的ID orgId,memberId
     * @param mngOrgId 老板ID
     * @param orgId 公司ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    PageUtil getCusPayInfo(String cmd, String queryId, String mngOrgId, String orgId, String payYear, PageInfo info);
    
    /**
     * 获取合同列表信息
     * 
     * @param userId 系统当前登录人
     * @param cusId 客户ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    PageUtil getContractInfo(String cusId, PageInfo info);
    
    /**
     * 保存合同信息
     * 
     * @param entity 合同信息实体类
     * @param file 附件
     * @param userId 当前系统登录人
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    int saveContractInfo(MntCustomContract entity, MultipartFile file, String userId);
    
    /**
     * 删除合同信息
     * 
     * @param contractId 合同ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean deleteContractInfo(String contractId);
    
    /**
     * 更新合同信息
     * 
     * @param contractId 合同ID
     * @param file 合同附件
     * @param vo 合同信息实体类
     * @param userId 当前系统登录人
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    int updateContractInfo(int contractId, MultipartFile file, MntCustomContract vo, int userId);
    
    /**
     * 删除合同附件
     * 
     * @param contractId 合同ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean deleteContractFile(int contractId);
    
    /**
     * 下载合同附件
     * 
     * @param contractId 合同ID
     * @return ResponseEntity<byte[]>
     * @throws IOException IOException
     * @see [类、类#方法、类#成员]
     */
    ResponseEntity<byte[]> downloadContractFile(int contractId)
        throws IOException;
    
    /**
     * 获取员工下拉列表
     * 
     * @param orgId 公司ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getEmpList(int orgId);
    
    /**
     * 根据选中的部门获取该部门下的员工
     * 
     * @param depSelect 选中的部门
     * @param isAdmin 是否是管理员
     * @param orgId 公司ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getEmpByDep(String depSelect, boolean isAdmin, String orgId);
    
    /**
     * 获取账簿列表
     * 
     * @param dataStr cusId串
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getBookOrgList(String dataStr);
    
    /**
     * 保存派工信息
     * 
     * @param userId 当前登录人ID
     * @param userName 当前登录人姓名
     * @param disp 派工信息数据
     * @see [类、类#方法、类#成员]
     */
    void saveDispatching(int userId, String userName, JsonNode disp);
    
    /**
     * 检测派工信息重复
     * 
     * @param roleId 权限ID
     * @param memberId 部员ID
     * @param orgId 公司ID
     * @param bookId 账簿ID
     * @return char
     * @see [类、类#方法、类#成员]
     */
    char checkDispRepeat(int roleId, int memberId, int orgId, int bookId);
    
    /**
     * 派工列表查询
     * 
     * @param userId 当前登录人ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getDispList(int userId, int orgId);
    
    /**
     * 删除派工信息
     * 
     * @param mBookId 派工ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean delDisp(int mBookId);
    
    /**
     * 转派工
     * 
     * @param mBookId 派工ID
     * @param depId 部门ID
     * @param empId 员工ID
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int updateDisp(int mBookId, int depId, int empId);
    
    /**
     * 授权查看者
     * 
     * @param userId 当前登录人ID
     * @param userName 当前登录人姓名
     * @param tel 授权的手机号码
     * @param disp 授权信息
     * @return int
     * @see [类、类#方法、类#成员]
     */
    boolean saveGrantView(int userId, String userName, String tel, JsonNode disp);
    
    /**
     * 根据电话号查询员工ID
     * 
     * @param tel 祖册的手机号
     * @return Map<String,Object>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getMemberIdByTel(String tel);
    
    /**
     * 获取授权查看信息列表
     * 
     * @param userId 系统当前登录人ID
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getGrantViewList(int userId, int orgId);
    
    /**
     * 根据orgId获取orgName
     * 
     * @param orgId 公司ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getOrgCoTractByOrgId(String orgId);
    
    /**
     * 根据orgId 获取biz_member 的ID
     * 
     * @param orgId 公司ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
    String getBizMemberIdByOrgId(String orgId);
    
    MntMember getMemberInfoByCusMobile(String telphone);
    
    MntMember getMemberInfoByOrgID(int BookId);

    /**
     * 保存缴税信息
     * 
     * @param userId 当前系统登录人
     * @param vo 缴费VO
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean saveCustomPay(int orgId, int userId, CustomPayVo vo);
    
    /**
     * 获取报税列表
     * 
     * @param orgId 客户ID
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getTallyList(String orgId);
    
    /**
     * 根据orgId获取相应的报税信息
     * 
     * @param orgId 客户ID
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getTallyInfo(String orgId);
    
    /**
     * 获取本月报税状态
     * 
     * @param orgId
     * @return
     * @see [类、类#方法、类#成员]
     */
    boolean getThisMonthTallyFlag(String orgId);
    
    /**
     * 获取税务明细列表
     * 
     * @param orgId 客戶ID
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getTaxDetailList(String orgId);
    
    /**
     * 保存报税信息
     * 
     * @param vo 报税实体
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    int saveTally(MntOrgtax vo);
    
    /**
     * 验证当月是否报过税
     * 
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkTallyTaxDate(int orgId, String taxDate);
    
    /**
     * 获取创建账套所需要的信息
     * 
     * @param cusId 客户ID
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getCreateBookMess(String cusId);
    
    /**
     * 根据报税ID获取税务信息
     * 
     * @param taxId 报税ID
     * @return List<Map<String,Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getTaxInfoByTaxId(String taxId);
    
    /**
     * 更新短信发送时间
     * 
     * @param taxId 报税ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean updateTaxUpdateMessDate(String taxId);
    
    /**
     * 获取催费信息
     * 
     * @param orgId 客户ID
     * @param orgName 客户公司名称
     * @return Map<String,String>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> saveFeeNoticeInfoByOrgId(int mngId, String orgId, String orgName);
    
    /**
     * 验证当月缴费情况
     * 
     * @param payMonths 缴费月份
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkConPayMonths(String bMonths, String eMonths, String payMonths);
    
    /**
     * 验证当月缴费次数
     * 
     * @param orgId 客户ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkThisMonthFee(String orgId);
    
    /**
     * 验证当月缴费频率表中插入记录
     * 
     * @param orgId 客户ID
     * @see [类、类#方法、类#成员]
     */
    void saveFeeNum(String orgId);
    
    /**
     * 验证合同日期是否有交集
     * 
     * @param cusId 客户ID
     * @return boolean
     */
    boolean checkContractDate(int id, String cusId, String bDate, String eDate, String flag);
    
    /**
     * 获取合同每个月缴费数据
     * 
     * @param conId 合同ID
     * @param payYear 缴费年份
     * @return List<Map<String,Object>>
     */
    Map<String, Map<String, Object>> getContractMonthCost(String conId, String payYear);

    /**
     * 接口：获取代帐公司下小微企业缴费情况信息
     * 
     * @param memberId biz_member中账号
     * @return LoginVo
     */
    LoginVo getConPayInfo(String mMemId, String bMemId, String date);
    
    /**
     * 更新报税总额与明细
     * 
     * @param taxId 报税ID
     * @param amount 报税总额
     * @param taxDetail 报税明细
     * @return
     */
    boolean updateTally(String taxId, String amount, String taxDetail);
    
    /**
     * 获取主页面跟进信息
     * 
     * @param cusId 客户ID
     * @return Map<String,Object>
     */
    Map<String, Object> getMainFollowInfo(int cusId);
    
    /**
     * 批量完成跟进记录
     * 
     * @param cusId 客户ID
     * @return boolean
     */
    boolean updateFollow(String cusId, int userId);
    
    /**
     * 获取更多跟进列表
     * 
     * @param userId 系统当前登录人ID
     * @param orgName 客户ID
     * @param followTime 跟进时间
     * @param isRead 是否完成
     * @return List<Map<String, Object>>
     */
    PageUtil getMoreFollowList(int userId, String orgName, String followTimeBegin, String followTimeEnd, String isRead, PageInfo info);
    
    /**
     * 完成单条跟进信息
     * 
     * @param followId 跟进ID
     * @return boolean
     */
    boolean updateSingleFollow(int followId);
    
    /**
     * 重新开启
     * 
     * @param followId 跟进ID
     * @return boolean
     */
    boolean updateReloadSingleFollow(int followId);
    
    /**
     * @param 获取缴费月份
     * @return String
     */
    String getPayMonth(String orgId, String payYear, String accNo);
    
    String getPayMonths(String orgId, String payYear);
    /**
     * 获取缴费审批列表
     * 
     * @param info 分页信息
     * @param cusName 客户名称
     * @param isAdmin 是否为管理员登陆
     * @param userId memberID
     * @param bDate 交费开始日期
     * @param eDate 交费结束日期
     * @return PageUtil
     */
    PageUtil getAuditList(PageInfo info, boolean isAdmin, int userId, int mngId, String cusName, String bDate, String eDate);
    
    /**
     * 获取缴费审批历史列表
     * 
     * @param info 分页信息
     * @param isAdmin 是否为管理员登录
     * @param userId memberID
     * @param mntId 公司ID
     * @param orgId 客户ID
     * @return PageUtil
     */
    PageUtil getAuditListByOrg(PageInfo info, boolean isAdmin, int userId, int mngId, String orgId);
    
    /**
     * 获取自动催费公司信息
     * 
     * @return <Map<String,Object>>
     */
    List<Map<String, Object>> getAutoSendfeeOrgInfo();
    
    /**
     * 合同是否可以修改
     * 
     * @param conStart 合同中开始时间
     * @param conEnd 合同中结束时间
     * @param cusId 客户ID
     * @return boolean
     */
    boolean conCanEdit(String conStart, String conEnd, String cusId,String ifPayBK);
    
    /**
     * 导出客户Excel
     * 
     * @param orgIds 客户ID数组
     * @return ResponseEntity<byte[]>
     */
    ResponseEntity<byte[]> downLoadExcel(int mngId, String[] orgIds)
        throws Exception;
    
    Map<String, Object> checkFee(String orgId, String mngId);
    
    /**
     * 获取欠费月份
     * 
     * @param payMonths 缴费月份
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    Map<String, String> checkConPay(String bMonths, String eMonths, String payMonths);
    
    /**
     * 
     * 获取缴费年的缴费信息
     * 
     * @param mngId mngId
     * @param orgId orgId
     * @param payYear payYear
     * @return Map<String, Boolean>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Boolean> getPayYearExpInfo(String mngId, String orgId);
    
    /**
     * 验证指定年是否欠费
     * 
     * @param year year
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean checkYearIsPay(String orgId, String year);
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param mngId mngId
     * @param orgId orgId
     * @param year year
     * @return Map<String, String>
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getPayYearMonthExpInfo(String mngId, String orgId, String year);

/**
 * 客户管理-客户信息-业务办理-添加业务-业务添加管理
 * @param request
 * @param session
 * @param orgId
 * @return
 */
    public Map gotoDoServices(HttpServletRequest request, HttpSession session,int orgId);

    
    /**
     * 保存业务
     * @param request
     * @param session
     * @return
     */
    public String saveServices(int id,String servicesName,String servicesType);

	/**公司缴费提醒
	 * @return
	 */
	void createCustomPayRemind();

	String beforePayValidate(String str);

	boolean createMntExpenseDetail(String str);
	
	MntMember getPayInfo(String memberId);
	/*
	 * 通过合同id获取该合同的客户的手机号码
	 */
	List<Map<String, Object>> getTelByContractId(int contractId);
	/*
	 * 根据发送类型进行做发送处理
	 */
	int sendMsg(String type,String senContent,String custMobile);

}
