package com.wb.component.computer.hcy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.wb.component.computer.hcy.service.IkpiService;
import com.wb.framework.commonDao.BaseDao;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.ZhiBiaoji;
import com.wb.model.entity.computer.Formula;
import com.wb.model.entity.computer.Kpi;
import com.wb.model.entity.computer.Moban;
import com.wb.model.entity.computer.Model;


@Service(value = "kpiService")
public class KpiService extends BaseDao implements  IkpiService {
	 private static final Logger log = Logger.getLogger(KpiService.class);

	@Override
	public PageUtil getModelNodeList(PageInfo pageInfo) {
		StringBuffer sql = new StringBuffer();
    	sql.append(" select * from kpi order by sort ");	
//    	sql.append(" p.processTmpId='"+processTmpId+"' and processId is null order by p.orderSeq ");
	return this.findPageBySqlQuery(sql.toString(), pageInfo.getPage(), pageInfo.getRows());
	}

	@Override
	public List<Kpi> getKpis(Integer zhibiaoji_id) {
		StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("SELECT * from  kpi where zhibiaoji_id="+zhibiaoji_id+" order by sort");
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Kpi.class);
//        query.setParameter(0, processId);
        return query.list();
		
	}

	@Override
	public String deleteKpi(Integer id) {
		try { 
	        StringBuffer sqlStr2=new StringBuffer();
	        sqlStr2.append(" delete from kpi where id ="+id);
	        Query query2 = this.getSession().createSQLQuery(sqlStr2.toString()); 
	        query2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "error";
		}
		return "success";
	}

	@Override
	public String updatezzname(String zzname,Integer id) {
		try {
			boolean flag=this.checkRepeatone(zzname,id);
			if(flag==true){
				return "repeat";
			}
			Kpi entity=(Kpi) this.findByPrimaryKey(Kpi.class, id);
			entity.setZnname(zzname);
			this.update(entity);
			return "success";
		} catch (Exception e) {
			return "error";
		}
		
		
	}

	private boolean checkRepeatone(String zzname, Integer id) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from kpi where ");
		sql.append(" znname = ? and id <> ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, zzname);
		query.setParameter(1, id);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}
	
	

	@Override
	public String updatezid(String zid, Integer id) {
		try {
			boolean flag=checkRepeattwo(zid,id);
			if(flag){
				return "repeat";
			}
			Kpi kpi=(Kpi) this.findByPrimaryKey(Kpi.class, id);
			kpi.setZid(zid);
			this.update(kpi);
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}
	
	
	private boolean checkRepeattwo(String zid, Integer id) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from kpi where ");
		sql.append(" zid = ? and id <> ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, zid);
		query.setParameter(1, id);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public String updateremark(String remark, Integer id) {
		try {
//			boolean flag=this.checkRepeatThree(remark,id);
//			if(flag){
//				return "repeat";
//			}
			Kpi kpi=(Kpi) this.findByPrimaryKey(Kpi.class, id);
			kpi.setRemark(remark);
			this.update(kpi);
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}
	
	private boolean checkRepeatThree(String remark, Integer id) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from kpi where ");
		sql.append(" remark = ? and id <> ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, remark);
		query.setParameter(1, id);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public List<Kpi> findResult(String znname,Integer zhibiaoji_id) {
		StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("SELECT * from  kpi where znname like '%"+znname+"%' and zhibiaoji_id="+zhibiaoji_id+" order by sort");
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Kpi.class);
		return query.list();
	}

	@Override
	public PageUtil getModelList(PageInfo info,	Integer id) {
		StringBuffer sql = new StringBuffer();
        	sql.append(" select * from moban where type in(select id from model where type="+id+") ");	
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
		
	}

	@Override
	public String deleteModel(Integer id) {
		try { 
	        StringBuffer sqlStr2=new StringBuffer();
	        sqlStr2.append(" delete from moban where id ="+id);
	        Query query2 = this.getSession().createSQLQuery(sqlStr2.toString()); 
	        query2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			return "error";
		}
		return "success";
	}

	@Override
	public List<Model> findProduct(Integer id) {
		StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("select * from model where type="+id);
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Model.class);
		return query.list();
	}

	@Override
	public PageUtil getModelList_c(PageInfo info, Integer productId, Integer selectData) {
		StringBuffer sql = new StringBuffer();
    	if(selectData==0){
    		sql.append(" select * from moban where type in(select id from model where type="+productId+") ");
    	}else{
    		sql.append(" select * from moban where type="+selectData+" and type in(select id from model where type="+productId+") ");	
    	}
		
	return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	
	}

	@Override
	public String addmoban(Integer fenleiId, String name) {
		try {
			Moban moban=new Moban();
			moban.setName(name);
			moban.setType(fenleiId);
			this.save(moban);
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}

	@Override
	public String addmoban_defalut(Integer proId, Integer fenleiId, String name) {
		try {
			Moban m=new Moban();
			m.setName(name);
			boolean flag=this.checkRepectBusinessTempForUpdatedefalut(proId,fenleiId,name);
			if(flag){
				return "repeat";
			}
			StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append("select * from moban where type="+fenleiId);
	        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Moban.class);
			List<Moban> list=new ArrayList<Moban>();
			list=query.list();
			Moban moban=new Moban();
			moban.setName(name);
			moban.setType(fenleiId);
			moban.setVersion("V1");
			moban.setTime(new Date());
			moban.setCount(1);
			if(proId==1){
				if(list.size()>0){
					moban.setNum("DCM-PC-"+(list.size()+1));//版本编号
				}else{
					moban.setNum("DCM-PC-"+1);//版本编号
				}
			}else{
				if(list.size()>0){
					moban.setNum("SDR-PC-"+(list.size()+1));//版本编号
				}else{
					moban.setNum("SDR-PC-"+1);//版本编号
				}
			}
			this.save(moban);
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}

	private boolean checkRepectBusinessTempForUpdatedefalut(Integer proId, Integer fenleiId, String name) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from moban where ");
		sql.append(" name = ? and type = ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, name);
		query.setParameter(1, fenleiId);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public Moban findMobanByPrimaryKey(Integer processTmpId) {
		Moban moban=(Moban) this.findByPrimaryKey(Moban.class, processTmpId);
		return moban;
	}
	
	// 增加模板的时候 校验是否同名
	private boolean checkRepectBusinessTempForUpdate(Moban moban) {
		int num = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) AS num");
		sql.append(" from moban where ");
		sql.append(" name = ? and id <> ?");
		Query query = this.getSession().createSQLQuery(sql.toString());
		query.setParameter(0, moban.getName());
		query.setParameter(1, moban.getId());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.list();
		for (Map<String, Object> map : list)
		{
			num = Integer.parseInt(map.get("num").toString());
		}
		if (num > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public int updateMobanAndCheckCode(Moban moban) {
		try {
			boolean flag=checkRepectBusinessTempForUpdate(moban);
			if(flag){
				return 1;
			}
			Moban demo=new Moban();
			demo=(Moban) this.findByPrimaryKey(Moban.class, moban.getId());
			StringBuffer sql = new StringBuffer(); 
			sql.append(" update moban set name=?,version=?,count="+(demo.getCount()+1)+" ,time=now() where id=? ");
			final Query query = this.getSession().createSQLQuery(sql.toString());
	        query.setParameter(0, moban.getName());
	        query.setParameter(1,"V"+(demo.getCount()+1));
	        query.setParameter(2, moban.getId());
	        query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 2;
		}
		
		return 3;
	}

	@Override
	public PageUtil getAllZhibiao(PageInfo info) {
		StringBuffer sql = new StringBuffer();
    	sql.append(" select * from zhibiaoji");	
	return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
	}

	@Override
	public String savezhibiaoji(Integer fenleiId, String name) {
		try {
			ZhiBiaoji biaoji=new ZhiBiaoji();
			biaoji.setName(name);
			biaoji.setTime(new Date());
			biaoji.setType(fenleiId);
			this.save(biaoji);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "error";
		}
	}

	@Override
	public List<Moban> showAllMoban(Integer type) {
		StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("select * from moban where type="+type);
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Moban.class);
		return query.list();
	}

	@Override
	public String savezhibiaoJI(Integer fenleiId, String name) {
		try {
			ZhiBiaoji biaoji=new ZhiBiaoji();
			biaoji.setName(name);
			biaoji.setTime(new Date());
			biaoji.setType(fenleiId);
			this.save(biaoji);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "error";
		}
	}

	@Override
	public String deleteZhiBiaoJi(Integer id) {
		try {
			ZhiBiaoji biaoji=new ZhiBiaoji();
			biaoji=(ZhiBiaoji) this.findByPrimaryKey(ZhiBiaoji.class, id);
			this.delete(biaoji);
			// 删除指标集对应的指标
			List<Kpi> kpis=new ArrayList<Kpi>();
			StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append("select * from kpi where zhibiaoji_id="+id);
	        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Kpi.class);
			kpis=query.list();
			for(Kpi kpi:kpis){
				this.delete(kpi);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "error";
		}
	}

	@Override
	public List<Kpi> getKpisbyId(Integer id) {
		StringBuffer sqlStr=new StringBuffer();
        sqlStr.append("SELECT * from  kpi where zhibiaoji_id="+id+" order by sort");
        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Kpi.class);
        return query.list();
	}

	@Override
	public String savezhibiaoji_hcy(String zid, String znname, Integer zhibiaoji_id) {
		try {
			boolean flag=checkRepectForZhibiaoji(zid,znname,zhibiaoji_id);
			if(flag){
				return "repeat";
			}
			// 求出最大的sort
			int max=maxzhibiaoji(zhibiaoji_id);
			Kpi kpi=new Kpi();
			kpi.setTime("年");
			kpi.setZhibiaojiId(zhibiaoji_id);
			kpi.setZid(zid);
			kpi.setZnname(znname);
			kpi.setSort(max+1);
			this.save(kpi);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "error";
		}
	}
	
	// 验证指标的名称
		private boolean checkRepectForZhibiaoji(String zid, String znname, Integer zhibiaoji_id) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) AS num ");
			sql.append(" from kpi where ");
			sql.append(" (znname = ? or zid =?) group by zhibiaoji_id having zhibiaoji_id=?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, znname);
			query.setParameter(1, zid);
			query.setParameter(2, zhibiaoji_id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
			{
				if(map.get("num")==null){
					return false;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			if (num > 0)
			{
				return true;
			}
			return false;
		}
		
		
		private int maxzhibiaoji(Integer zhibiaoji_id) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT max(sort) AS num");
			sql.append(" from kpi where ");
			sql.append(" zhibiaoji_id=?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, zhibiaoji_id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
				
			{
				if(map.get("num")==null){
					return num;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			return num;
		}

		@Override
		public PageUtil getAllZhibiaobyMoban(PageInfo info, Integer id) {
			StringBuffer sql = new StringBuffer();
	    	sql.append(" select * from zhibiaoji where type="+id);	
		return this.findPageBySqlQuery(sql.toString(), info.getPage(), info.getRows());
		}

		@Override
		public String savezhibiaoJI_de(Integer id, String name) {
			try {
				// 验证指标集是否重复
				boolean flag=checkReapectzhibiaoji(id,name);
				if(flag){
					return "repeat";
				}
				ZhiBiaoji biaoji=new ZhiBiaoji();
				biaoji.setName(name);
				biaoji.setTime(new Date());
				biaoji.setType(id);
				this.save(biaoji);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
		}

		private boolean checkReapectzhibiaoji(Integer id, String name) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) AS num ");
			sql.append(" from zhibiaoji where ");
			sql.append(" name = ? and type =? group by type having type=?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, name);
			query.setParameter(1, id);
			query.setParameter(2, id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
			{
				if(map.get("num")==null){
					return false;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			if (num > 0)
			{
				return true;
			}
			return false;
		}

		@Override
		public ZhiBiaoji getzhibiaojiById(Integer id) {
			ZhiBiaoji biaoji=(ZhiBiaoji) this.findByPrimaryKey(ZhiBiaoji.class, id);
			return biaoji;
		}

		@Override
		public List<Formula> getAllFormulabuzhijiaojiId(Integer id) {
			StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append("SELECT * from  formula where zhibiaoji_id="+id);
	        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Formula.class);
	        return query.list();
		}

		@Override
		public List<Formula> getAllFormulabuzhijiaojiName(String name,Integer zhibiaoji_id) {
			StringBuffer sqlStr=new StringBuffer();
	        sqlStr.append("SELECT * from  formula where formula_name like '%"+name+"%'" +" and zhibiaoji_id="+zhibiaoji_id);
	        Query query = this.getSession().createSQLQuery(sqlStr.toString()).addEntity(Formula.class);
	        return query.list();
		}

		@Override
		public String uptdateformulaFname(Integer id, String fname) {
			try {
				boolean flag=this.checkformulaFname(id,fname);
				if(flag){
					return "repeat";
				}
				Formula formula=(Formula) this.findByPrimaryKey(Formula.class, id);
				formula.setFname(fname);
				this.update(formula);
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "success";
		}

		private boolean checkformulaFname(Integer id, String fname) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) AS num ");
			sql.append(" from formula where ");
			sql.append(" formula_name = ? and id<>?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, fname);
			query.setParameter(1, id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
			{
				if(map.get("num")==null){
					return false;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			if (num > 0)
			{
				return true;
			}
			return false;
		}

		@Override
		public String uptdateformulafid(Integer id, String fid) {
			try {
				boolean flag=this.checkformulaid(id,fid);
				if(flag){
					return "repeat";
				}
				Formula formula=(Formula) this.findByPrimaryKey(Formula.class, id);
				formula.setFid(fid);
				this.update(formula);
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "success";
		}

		private boolean checkformulaid(Integer id, String fid) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) AS num ");
			sql.append(" from formula where ");
			sql.append(" formula_id = ? and id<>?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, fid);
			query.setParameter(1, id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
			{
				if(map.get("num")==null){
					return false;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			if (num > 0)
			{
				return true;
			}
			return false;
		}

		@Override
		@Transactional
		public String uptdateformularemark(String remark,Integer id) {
			try {
				Formula formula=(Formula) this.findByPrimaryKey(Formula.class,id);
				formula.setRemark(remark);
				this.update(formula);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
		}

		@Override
		public String saveformula(String fid, Integer zhibiaoji_id, String fname) {
			try {
				boolean flag=checkrepeat(fid,zhibiaoji_id,fname);
				if(flag){
					return "repeat";
				}
				
				Formula formula=new Formula();
				formula.setFid(fid);
				formula.setFname(fname);
				formula.setZhibiaojiid(zhibiaoji_id);
				this.save(formula);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
		}

		private boolean checkrepeat(String fid, Integer zhibiaoji_id, String fname) {
			int num = 0;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT count(*) AS num ");
			sql.append(" from formula where ");
			sql.append(" (formula_name = ? or formula_id =?) group by zhibiaoji_id having zhibiaoji_id=?");
			Query query = this.getSession().createSQLQuery(sql.toString());
			query.setParameter(0, fname);
			query.setParameter(1, fid);
			query.setParameter(2, zhibiaoji_id);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.list();
			for (Map<String, Object> map : list)
			{
				if(map.get("num")==null){
					return false;
				}
				num = Integer.parseInt(map.get("num").toString());
			}
			if (num > 0)
			{
				return true;
			}
			return false;
		}

		@Override
		public String deleteformula(Integer id) {
			try {
				Formula formula = (Formula) this.findByPrimaryKey(Formula.class, id);
				this.delete(formula);
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
		}
		
	
}

