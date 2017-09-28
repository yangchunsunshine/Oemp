package com.wb.component.computer.payManage.service.imp;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.payManage.service.IPayManagerService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.model.entity.computer.MntAlipayInfo;

@Service(value = "payManagerService")
public class PayManagerService extends BaseDao implements IPayManagerService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(PayManagerService.class);
    
    @Override
    public boolean saveAlipaySetting(int mngId, List<MntAlipayInfo> aliParams)
    {
        try
        {
            this.deleteByProperty(MntAlipayInfo.class, "mntId", Integer.toString(mngId));
            for (MntAlipayInfo alipayInfo : aliParams)
            {
                alipayInfo.setMntId(Integer.toString(mngId));
                alipayInfo.setDeleteFlag("0");
                this.save(alipayInfo);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    
    @Override
    public List<Map<String, Object>> getAlipayInfo(int mngId)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT payKey,payValue");
        sql.append(" FROM mnt_alipayInfo");
        sql.append(" WHERE mntId = ?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setParameter(0, mngId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
}
