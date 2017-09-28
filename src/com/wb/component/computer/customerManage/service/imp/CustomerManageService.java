package com.wb.component.computer.customerManage.service.imp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.component.computer.auditSettings.service.IAuditSettingsService;
import com.wb.component.computer.billManage.entity.ExpenseDetailInfo;
import com.wb.component.computer.billManage.entity.PayValidate;
import com.wb.component.computer.billManage.entity.PayValidateMsg;
import com.wb.component.computer.common.hessian.factory.HessianFactory;
import com.wb.component.computer.customerManage.service.HessianService;
import com.wb.component.computer.customerManage.service.ICustomerManageService;
import com.wb.component.computer.login.util.SortNameCN;
import com.wb.framework.commonConstant.Constant;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.ExcelUtil;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.framework.commonUtil.PropertiesReader;
import com.wb.framework.commonUtil.StrUtils;
import com.wb.framework.commonUtil.calendar.DateUtil;
import com.wb.framework.commonUtil.encrypt.AES;
import com.wb.framework.commonUtil.smssSender.SMSSender;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMessage;
import com.wb.model.entity.computer.MntOrgtax;
import com.wb.model.entity.computer.accTableEntity.BizOrganization;
import com.wb.model.entity.computer.cusManage.MntCustomAttachment;
import com.wb.model.entity.computer.cusManage.MntCustomContract;
import com.wb.model.entity.computer.cusManage.MntCustomInfo;
import com.wb.model.entity.computer.cusManage.MntCustomTaxInfo;
import com.wb.model.entity.computer.cusManage.MntExpenseDetail;
import com.wb.model.entity.computer.cusManage.MntRemind;
import com.wb.model.entity.computer.news.MntNews;
import com.wb.model.pojo.computer.CustomAttachment;
import com.wb.model.pojo.computer.CustomFollowInfo;
import com.wb.model.pojo.computer.CustomInfoVo;
import com.wb.model.pojo.computer.CustomPayVo;
import com.wb.model.pojo.computer.CustomTaxInfo;
import com.wb.model.pojo.mobile.login.LoginVo;

