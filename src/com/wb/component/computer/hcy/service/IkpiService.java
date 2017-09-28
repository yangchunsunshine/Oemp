package com.wb.component.computer.hcy.service;

import java.util.List;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.ZhiBiaoji;
import com.wb.model.entity.computer.Formula;
import com.wb.model.entity.computer.Kpi;
import com.wb.model.entity.computer.Moban;
import com.wb.model.entity.computer.Model;

public interface IkpiService {

	PageUtil getModelNodeList(PageInfo pageInfo);

	List<Kpi> getKpis(Integer zhibiaoji_id);

	String deleteKpi(Integer id);

	String updatezzname(String zzname,Integer id);

	String updatezid(String zid, Integer id);

	String updateremark(String remark, Integer id);

	List<Kpi> findResult(String znname,Integer zhibiaoji_id);

	PageUtil getModelList( PageInfo info,Integer id);

	String deleteModel(Integer id);

	List<Model> findProduct(Integer id);

	PageUtil getModelList_c(PageInfo info, Integer productId, Integer selectData);

	String addmoban(Integer fenleiId, String name);

	String addmoban_defalut(Integer proId, Integer fenleiId, String name);

	Moban findMobanByPrimaryKey(Integer processTmpId);

	int updateMobanAndCheckCode(Moban moban);

	PageUtil getAllZhibiao(PageInfo info);

	String savezhibiaoji(Integer fenleiId, String name);

	List<Moban> showAllMoban(Integer type);

	String savezhibiaoJI(Integer fenleiId, String name);

	String deleteZhiBiaoJi(Integer id);

	List<Kpi> getKpisbyId(Integer id);

	String savezhibiaoji_hcy(String zid, String znname, Integer zhibiaoji_id);

	PageUtil getAllZhibiaobyMoban(PageInfo info, Integer id);

	String savezhibiaoJI_de(Integer id, String name);

	ZhiBiaoji getzhibiaojiById(Integer id);

	List<Formula> getAllFormulabuzhijiaojiId(Integer id);

	List<Formula> getAllFormulabuzhijiaojiName(String name,Integer zhibiaoji_id);

	String uptdateformulaFname(Integer id, String fname);

	String uptdateformulafid(Integer id, String fid);

	String uptdateformularemark(String remark,Integer id);

	String saveformula(String fid, Integer zhibiaoji_id, String fname);

	String deleteformula(Integer id);

}
