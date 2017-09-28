package com.wb.component.computer.login.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.wb.component.computer.login.service.INewsService;
import com.wb.component.computer.login.util.FileIOUtil;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.framework.nestLogger.NestLogger;
import com.wb.model.entity.computer.news.MntNews;

/**
 * 
 * 消息ser
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-1]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "newsService")
public class NewsService extends BaseDao implements INewsService
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(NewsService.class);
    
    @Override
    public int createPushMessage(MntNews vo, MultipartFile imageFile, int orgId, int creater, String createrName,String cusId)
    {
        try
        {
//由于需要发布公告给所有小微企业，所以查询是否发布过该公告方法停用。
//			final StringBuffer sql = new StringBuffer();
//		    sql.append(" SELECT * FROM mnt_news where title=? or context=?");
//		    final Query query = this.createSqlQuery(sql.toString());
//		    query.setString(0, vo.getTitle());
//		    query.setString(1, vo.getContext());
//		    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//		    List<Map<String, Object>> newsList=query.list();
//		    if(newsList.size()>0){
//		    	return 4;
//		    }
            if (imageFile != null)
            {
            	if(imageFile.getSize()>5242880){
            		return 5;
            	}
                final InputStream in = new ByteArrayInputStream(imageFile.getBytes());
                if (FileIOUtil.ifImage(in))
                {
                    vo.setImage(imageFile.getBytes());
                    vo.setImageName(imageFile.getOriginalFilename());
                }
                else
                {
                    return 3;
                }
            }
            vo.setOrgId(orgId);
            vo.setCreater(creater);
            vo.setCreaterName(createrName);
            vo.setCreateTime(new Date());
            vo.setCusId(cusId);
            vo.setIfPushCus("on");
            Date date = new Date();
            String stamp = date.getTime() + "";
            vo.setStamp(stamp);
            this.save(vo);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("保存公告信息:", e, true));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
        }
        return 1;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getPushMessage(int orgId, int limit)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" select t.id,t.orgId,t.title,t.context,t.creater,t.createrName,t.createTime,t.stamp from mnt_news t where t.orgId = ? and cusId is null order by t.stamp DESC limit 0, " + limit);
        final Query query = this.createSqlQuery(sql.toString(), orgId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    /**
     * 重载方法
     * 
     * @param prgId
     * @param msgId
     * @return
     */
    @Override
    public Map<String, Object> getPushMessage(int orgId, String msgId)
    {
        final StringBuffer sql = new StringBuffer();
        sql.append(" select t.id,t.orgId,t.title,t.context,t.creater,t.createrName,t.createTime,t.stamp from mnt_news t where t.orgId = ? and id = ? and cusId is null ");
        final Query query = this.createSqlQuery(sql.toString(), orgId, msgId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>)query.uniqueResult();
    }
    
    @Override
    public ResponseEntity<byte[]> downLoadImage(int id)
    {
        try
        {
            final MntNews mntNews = (MntNews)this.findByPrimaryKey(MntNews.class, id);
            if (mntNews.getImageName() == null)
            {
                return null;
            }
            final HttpHeaders headers = new HttpHeaders();
            final String fileName = new String(mntNews.getImageName().getBytes("UTF-8"), "iso-8859-1");
            headers.setContentDispositionFormData("mntNews", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            final byte[] bytes = mntNews.getImage();
            final ResponseEntity<byte[]> outFile = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
            return outFile;
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("下载公告附件:", e, true));
            return null;
        }
    }
    
    @Override
    public boolean delNotice(String noticeId)
    {
        try
        {
            final String sql = " DELETE FROM mnt_news WHERE id = ?";
            final Query query = this.getSession().createSQLQuery(sql);
            query.setParameter(0, noticeId);
            query.executeUpdate();
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
    public PageUtil getNewList(String title, String createDate, int orgId, PageInfo info)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" id AS newId,orgId,createrName,id AS operate,");
        sql.append(" title,context,DATE_FORMAT(createTime,'%Y-%m-%d') AS createTime");
        sql.append(" FROM mnt_news");
        sql.append(" WHERE orgId = ? and cusId is null ");
        if (!"".equals(title) && title != null)
        {
            sql.append(" AND title LIKE '%" + title + "%'");
        }
        if (!"".equals(createDate) && createDate != null)
        {
            sql.append(" AND DATE_FORMAT(createTime,'%Y-%m-%d') = '" + createDate + "'");
        }
        sql.append(" ORDER BY createTime DESC");
        return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows(), orgId);
    }
    
    @Override
    public List<Map<String, Object>> getNewInfo(int newId)
    {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT id AS newId,title,context,imageName,");
        sql.append(" (CASE WHEN imageName != ''");
        sql.append(" AND imageName IS NOT NULL THEN 1");
        sql.append(" ELSE 0 END) AS haveImg");
        sql.append(" FROM mnt_news");
        sql.append(" WHERE id =?");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setInteger(0, newId);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }
    
    @Override
    public int updateMessage(MntNews vo, MultipartFile imageFile)
    {
        try
        {
            MntNews entity = (MntNews)this.findByPrimaryKey(MntNews.class, vo.getId());
            if (imageFile != null)
            {
                final InputStream in = new ByteArrayInputStream(imageFile.getBytes());
                if (FileIOUtil.ifImage(in))
                {
                    entity.setImage(imageFile.getBytes());
                    entity.setImageName(imageFile.getOriginalFilename());
                }
                else
                {
                    return 3;
                }
            }
            entity.setTitle(vo.getTitle());
            entity.setContext(vo.getContext());
            this.update(entity);
        }
        catch (Exception e)
        {
            NestLogger.showException(e);
            log.error(NestLogger.buildLog("更新公告信息:", e, true));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
        }
        return 1;
    }
    
    @Override
    public ResponseEntity<byte[]> downLoadImageFile(int newId)
        throws UnsupportedEncodingException
    {
        MntNews entity = (MntNews)this.findByPrimaryKey(MntNews.class, newId);
        final HttpHeaders headers = new HttpHeaders();
        final String fileName = new String(entity.getImageName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        headers.setContentDispositionFormData("entity", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        final byte[] bytes = entity.getImage();
        final ResponseEntity<byte[]> outFile = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        return outFile;
    }
    
    @Override
    public boolean deleteImageFile(int newId)
    {
        try
        {
            final MntNews entity = (MntNews)this.findByPrimaryKey(MntNews.class, newId);
            if (entity != null)
            {
                entity.setImage(null);
                entity.setImageName(null);
                this.update(entity);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 通过代帐公司MntMember表中的ID找到它下面的公司
     */
	@Override
	public List selectCompanyByMntMember(int mntMemberId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  DISTINCT IFNULL(org.ID,'') AS ORGID,IFNULL(cus.ID,'') AS CUSID,IFNULL(org. NAME,'') AS ORGNAME,cus.androidDeviceToken,cus.iosDeviceToken ");
        sql.append(" FROM mnt_customContract con LEFT JOIN mnt_customInfo cus ON con.cusId = cus.id LEFT JOIN biz_organization org ON  org.mntCustomId = cus.id LEFT JOIN");
        sql.append(" biz_member mem ON org.ownerId = mem.ID LEFT JOIN mnt_departmentInfo dep ON mem.departmentId = dep.id LEFT JOIN mnt_mngandusers mng ON mem.ID = mng.userMemberId ");
        sql.append(" WHERE mng.state = 1 AND org.Enable = 1 ");
        sql.append(" and mng.mntMemberId='"+mntMemberId +"'");
        sql.append(" GROUP BY org.id,org.seqCode,con.accNo limit 0,100");
        final Query query = this.getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return query.list();
	}
    
}