/**
 * 客户管理Service层
 * 
 * @author 郑炜
 * @version [版本号, 2016-3-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "customerManageService")
public class CustomerManageService extends BaseDao implements ICustomerManageService {
	/**
	 * 日志服务
	 */
	private static final Logger log = Logger
			.getLogger(CustomerManageService.class);

	@Autowired
	@Qualifier("auditSettingsService")
	private IAuditSettingsService auditSettingsService;

	@Override
	public String createCustom(CustomInfoVo vo, String userId) throws Exception {
		final MntCustomInfo entity = new MntCustomInfo();
		BeanUtils.copyProperties(vo, entity);
		final String id = this.idCreater(userId);
		entity.setCreater(userId);
		entity.setId(id);
		if (this.checkOrgNumRepeat(vo.getOrgNum(), "create", userId)) {
			return "2";// orgNum重复
		}
		if (this.checkOrgNameRepeat(vo.getOrgName(), "create")) {
			return "3";// orgName重复
		}
		try {
			this.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "4";// 插入costomInfo表数据失败
		}
		try {
			this.addTaxInfo(vo, id);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "5";// 插入customTaxInfo表数据失败
		}
		try {
			this.fileUpLoad(vo, id);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "6";// 插入customAttachment表数据失败
		}
		try {
			this.createOffice(vo, userId, id);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "7";// 插入biz_organization表数据失败
		}
		return entity.getId();
	}

	@Override
	public int updateCustom(CustomInfoVo vo, String cusId, String userId)
			throws Exception {
		final MntCustomInfo entity = new MntCustomInfo();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(cusId);
		entity.setCreater(userId);
		final String deleteTaxSql = "DELETE FROM mnt_customTaxInfo WHERE cusId='"
				+ cusId + "'";
		this.getSession().createSQLQuery(deleteTaxSql).executeUpdate();
		if (this.checkOrgNumRepeat(vo.getOrgNum(), cusId, userId)) {
			return 2;// orgNum重复
		}
		if (this.checkOrgNameRepeat(vo.getOrgName(), cusId)) {
			return 3;// orgName重复
		}
		try {
			this.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 4;// 插入costomInfo表数据失败
		}
		try {
			this.addTaxInfo(vo, cusId);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 5;// 插入customTaxInfo表数据失败
		}
		try {
			this.fileUpLoad(vo, cusId);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 6;// 插入customAttachment表数据失败
		}
		try {
			this.updateOffice(vo, userId, cusId);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 7;// 插入biz_organization表数据失败
		}
		return 1;
	}

	@Override
	public void addTaxInfo(CustomInfoVo vo, String cusId) {
		if (vo.getTaxList() == null) {
			return;
		}
		for (CustomTaxInfo item : vo.getTaxList()) {
			if (item == null) {
				continue;

			}
			if (StringUtils.isEmpty(item.getTaxKind())) {
				continue;
			}
			final MntCustomTaxInfo entity = new MntCustomTaxInfo();
			entity.setCusId(cusId);
			entity.setTaxKind(item.getTaxKind());
			entity.setTaxName(item.getTaxName());
			entity.setRepeortType(item.getRepeortType());
			entity.setDays(item.getDays());
			entity.setTaxPeople(item.getTaxPeople());
			entity.setCreateTime(new Date());
			this.save(entity);
		}
	}

	@Override
	public boolean checkOrgNumRepeat(String orgNum, String cmd, String ownerId) {
		boolean result = false;
		if ("create".equals(cmd)) {
			final String sql = "SELECT COUNT(*) FROM biz_organization WHERE seqCode = ? and ownerId = ? and Enable = 1";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setString(0, orgNum);
			query.setParameter(1, ownerId);
			final BigInteger num = (BigInteger) query.uniqueResult();
			if (num.intValue() > 0) {
				result = true;
			}
		} else {
			final String sql = "SELECT COUNT(*) FROM biz_organization WHERE seqCode = ? and mntCustomId != ? and ownerId = ?  and Enable = 1";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setString(0, orgNum);
			query.setString(1, cmd);
			query.setParameter(2, ownerId);
			final BigInteger num = (BigInteger) query.uniqueResult();
			if (num.intValue() > 0) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean checkOrgNameRepeat(String orgName, String cmd) {
		boolean result = false;
		if ("create".equals(cmd)) {
			final String sql = "SELECT COUNT(*) FROM biz_organization WHERE Name = ? and Enable = 1";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setString(0, orgName);
			final BigInteger num = (BigInteger) query.uniqueResult();
			if (num.intValue() > 0) {
				result = true;
			}
		} else {
			final String sql = "SELECT COUNT(*) FROM biz_organization WHERE Name = ? and mntCustomId != ? and Enable = 1";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setString(0, orgName);
			query.setString(1, cmd);
			final BigInteger num = (BigInteger) query.uniqueResult();
			if (num.intValue() > 0) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public String idCreater(String userId) {
		final String id = userId + System.currentTimeMillis();
		return id;
	}

	@Override
	public boolean fieldNameFilter(String fieldName) {
		boolean result = true;
		if ("taxList".equals(fieldName)) {
			result = false;
		} else if ("fileListInfo".equals(fieldName)) {
			result = false;
		}
		return result;
	}

	@Override
	public void fileUpLoad(CustomInfoVo vo, String cusId) throws IOException {
		if (vo.getFileListInfo() != null) {
			final List<CustomAttachment> fileListInfo = vo.getFileListInfo();
			for (CustomAttachment file : fileListInfo) {
				if (StringUtils.isEmpty(file.getFile())) {
					continue;
				}
				final MntCustomAttachment entity = new MntCustomAttachment();
				entity.setCusId(cusId);
				entity.setComments(file.getComments());
				entity.setFileName(file.getFile().getOriginalFilename());
				entity.setDemoFile(file.getFile().getBytes());
				entity.setUpTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()));
				entity.setFlag(0);
				this.save(entity);
			}
		}
	}

	@Override
	public PageUtil getCompanyInfo(String cmd, String conType, String disFlag,
			String id, int orgId, String companySearchName, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		if(id.equals("")){
		       List records =new ArrayList();
		       PageUtil pageUtil=new PageUtil(records, 0, 0, 50);
		       return pageUtil;
		}else{
			if("1".equals(conType)){
				//sql.append("(");
			}
			sql.append(" SELECT bo.id AS OID, bo.mntCustomId AS ORGID, bm.name AS MEMBERNAME, bo.seqCode AS ORGSEQ, ");
			sql.append(" bo.Name AS ORGNAME, bo.mntCustomId AS OPERATE");
			sql.append(" FROM mnt_departmentInfo mdp");
			sql.append(" INNER JOIN biz_member bm on mdp.id = bm.departmentId ");
			sql.append(" INNER JOIN biz_organization bo on bm.id = bo.ownerId and bo.Enable = 1 ");
			sql.append(" INNER JOIN mnt_customInfo cus on bo.mntCustomId = cus.id ");
			sql.append(" INNER JOIN mnt_mngandusers mmu on bm.id = mmu.userMemberId and mmu.mntMemberId = '"
					+ orgId + "' and mmu.state = 1 ");
			sql.append(" WHERE 1 = 1 ");
			if (!"".equals(conType) && conType != null && !"1".equals(conType)) {
				sql.append(" AND EXISTS (SELECT con.id");
				sql.append(" FROM mnt_customContract con");
				sql.append(" WHERE cus.id = con.cusId");
				sql.append(" AND con.contractType = " + conType + ")");
			} else {
				sql.append(" AND NOT EXISTS (SELECT con.id");
				sql.append(" FROM mnt_customContract con");
				sql.append(" WHERE cus.id = con.cusId)");
			}
			if (disFlag != null && "1".equals(disFlag)) {
				sql.append(" AND EXISTS (SELECT mBook.OrgId");
				sql.append(" FROM biz_orgmemberbook mBook");
				sql.append(" WHERE mBook.OrgId = bo.id)");
			} else if (disFlag != null && "0".equals(disFlag)) {
				sql.append(" AND NOT EXISTS (SELECT mBook.OrgId");
				sql.append(" FROM biz_orgmemberbook mBook");
				sql.append(" WHERE mBook.OrgId = bo.id)");
			}
			if ("BY_EMP".equals(cmd)) {
				sql.append(" and bo.ownerId = '" + id + "'");
			} else if ("BY_DEP".equals(cmd)) {
				sql.append(" and (mdp.idPath like CONCAT((SELECT idPath FROM mnt_departmentInfo WHERE id = '"
						+ id + "'),'-%')");
				sql.append(" or mdp.idPath = (SELECT idPath FROM mnt_departmentInfo WHERE id = '"
						+ id + "'))");
			}
			if (!StrUtils.isEmpty(companySearchName)) {
				sql.append(" and (bo.seqCode like '%" + companySearchName);
				sql.append("%' or bo.Name like '%" + companySearchName + "%')");
			}
			if(!"1".equals(conType)){
				sql.append(" order by bo.id DESC");
			}else if("1".equals(conType)){
				//sql.append(")");
			}
			
		}
		if("1".equals(conType)){
			if(id.equals("")){
			       List records =new ArrayList();
			       PageUtil pageUtil=new PageUtil(records, 0, 0, 50);
			       return pageUtil;
			}else{
				
				sql.append(" UNION ");
				sql.append(" SELECT bo.id AS OID, bo.mntCustomId AS ORGID, bm.name AS MEMBERNAME, bo.seqCode AS ORGSEQ, ");
				sql.append(" bo.Name AS ORGNAME, bo.mntCustomId AS OPERATE");
				sql.append(" FROM mnt_departmentInfo mdp");
				sql.append(" INNER JOIN biz_member bm on mdp.id = bm.departmentId ");
				sql.append(" INNER JOIN biz_organization bo on bm.id = bo.ownerId and bo.Enable = 1 ");
				sql.append(" INNER JOIN mnt_customInfo cus on bo.mntCustomId = cus.id ");
				sql.append(" INNER JOIN mnt_mngandusers mmu on bm.id = mmu.userMemberId and mmu.mntMemberId = '"
						+ orgId + "' and mmu.state = 1 ");
				sql.append(" WHERE 1 = 1 ");
				if (!"".equals(conType) && conType != null && "1".equals(conType)) {
					sql.append(" AND EXISTS (SELECT con.id");
					sql.append(" FROM mnt_customContract con");
					sql.append(" WHERE cus.id = con.cusId");
					sql.append(" AND con.contractType in( " + "200101,200100,200102,200103,200104,200105" + ")").append(")");
				}
				if (disFlag != null && "1".equals(disFlag)) {
					sql.append(" AND EXISTS (SELECT mBook.OrgId");
					sql.append(" FROM biz_orgmemberbook mBook");
					sql.append(" WHERE mBook.OrgId = bo.id)");
				} else if (disFlag != null && "0".equals(disFlag)) {
					sql.append(" AND NOT EXISTS (SELECT mBook.OrgId");
					sql.append(" FROM biz_orgmemberbook mBook");
					sql.append(" WHERE mBook.OrgId = bo.id)");
				}
				if ("BY_EMP".equals(cmd)) {
					sql.append(" and bo.ownerId = '" + id + "'");
				} else if ("BY_DEP".equals(cmd)) {
					sql.append(" and (mdp.idPath like CONCAT((SELECT idPath FROM mnt_departmentInfo WHERE id = '"
							+ id + "'),'-%')");
					sql.append(" or mdp.idPath = (SELECT idPath FROM mnt_departmentInfo WHERE id = '"
							+ id + "'))");
				}
				if (!StrUtils.isEmpty(companySearchName)) {
					sql.append(" and (bo.seqCode like '%" + companySearchName);
					sql.append("%' or bo.Name like '%" + companySearchName + "%')");
				}
				//sql.append(" )");
				sql.append(" order by oid DESC");
			}
		}
		System.out.println("sql-------"+sql.toString());
		PageUtil util = this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
		return util;
	}

	@Override
	public PageUtil getFollowInfo(String cusId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id AS FOLLOWID,tel AS TEL,followtime AS FOLLOWTIME,");
		sql.append(" content AS CONTENT,contacts AS CONTACTS,");
		sql.append(" id AS OPERATE FROM mnt_customFollowInfo");
		sql.append(" WHERE cusId = '" + cusId + "'");
		sql.append(" order by followtime desc");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows());
	}

	@Override
	public String getCusInfo(String cusId) {
		String result = "";
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT Name FROM biz_organization ");
		sql.append(" WHERE mntCustomId = '" + cusId + "' and Enable = 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		final List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list) {
			result = map.get("Name").toString();
		}
		return result;
	}
	 /**
     * 获取公司信息
     * 
     * @param cusId 客户ID
     * @return String
     * @see [类、类#方法、类#成员]
     */
	@Override
	public  MntCustomInfo getCustomInfo(String cusId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id FROM mnt_customInfo    WHERE id = (SELECT mntCustomId FROM biz_organization  WHERE 1=1 AND id = ?)");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, cusId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		MntCustomInfo mntCus = new MntCustomInfo();
		if (list != null) {
			for (Map<String, Object> map : list) {
				mntCus.setId(map.get("id")!=null?map.get("id").toString():"");
			}
		}
		return mntCus;
	}
	@Override
	public  BizOrganization getCustomName(String cusId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM biz_organization  WHERE 1=1 AND id = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, cusId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		BizOrganization mntCus = new BizOrganization();
		if (list != null) {
			for (Map<String, Object> map : list) {
				mntCus.setName(map.get("Name")!=null?map.get("Name").toString():"");
				mntCus.setSeqCode(map.get("seqCode")!=null?Integer.parseInt(map.get("seqCode").toString()):0);
			}
		}
		return mntCus;
	}
	
	@Override
	public boolean saveFollowInfo(CustomFollowInfo vo, String userId) {
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" INSERT into mnt_customFollowInfo");
			sql.append(" (cusId,emId,followTime,content,contacts,tel,createTime)");
			sql.append(" values(?,?,?,?,?,?,SYSDATE())");
			final Query query = this.createSqlQuery(sql.toString(),
					vo.getCusId(), userId, vo.getFollowTime(), vo.getContent(),
					vo.getContacts(), vo.getTel());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFollowInfo(String followId) {
		try {
			final String sql = " DELETE FROM mnt_customFollowInfo WHERE id = ? ";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setParameter(0, followId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateFollowInfo(String followId, CustomFollowInfo vo) {
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_customFollowInfo SET followTime=?,content=?,contacts=?,tel=? WHERE id=? ");
			final Query query = this.createSqlQuery(sql.toString(),
					vo.getFollowTime(), vo.getContent(), vo.getContacts(),
					vo.getTel(), followId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomInfoVo getCustomInfoVo(String cusId) {
		final CustomInfoVo vo = new CustomInfoVo();
		final MntCustomInfo mnt = (MntCustomInfo) this.findByPrimaryKey(
				MntCustomInfo.class, cusId);
		final List<BizOrganization> biz = (List<BizOrganization>) this
				.findByProperty(BizOrganization.class, "mntCustomId", cusId);
		BeanUtils.copyProperties(mnt, vo);
		vo.setTempId(cusId);
		if (biz != null) {
			vo.setCreator(biz.get(0).getCreator());
			vo.setOrgNum(StrUtils.nullToString(biz.get(0).getSeqCode()));
			vo.setCreateDate(DateUtil.getDateString(biz.get(0).getCreateTime(),
					"yyyy-MM-dd"));
			vo.setStartAcc(DateUtil.getDateString(biz.get(0).getStartTime(),
					"yyyy-MM-dd"));
			vo.setOrgName(biz.get(0).getName());
			vo.setInstitutionNo(biz.get(0).getOrgCode());
			vo.setBussLicence(biz.get(0).getLisence());
			vo.setOrgShortName(biz.get(0).getAbbreviation());
		}
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getAttachmentInfoVo(String cusId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id,cusId,comments,fileName,");
		sql.append(" upTime,flag FROM mnt_customAttachment");
		sql.append(" WHERE cusId=?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, cusId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MntCustomTaxInfo> getTaxInfoVo(String cusId) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("cusId", cusId);
		return this.findByProperties(MntCustomTaxInfo.class, map);
	}

	@Override
	public boolean deleteFileById(String fileId) {
		try {
			final String sql = " DELETE FROM mnt_customAttachment WHERE id = ? ";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setParameter(0, fileId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResponseEntity<byte[]> download(String fileId) throws IOException {
		final Class clazz = MntCustomAttachment.class;
		final MntCustomAttachment attachment = (MntCustomAttachment) this
				.findByPrimaryKey(clazz, Integer.parseInt(fileId));
		final HttpHeaders headers = new HttpHeaders();
		final String fileName = new String(attachment.getFileName().getBytes(
				"UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		final byte[] bytes = attachment.getDemoFile();
		final ResponseEntity<byte[]> outFile = new ResponseEntity<byte[]>(
				bytes, headers, HttpStatus.CREATED);
		return outFile;
	}

	@Override
	public void createOffice(CustomInfoVo vo, String userId, String cusId) {
		final String Acronym = SortNameCN.getAcronym(vo.getOrgName());
		final com.hessian.BizOrganization bizOrganization = new com.hessian.BizOrganization();
		bizOrganization.setSeqCode(Integer.parseInt(vo.getOrgNum()));
		bizOrganization.setName(vo.getOrgName());
		bizOrganization.setOrgCode(vo.getInstitutionNo());
		bizOrganization.setCreator(Integer.parseInt(userId));
		bizOrganization.setLisence(vo.getBussLicence());
		bizOrganization.setAbbreviation(vo.getOrgShortName());
		bizOrganization.setAcronym(Acronym);
		bizOrganization.setCreateTime(new Date());// 创建时间
		bizOrganization.setStartTime(new Date());// 开始代账时间
		bizOrganization.setEnable(1);
		bizOrganization.setOwnerId(Integer.parseInt(userId));
		bizOrganization.setMntCustomId(cusId);
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String bookName = bizOrganization.getName() + date + "进销存账簿";
		PropertiesReader reader = PropertiesReader.getInstance();
		String url = reader.getValue("/com/wb/config/application",
				"ACC_HESSIAN_URL");
		String port = reader.getValue("/com/wb/config/application",
				"ACC_HESSIAN_PORT");
		String afterStr = reader.getValue("/com/wb/config/application",
				"ACC_HESSIAN_AFTERSTR");
		HessianService hs = (HessianService) HessianFactory.initHession(
				HessianService.class, url, port, afterStr);
		hs.createOrg(bizOrganization, bookName, Integer.parseInt(userId));
	}

	@Override
	public void updateOffice(CustomInfoVo vo, String userId, String cusId)
			throws ParseException {
		final String Acronym = SortNameCN.getAcronym(vo.getOrgName());
		final List<BizOrganization> bizOrganization = (List<BizOrganization>) this.findByProperty(BizOrganization.class, "mntCustomId", cusId);
		if (bizOrganization != null) {
			bizOrganization.get(0).setSeqCode(Integer.parseInt(vo.getOrgNum()));
			bizOrganization.get(0).setName(vo.getOrgName());
			bizOrganization.get(0).setOrgCode(vo.getInstitutionNo());
			bizOrganization.get(0).setCreator(Integer.parseInt(userId));
			bizOrganization.get(0).setLisence(vo.getBussLicence());
			bizOrganization.get(0).setAbbreviation(vo.getOrgShortName());
			bizOrganization.get(0).setAcronym(Acronym);
			bizOrganization.get(0).setOwnerId(Integer.parseInt(userId));
			this.update(bizOrganization.get(0));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageUtil getCusPayInfo(String cmd, String queryId, String mngOrgId,
			String orgId, String payYear, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" IFNULL(org.ID, '') AS ORGID,");
		sql.append(" IFNULL(cus.ID, '') AS CUSID,");
		sql.append(" IFNULL(org.seqCode, '') AS ORGNUM,");
		sql.append(" IFNULL(org. NAME, '') AS ORGNAME,");
		sql.append(" SUM(IFNULL(exp.payAmount, 0)) AS PAYAMOUNT,");
		sql.append(" SUM(IFNULL(exp.realAmount, 0)) AS REALAMOUNT,");
		sql.append(" (CASE WHEN cus.id IN ( SELECT DISTINCT cusId");
		sql.append(" FROM mnt_customContract) THEN 'true' ELSE 'false' END) AS CON,");
		sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS PAYMONTHS,");
		sql.append(" exp.accNo AS EXPACCNO,");
		sql.append(" con.id as CONID,");
		sql.append(" con.accNo as ACCNO,");
		sql.append(" con.accBookCostPay as BOOKFEE,");
		sql.append(" IF(DATE_FORMAT(con.accStartTime, '%Y')<'"
				+ payYear
				+ "', '01', IFNULL(DATE_FORMAT(con.accStartTime, '%m'), '')) AS BMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime, '%Y')>'"
				+ payYear
				+ "', 12, IFNULL(DATE_FORMAT(con.accEndTime , '%m'), '')) AS EMONTHS ");
		sql.append(" FROM mnt_customContract con LEFT JOIN mnt_customInfo cus");
		sql.append(" ON DATE_FORMAT(con.accStartTime, '%Y') <= '" + payYear
				+ "' AND DATE_FORMAT(con.accEndTime, '%Y') >= '" + payYear
				+ "'");
		sql.append(" AND con.cusId = cus.id");
		sql.append(" LEFT JOIN  biz_organization org ON ");
		sql.append(" org.mntCustomId = cus.id");
		sql.append(" LEFT JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id");
		sql.append(" LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId");
		sql.append(" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.accNo = con.accNo AND exp.deleteFlag = 0 AND exp.id not in ");
		sql.append(" (SELECT ar.correlationId FROM mnt_auditRoute ar WHERE ar.mngId =  '"
				+ mngOrgId + "' and ar.auditFlag <> 0)");
		sql.append(" AND DATE_FORMAT(exp.payDate, '%Y') =  '" + payYear + "'");
		sql.append(" WHERE");
		sql.append(" mng.state = 1 AND org.Enable = 1");
		sql.append(" AND mng.mntMemberId = '" + mngOrgId + "'");
		if ("BY_EMP".equals(cmd)) {
			sql.append(" and org.ownerId = '" + queryId + "'");
		} else if ("BY_DEP".equals(cmd)) {
			sql.append(" and (dep.idPath like CONCAT((SELECT idPath FROM mnt_departmentInfo WHERE id = '"
					+ queryId + "'),'-%')");
			sql.append(" or dep.idPath = (SELECT idPath FROM mnt_departmentInfo WHERE id = '"
					+ queryId + "'))");
		}
		if (orgId != null && !"".equals(orgId) && !"0".equals(orgId)) {
			sql.append(" and org.ID='" + orgId + "'");
		}
		//sql.append(" AND org.id=4064 ");
		sql.append(" GROUP BY org.id,org.seqCode,con.accNo");
		PageUtil pageUtil = this.findPageBySqlQuery(sql.toString(),
				info.getPage(), info.getRows());
		List<Map<String, Object>> list = pageUtil.getRecords();
		for (Map<String, Object> map : list) {
			String payMonths = map.get("PAYMONTHS").toString();
			if (payMonths != null && !"".equals(payMonths)) {
				payMonths = checkDupArray(payMonths.split(","));// 由于sql语句查出来月份会重复此处去重排序;
				map.put("PAYMONTHS", payMonths);
			}
			String bMonths = map.get("BMONTHS").toString();
			String eMonths = map.get("EMONTHS").toString();
			
			boolean arrearage = checkIfConPayMonthsArr(bMonths, eMonths,
					payMonths, payYear);// 是否欠费
			if(!arrearage){
				boolean isOver=checkIfConPayMonths7Over(bMonths,eMonths, payMonths, payYear);
				map.put("ISOVER", isOver);
			}
			map.put("ARR", arrearage);
		}
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				boolean arr = (Boolean) ((Map<String, Object>) o2).get("ARR");
				if (arr) {
					return 1;
				}
				return -1;
			}
		});
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				boolean con = Boolean.parseBoolean(((Map<String, Object>) o2)
						.get("CON").toString());
				if (con) {
					return 1;
				}
				return -1;
			}
		});
		return pageUtil;
	}

	/**
	 * 判断是否有欠费月份
	 * 
	 * @param bMonths
	 *            合同起始月份
	 * @param eMonths
	 *            合同截止月份
	 * @param payMonths
	 *            已缴费月份 逗号分隔格式
	 * @param payYear
	 *            缴费年份
	 * @return
	 */
	public boolean checkIfConPayMonthsArr(String bMonths, String eMonths,
			String payMonths, String payYear) {
		int fromMonth = Integer.parseInt(bMonths);// 合同起始月份
		int toMonth = Integer.parseInt(eMonths);// 合同结束月份
		List list = new ArrayList();// 应该缴费的月份
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份

		for (int i = fromMonth; i < toMonth + 1; i++) {
			list.add(i);
		}
		String[] alreadyPayM = payMonths.split(",");// 已交缴费的月份
		for (int i = 0; i < alreadyPayM.length; i++) {// 从应该缴费的月份中去掉已经缴费的月份
			Iterator it = list.iterator(); // 应该缴费的月份
			while (it.hasNext()) {
				int shoudPay = (Integer) it.next();
				if (!"".equals(alreadyPayM[i])) {// 查出来的数据有,的情况
					if (Integer.parseInt(alreadyPayM[i]) == shoudPay) {
						it.remove(); // 移除该对象
					}
				}
			}
			list.remove(alreadyPayM[i]);
		}
		if (list.size() > 0) {
			// 如果存在仍未缴费的月份，判断缴费年费是否小于当前系统年份，如果小于当前系统年份都为欠费
			if (Integer.parseInt(payYear) < currYear) {
				return true;
			} else if (Integer.parseInt(payYear) == currYear) {// 如果不小于但前年分则判断未交费月份是否小于当前月份，小于则欠费
				Collections.sort(list);// 仍未缴费的月份排序
				// 判断仍未缴费的月份是否小于当前月份
				int noPayM = (Integer) list.get(0);
				if (noPayM <=currMonth) {// 小于则欠费
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断合同是否还差7天到期,
	 * 
	 * @param eMonths
	 *            合同截止月份
	 * @param payMonths
	 *            已缴费月份 逗号分隔格式
	 * @param payYear
	 *            缴费年份
	 *            
	 * @return  
	 * add by zhouww 业务逻辑：缴费日期是当前月，下个月没缴费。在本月七天前生成即将到期按钮
	 */
	public boolean checkIfConPayMonths7Over(String bMonths ,String eMonths, String payMonths, String payYear) {
		
		int fromMonth = Integer.parseInt(bMonths);// 合同起始月份
		int toMonth = Integer.parseInt(eMonths);// 合同结束月份
		List list = new ArrayList();// 应该缴费的月份
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份

		for (int i = fromMonth; i < toMonth + 1; i++) {
			list.add(i);
		}
		String[] alreadyPayM = payMonths.split(",");// 已交缴费的月份
		for (int i = 0; i < alreadyPayM.length; i++) {// 从应该缴费的月份中去掉已经缴费的月份
			Iterator it = list.iterator(); // 应该缴费的月份
			while (it.hasNext()) {
				int shoudPay = (Integer) it.next();
				if (!"".equals(alreadyPayM[i])) {// 查出来的数据有,的情况
					if (Integer.parseInt(alreadyPayM[i]) == shoudPay) {
						it.remove(); // 移除该对象
					}
				}
			}
			list.remove(alreadyPayM[i]);
		}
		if (list.size() > 0) {
			// 如果存在仍未缴费的月份，判断缴费年费是否小于当前系统年份，如果小于当前系统年份都为欠费
			if (Integer.parseInt(payYear) < currYear) {
				return true;
			} else if (Integer.parseInt(payYear) == currYear) {// 如果不小于但前年分则判断未交费月份是否小于当前月份，小于则欠费
				Collections.sort(list);// 仍未缴费的月份排序
				// 判断仍未缴费的月份是否小于当前月份
				int noPayM = (Integer) list.get(0);
				
				SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				today = dfs.format(new Date());// new Date()为获取当前系统年月
				currYear = Integer.parseInt(today.substring(0, 4));
				currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份
				int currday = Integer.parseInt(today.substring(8, 10));
				if (currMonth == noPayM - 1 && Integer.parseInt(payYear) == currYear) {
					if(currday>=getLastSevenDay()){
					return true;
					}
				}
				
			}
		}
		
		//int toMonth = Integer.parseInt(eMonths);// 合同结束月份
		
		return false;
	}
	
	/**
	 * 去掉数组中的重复月份
	 * 
	 * @param strs
	 * @return
	 */
	private String checkDupArray(String[] strs) {
		Set<String> strSet = new HashSet<String>();
		for (String str : strs) {
			strSet.add(str);
		}
		String strsN = "";
		for (String str : strSet) {
			strsN += str + ",";
		}
		return strsN.substring(0, strsN.length() - 1);
	}

	@Override
	public PageUtil getContractInfo(String cusId, PageInfo info) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id AS CONTRACTID,cusId AS CUSID,reviseId AS REVISEID,");
		sql.append(" emId AS EMID,cusName AS CUSNAME,signTIme AS SIGNTIME,");
		sql.append(" accStartTime AS ACCSTARTTIME,accEndTime AS ACCENDTIME,");
		sql.append(" monthCost AS MONTHCOST,discount*100 AS DISCOUNT,commission AS COMMISSION,");// 折扣在数据库中改为浮点类型100%=1
		sql.append(" accBookCost AS ACCBOOKCOST,payType AS PAYTYPE,");
		sql.append(" demo AS DEMO,attachmentFile AS ATTACHMENTFILE,");
		sql.append(" cancleFlag AS CANCLEFLAG,id AS OPERATE,haveFile AS HAVEFILE,");
		sql.append(" createTime AS CREATETIME,updateTime AS UPDATETIME,contractType AS CONTRACTTYPE,accBookCostPay as IFPAYBK");
		sql.append(" FROM mnt_customContract");
		sql.append(" WHERE cusId = '" + cusId + "' order by updateTime desc ");
		System.out.println(sql);
		PageUtil pageUtil = this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
		List<Map<String, Object>> list = pageUtil.getRecords();
		for (Map<String, Object> map : list) {
			String conStart = map.get("ACCSTARTTIME").toString().substring(0, 7);
			String conEnd = map.get("ACCENDTIME").toString().substring(0, 7);
			String orgId = map.get("CUSID").toString();
			String ifPayBK = (map.get("IFPAYBK")!=null ? map.get("IFPAYBK").toString() : "0");
			//String ifPayBK = map.get("IFPAYBK").toString();
			boolean canEdit = this.conCanEdit(conStart, conEnd, orgId, ifPayBK);
			map.put("canEdit", canEdit);
		}
		return pageUtil;
	}
	/**
	 * 获取当月最后7天
	 * 
	 * @param days
	 * @return
	 */
	public int getLastSevenDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		List<String> list = new ArrayList<String>();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, -7);
		int theDate = calendar.getTime().getDate();
		return theDate;
	}
	@Override
	public int saveContractInfo(MntCustomContract entity, MultipartFile file, String userId) {
		try {
			String cusId = entity.getCusId();
			String bDate = new SimpleDateFormat("yyyy-MM").format(entity.getAccStartTime());
			String eDate = new SimpleDateFormat("yyyy-MM").format(entity.getAccEndTime());
			if (this.checkContractDate(0, cusId, bDate, eDate, "save")) {
				return 2;
			}
			entity.setCancleFlag("0");
			entity.setCreateTime(new Date());
			entity.setEmId(Integer.parseInt(userId));
			// 合同编号
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = sdf.format(date);

			String accNo = dateStr + Math.round(Math.random() * 8999 + 1000);
			entity.setAccNo(accNo);
			// 合同账本费默认为“未缴纳”
			entity.setAccBookCostPay(0);

			if (file != null) {
				entity.setAttachmentFile(file.getBytes());
				entity.setAttachmentFileName(file.getOriginalFilename());
				entity.setHaveFile(1);
			} else {
				entity.setHaveFile(0);
			}
			entity.setDiscount(entity.getDiscount().multiply(new BigDecimal("0.01")));// 折扣在数据库中改为浮点类型100%=1
			this.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	@Override
	public boolean deleteContractInfo(String contractId) {
		try {
			final String sql = " DELETE FROM mnt_customContract WHERE id = ?";
			final Query query = this.getSession().createSQLQuery(sql);
			query.setParameter(0, contractId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public int updateContractInfo(int contractId, MultipartFile file,
			MntCustomContract vo, int userId) {
		try {
			int id = contractId;
			String cusId = vo.getCusId();
			String bDate = new SimpleDateFormat("yyyy-MM").format(vo
					.getAccStartTime());
			String eDate = new SimpleDateFormat("yyyy-MM").format(vo
					.getAccEndTime());
			if (this.checkContractDate(id, cusId, bDate, eDate, "update")) {
				return 2;
			}
			final MntCustomContract mntCustomContract = (MntCustomContract) this
					.findByPrimaryKey(MntCustomContract.class, contractId);
			if (mntCustomContract != null) {
				if (0 == mntCustomContract.getHaveFile() && file != null) {
					mntCustomContract.setAttachmentFile(file.getBytes());
					mntCustomContract.setAttachmentFileName(file
							.getOriginalFilename());
					mntCustomContract.setHaveFile(1);
				}
				mntCustomContract.setReviseId(userId);
				mntCustomContract.setSignTime(vo.getSignTime());
				mntCustomContract.setAccStartTime(vo.getAccStartTime());
				mntCustomContract.setAccEndTime(vo.getAccEndTime());
				mntCustomContract.setMonthCost(vo.getMonthCost());
				mntCustomContract.setDiscount(vo.getDiscount().multiply(
						new BigDecimal("0.01")));// 折扣在数据库中改为浮点类型100%=1
				mntCustomContract.setAccBookCost(vo.getAccBookCost());
				mntCustomContract.setPayType(vo.getPayType());
				mntCustomContract.setDemo(vo.getDemo());
				mntCustomContract.setUpdateTime(new Date());
				mntCustomContract.setContractType(vo.getContractType());
				mntCustomContract.setCommission(vo.getCommission());
				this.update(mntCustomContract);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	@Override
	public boolean deleteContractFile(int contractId) {
		try {
			final MntCustomContract mntCustomContract = (MntCustomContract) this
					.findByPrimaryKey(MntCustomContract.class, contractId);
			if (mntCustomContract != null) {
				mntCustomContract.setAttachmentFile(null);
				mntCustomContract.setAttachmentFileName(null);
				mntCustomContract.setHaveFile(0);
				this.update(mntCustomContract);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ResponseEntity<byte[]> downloadContractFile(int contractId)
			throws IOException {
		final Class clazz = MntCustomContract.class;
		final MntCustomContract mntCustomContract = (MntCustomContract) this
				.findByPrimaryKey(clazz, contractId);
		final HttpHeaders headers = new HttpHeaders();
		final String fileName = new String(mntCustomContract
				.getAttachmentFileName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("mntCustomContract", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		final byte[] bytes = mntCustomContract.getAttachmentFile();
		final ResponseEntity<byte[]> outFile = new ResponseEntity<byte[]>(
				bytes, headers, HttpStatus.CREATED);
		return outFile;
	}

	@Override
	public List<Map<String, Object>> getEmpList(int orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT b.ID AS id,b.Name AS name FROM mnt_member a,");
		sql.append(" biz_member b,mnt_mngandusers c");
		sql.append(" WHERE c.state = 1");
		sql.append(" and a.id = c.mntMemberId");
		sql.append(" and b.id = c.userMemberId");
		sql.append(" and a.id = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, orgId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getEmpByDep(String depSelect,
			boolean isAdmin, String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT bm.id AS empId,");
		sql.append(" bm.Name AS empName");
		sql.append(" FROM biz_member bm");
		sql.append(" INNER JOIN");
		sql.append(" mnt_mngandusers mmu ");
		sql.append(" on bm.id = mmu.userMemberId");
		sql.append(" and mmu.mntMemberId = ?");
		sql.append(" and mmu.state = 1");
		sql.append(" LEFT JOIN mnt_departmentInfo mdp");
		sql.append(" ON bm.departmentId = mdp.id");
		sql.append(" WHERE bm.departmentId IN (");
		sql.append(" SELECT DISTINCT id");
		sql.append(" FROM mnt_departmentInfo t");
		sql.append(" WHERE t.idPath LIKE CONCAT((SELECT");
		sql.append(" idPath FROM");
		sql.append(" mnt_departmentInfo tt");
		sql.append(" WHERE tt.id = ?), '-%')");
		sql.append(" OR t.idPath = (");
		sql.append(" SELECT idPath FROM");
		sql.append(" mnt_departmentInfo tt");
		sql.append(" WHERE tt.id = ?)) ");
		sql.append(" AND bm.departmentId <> ''");
		if (isAdmin) {
			sql.append(" UNION ALL SELECT DISTINCT");
			sql.append(" bm.id AS empId,bm.Name AS empName");
			sql.append(" FROM biz_member bm INNER JOIN");
			sql.append(" mnt_mngandusers mmu ");
			sql.append(" on bm.id = mmu.userMemberId");
			sql.append(" and mmu.mntMemberId = ?");
			sql.append(" and (bm.departmentId is null ");
			sql.append(" or bm.departmentId = '') ");
			sql.append(" and mmu.state = 1");
		}
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, orgId);
		query.setParameter(1, depSelect);
		query.setParameter(2, depSelect);
		if (isAdmin) {
			query.setParameter(3, orgId);
		}
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getBookOrgList(String dataStr) {
		dataStr = "('" + dataStr + "accountbook')";
		dataStr = dataStr.replace("-", "','");
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT a.id AS bookId,a.name AS bookName,");
		sql.append(" b.id AS orgId,b.name AS orgName");
		sql.append(" FROM biz_accountbook a,biz_organization b");
		sql.append(" WHERE a.OrgID = b.ID and b.Enable = 1 and a.Enable = 1");
		sql.append(" and a.OrgID in" + dataStr);
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public void saveDispatching(int userId, String userName, JsonNode disp) {
		try {
			final char code = this.checkDispRepeat(disp.get("roleId").asInt(),
					disp.get("memberId").asInt(), disp.get("orgId").asInt(),
					disp.get("bookId").asInt());
			final StringBuffer sql = new StringBuffer();
			final Query query;
			switch (code) {
			case 0:// 没有重复记录，直接新增
				sql.append(" INSERT into biz_orgmemberbook");
				sql.append(" (MemberID,DepID,OrgID,BookID,Record,RoleID,Power,Operator,Stamp)");
				sql.append(" values (?,?,?,?,?,?,1,?,SYSDATE())");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, disp.get("memberId").asInt());
				query.setInteger(1, disp.get("depId").asInt());
				query.setInteger(2, disp.get("orgId").asInt());
				query.setInteger(3, disp.get("bookId").asInt());
				query.setString(4, userName + "分配权限");
				query.setInteger(5, disp.get("roleId").asInt());
				query.setInteger(6, userId);
				query.executeUpdate();
				break;
			case 1:// 有重复记录，但是角色为空，更新RoleId字段
				sql.append(" update biz_orgmemberbook");
				sql.append(" set RoleID = ?");
				sql.append(" WHERE MemberID = ?");
				sql.append(" and OrgID = ?");
				sql.append(" and BookID = ?");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, disp.get("roleId").asInt());
				query.setInteger(1, disp.get("memberId").asInt());
				query.setInteger(2, disp.get("orgId").asInt());
				query.setInteger(3, disp.get("bookId").asInt());
				query.executeUpdate();
				break;
			case 2:// 有重复记录，角色不为空，但是和已有的角色相等，直接return
				break;
			case 3:// 有重复记录，角色不为空，但是和已有的角色不相等，更新RoleId字段
				sql.append(" update biz_orgmemberbook");
				sql.append(" set RoleID = ?");
				sql.append(" WHERE MemberID = ?");
				sql.append(" and OrgID = ?");
				sql.append(" and BookID = ?");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, disp.get("roleId").asInt());
				query.setInteger(1, disp.get("memberId").asInt());
				query.setInteger(2, disp.get("orgId").asInt());
				query.setInteger(3, disp.get("bookId").asInt());
				query.executeUpdate();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}

	@Override
	public char checkDispRepeat(int roleId, int memberId, int orgId, int bookId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT RoleID FROM biz_orgmemberbook");
		sql.append(" WHERE MemberID = ?");
		sql.append(" and OrgID = ?");
		sql.append(" and BookID = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, memberId);
		query.setInteger(1, orgId);
		query.setInteger(2, bookId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		final List<Map<String, Object>> dispList = query.list();
		final int num = dispList.size();
		if (num == 0) {
			return 0;// 没有重复记录，直接新增
		} else {
			if (dispList.get(0).get("RoleID") == null
					|| "".equals(dispList.get(0).get("RoleID"))) {
				return 1;// 有重复记录，但是角色为空，更新RoleId字段
			} else {
				if (dispList.get(0).get("RoleID").equals(roleId)) {
					return 2;// 有重复记录，角色不为空，但是和已有的角色相等，直接return
				} else {
					return 3;// 有重复记录，角色不为空，但是和已有的角色不相等，更新RoleId字段
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getDispList(int userId, int orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IFNULL(mBook.id,'') AS mBookId,IFNULL(accbook.name,'') AS accBookName,");
		sql.append(" IFNULL(mBook.MemberID,'') AS memId,IFNULL(mBook.Operator,'') AS Operator,");
		sql.append(" IFNULL((case when mBook.MemberID=mBook.Operator then '账薄创建者'");
		sql.append(" else role.roleName end),'') AS roleName,");
		sql.append(" IFNULL(org.name,'') AS orgName,");
		sql.append(" IFNULL(mem.name,'') AS memName,");
		sql.append(" IFNULL(dep.partname,'') AS depName");
		sql.append(" FROM biz_orgmemberbook mBook");
		sql.append(" INNER JOIN biz_organization org on mBook.orgid = org.id and org.Enable = 1");
		sql.append(" INNER JOIN biz_member mem on mBook.memberid = mem.id");
		sql.append(" INNER JOIN mnt_mngandusers mmu on mem.id = mmu.userMemberId and mmu.mntMemberId = '"
				+ orgId + "' and mmu.state = 1 ");
		sql.append(" left JOIN mnt_departmentInfo dep on mBook.depid = dep.id");
		sql.append(" left JOIN biz_accountbook accbook on mBook.bookid = accbook.id and accbook.Enable = 1");
		sql.append(" left JOIN sys_role role on mBook.RoleID = role.id");
		sql.append(" WHERE mBook.operator = ? order by mBook.stamp desc,mem.name,role.roleName");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, userId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public boolean delDisp(int mBookId) {
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" DELETE FROM biz_orgmemberbook");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, mBookId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public int updateDisp(int mBookId, int depId, int empId) {
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" update biz_orgmemberbook");
			sql.append(" set MemberID = ?,DepID = ?");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, empId);
			query.setInteger(1, depId);
			query.setInteger(2, mBookId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			if ("ConstraintViolationException".equals(e.getClass()
					.getSimpleName())) {
				return 2;
			}
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	@Override
	public boolean saveGrantView(int userId, String userName, String tel,
			JsonNode disp) {
		try {
			final Map<String, Object> map = this.getMemberIdByTel(tel);
			final boolean result = Boolean.parseBoolean(map.get("result")
					.toString());
			int memberId;
			if (result) {
				memberId = Integer.parseInt(map.get("memberId").toString());
			} else {
				return false;// 没有此用户信息
			}
			final char code = this.checkDispRepeat(2, memberId,
					disp.get("orgId").asInt(), disp.get("bookId").asInt());
			final StringBuffer sql = new StringBuffer();
			final Query query;
			switch (code) {
			case 0:// 没有重复记录，直接新增
				sql.append(" INSERT into biz_orgmemberbook");
				sql.append(" (MemberID,OrgID,BookID,Record,RoleID,Power,Operator,Stamp)");
				sql.append(" values (?,?,?,?,-1,1,?,SYSDATE())");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, memberId);
				query.setInteger(1, disp.get("orgId").asInt());
				query.setInteger(2, disp.get("bookId").asInt());
				query.setString(3, userName + "分配权限");
				query.setInteger(4, userId);
				query.executeUpdate();
				break;
			case 1:// 有重复记录，但是角色为空，更新RoleId字段
				sql.append(" update biz_orgmemberbook");
				sql.append(" set RoleID = -1");
				sql.append(" WHERE MemberID = ?");
				sql.append(" and OrgID = ?");
				sql.append(" and BookID = ?");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, memberId);
				query.setInteger(1, disp.get("orgId").asInt());
				query.setInteger(2, disp.get("bookId").asInt());
				query.executeUpdate();
				break;
			case 2:// 有重复记录，角色不为空，但是和已有的角色相等，直接return
				break;
			case 3:// 有重复记录，角色不为空，但是和已有的角色不相等，更新RoleId字段
				sql.append(" update biz_orgmemberbook");
				sql.append(" set RoleID = -1");
				sql.append(" WHERE MemberID = ?");
				sql.append(" and OrgID = ?");
				sql.append(" and BookID = ?");
				query = this.getSession().createSQLQuery(sql.toString());
				query.setInteger(0, memberId);
				query.setInteger(1, disp.get("orgId").asInt());
				query.setInteger(2, disp.get("bookId").asInt());
				query.executeUpdate();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return true;
	}

	@Override
	public Map<String, Object> getMemberIdByTel(String tel) {
		final Map<String, Object> map = new HashMap<String, Object>();

		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ID FROM biz_member");
		sql.append(" WHERE Telphone = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, tel);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		final List<Map<String, Object>> list = query.list();
		if (list.size() > 0) {
			final int memberId = Integer.parseInt(list.get(0).get("ID")
					.toString());
			map.put("result", true);
			map.put("memberId", memberId);
		} else {
			map.put("result", false);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getGrantViewList(int userId, int orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IFNULL(mBook.id,'') AS mBookId,IFNULL(accbook.name,'') AS accBookName,");
		sql.append(" IFNULL(mBook.MemberID,'') AS memId,IFNULL(mBook.Operator,'') AS Operator,");
		sql.append(" IFNULL((case when mBook.MemberID=mBook.Operator then '账薄创建者'");
		sql.append(" else role.roleName end),'') AS roleName,");
		sql.append(" IFNULL(org.name,'') AS orgName,");
		sql.append(" IFNULL(mem.name,'') AS memName,");
		sql.append(" IFNULL(dep.partname,'') AS depName");
		sql.append(" FROM biz_orgmemberbook mBook");
		sql.append(" INNER JOIN biz_organization org on mBook.orgid = org.id and org.Enable = 1");
		sql.append(" INNER JOIN biz_member mem on mBook.memberid = mem.id");
		sql.append(" left JOIN mnt_departmentInfo dep on mBook.depid = dep.id");
		sql.append(" left JOIN biz_accountbook accbook on mBook.bookid = accbook.id");
		sql.append(" left JOIN sys_role role on mBook.RoleID = role.id");
		sql.append(" WHERE mBook.operator = ? and mBook.RoleID = -1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, userId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getOrgCoTractByOrgId(String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" mc.cusName AS cusName,");
		sql.append(" mc.id AS id,");
		sql.append(" mc.accStartTime AS accStartTime,");
		sql.append(" mc.accEndTime AS accEndTime,");
		sql.append(" mc.accBookCost AS accBookCost,");
		sql.append(" mc.monthCost AS monthCost,");
		sql.append(" mc.payType AS payType,");
		sql.append(" mc.discount");
		sql.append(" FROM");
		sql.append(" biz_organization bo");
		sql.append(" LEFT JOIN mnt_customContract mc ON bo.mntCustomId = mc.cusId");
		sql.append(" WHERE");
		sql.append(" bo.id = ?");
		sql.append(" AND bo. ENABLE = 1");
		sql.append(" ORDER BY");
		sql.append(" mc.updateTime DESC");
		sql.append(" LIMIT 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, Integer.parseInt(orgId));
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		final Map<String, Object> result = (Map<String, Object>) query
				.uniqueResult();
		return result;
	}

	@Override
	public String getBizMemberIdByOrgId(String orgId) {
		String memberId = "";
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" SELECT ownerId FROM biz_organization org");
			sql.append(" WHERE org.id= '" + orgId + "' and org.Enable = 1");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			final List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list) {
				memberId = map.get("ownerId").toString();
			}
			memberId = AES.Encrypt(memberId, Constant.PACKETKEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberId;
	}

	/**
	 * 重载方法
	 * 
	 * @param telphone
	 * @return
	 */
	@Override
	public MntMember getMemberInfoByCusMobile(String telphone) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT mm.* FROM ");
		sb.append(" mnt_customInfo mc ");
		sb.append(" INNER JOIN mnt_mngandusers mmu ON mc.creater = mmu.userMemberId ");
		sb.append(" INNER JOIN mnt_member mm ON mmu.mntMemberId = mm.id ");
		sb.append(" INNER JOIN mnt_customContract mcc on mc.id = mcc.cusId and mcc.cancleFlag = 0");
		sb.append(" and DATE_FORMAT(mcc.accEndTime,'%y-%m') >= DATE_FORMAT(SYSDATE(),'%y-%m')");
		sb.append(" AND mmu.state = 1 ");
		sb.append(" WHERE ");
		sb.append(" mc.mobile = ? order by mcc.signTime desc limit 1 ");
		final Query query = this.createSqlQuery(sb.toString(), telphone);
		query.setResultTransformer(new AliasToBeanResultTransformer(
				MntMember.class));
		return (MntMember) query.uniqueResult();
	}

	/**
	 * 重载方法
	 * 
	 * @param telphone
	 * @return
	 */
	@Override
	public MntMember getMemberInfoByOrgID(int bookId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT mm.* FROM ");
		sb.append(" biz_organization mc ");
		sb.append(" INNER JOIN mnt_mngandusers mmu ON mc.ownerId = mmu.userMemberId ");
		sb.append(" INNER JOIN mnt_member mm ON mmu.mntMemberId = mm.id ");
		sb.append(" INNER JOIN mnt_customContract mcc on mc.mntCustomId = mcc.cusId and mcc.cancleFlag = 0");
		sb.append(" and DATE_FORMAT(mcc.accEndTime,'%y-%m') >= DATE_FORMAT(SYSDATE(),'%y-%m')");
		sb.append(" AND mmu.state = 1 ");
		sb.append(" WHERE ");
		sb.append(" mc.id = ? order by mcc.signTime desc limit 1 ");
		final Query query = this.createSqlQuery(sb.toString(), bookId);
		query.setResultTransformer(new AliasToBeanResultTransformer(
				MntMember.class));
		return (MntMember) query.uniqueResult();
	}

	@Override
	public boolean saveCustomPay(int orgId, int userId, CustomPayVo vo) {
		try {
			final MntExpenseDetail entity = new MntExpenseDetail();
			entity.setOrgId(vo.getOrgId());
			entity.setPayDate(DateUtil.stringToDate(vo.getPayYear() + "-01-01"));
			entity.setRealAmount(Double.parseDouble(vo.getRealAmount()));
			entity.setPayMonths(vo.getPayMonth());
			entity.setPayAmount(Double.parseDouble(vo.getPayAmount()));
			entity.setDiscount(vo.getDiscount());
			entity.setPayment(Integer.parseInt(vo.getPayment()));
			entity.setPayStamp(new Date());
			entity.setCharger(Integer.toString(userId));
			entity.setDemo(vo.getDemo());
			entity.setBookFee(vo.getIfBookFee());
			entity.setDeleteFlag(0);

			/* 如果缴纳了账本费，则修改合同中账本费的缴费状态[accBookCostPay是否已交账本费] */
			if ("1".equals(vo.getIfBookFee())) {
				updateAccBookCostPay(vo.getConId());
			}
			entity.setAccNo(vo.getAccNO());
			entity.setAccId(vo.getConId());

			if (vo.getPayMonth() != null && !"".equals(vo.getPayMonth())) {
				// 将缴费月份处理成缴费起始月和结束月份
				String startMonth = "";
				String endMonth = "";
				String[] months = vo.getPayMonth().split(",");
				if (months.length == 1) {
					startMonth = formatMonth(months[0]);
					endMonth = formatMonth(months[0]);
				} else {
					startMonth = formatMonth(months[0]);
					endMonth = formatMonth(months[months.length - 1]);// 因为缴费月份是按顺序的
																		// 固最后一个即为缴费结束月份
				}
				entity.setPayFromDate(DateUtil.stringToDate(vo.getPayYear()
						+ "-" + startMonth + "-01"));
				entity.setPayToDate(DateUtil.stringToDate(vo.getPayYear() + "-"
						+ endMonth + "-01"));
			}

			this.save(entity);
			if (vo.isAuditFlag()) {
				int correlationId = entity.getId();
				int auditFlag = auditSettingsService.getAuditFlag(1, orgId);
				int maxAuditLevel = auditSettingsService.getMaxAuditLevel(1,
						orgId);
				auditSettingsService.createAudit(orgId, correlationId, 1,
						auditFlag, maxAuditLevel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	/**
	 * 格式化月份xx eg.06 六月份
	 * 
	 * @param month
	 * @return
	 */
	private String formatMonth(String month) {
		int mon = Integer.parseInt(month);
		if (mon < 11) {
			month = "0" + month;
		}
		return month;
	}

	/**
	 * 如果缴纳了账本费，则修改合同中账本费的缴费状态[accBookCostPay是否已交账本费]
	 * 
	 * @param conids
	 * @return
	 */
	public boolean updateAccBookCostPay(String conid) {
		try {
			final StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_customContract SET accBookCostPay=1 WHERE id in ("
					+ conid + ") ");
			final Query query = this.createSqlQuery(sql.toString());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getTallyList(String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT org.id AS orgId,tax.id AS taxId,IFNULL(org.seqCode,'') AS orgNum,");
		sql.append(" IFNULL(org. NAME,'') AS orgName,IFNULL(cus.mobile,'') AS tel,");
		sql.append(" IFNULL(tax.taxDate,'') AS taxDate,IFNULL(tax.amount,'') AS taxAmount,");
		sql.append(" IFNULL(tax.taxDetail,'') AS taxDetail,IFNULL(tax.isMsg,'') AS isMsg,");
		sql.append(" IFNULL(tax.msgStamp,'') AS msgTime");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN mnt_orgtax tax ON org.ID = tax.orgId");
		sql.append(" WHERE org.id = ? and org.Enable = 1 ORDER BY tax.stamp DESC");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, Integer.parseInt(orgId));
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getTallyInfo(String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT org.id AS orgId,");
		sql.append(" org. NAME AS orgName,");
		sql.append(" cus.mobile AS tel,cus.hiddenValue");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" WHERE org.Id = ? and org.Enable = 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, Integer.parseInt(orgId));
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public boolean getThisMonthTallyFlag(String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM mnt_orgtax");
		sql.append(" WHERE orgId = ?");
		sql.append(" AND taxDate = DATE_FORMAT(SYSDATE(), '%Y-%m')");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, Integer.parseInt(orgId));
		final List list = query.list();
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getTaxDetailList(String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT taxInfo.taxKind AS taxKind,");
		sql.append(" taxInfo.taxName AS taxName,0 AS taxMoney");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_customTaxInfo taxInfo");
		sql.append(" on org.mntCustomId = taxInfo.cusId");
		sql.append(" WHERE org.id = ? and org.Enable = 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, Integer.parseInt(orgId));
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int saveTally(MntOrgtax vo) {
		try {
			if (this.checkTallyTaxDate(vo.getOrgId(), vo.getTaxDate())) {
				return 2;
			}
			vo.setIsMsg(0);
			vo.setStamp(DateUtil.getCurDateTime());
			this.save(vo);
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return 1;
		}
		return 0;
	}

	@Override
	public boolean checkTallyTaxDate(int orgId, String taxDate) {
		boolean result = false;
		final String sql = "SELECT * FROM mnt_orgtax WHERE orgId = ? and taxDate = ?";
		final Query query = this.getSession().createSQLQuery(sql);
		query.setInteger(0, orgId);
		query.setString(1, taxDate);
		final List list = query.list();
		if (list.size() > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getCreateBookMess(String cusId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" org.id AS orgId,");
		sql.append(" mem.Telphone AS tel,");
		sql.append(" CONCAT(org.NAME,date_format(SYSDATE(),'%Y年%m月%d日'),'账簿') AS name,");
		sql.append(" date_format(SYSDATE(),'%Y-%m-%d') AS createDate");
		sql.append(" FROM");
		sql.append(" biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cost ON org.mntCustomId = cost.id");
		sql.append(" INNER JOIN biz_member mem ON cost.creater = mem.ID");
		sql.append(" WHERE");
		sql.append(" cost.id = ? and org.Enable = 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, cusId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public List<Map<String, Object>> getTaxInfoByTaxId(String taxId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" org.id AS orgId,tax.id AS taxId,IFNULL(org.seqCode, '') AS orgNum,");
		sql.append(" IFNULL(org. NAME, '') AS orgName,IFNULL(mem. NAME, '') AS taxPerson,");
		sql.append(" IFNULL(cus.mobile, '') AS tel,IFNULL(tax.taxDate, '') AS taxDate,");
		sql.append(" IFNULL(tax.amount, '') AS taxAmount,IFNULL(tax.taxDetail, '') AS taxDetail,");
		sql.append(" IFNULL(tax.isMsg, '') AS isMsg,IFNULL(tax.msgStamp, '') AS msgTime");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN mnt_orgtax tax ON org.ID = tax.orgId");
		sql.append(" INNER JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" WHERE tax.id = ? and org.Enable = 1");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, taxId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public boolean updateTaxUpdateMessDate(String taxId) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_orgtax");
			sql.append(" SET msgStamp = SYSDATE(),isMsg = 1");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, Integer.parseInt(taxId));
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> saveFeeNoticeInfoByOrgId(int mngId,
			String orgId, String orgName) {
		Map<String, Object> feeResult = new HashMap<String, Object>();
		try {
			String tel = "";
			String cusName = "";
			String comName = orgName;
			String payMonths = "";
			String payMess = "即将欠费";
			String memTel = "";
			String bMonths = "";
			String eMonths = "";
			final StringBuffer sql = new StringBuffer();
			sql.append(" SELECT tab.*,");
			sql.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<date_format(SYSDATE(),'%Y'),1,");
			sql.append(" IFNULL(group_concat(date_format(con.accStartTime, '%m') SEPARATOR ','),'')) AS BMONTHS,");
			sql.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>date_format(SYSDATE(),'%Y'),12,");
			sql.append(" IFNULL(group_concat(date_format(con.accEndTime, '%m') SEPARATOR ','),'')) AS EMONTHS");
			sql.append(" FROM(SELECT");
			sql.append(" IFNULL(cus.mobile,'') AS tel,");
			sql.append(" IFNULL(org.name,'') AS cusName,");
			sql.append(" IFNULL(cus.ID, '') AS cusId,");
			sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS payMonths,");
			sql.append(" IFNULL(mem.Telphone,'') AS memTel");
			sql.append(" FROM");
			sql.append(" biz_organization org");
			sql.append(" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.deleteFlag = 0");
			sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
			sql.append(" INNER JOIN biz_member mem ON org.ownerId = mem.ID");
			sql.append(" WHERE org.id = ? and org.Enable = 1 and (date_format(exp.payDate,'%Y') = date_format(SYSDATE(),'%Y') or exp.payDate is null)");
			sql.append(" GROUP BY cus.mobile,org.name,mem.Telphone) tab");
			sql.append(" LEFT JOIN mnt_customContract con ON tab.cusId = con.cusId");
			sql.append(" AND DATE_FORMAT(con.accStartTime, '%Y') <= date_format(SYSDATE(),'%Y')");
			sql.append(" AND DATE_FORMAT(con.accEndTime, '%Y') >= date_format(SYSDATE(),'%Y')");
			sql.append(" GROUP BY tab.tel,tab.cusName,tab.memTel");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, Integer.parseInt(orgId));
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			Map<String, Object> orgInfo = (Map<String, Object>) query
					.uniqueResult();
			tel = orgInfo.get("tel").toString();
			cusName = orgInfo.get("cusName").toString();
			payMonths = orgInfo.get("payMonths").toString();
			memTel = orgInfo.get("memTel").toString();
			bMonths = orgInfo.get("BMONTHS").toString();
			eMonths = orgInfo.get("EMONTHS").toString();
			if (this.checkConPayMonths(bMonths, eMonths, payMonths)) {
				if (this.checkThisMonthFee(orgId)) {
					Map<String, Object> map = SMSSender.sendfeeNotice(tel,
							new String[] { cusName + ",", comName,
									payMess + ",", memTel });
					feeResult.put("orgId", orgId);
					feeResult.put("cusName", cusName);
					feeResult.put("tel", tel);
					feeResult.put("statusCode", map.get("statusCode"));
					feeResult.put("mess",
							SMSSender.messCode.get(map.get("statusCode")));
					this.saveFeeNum(orgId);
//不插入MntNews表，改为插入Mnt_Message表
//					MntNews news = new MntNews();
//					news.setContext("尊敬的客户,您在" + comName
//							+ "的代理记账业务将在本月欠费,请您提前续费,以免耽误您公司的记账报税业务.详情请咨询:"
//							+ memTel);
//					news.setCreater(0000);
//					news.setCreaterName("system");
//					news.setCreateTime(new Date());
//					news.setCusId(orgId);
//					news.setIfPushCus("on");
//					news.setOrgId(mngId);
//					news.setStamp(DateUtil.getCurDateTimeForString());
//					news.setTitle("欠费信息");
//					this.save(news);
					
					MntMessage message = new MntMessage();
					message.setMessage("尊敬的客户,您在" + comName
							+ "的代理记账业务将在本月欠费,请您提前续费,以免耽误您公司的记账报税业务.详情请咨询:"
							+ memTel);
					message.setMngid(Integer.parseInt(orgId));
					message.setStamp(new Date().toString());
					message.setIsread(0);
					message.setTabname("欠费短信信息");
					message.setType(6);
					this.save(message);
					
					
				} else {
					feeResult.put("orgId", orgId);
					feeResult.put("cusName", cusName);
					feeResult.put("tel", tel);
					feeResult.put("statusCode", "888888");
					feeResult.put("mess", "本月催费次数已达到上限！");
				}
			} else {
				feeResult.put("orgId", orgId);
				feeResult.put("cusName", cusName);
				feeResult.put("tel", tel);
				feeResult.put("statusCode", "999999");
				feeResult.put("mess", "此客户未欠费！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feeResult;
	}

	@Override
	public boolean checkConPayMonths(String bMonths, String eMonths,
			String payMonths) {
		List<Integer> conMonthList = new ArrayList<Integer>();
		String[] bMonthArr = bMonths.split(",");
		String[] eMonthsArr = eMonths.split(",");
		int length = bMonthArr.length;
		for (int i = 0; i < length; i++) {
			if (StringUtils.isEmpty(bMonthArr[i])) {
				continue;
			}
			int begin = Integer.parseInt(bMonthArr[i]);
			int end = 12;
			/*
			 * 由于bMonths与eMonths长度不一定相同,因此此处会数组越界异常,又因 此处代码被多处调用不能轻易修改
			 * 固做异常处理,如果越界则取最后逗号后最后一位
			 */
			try {
				end = Integer.parseInt(eMonthsArr[i]);
			} catch (ArrayIndexOutOfBoundsException e) {
				end = Integer.parseInt(eMonthsArr[eMonthsArr.length - 1]);
			}

			for (int j = begin; j <= end; j++) {
				conMonthList.add(j);
			}
		}
		HashSet hashSet = new HashSet(conMonthList);
		conMonthList.clear();
		conMonthList.addAll(hashSet);
		Collections.sort(conMonthList);
		int thisMonth = Integer.parseInt(new SimpleDateFormat("MM")
				.format(new Date()));
		String[] payMonthsArray = payMonths.split(",");
		for (Integer conMonth : conMonthList) {
			if (conMonth == thisMonth) {
				for (String payMonth : payMonthsArray) {
					if ("".equals(payMonth)) {
						return true;
					}
					if (thisMonth == Integer.parseInt(payMonth)) {
						return false;
					} else {
						continue;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkThisMonthFee(String orgId) {
		int feeNum = 0;
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" COUNT(*) AS feeNum");
		sql.append(" FROM");
		sql.append(" mnt_feeMessNum");
		sql.append(" WHERE");
		sql.append(" feeMonth = DATE_FORMAT(SYSDATE(), '%Y-%m')");
		sql.append(" AND orgId =?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, orgId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list) {
			feeNum = Integer.parseInt(map.get("feeNum").toString());
		}
		if (feeNum < 4) {
			return true;
		}
		return false;
	}

	@Override
	public void saveFeeNum(String orgId) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" INSERT INTO");
			sql.append(" mnt_feeMessNum");
			sql.append(" (feeMonth,orgId)");
			sql.append(" VALUES");
			sql.append(" (DATE_FORMAT(SYSDATE(),'%Y-%m'),?)");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, Integer.parseInt(orgId));
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
	}

	@Override
	public boolean checkContractDate(int id, String cusId, String bDate,String eDate, String flag) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) AS conNum FROM");
		sql.append(" mnt_customContract");
		sql.append(" WHERE cusId = ?");
		sql.append(" AND (? BETWEEN DATE_FORMAT(accStartTime, '%Y-%m') AND DATE_FORMAT(accEndTime, '%Y-%m')");
		sql.append(" OR ? BETWEEN DATE_FORMAT(accStartTime, '%Y-%m') AND DATE_FORMAT(accEndTime, '%Y-%m')");
		sql.append(" OR DATE_FORMAT(accStartTime, '%Y-%m') BETWEEN ? AND ? OR DATE_FORMAT(accEndTime, '%Y-%m') BETWEEN ? AND ?)");
		if ("update".equals(flag)) {
			sql.append(" AND id <> ?");
		}
		System.out.println(sql);
		final Query query = this.getSession().createSQLQuery(sql.toString());

		query.setParameter(0, cusId);
		query.setParameter(1, bDate);
		query.setParameter(2, eDate);
		query.setParameter(3, bDate);
		query.setParameter(4, eDate);
		query.setParameter(5, bDate);
		query.setParameter(6, eDate);
		if ("update".equals(flag)) {
			query.setParameter(7, id);
		}
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list) {
			if (Integer.parseInt(map.get("conNum").toString()) > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Map<String, Map<String, Object>> getContractMonthCost(String conId,
			String payYear) {
		Map<String, Map<String, Object>> conCostInfoMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> monthCostMap = new HashMap<String, Object>();
		Map<String, Object> bookCostMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<?,1,");
		sql.append(" IFNULL(date_format(con.accStartTime, '%m'),'')) AS bMonth,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>?,12,");
		sql.append(" IFNULL(date_format(con.accEndTime, '%m'),'')) AS eMonth,");
		sql.append(" con.monthCost AS mCost,");
		sql.append(" con.discount AS dis,");
		sql.append(" con.accBookCost AS bCost,");
		sql.append(" con.accBookCostPay AS ifPayBF");
		sql.append(" from mnt_customContract con");
		sql.append(" where con.id= ? ");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setString(0, payYear);
		query.setString(1, payYear);
		query.setString(2, conId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> queryList = query.list();
		BigDecimal bCost = new BigDecimal("0");
		Map<String, Object> map = queryList.get(0);
		int bMonth = Integer.parseInt(map.get("bMonth").toString());
		int eMonth = Integer.parseInt(map.get("eMonth").toString());
		String mCost = map.get("mCost").toString();
		String dis = map.get("dis").toString();
		if (!"".equals(map.get("ifPayBF").toString())
				&& "0".equals(map.get("ifPayBF").toString())) {// 如果没交账本费
			bCost = new BigDecimal(map.get("bCost").toString());
		}
		for (int i = bMonth; i <= eMonth; i++) {
			monthCostMap.put("month" + i, mCost + "," + dis);
		}

		bookCostMap.put("bCost", bCost);// 该合同的账本费
		conCostInfoMap.put("mCostMap", monthCostMap);
		conCostInfoMap.put("bCostMap", bookCostMap);
		return conCostInfoMap;
	}

	// @Override
	// public Map<String, Map<String, Object>> getContractMonthCost(String
	// orgId, String payYear)
	// {
	// Map<String, Map<String, Object>> conCostInfoMap = new HashMap<String,
	// Map<String, Object>>();
	// Map<String, Object> monthCostMap = new HashMap<String, Object>();
	// Map<String, Object> bookCostMap = new HashMap<String, Object>();
	// StringBuffer sql = new StringBuffer();
	// sql.append(" SELECT");
	// sql.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<?,1,");
	// sql.append(" IFNULL(date_format(con.accStartTime, '%m'),'')) AS bMonth,");
	// sql.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>?,12,");
	// sql.append(" IFNULL(date_format(con.accEndTime, '%m'),'')) AS eMonth,");
	// sql.append(" con.monthCost AS mCost,");
	// sql.append(" con.discount AS dis,");
	// sql.append(" con.accBookCost AS bCost");
	// sql.append(" FROM biz_organization org");
	// sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
	// sql.append(" INNER JOIN mnt_customContract con ON cus.id = con.cusId");
	// //并且该合同账本费未交 则页面账本费计算在内
	// sql.append(" WHERE org.id = ? and org.Enable = 1 AND con.accBookCostPay =0 AND DATE_FORMAT(con.accStartTime,'%Y') <= ? AND DATE_FORMAT(con.accEndTime,'%Y') >= ?");
	// final Query query = this.getSession().createSQLQuery(sql.toString());
	// query.setString(0, payYear);
	// query.setString(1, payYear);
	// query.setInteger(2, Integer.parseInt(orgId));
	// query.setString(3, payYear);
	// query.setString(4, payYear);
	// query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	// List<Map<String, Object>> queryList = query.list();
	// BigDecimal bCost = new BigDecimal("0");
	// for (Map<String, Object> map : queryList)
	// {
	// int bMonth = Integer.parseInt(map.get("bMonth").toString());
	// int eMonth = Integer.parseInt(map.get("eMonth").toString());
	// String mCost = map.get("mCost").toString();
	// String dis = map.get("dis").toString();
	// bCost = bCost.add(new BigDecimal(map.get("bCost").toString()));
	// for (int i = bMonth; i <= eMonth; i++)
	// {
	// monthCostMap.put("month" + i, mCost + "," + dis);
	// }
	// }
	// bookCostMap.put("bCost", bCost);
	// conCostInfoMap.put("mCostMap", monthCostMap);
	// conCostInfoMap.put("bCostMap", bookCostMap);
	// return conCostInfoMap;
	// }
	//
	@Override
	public LoginVo getConPayInfo(String mMemId, String bMemId, String date) {
		LoginVo lVo = new LoginVo();
		/**
		 * 新进公司+总公司数目
		 */
		StringBuffer sqlA = new StringBuffer();
		sqlA.append(" SELECT IFNULL(COUNT(*),0) AS totalCom,");
		sqlA.append(" IFNULL(SUM(CASE WHEN DATE_FORMAT(info.CreateTime, '%Y-%m') = DATE_FORMAT(?, '%Y-%m') THEN");
		sqlA.append(" 1 ELSE 0 END ),0) AS enterCom FROM (SELECT org.CreateTime FROM mnt_member mMem");
		sqlA.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId AND mng.state = 1");
		sqlA.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
		sqlA.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID and org.Enable = 1");
		sqlA.append(" WHERE mMem.id = ?) info");
		final Query queryA = this.getSession().createSQLQuery(sqlA.toString());
		queryA.setParameter(0, date);
		queryA.setInteger(1, Integer.parseInt(mMemId));
		queryA.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> listA = queryA.list();
		for (Map<String, Object> map : listA) {
			lVo.setTotalCom(map.get("totalCom") + "");
			lVo.setEnterCom(map.get("enterCom") + "");
		}
		/**
		 * 预交费查询
		 */
		String month = (new Date().getMonth() + 1) + "";
		try {
			month = new SimpleDateFormat("MM").format(new SimpleDateFormat(
					"yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			NestLogger.showException(e);
		}
		StringBuffer sqlB = new StringBuffer();
		sqlB.append(" SELECT COUNT(*) AS prePayNum FROM mnt_member mMem");
		sqlB.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId");
		sqlB.append(" AND mng.state = 1");
		sqlB.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
		sqlB.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID and org.Enable = 1");
		sqlB.append(" INNER JOIN mnt_expenseDetail exp ON exp.orgId = org.ID  AND exp.deleteFlag = 0");
		sqlB.append(" WHERE mMem.id = ?");
		sqlB.append(" AND exp.payMonths LIKE ? AND DATE_FORMAT(?, '%Y') = DATE_FORMAT(exp.payDate, '%Y')");
		final Query queryB = this.getSession().createSQLQuery(sqlB.toString());
		queryB.setParameter(0, mMemId);
		queryB.setParameter(1, "%" + month + "%");
		queryB.setParameter(2, date);
		queryB.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> listB = queryB.list();
		for (Map<String, Object> map : listB) {
			lVo.setPrePayNum(Integer.parseInt(map.get("prePayNum") + ""));
		}
		/**
		 * 欠费查询
		 */
		StringBuffer sqlC = new StringBuffer();
		// sqlC.append(" SELECT tab.*,");
		// sqlC.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<date_format(SYSDATE(),'%Y'),1,");
		// sqlC.append(" IFNULL(group_concat(date_format(con.accStartTime, '%m') SEPARATOR ','),'')) AS BMONTHS,");
		// sqlC.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>date_format(SYSDATE(),'%Y'),12,");
		// sqlC.append(" IFNULL(group_concat(date_format(con.accEndTime, '%m') SEPARATOR ','),'')) AS EMONTHS");
		// sqlC.append(" FROM(SELECT");
		// sqlC.append(" org.id,IFNULL(group_concat(exp.payMonths order by exp.payMonths separator ','), '') AS payMonths,");
		// sqlC.append(" IFNULL(cus.ID, '') AS cusId");
		// sqlC.append(" FROM mnt_member mMem");
		// sqlC.append(" INNER JOIN mnt_mngandusers mng ON mMem.id = mng.mntMemberId");
		// sqlC.append(" AND mng.state = 1");
		// sqlC.append(" INNER JOIN biz_member bMem ON bMem.ID = mng.userMemberId");
		// sqlC.append(" INNER JOIN biz_organization org ON org.ownerId = bMem.ID and org.Enable = 1");
		// sqlC.append(" INNER JOIN mnt_customInfo cus on cus.id = org.mntCustomId");
		// sqlC.append(" LEFT JOIN mnt_expenseDetail exp ON exp.orgId = org.ID  AND exp.deleteFlag = 0");
		// sqlC.append(" and date_format(exp.payDate,'%Y') = date_format(SYSDATE(),'%Y')");
		// sqlC.append(" WHERE mMem.id = ?");
		// if (bMemId != null)
		// {
		// sqlC.append(" and bMem.id = ?");
		// }
		// sqlC.append(" GROUP BY org.id) tab");
		// sqlC.append(" LEFT JOIN mnt_customContract con ON tab.CUSID = con.cusId");
		// sqlC.append(" AND DATE_FORMAT(con.accStartTime, '%Y') <= date_format(SYSDATE(),'%Y') AND DATE_FORMAT(con.accEndTime, '%Y') >= date_format(SYSDATE(),'%Y')");
		// sqlC.append(" WHERE con.accStartTime IS NOT NULL");
		// sqlC.append(" GROUP BY tab.id");
		sqlC.append(" select con.id AS CONID , con.ACCNO as CONACCNO, con.cusId as CUSID,con.cusName AS CUSNAME,DATE_FORMAT(con.accStartTime,'%Y%m') AS ACCSTARTTIME,");
		sqlC.append(" DATE_FORMAT(con.accEndTime,'%Y%m')  AS ACCENDTIME, exp.accId AS EXPCONID,exp.accNo AS EXPACCNO ,DATE_FORMAT(exp.paydate,'%Y')  AS EXPPAYDATE ,exp.payMonths AS EXPPAYMONTHS");
		sqlC.append(" from  mnt_customContract con   left join mnt_expenseDetail exp   on exp.accId=con.Id");
		sqlC.append(" LEFT JOIN  mnt_customInfo cus  ON  con.cusId = cus.id");
		sqlC.append(" LEFT JOIN  biz_organization org  ON  org.mntCustomId = cus.id");
		sqlC.append(" LEFT JOIN  biz_member mem ON org.ownerId = mem.ID and org.Enable = 1 ");
		sqlC.append(" LEFT JOIN  mnt_mngandusers mng  ON  mem.ID = mng.userMemberId");
		sqlC.append(" LEFT JOIN  mnt_member mMem  ON mMem.id = mng.mntMemberId  AND mng.state = 1");
		sqlC.append(" AND exp.deleteFlag = 0 AND exp.orgId = org.ID");
		sqlC.append(" where DATE_FORMAT(exp.paydate,'%Y')<=date_format(SYSDATE(),'%Y')");
		sqlC.append(" AND mMem.id = ? ");
		if (bMemId != null) {
			sqlC.append(" and mem.id = ?");
		}
		final Query queryC = this.getSession().createSQLQuery(sqlC.toString());
		queryC.setInteger(0, Integer.parseInt(mMemId));
		if (bMemId != null) {
			queryC.setInteger(1, Integer.parseInt(bMemId));
		}
		queryC.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> listC = queryC.list();

		// 将所有cusid+合同年月存入conShouldPayNoSet中，将所有cusid+已交年月存入conPayNoSet
		Set<String> conShouldPayNoSet = new HashSet<String>();
		Set<String> conPayNoSet = new HashSet<String>();
		Set<String> cusIdSet = new HashSet<String>();// 最后的cusIdSet
		for (Map<String, Object> map : listC) {
			String cusId = map.get("CUSID").toString();// 客户ID
			String bYMonths = map.get("ACCSTARTTIME").toString();// 合同起始年月201507
			String eYMonths = map.get("ACCENDTIME").toString();// 合同截止年月201605
			String expPayYear = map.get("EXPPAYDATE").toString();// yyyy
			String expPayMonth = map.get("EXPPAYMONTHS").toString();// x,x,x,x,
			// 计算合同年月set
			conShouldPayNoSet = addConShouldPayNoSet(cusId, bYMonths, eYMonths,
					conShouldPayNoSet);//
			if (expPayMonth != null && !"".equals(expPayMonth)) {
				conPayNoSet = addConPayNoSet(cusId, expPayYear, expPayMonth,
						conPayNoSet);
			}
		}
		// 将已缴费的cusid+已交年月存conPayNoSet 从所有cusid+合同年月存入conShouldPayNoSet中移除
		for (String shouldPay : conPayNoSet) {
			conShouldPayNoSet.remove(shouldPay);
		}
		for (String noPay : conShouldPayNoSet) {
			cusIdSet.add(noPay.substring(0, 16));
		}

		lVo.setArrPayNum(cusIdSet.size());

		StringBuffer sqlD = new StringBuffer();
		sqlD.append(" SELECT");
		sqlD.append(" COUNT(*)");
		sqlD.append(" FROM");
		sqlD.append(" biz_organization boz");
		sqlD.append(" INNER JOIN biz_member bm ON boz.ownerId = bm.ID");
		sqlD.append(" INNER JOIN mnt_mngandusers mng ON bm.id = mng.mntMemberId AND mng.state = 1");
		sqlD.append(" INNER JOIN mnt_member mmb on mng.mntMemberId = mmb.id");
		sqlD.append(" WHERE boz.CreateTime < SUBDATE(CURDATE(),INTERVAL 3 month) and mmb.id = ? and boz.Enable = 1");
		final Query queryD = this.getSession().createSQLQuery(sqlD.toString());
		queryD.setParameter(0, Integer.parseInt(mMemId));
		BigInteger num = (BigInteger) queryD.uniqueResult();
		int number = num.intValue();
		int outNum = Integer.parseInt(lVo.getTotalCom()) - number;
		if (outNum > 0) {
			lVo.setOutCom(outNum + "");
		} else {
			lVo.setOutCom("0");
		}
		return lVo;
	}

	/**
	 * 将所有cusid+已交年月存入conPayNoSet
	 * 
	 * @param cusId
	 * @param expPayYear
	 * @param expPayMonth
	 *            x,x,x,x
	 * @param conPayNoSet
	 * @return
	 */
	private Set addConPayNoSet(String cusId, String expPayYear,
			String expPayMonth, Set conPayNoSet) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份

		// 如果缴费年份大于当前年则跳过
		if (Integer.parseInt(expPayYear) > currYear) {
			return conPayNoSet;
		}
		String[] expPayMons = expPayMonth.split(",");
		for (String expPayMon : expPayMons) {
			if (!"".equals(expPayMon)) {
				conPayNoSet.add(cusId + expPayYear + expPayMon);
			}
		}
		return conPayNoSet;
	}

	/**
	 * //将所有cusid+合同年月存入conShouldPayNoSet中
	 * 
	 * @param cusId
	 * @param conShouldPayNoSet
	 * @param bYMonths201507
	 * @param eYMonths201605
	 * @return
	 */
	private Set addConShouldPayNoSet(String cusId, String bYMonths,
			String eYMonths, Set conShouldPayNoSet) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");// 设置日期格式
		String today = df.format(new Date());// new Date()为获取当前系统年月
		int currYear = Integer.parseInt(today.substring(0, 4));// 当前年
		int currMonth = Integer.parseInt(today.substring(5, 7));// 当前月份

		if (bYMonths.length() == 6 && eYMonths.length() == 6) {
			String bYear = bYMonths.substring(0, 4);// 起始年
			String bMonth = bYMonths.substring(4, 6);// 起始月
			if ("0".equals(bMonth.substring(0, 1))) {
				bMonth = bMonth.substring(1, 2);
			}
			String eYear = eYMonths.substring(0, 4);// 截止年
			String eMonth = eYMonths.substring(4, 6);// 截止月
			if ("0".equals(eMonth.substring(0, 1))) {
				eMonth = eMonth.substring(1, 2);
			}
			// 如果截至年大于当前年则按当前年作为截至年当前月作为截至月
			if (currYear < Integer.parseInt(eYear)) {
				eYear = "" + currYear;
				eMonth = "" + currMonth;
			}

			for (int i = Integer.parseInt(bYear); i < Integer.parseInt(eYear) + 1; i++) {
				if (i == Integer.parseInt(eYear)) {
					for (int j = Integer.parseInt(bMonth); j < Integer
							.parseInt(eMonth) + 1; j++) {
						conShouldPayNoSet.add(cusId + i + j);
					}
				} else {
					for (int j = 1; j < 13; j++) {// cusID20157 cusID20167
						conShouldPayNoSet.add(cusId + i + j);
					}
				}
			}
		}
		return conShouldPayNoSet;
	}

	@Override
	public boolean updateTally(String taxId, String amount, String taxDetail) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_orgtax");
			sql.append(" SET amount = ?,");
			sql.append(" taxDetail = ?");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, Integer.parseInt(amount));
			query.setString(1, taxDetail);
			query.setInteger(2, Integer.parseInt(taxId));
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getMainFollowInfo(int userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer listSql = new StringBuffer();
		listSql.append(" SELECT");
		listSql.append(" org.id AS orgid,");
		listSql.append(" cus.id AS cusId,");
		listSql.append(" org.seqCode AS orgNum,");
		listSql.append(" org. NAME AS orgName,");
		listSql.append(" fol.followTime AS folDate");
		listSql.append(" FROM biz_organization org");
		listSql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		listSql.append(" INNER JOIN mnt_customFollowInfo fol ON cus.id = fol.cusId");
		listSql.append(" WHERE fol.followTime = DATE_FORMAT(SYSDATE(), '%Y-%m-%d') AND isRead=0");
		listSql.append(" AND fol.emId = ? and org.Enable = 1 GROUP BY org.id,cus.id,org.seqCode,org. NAME,fol.followTime");
		listSql.append(" ORDER BY fol.followTime limit 0,4");
		final Query queryFolList = this.getSession().createSQLQuery(
				listSql.toString());
		queryFolList.setInteger(0, userId);
		queryFolList.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> folList = queryFolList.list();
		StringBuffer numSql = new StringBuffer();
		numSql.append(" SELECT");
		numSql.append(" COUNT(*) AS folNum");
		numSql.append(" FROM");
		numSql.append(" biz_organization org");
		numSql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		numSql.append(" INNER JOIN mnt_customFollowInfo fol ON cus.id = fol.cusId");
		numSql.append(" WHERE");
		numSql.append(" fol.followTime = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')");
		numSql.append(" AND isRead = 0 and org.Enable = 1");
		numSql.append(" AND fol.emId = ?");
		final Query queryFolTotal = this.getSession().createSQLQuery(
				numSql.toString());
		queryFolTotal.setInteger(0, userId);
		queryFolTotal.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> folNumList = queryFolTotal.list();
		if (folNumList != null) {
			result.put("folTotal", folNumList.get(0).get("folNum"));
		} else {
			result.put("folTotal", 0);
		}
		result.put("folList", folList);
		return result;
	}

	@Override
	public boolean updateFollow(String cusId, int userId) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_customFollowInfo");
			sql.append(" SET isRead = 1");
			sql.append(" WHERE followTime = DATE_FORMAT(SYSDATE(), '%Y-%m-%d')");
			sql.append(" AND emId = ?");
			sql.append(" AND cusId = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setParameter(0, userId);
			query.setParameter(1, cusId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public PageUtil getMoreFollowList(int userId, String orgName,
			String followTimeBegin, String followTimeEnd, String isRead,
			PageInfo info) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" org.id AS orgId,");
		sql.append(" cus.id AS cusId,");
		sql.append(" org.seqCode AS orgNum,");
		sql.append(" org. NAME AS orgName,");
		sql.append(" fol.id AS followId,");
		sql.append(" fol.followTime AS followTime,");
		sql.append(" fol.content AS content,");
		sql.append(" fol.contacts AS contacts,");
		sql.append(" fol.tel AS tel,");
		sql.append(" fol.isRead AS isRead");
		sql.append(" FROM");
		sql.append(" biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN mnt_customFollowInfo fol ON cus.id = fol.cusId");
		sql.append(" WHERE fol.emId = ? and org.Enable = 1");
		if (!"".equals(orgName) && orgName != null) {
			sql.append(" AND org. NAME LIKE '%" + orgName + "%'");
		}
		if (!"".equals(followTimeBegin) && followTimeBegin != null) {
			sql.append(" AND fol.followTime >= '" + followTimeBegin + "'");
		}
		if (!"".equals(followTimeEnd) && followTimeEnd != null) {
			sql.append(" AND fol.followTime <= '" + followTimeEnd + "'");
		}
		if (!"".equals(isRead) && isRead != null) {
			sql.append(" AND fol.isRead = '" + isRead + "'");
		}
		sql.append(" ORDER BY");
		sql.append(" fol.followTime DESC");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows(), userId);
	}

	@Override
	public boolean updateSingleFollow(int followId) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_customFollowInfo");
			sql.append(" SET isRead = 1");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, followId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateReloadSingleFollow(int followId) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" UPDATE mnt_customFollowInfo");
			sql.append(" SET isRead = 0");
			sql.append(" WHERE id = ?");
			final Query query = this.getSession()
					.createSQLQuery(sql.toString());
			query.setInteger(0, followId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return false;
		}
		return true;
	}

	@Override
	public String getPayMonths(String orgId, String payYear) {
		String payMonths = "";
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT group_concat(payMonths ORDER BY payMonths SEPARATOR ',') AS PAYMONTHS");
		sql.append(" FROM mnt_expenseDetail exp");
		sql.append(" WHERE DATE_FORMAT(payDate, '%Y') = ?");
		sql.append(" AND exp.orgId = ?");
		sql.append(" AND exp.deleteFlag = 0");
		sql.append(" GROUP BY exp.orgId");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, payYear);
		query.setParameter(1, orgId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if (list != null) {
			for (Map<String, Object> map : list) {
				payMonths = map.get("PAYMONTHS").toString();
			}
		}
		return payMonths;
	}

	@Override
	public PageUtil getAuditList(PageInfo info, boolean isAdmin, int userId,
			int mngId, String cusName, String bDate, String eDate) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" org.ID AS orgId,");
		sql.append(" exp.id AS expId,");
		sql.append(" ar.id AS routeId,");
		sql.append(" DATE_FORMAT(exp.payStamp, '%Y-%m-%d') AS payDate,");
		sql.append(" org.name AS orgName,");
		sql.append(" CONCAT(DATE_FORMAT(exp.payDate, '%Y'),'年',exp.payMonths,'月') AS paySection,");
		sql.append(" exp.bookFee,");
		sql.append(" realAmount,");
		sql.append(" ar.auditFlag,");
		sql.append(" ar.id AS opera");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_expenseDetail exp ON exp.orgId = org.ID AND exp.deleteFlag = 0");
		sql.append(" INNER JOIN mnt_auditRoute ar ON ar.correlationId = exp.id");
		sql.append(" WHERE ar.mngId= ? and org.Enable = 1");
		if (!isAdmin) {
			int auditorLevel = auditSettingsService.getAuditorLevel(userId,
					mngId, 1);
			sql.append(" AND (auditFlag >= '" + auditorLevel
					+ "' or auditFlag = 0)");
		}
		if (!"".equals(cusName) && cusName != null) {
			sql.append(" AND org.name like '%" + cusName + "%'");
		}
		if (!"".equals(bDate) && bDate != null) {
			sql.append(" AND exp.payStamp >= '" + bDate + "'");
		}
		if (!"".equals(eDate) && eDate != null) {
			sql.append(" AND exp.payStamp <= '" + eDate + "'");
		}
		sql.append(" ORDER BY ar.auditFlag desc,exp.payStamp desc");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows(), mngId);
	}

	@Override
	public PageUtil getAuditListByOrg(PageInfo info, boolean isAdmin,
			int userId, int mngId, String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" org.ID AS orgId,");
		sql.append(" exp.id AS expId,");
		sql.append(" ar.id AS routeId,");
		sql.append(" DATE_FORMAT(exp.payStamp, '%Y-%m-%d') AS payDate,");
		sql.append(" org.name AS orgName,");
		sql.append(" CONCAT(DATE_FORMAT(exp.payDate, '%Y'),'年',exp.payMonths,'月') AS paySection,");
		sql.append(" exp.bookFee,");
		sql.append(" realAmount,");
		sql.append(" ar.auditFlag,");
		sql.append(" ar.id AS opera");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_expenseDetail exp ON exp.orgId = org.ID AND exp.deleteFlag = 0");
		sql.append(" INNER JOIN mnt_auditRoute ar ON ar.correlationId = exp.id");
		sql.append(" WHERE ar.mngId= ? AND org.id = ? and org.Enable = 1");
		if (!isAdmin) {
			int auditorLevel = auditSettingsService.getAuditorLevel(userId,
					mngId, 1);
			sql.append(" AND (auditFlag >= '" + auditorLevel
					+ "' or auditFlag = 0)");
		}
		sql.append(" ORDER BY ar.auditFlag desc,exp.payStamp desc");
		return this.findPageBySqlQuery(sql.toString(), info.getPage(),
				info.getRows(), mngId, orgId);
	}

	@Override
	public List<Map<String, Object>> getAutoSendfeeOrgInfo() {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT org.ID AS orgId,mMem.orgName,mMem.id as mngId");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN biz_member bMem ON org.ownerId = bMem.ID");
		sql.append(" INNER JOIN mnt_mngandusers mng ON bMem.ID = mng.userMemberId");
		sql.append(" INNER JOIN mnt_member mMem ON mMem.id = mng.mntMemberId");
		sql.append(" WHERE org. ENABLE = 1 AND mng.state = 1");
		sql.append(" ORDER BY org.ID");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public boolean conCanEdit(String conStart, String conEnd, String cusId,String ifPayBK) {
		boolean result = true;
		Map<String, Object> map = new HashMap<String, Object>();
		int conBYear = Integer.parseInt(conStart.split("-")[0]);
		int conBMonth = Integer.parseInt(conStart.split("-")[1]);
		int conEYear = Integer.parseInt(conEnd.split("-")[0]);
		int conEMonth = Integer.parseInt(conEnd.split("-")[1]);
		if (conBYear == conEYear) {
			for (int i = conBMonth; i <= conEMonth; i++) {
				String tempDate = conBYear + "-" + i;
				map.put(tempDate, tempDate);
			}
		} else {
			for (int i = conBYear; i <= conEYear; i++) {
				if (i == conBYear && i != conEYear) {
					for (int j = conBMonth; j <= 12; j++) {
						String tempDate = i + "-" + j;
						map.put(tempDate, tempDate);
					}
				} else if (i != conBYear && i != conEYear) {
					for (int j = 1; j <= 12; j++) {
						String tempDate = i + "-" + j;
						map.put(tempDate, tempDate);
					}
				} else if (i != conBYear && i == conEYear) {
					for (int j = 1; j <= conEMonth; j++) {
						String tempDate = i + "-" + j;
						map.put(tempDate, tempDate);
					}
				}
			}
		}
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" DATE_FORMAT(exp.payDate, '%Y') AS payYear,");
		sql.append(" exp.payMonths");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_expenseDetail exp ON org.ID = exp.orgId");
		sql.append(" WHERE org.mntCustomId = '" + cusId + "'");
		sql.append(" AND exp.deleteFlag = 0;");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> exp : list) {
			String payYear = exp.get("payYear").toString();
			// 如果缴费月份为空 则说明该条缴纳的费用只有账本费
			if (exp.get("payMonths") == null || "".equals(exp.get("payMonths"))) {
				if (ifPayBK == "1") {
					result = false;// 如果缴纳了账本费 本合同也不可修改
				}
			} else {
				String[] payMonths = exp.get("payMonths").toString().split(",");
				for (String payMonth : payMonths) {
					String tempDate = payYear + "-"
							+ Integer.parseInt(payMonth);
					Object obj = map.get(tempDate);
					if (obj != null) {
						result = false;
					}
				}
			}
		}
		return result;
	}

	@Override
	public ResponseEntity<byte[]> downLoadExcel(int mngId, String[] orgIds)
			throws Exception {
		StringBuffer orgIdStr = new StringBuffer();
		for (String orgId : orgIds) {
			orgIdStr.append("'" + orgId + "',");
		}
		orgIdStr.deleteCharAt(orgIdStr.length() - 1);
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" IFNULL(org.seqCode,'') AS '0',");
		sql.append(" IFNULL(org.name,'') AS '1',");
		sql.append(" IFNULL(bMem.name,'') AS '2',");
		sql.append(" IFNULL(cus.mobile,'') AS '3'");
		sql.append(" FROM mnt_departmentInfo dep");
		sql.append(" INNER JOIN biz_member bMem ON dep.id = bMem.departmentId");
		sql.append(" INNER JOIN biz_organization org ON bMem.id = org.ownerId AND org.enable = 1");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN mnt_mngandusers mmu ON bMem.id = mmu.userMemberId AND mmu.mntMemberId = ? and mmu.state = 1");
		sql.append(" WHERE org.id IN (" + orgIdStr + ")");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, mngId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.cellVal(0, 0, 0, "公司编号");
		excelUtil.cellVal(0, 0, 1, "公司名称");
		excelUtil.cellVal(0, 0, 2, "所属主管");
		excelUtil.cellVal(0, 0, 3, "手机号码");
		excelUtil.getWorkBook().getSheetAt(0).setColumnWidth(0, 10000);
		excelUtil.getWorkBook().getSheetAt(0).setColumnWidth(1, 10000);
		excelUtil.getWorkBook().getSheetAt(0).setColumnWidth(2, 10000);
		excelUtil.getWorkBook().getSheetAt(0).setColumnWidth(3, 10000);
		for (int rowIndex = 0; rowIndex < list.size(); rowIndex++) {
			for (String key : list.get(rowIndex).keySet()) {
				excelUtil.cellVal(0, rowIndex + 1, Integer.parseInt(key), list
						.get(rowIndex).get(key).toString());
			}
		}
		final HttpHeaders headers = new HttpHeaders();
		final String fileName = new String("导出公司信息.xls".getBytes("UTF-8"),
				"iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		excelUtil.getWorkBook().write(out);
		final byte[] bytes = out.toByteArray();
		final ResponseEntity<byte[]> outFile = new ResponseEntity<byte[]>(
				bytes, headers, HttpStatus.CREATED);
		return outFile;
	}

	/**
	 * 重载方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> checkFee(String orgId, String mngId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tab.*,");
		sql.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<date_format(SYSDATE(),'%Y'),1,");
		sql.append(" IFNULL(group_concat(date_format(con.accStartTime, '%m') SEPARATOR ','),'')) AS BMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>date_format(SYSDATE(),'%Y'),12,");
		sql.append(" IFNULL(group_concat(date_format(con.accEndTime, '%m') SEPARATOR ','),'')) AS EMONTHS");
		sql.append(" FROM(SELECT");
		sql.append(" IFNULL(cus.mobile,'') AS tel,");
		sql.append(" IFNULL(org.name,'') AS cusName,");
		sql.append(" IFNULL(cus.ID, '') AS cusId,");
		sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS payMonths,");
		sql.append(" IFNULL(mem.Telphone,'') AS memTel");
		sql.append(" FROM");
		sql.append(" biz_organization org");
		sql.append(" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.deleteFlag = 0");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" WHERE org.id = ? and org.Enable = 1 and (date_format(exp.payDate,'%Y') = date_format(SYSDATE(),'%Y') or exp.payDate is null)");
		sql.append(" GROUP BY cus.mobile,org.name,mem.Telphone) tab");
		sql.append(" LEFT JOIN mnt_customContract con ON tab.cusId = con.cusId");
		sql.append(" AND DATE_FORMAT(con.accStartTime, '%Y') <= date_format(SYSDATE(),'%Y')");
		sql.append(" AND DATE_FORMAT(con.accEndTime, '%Y') >= date_format(SYSDATE(),'%Y') AND con.mngId = ?");
		sql.append(" GROUP BY tab.tel,tab.cusName,tab.memTel");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, Integer.parseInt(orgId));
		query.setParameter(1, mngId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> orgInfo = (Map<String, Object>) query
				.uniqueResult();
		String payMonths = "";
		String bMonths = "";
		String eMonths = "";
		try {
			payMonths = orgInfo.get("payMonths").toString();
			bMonths = orgInfo.get("BMONTHS").toString();
			eMonths = orgInfo.get("EMONTHS").toString();
		} catch (Exception e) {
			return null;
		}
		Map<String, String> result = this.checkConPay(bMonths, eMonths,
				payMonths);
		Map<String, Object> returnRes = new HashMap<String, Object>();
		returnRes.put("arrearage", result);
		returnRes.put("contentType", "");
		return returnRes;
	}

	/**
	 * 重载方法
	 * 
	 * @param bMonths
	 * @param eMonths
	 * @param payMonths
	 * @return
	 */
	@Override
	public Map<String, String> checkConPay(String bMonths, String eMonths,
			String payMonths) {
		try {
			int bm = Integer.parseInt(bMonths);
			int em = Integer.parseInt(eMonths);
			String[] pm = payMonths.split(",");
			Map<String, String> map = new HashMap<String, String>();
			for (int i = bm; i < em; i++) {
				map.put(String.valueOf(i), String.valueOf(i));
			}
			for (String temp : pm) {
				map.remove(temp);
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 重载方法
	 * 
	 * @param mngId
	 * @param orgId
	 * @param payYear
	 * @return
	 */
	@Override
	public Map<String, Boolean> getPayYearExpInfo(String mngId, String orgId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT org.ID AS orgId,");
		sql.append(" DATE_FORMAT(con.accStartTime, '%Y') AS bYear,");
		sql.append(" DATE_FORMFAT(con.accEndTime, '%Y') AS eYear");
		sql.append(" FROM biz_organization org");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" INNER JOIN mnt_customContract con ON cus.id = con.cusid");
		sql.append(" WHERE org.ID = ? AND con.mngId = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, orgId);
		query.setParameter(1, mngId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		Set<String> set = new HashSet<String>();
		for (Map<String, Object> map : list) {
			int bYear = Integer.parseInt(map.get("bYear").toString());
			int eYear = Integer.parseInt(map.get("eYear").toString());
			for (int i = bYear; i <= eYear; i++) {
				set.add(Integer.toString(i));
			}
		}
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		for (String year : set) {
			if (this.checkYearIsPay(orgId, year)) {
				result.put(year, true);
			} else {
				result.put(year, false);
			}
		}
		return result;
	}

	/**
	 * 重载方法
	 * 
	 * @param year
	 * @return
	 */
	@Override
	public boolean checkYearIsPay(String orgId, String year) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" FROM mnt_expenseDetail");
		sql.append(" WHERE orgId = ?");
		sql.append(" AND DATE_FORMAT(payDate, '%Y') = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, orgId);
		query.setParameter(1, year);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		int num = Integer.parseInt(list.get(0).get("num").toString());
		if (num > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 重载方法
	 * 
	 * @param mngId
	 * @param orgId
	 * @param year
	 * @return
	 */
	@Override
	public Map<String, Object> getPayYearMonthExpInfo(String mngId,
			String orgId, String year) {
		Map<String, Object> result = new HashMap<String, Object>();
		String payMonths = this.getPayMonths(orgId, year);
		result.put("payMonths", payMonths);
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tab.*,con.id as CONID,");
		sql.append(" IF(DATE_FORMAT(con.accStartTime,'%Y')<'" + year + "',1,");
		sql.append(" IFNULL(group_concat(date_format(con.accStartTime, '%m') SEPARATOR ','),'')) AS BMONTHS,");
		sql.append(" IF(DATE_FORMAT(con.accEndTime,'%Y')>'" + year + "',12,");
		sql.append(" IFNULL(group_concat(date_format(con.accEndTime, '%m') SEPARATOR ','),'')) AS EMONTHS");
		sql.append(" FROM(SELECT");
		sql.append(" IFNULL(org.ID, '') AS ORGID,");
		sql.append(" IFNULL(cus.ID, '') AS CUSID,");
		sql.append(" IFNULL(org.seqCode, '') AS ORGNUM,");
		sql.append(" IFNULL(org. NAME, '') AS ORGNAME,");
		sql.append(" IFNULL(exp.bookFee, '') AS BOOKFEE,");
		sql.append(" SUM(IFNULL(exp.payAmount, 0)) AS PAYAMOUNT,");
		sql.append(" SUM(IFNULL(exp.realAmount, 0)) AS REALAMOUNT,");
		sql.append(" (CASE WHEN cus.id IN ( SELECT DISTINCT cusId");
		sql.append(" FROM mnt_customContract) THEN 'true' ELSE 'false' END) AS CON,");
		sql.append(" IFNULL(group_concat(exp.payMonths ORDER BY exp.payMonths SEPARATOR ','),'') AS PAYMONTHS");
		sql.append(" FROM biz_organization org");
		sql.append(" LEFT JOIN mnt_expenseDetail exp ON org.ID = exp.orgId AND exp.deleteFlag = 0 AND exp.id not in ");
		sql.append(" (SELECT ar.correlationId FROM mnt_auditRoute ar WHERE ar.mngId =  '"
				+ mngId + "' and ar.auditFlag <> 0)");
		sql.append(" AND DATE_FORMAT(exp.payDate, '%Y') =  '" + year + "'");
		sql.append(" INNER JOIN biz_member mem ON org.ownerId = mem.ID");
		sql.append(" LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id");
		sql.append(" INNER JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId");
		sql.append(" INNER JOIN mnt_customInfo cus ON org.mntCustomId = cus.id");
		sql.append(" WHERE");
		sql.append(" mng.state = 1 AND org.Enable = 1 AND org.id = " + orgId);
		sql.append(" AND mng.mntMemberId = '" + mngId + "'");
		sql.append(" GROUP BY org.id,org.seqCode) tab");
		sql.append(" LEFT JOIN mnt_customContract con ON tab.CUSID = con.cusId");
		sql.append(" AND DATE_FORMAT(con.accStartTime, '%Y') <= '" + year + "'");
		sql.append(" AND DATE_FORMAT(con.accEndTime, '%Y') >= '" + year + "'");
		sql.append(" GROUP BY tab.ORGID,tab.ORGNUM order by tab.CON DESC");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list) {
			String payMonths_temp = map.get("PAYMONTHS").toString();
			String bMonths = map.get("BMONTHS").toString();
			String eMonths = map.get("EMONTHS").toString();
			boolean arrearage = this.checkConPayMonths(bMonths, eMonths,
					payMonths_temp);
			result.put("arr", arrearage);// 是否缴费
			result.put("bookFee", map.get("BOOKFEE").toString());// 账本费
			result.put("payAmount", map.get("PAYAMOUNT").toString());// 月缴费总额
			result.put("realAmount", map.get("REALAMOUNT").toString());// 实际月缴费总额
		}
		return result;
	}

	@Override
	public void createCustomPayRemind() {
		// 查询是否有合同
		String sql_Contract = "select c.id,o.id orgid,c.accEndTime from  mnt_customContract c ,biz_organization o where c.cusId=o.mntCustomId"
				+ " and DATE_FORMAT(CURRENT_DATE(),'%Y-%m-%d') >= c.accStartTime and DATE_FORMAT(CURRENT_DATE(),'%Y-%m-%d') < DATE_ADD(c.accEndTime,INTERVAL 1 MONTH) "
				+ "  order by c.createTime";

		final Query Query_Contract = this.getSession().createSQLQuery(
				sql_Contract.toString());
		Query_Contract.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list_Contract = Query_Contract.list();
		String content = "";
		// 如果有合同判断是否有缴费记录
		if (list_Contract.size() > 0) {
			for (Map<String, Object> map_Contract : list_Contract) {
				String accId = map_Contract.get("id").toString();
				String orgid = map_Contract.get("orgid").toString();
				Date accEndTime = (Date) map_Contract.get("accEndTime");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String accEndTimeStr = sdf.format(accEndTime);
				String sql_paymonths = "SELECT max(e.payToDate) payToDate from mnt_expenseDetail e  where e.accId='"
						+ accId + "'";
				final Query query = this.getSession().createSQLQuery(
						sql_paymonths.toString());
				query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> list = query.list();

				Calendar aCalendar1 = Calendar.getInstance();
				int year = aCalendar1.get(Calendar.YEAR);
				aCalendar1.setTime(new Date());
				aCalendar1.set(Calendar.HOUR_OF_DAY, 0);
				aCalendar1.set(Calendar.SECOND, 0);
				aCalendar1.set(Calendar.MINUTE, 0);
				aCalendar1.set(Calendar.MILLISECOND, 0);
				long dateNowTime = aCalendar1.getTimeInMillis();
				// 有缴费记录，根据当前日期查询下个月是否有缴费
				if (list.size() > 0) {
					Date payToDate = (Date) list.get(0).get("payToDate");
					if (payToDate != null && !"".equals(payToDate)) {
						// 判断合同是否到期
						if (!accEndTimeStr.equals(sdf.format(payToDate))) {
							Calendar aCalendar2 = Calendar.getInstance();
							aCalendar2.setTime(payToDate);
							aCalendar2.set(Calendar.HOUR_OF_DAY, 0);
							aCalendar2.set(Calendar.SECOND, 0);
							aCalendar2.set(Calendar.MINUTE, 0);
							aCalendar2.set(Calendar.MILLISECOND, 0);
							aCalendar2.add(Calendar.MONTH, 1);

							int needPayYear = aCalendar2.get(Calendar.YEAR);
							long datePayTime = aCalendar2.getTimeInMillis();
							long diffDay = (datePayTime - dateNowTime)
									/ (3600 * 24 * 1000);
							if (0 < diffDay && diffDay <= 3) {
								// content="您" + needPayYear + "年"+ needPayMonth
								// + "月还没有缴费,请缴费";
								content = "您" + needPayYear + "年的合同需要缴费，请缴费";
								saveCustomPayRemind(content, orgid, needPayYear);
							}
						}
					} else {// 没有缴费记录，提醒缴费
						content = "您" + year + "年合同还没有缴费记录，请缴费";
						saveCustomPayRemind(content, orgid, year);
					}
				} else {
					content = "您" + year + "年合同还没有缴费记录，请缴费";
					saveCustomPayRemind(content, orgid, year);

				}
			}

		}
	}

	public void saveCustomPayRemind(String content, String orgid, int year) {
		/*
		 * final StringBuffer sql = new StringBuffer();
		 * sql.append(" INSERT into mnt_remind");
		 * sql.append("(id,title,content,orgId,type,createTime)");
		 * sql.append(" values(uuid(),?,?,?,1,SYSDATE())"); final Query query =
		 * this.createSqlQuery(sql.toString(), "代理服务缴费提醒",content,orgid);
		 * query.executeUpdate();
		 */
		MntRemind remindEnty = new MntRemind();
		remindEnty.setContent(content);
		remindEnty.setType(1);
		remindEnty.setYear(year);
		remindEnty.setTitle("代理服务缴费提醒");
		remindEnty.setOrgId(orgid);
		remindEnty.setPhoneNum("");
		remindEnty.setCreateTime(new Date());
		this.save(remindEnty);
	}

	@Override
	public String beforePayValidate(String str) {
		ObjectMapper mapper = new ObjectMapper();
		PayValidate vo = new PayValidate();
		try {
			vo = mapper.readValue(str, PayValidate.class);
			System.out
					.println("===================================================");
			PayValidateMsg payValidateMsg = new PayValidateMsg();
			payValidateMsg.setSucesse(true);
			payValidateMsg.setMsg("可以正常缴费");
			String msgMonth = "";
			String sql = "select GROUP_CONCAT(e.payMonths separator ',') payMonths "
					+ " from mnt_expenseDetail e "
					+ " INNER join"
					+ " (select c.id FROM mnt_customContract c,biz_organization o where  c.cusId=o.mntCustomId and o.id='"
					+ vo.getMemberId()
					+ "' and c.mngId='"
					+ vo.getMngId()
					+ "') contract"
					+ " on contract.id=e.accId"
					+ " and DATE_FORMAT(e.payFromDate,'%Y')= " + vo.getYear();

			final Query query = this.getSession().createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			if (list != null && list.size() > 0) {
				Object payMonthObj = list.get(0).get("payMonths");

				if (payMonthObj != null && !"".equals(payMonthObj)) {
					String payMonthStr = payMonthObj.toString();
					String[] payMonthArr = payMonthStr.split(",");
					String[] months = vo.getMonth();

					for (int i = 0; i < months.length; i++) {
						if (Arrays.asList(payMonthArr).contains(months[i])) {

							if (i == 0) {
								msgMonth = months[i];
							} else {
								msgMonth += "," + months[i];
							}
						}
					}
				}
			}
			if (msgMonth != "") {
				payValidateMsg.setSucesse(false);
				payValidateMsg.setMsg("您" + msgMonth + "月已经缴费,无需再次支付");
			}
			return mapper.writeValueAsString(payValidateMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean createMntExpenseDetail(String str) {
		ObjectMapper mapper = new ObjectMapper();
		ExpenseDetailInfo vo = new ExpenseDetailInfo();
		boolean sucess = true;
		try {
			vo = mapper.readValue(str, ExpenseDetailInfo.class);
			List montharr=vo.getMonth();
			
           String sql="select id,accNo,accStartTime,accEndTime,monthCost,discount from mnt_customContract c " +
           		"where c.cusId=(select o.mntCustomId FROM biz_organization o where o.id='"+vo.getOrgId()+"') and c.mngId='"+vo.getMngId()+"' " +
           				"and DATE_FORMAT(c.accStartTime,'%Y') <= "+vo.getYear()
           				+" and DATE_FORMAT(c.accEndTime,'%Y') >=  "+vo.getYear();
           
           final Query query = this.getSession().createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> contractList = query.list();
		
			if(contractList!=null&&contractList.size()>0){
				for(Map<String, Object> contractMap:contractList){
					Date accStartTime=(Date) contractMap.get("accStartTime");
					Date accEndTime=(Date) contractMap.get("accEndTime");
					String month="";
					int timeNum=0;
					for(int j=0;j<montharr.size();j++){
						String monStr=vo.getYear()+"-"+montharr.get(j)+"-01";
						Date monStrDate=java.sql.Date.valueOf(monStr);
						if(monStrDate.getTime()>=accStartTime.getTime() && monStrDate.getTime()<=accEndTime.getTime()){
							if("".equals(month)){
								month=montharr.get(j).toString();	
							}else{
								month+=","+montharr.get(j);
							}
							timeNum++;
						}
					}
					if(!"".equals(month)){
						double payAmount=Double.parseDouble(contractMap.get("monthCost").toString())*timeNum;
						double realAmount=payAmount*Double.parseDouble(contractMap.get("discount").toString());
						CustomPayVo cpv=new CustomPayVo();
						cpv.setConId(contractMap.get("id").toString());
						cpv.setAccNO(contractMap.get("accNo").toString());
						cpv.setPayAmount(payAmount+"");
						cpv.setRealAmount(realAmount+"");
						cpv.setDiscount(contractMap.get("discount").toString());
						cpv.setPayment(vo.getPayment());
						cpv.setPayMonth(month);
						cpv.setPayYear(vo.getYear());
						cpv.setOrgId(vo.getOrgId()+"");
						cpv.setAuditFlag(false);
						
						 sucess=this.saveCustomPay(vo.getOrgId(),vo.getMngId(),cpv);	
						 if(sucess==false){
							 return sucess;
						 }
					}
				}
			}else{
				return false;
			}
           
			return sucess;
           				
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String getPayMonth(String orgId, String payYear, String accNo) {
		String payMonths = "";
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT group_concat(payMonths ORDER BY payMonths SEPARATOR ',') AS PAYMONTHS");
		sql.append(" FROM mnt_expenseDetail exp");
		sql.append(" WHERE DATE_FORMAT(payDate, '%Y') = ?");
		sql.append(" AND exp.orgId = ?");
		sql.append(" AND exp.deleteFlag = 0");
		sql.append(" AND exp.accNo = ?");
		sql.append(" GROUP BY exp.orgId");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, payYear);
		query.setParameter(1, orgId);
		query.setParameter(2, accNo);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		if (list != null) {
			for (Map<String, Object> map : list) {
				payMonths = map.get("PAYMONTHS").toString();
			}
		}
		return payMonths;
	}

	@Override
	public MntMember getPayInfo(String memberId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT mnt.payId , mnt.aliPay , mnt.aliPayKey ");
		sql.append(" from mnt_member mnt where mnt.id = ? ");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, memberId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		MntMember mnt = new MntMember();
		if (list != null) {
			for (Map<String, Object> map : list) {
				mnt.setPayId(map.get("payId")!=null?map.get("payId").toString():"");
				mnt.setAliPay(map.get("aliPay")!=null?map.get("aliPay").toString():"");
				mnt.setAliPayKey(map.get("aliPayKey")!=null?map.get("aliPayKey").toString():"");
			}
		}
		return mnt;
	}

	@Override
	public Map gotoDoServices(HttpServletRequest request, HttpSession session, int orgId) {
    	final StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT *");
//		sql.append(" IFNULL(org. NAME, '') AS ORGNAME,");
//		sql.append(" IFNULL(org.ID, '') AS ORGID,");
		sql.append(" FROM biz_organization org");
		sql.append(" WHERE org.ID="+orgId);	
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		Map<String, Object> bizOrganization = (Map<String, Object>) query.uniqueResult();
		return bizOrganization;
	}

	@Override
	public String saveServices(int id, String servicesName, String servicesType) {
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String str = sdf.format(date);
		final StringBuffer sql = new StringBuffer();
    	sql.append(" INSERT INTO mnt_processInfo (mngId,processName,processType,canUse,stamp) VALUE(");
		sql.append(id+",'"+servicesName+"','"+servicesType+"',1,'");
		sql.append(str);
		sql.append("')");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		int i= query.executeUpdate();
		return null;
	}

	@Override
	public List<Map<String, Object>> getTelByContractId(int contractId) {
		final StringBuffer sql = new StringBuffer();
		sql.append(" SELECT cust.mobile FROM mnt_customContract c,mnt_customInfo cust where cust.id = c.cusId AND c.id = ?");
		final Query query = this.getSession().createSQLQuery(sql.toString());
		query.setInteger(0, contractId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	@Override
	public int sendMsg(String type, String senContent, String custMobile) {
		// TODO Auto-generated method stub
		
		//根据type 进行判断是定时发送还是立即发送，1：立即发送，2：定时发送
		if("1".equals(type)){//立即
			
			return 0;
			
		}else{//插入定时任务数据
			final StringBuffer sql = new StringBuffer();
	    	
			final Query query = this.getSession().createSQLQuery(sql.toString());
			int i= query.executeUpdate();
			
			return 0;
		}
	}

}
