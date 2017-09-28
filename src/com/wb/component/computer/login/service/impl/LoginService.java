/*
 * 文 件 名:  LoginService.java
 * 版    权:  gomyck
 * 描    述:  <描述>
 * 修 改 人:  郝洋
 * 修改时间:  2016-3-18
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wb.component.computer.login.service.impl;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.wb.component.computer.login.service.ILoginService;
import com.wb.component.computer.login.util.HttpUtil;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PropertiesReader;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntMngandusers;
import com.wb.model.entity.computer.MsgNotification;
import com.wb.model.entity.computer.accTableEntity.BizMember;
import com.wb.model.entity.computer.cusManage.MntCustomInfo;
import com.wb.model.pojo.computer.EnterpriseQueryForm;
import com.wb.model.pojo.computer.HomeShowForComputer;

/**
 * 登陆注册相关service
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "loginService")
public class LoginService extends BaseDao implements ILoginService
{
    
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(LoginService.class);
    
    /**
     * 
     * 重载方法
     * 
     * @param tel tel
     * @return MntMember MntMember
     */
    @SuppressWarnings("unchecked")
    @Override
    public MntMember findByTel(String tel)
    {
        /**
         * 先查看公司老板账号信息是否为当前登陆人
         */
        List<MntMember> mntMembers = this.findByProperty(MntMember.class, "telphone", tel);
        if (mntMembers != null && mntMembers.size() > 0)
        {
            final MntMember mntMember = mntMembers.get(0);
            mntMember.setAdmin(true);
            List<BizMember> bizMembers = this.findByProperty(BizMember.class, "telphone", tel);
            if (bizMembers != null && bizMembers.size() > 0)
            {
                final BizMember bizMember = bizMembers.get(0);
                mntMember.setEmpInfo(bizMember);
            }
            return mntMember;
        }
        /**
         * 非老板登陆,查询员工信息表
         */
        List<BizMember> bizMembers = this.findByProperty(BizMember.class, "telphone", tel);
        if (bizMembers == null || bizMembers.size() == 0)
        {
            return null;
        }
        final BizMember bizMember = bizMembers.get(0);
        String sql = " select * from sys_member_role where memberID = ? ";
        List<Map<String, Object>> roleInfo = this.createSqlQuery(sql, bizMember.getId()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        Object aId = this.createSqlQuery(" SELECT t.mntMemberId FROM mnt_mngandusers t WHERE t.userMemberId = ? AND t.state = 1 ", bizMember.getId()).uniqueResult();
        if (aId == null)
        {
            return null;
        }
        int adminId = Integer.parseInt(aId.toString());
        final List<MntMember> this_mntMembers = this.findByProperty(MntMember.class, "id", adminId);
        if (this_mntMembers != null && this_mntMembers.size() > 0)
        {
            final MntMember mntMember = this_mntMembers.get(0);
            mntMember.setEmpInfo(bizMember);
            mntMember.setRoleInfo(roleInfo);
            mntMember.setAdmin(false);
            return mntMember;
        }
        return null;
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param tel tel
     * @return MntMember MntMember
     */
    @SuppressWarnings("unchecked")
    @Override
    public MntMember findByTelForChangeRole(String tel ,String userType)
    {
        /**
         * 先查看公司老板账号信息是否为当前登陆人
         */
    	if("1".equals(userType)){
    		List<MntMember> mntMembers = this.findByProperty(MntMember.class, "telphone", tel);
            if (mntMembers != null && mntMembers.size() > 0)
            {
                final MntMember mntMember = mntMembers.get(0);
                mntMember.setAdmin(true);
                List<BizMember> bizMembers = this.findByProperty(BizMember.class, "telphone", tel);
                if (bizMembers != null && bizMembers.size() > 0)
                {
                    final BizMember bizMember = bizMembers.get(0);
                    mntMember.setEmpInfo(bizMember);
                }
                return mntMember;
            }
    	}else{
    		 /**
             * 非老板登陆,查询员工信息表
             */
            List<BizMember> bizMembers = this.findByProperty(BizMember.class, "telphone", tel);
            if (bizMembers == null || bizMembers.size() == 0)
            {
                return null;
            }
            final BizMember bizMember = bizMembers.get(0);
            String sql = " select * from sys_member_role where memberID = ? ";
            List<Map<String, Object>> roleInfo = this.createSqlQuery(sql, bizMember.getId()).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            Object aId = this.createSqlQuery(" SELECT t.mntMemberId FROM mnt_mngandusers t WHERE t.userMemberId = ? AND t.state = 1 ", bizMember.getId()).uniqueResult();
            if (aId == null)
            {
                return null;
            }
            int adminId = Integer.parseInt(aId.toString());
            final List<MntMember> this_mntMembers = this.findByProperty(MntMember.class, "id", adminId);
            if (this_mntMembers != null && this_mntMembers.size() > 0)
            {
                final MntMember mntMember = this_mntMembers.get(0);
                mntMember.setEmpInfo(bizMember);
                mntMember.setRoleInfo(roleInfo);
                mntMember.setAdmin(false);
                return mntMember;
            }
            
    	}
    	return null;
       
    }
    
    
    
    /**
     * 
     * 重载方法
     * 
     * @param member member
     */
    @SuppressWarnings("unchecked")
    @Override
    public void insert(MntMember member, BizMember bm)
    {
        this.save(member);
        List<BizMember> result = this.findByProperty(BizMember.class, "telphone", member.getTelphone());
        if ((result == null || result.size() == 0) && bm != null)
        {
            this.save(bm);
        }
        PropertiesReader reader = PropertiesReader.getInstance();
        String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");
    	StringBuilder paramsBuffer = new StringBuilder();
		paramsBuffer.append("mntMemberId=").append(member.getId()).append("&");
		paramsBuffer.append("bizMemberId=").append(bm.getId());
        try {
			//HttpUtil.doFormPost(url+"/auth/sysauth/memberRoleInit/put.jspx", paramsBuffer.toString());
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param map map
     * @param sqlStr sqlStr
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateManager(Boolean isAdmin, Map map, String sqlStr)
    {
        if (isAdmin == null || isAdmin)
        {
            this.update(MntMember.class, map, sqlStr);
        }
        else
        {
            this.update(BizMember.class, map, sqlStr);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public  void updateCustom(Map map, String sqlStr)
    {
       
        this.update(MntCustomInfo.class, map, sqlStr);
    
    }
    /**
     * 
     * 重载方法
     * 
     * @param userId userId
     * @param direction direction
     * @return Long
     */
    @Override
    public Long countNotificationById(Integer mngId,Integer userId, Integer direction)
    {
        final DetachedCriteria criteria = DetachedCriteria.forClass(MsgNotification.class);
        if (direction.equals(1))
        {
            criteria.add(Restrictions.eq("mngId", mngId));
            criteria.add(Restrictions.eq("userId", userId));
        }
        else
        {
        	criteria.add(Restrictions.eq("mngId", mngId));
            criteria.add(Restrictions.eq("userId", userId));
        }
        criteria.add(Restrictions.eq("isread", 0));
        criteria.add(Restrictions.eq("direction", direction));
        criteria.addOrder(Order.desc("stamp"));
        return this.findCountByCriteria(MntMngandusers.class, criteria);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param form
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public HomeShowForComputer findHomeShowData(EnterpriseQueryForm form)
    {
        try
        {
            final List<HomeShowForComputer> list = sqlMapClient.queryForList("findHomeShowForComputer", form);
            if (list.size() > 0)
            {
                return list.get(0);
            }
        }
        catch (SQLException e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("查询主界面显示信息失败:", e, true));
        }
        return null;
    }
    
    /**
     * 重载方法
     * 
     * @param id
     * @return
     */
    @Override
    public MntMember findById(int id)
    {
        return (MntMember)this.findByPrimaryKey(MntMember.class, id);
    }
    
}
