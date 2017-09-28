package com.wb.component.computer.hcy.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.hcy.service.IkpiService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.ZhiBiaoji;
import com.wb.model.entity.computer.Formula;
import com.wb.model.entity.computer.Kpi;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.MntProcessInfoTmp;
import com.wb.model.entity.computer.Moban;
import com.wb.model.entity.computer.Model;
@Controller
@RequestMapping(value = "kpi")
public class KpiAction extends AjaxAction{
	 private static final Logger logger = Logger.getLogger(KpiAction.class);
	 
	 @Autowired
	 private IkpiService kpiService;
	 
		 @RequestMapping(value="forward/gotokpi",method=RequestMethod.GET)
		 public String gotoBusinessModel(HttpServletRequest request){
		    	return "kpi/showNode";
		    }
	 
	 
	    @RequestMapping(value="forward/gotoKpiList",method=RequestMethod.POST)
	    @ResponseBody
	    public Map<String, Object> gotoBusinessModelShowNode(HttpServletRequest request){
	    	PageInfo pageInfo=new PageInfo(1, 50);
	    	 final PageUtil util =kpiService.getModelNodeList(pageInfo);
	    	return util.initResult();
	    }
	 
	    
	    @RequestMapping(value="show/kpis",method=RequestMethod.GET)
		 public String showKpis(HttpServletRequest request){
	    	List<Kpi> kpis=new ArrayList<Kpi>();
//	    	kpis=kpiService.getKpis();
	    	
	    	request.setAttribute("kpis", kpis);
		    return "kpi/kpis";
		    }
	    
	    
	    @RequestMapping(value="show/kpisbyid",method=RequestMethod.GET)
		 public String showKpis_defauft(HttpServletRequest request,Integer id,HttpSession session){
	    	List<Kpi> kpis=new ArrayList<Kpi>();
	    	kpis=kpiService.getKpisbyId(id);
	    	ZhiBiaoji biaoji=kpiService.getzhibiaojiById(id);
//	    	kpis=kpiService.getKpis();
	    	request.setAttribute("kpis", kpis);
	    	request.setAttribute("name", biaoji.getName());
	    	//request.setAttribute("zhibiaoji_id", id);
	    	session.setAttribute("zhibiaoji_id", id);
		    return "kpi/kpis";
		    }
	    
	    /****
	     * 编辑公式
	     * @param request
	     * @param id
	     * @return
	     */
	    @RequestMapping(value="show/formulabyZhibiaoji",method=RequestMethod.GET)
		 public String formulabyZhibiaoji(HttpServletRequest request,Integer id,HttpSession session){
	    	/*List<Kpi> kpis=new ArrayList<Kpi>();
	    	kpis=kpiService.getKpisbyId(id);
	    	
//	    	kpis=kpiService.getKpis();
	    	request.setAttribute("kpis", kpis);
	    	request.setAttribute("name", biaoji.getName());
	    	//request.setAttribute("zhibiaoji_id", id);
	    	session.setAttribute("zhibiaoji_id", id);*/
	    	session.setAttribute("zhibiaoji_id", id);
	    	ZhiBiaoji biaoji=kpiService.getzhibiaojiById(id);
	    	request.setAttribute("name", biaoji.getName());
	    	List<Formula> formulas=new ArrayList<Formula>();
	    	formulas=kpiService.getAllFormulabuzhijiaojiId(id);
	    	request.setAttribute("formulas", formulas);
		    return "kpi/formula";
		    }
	    
	    
	     @RequestMapping(value="del/kpi",method=RequestMethod.POST)
	     @ResponseBody
		 public Map<String, Object> deleteKip(HttpServletRequest request,Integer id){
	    	 Map<String, Object> map=new HashMap<String, Object>();
	    	 String result=kpiService.deleteKpi(id);
	    	 map.put("result", result);
		    return map;
		    }
	    
	     
	     @RequestMapping(value="updatezzname",method=RequestMethod.POST)
	     @ResponseBody
		 public Map<String, Object> updatezzname(HttpServletRequest request,String znname,Integer id){
	    	 Map<String, Object> map=new HashMap<String, Object>();
	    	 String result=kpiService.updatezzname(znname,id);
	    	 map.put("result", result);
		    return map;
		    }
	 
	     
	     @RequestMapping(value="updatezid",method=RequestMethod.POST)
	     @ResponseBody
		 public Map<String, Object> updatezid(HttpServletRequest request,String zid,Integer id){
	    	 Map<String, Object> map=new HashMap<String, Object>();
	    	 String result=kpiService.updatezid(zid,id);
	    	 map.put("result", result);
		    return map;
		    }
	     
	     @RequestMapping(value="updateremark",method=RequestMethod.POST)
	     @ResponseBody
		 public Map<String, Object> updateremark(HttpServletRequest request,String remark,Integer id){
	    	 Map<String, Object> map=new HashMap<String, Object>();
	    	 String result=kpiService.updateremark(remark,id);
	    	 map.put("result", result);
		    return map;
		    }
	 
	     
	     @RequestMapping(value="searchResult",method=RequestMethod.GET)
		 public String  searchResult(HttpServletRequest request,String znname,Integer zhibiaoji_id) throws Exception{
	    	 List<Kpi> kpis=new ArrayList<Kpi>();
	    	 String name=URLDecoder.decode(znname,"UTF-8");
	    	 if(null==name || "".equals(name.trim())){
	    		 kpis=kpiService.getKpis(zhibiaoji_id);
	 	    	request.setAttribute("kpis", kpis);
	    	 }else{
	    		 kpis=kpiService.findResult(name,zhibiaoji_id);
		    	 request.setAttribute("kpis", kpis); 
	    	 }
	    	 return "kpi/kpis";
		    }
	     
	     
	     @RequestMapping(value="searchResultgongshi",method=RequestMethod.GET)
		 public String  searchResultgongshi(HttpServletRequest request,String fname,Integer zhibiaoji_id) throws Exception{
	    	 ZhiBiaoji biaoji=kpiService.getzhibiaojiById(zhibiaoji_id);
	    	 request.setAttribute("name", biaoji.getName());
	    	 List<Formula> formulas=new ArrayList<Formula>();
	    	 String name=URLDecoder.decode(fname,"UTF-8");
	    	 if(null==name || "".equals(name.trim())){
	    		formulas=kpiService.getAllFormulabuzhijiaojiId(zhibiaoji_id);
	 	    	request.setAttribute("formulas",formulas);
	    	 }else{
	    		 formulas=kpiService.getAllFormulabuzhijiaojiName(name,zhibiaoji_id);
		    	 request.setAttribute("formulas", formulas); 
	    	 }
	    	 return "kpi/formula";
		    }
	     
	     
	     
	     @RequestMapping(value="forward/model",method=RequestMethod.GET)
		 public String forwardmodel(HttpServletRequest request){
		    return "kpi/businessModel";
		    }
	     
	     @RequestMapping(value="kpi/getAllBusinessModel",method=RequestMethod.POST)
	     @ResponseBody
	     public Map<String, Object> getAllBusinessModel(HttpServletRequest request,HttpSession session, PageInfo info,Integer productId){
	    	 PageUtil util = kpiService.getModelList(info,productId);
			 return  util.initResult();
			  }
	     
	     
	     @RequestMapping(value="kpi/getAllBusinessModel_c",method=RequestMethod.POST)
	     @ResponseBody
	     public Map<String, Object> getAllBusinessModel_c(HttpServletRequest request,HttpSession session, PageInfo info,Integer productId,Integer selectData){
	    	 PageUtil util = kpiService.getModelList_c(info,productId,selectData);
			 return  util.initResult();
			  }
	     
	     
	     @RequestMapping(value="asyn/deleteProcessTemp",method=RequestMethod.POST)
	     @ResponseBody
	     public Map<String, Object> deleteProcessTemp(HttpServletRequest request,HttpSession session, Integer id){
	    	Map<String, Object> map=new HashMap<String, Object>();
	    	String result=kpiService.deleteModel(id);
	    	map.put("result", result);
			 return  map;
			}
	     
	     
	     /***
	      * 选中产品后迭代出分类  把所有分类平铺在下拉框中
	      */
	     
	     @RequestMapping(value="fenlei",method=RequestMethod.GET)
	     @ResponseBody
	     public Map<String, Object> fenlei(HttpServletRequest request,HttpSession session, Integer id){
	    	Map<String, Object> map=new HashMap<String, Object>();
	    	List<Model> result=kpiService.findProduct(id);
	    	map.put("result", result);
			return  map;
			}
	   
	     
	     @RequestMapping(value="addMoban",method=RequestMethod.POST)
	     @ResponseBody
	     public Map<String, Object> addMoban(HttpServletRequest request,HttpSession session, Integer proId,Integer fenleiId,String name){
	    	Map<String, Object> map=new HashMap<String, Object>();
	    	//String result=kpiService.addmoban(fenleiId,name);
	    	String result=kpiService.addmoban_defalut(proId,fenleiId,name);
	    	map.put("result", result);
			return  map;
			}
	     
	     
	     /**
	      * 
	      * @param request
	      * @param session
	      * @param proId
	      * @param fenleiId
	      * @param name
	      * @des: 修改模板名称
	      * @return
	      */
	     @RequestMapping(value="forward/gotoUpdateProceTemp",method=RequestMethod.GET)
	     public String gotoUpdateProceTemp(HttpServletRequest request,HttpSession session, Integer processTmpId){
	    	Moban moban=kpiService.findMobanByPrimaryKey(processTmpId);
	    	List<Moban> processInfoTmp=new ArrayList<Moban>();
	    	processInfoTmp.add(moban);
	    	request.setAttribute("processInfoTmp", processInfoTmp);
	    	 return "kpi/updateProceTemp";
			}
	     
	     
	     @RequestMapping(value="asyn/saveProcessTemp",method=RequestMethod.POST)
	     @ResponseBody
	     public Map<String, Object> updateProcessTemp(Integer id,String name,  HttpServletRequest request,HttpSession session){
	    	Map<String, Object> map=new HashMap<String, Object>();
	    	Moban moban=new Moban();
	    	moban.setName(name);
	    	moban.setId(id);
	    	int code=kpiService.updateMobanAndCheckCode(moban);
	    	map.put("code", code);
	    	return map;
			}
	     
//	     @RequestMapping(value = "asyn/saveProcessTemp", method = RequestMethod.POST)
//	     @ResponseBody
//	     public Map<String, Object> saveProcessTemp(@RequestBody List<MntProcessInfoTmp> nodes,HttpSession session)
//	     {
//	     	Map<String, Object> result = new HashMap<String, Object>();
//	     	final MntMember member = (MntMember)session.getAttribute("userInfo");
//	     	int adminId = member.getEmpInfo().getId();
//	     	final int code = processManageService.updateProcessInfoTemp(nodes.get(0),adminId);
//	     	result.put("code", code);
//	     	return result;
//	     }
	     
	     
	     @RequestMapping(value="forward/xinzengzhibiao",method=RequestMethod.GET)
	  	 public String xinzengzhibiao(HttpServletRequest request){
	  		    return "kpi/xinzengzhibiao";
	  		  }
	     
	     @RequestMapping(value="kpi/getAllZhibiao",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> getAllZhibiao(HttpServletRequest request,HttpSession session, PageInfo info){
	         PageUtil util = kpiService.getAllZhibiao(info);
			 return  util.initResult(); 
	  		  }
	     
	     @RequestMapping(value="kpi/getAllZhibiaoByMoban",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> getAllZhibiaoByMoban(HttpServletRequest request,HttpSession session, PageInfo info,Integer id){
	         //PageUtil util = kpiService.getAllZhibiao(info);
	    	 PageUtil util = kpiService.getAllZhibiaobyMoban(info,id);
	    	 
			 return  util.initResult(); 
	  		  }
	     
	     
	     @RequestMapping(value="kpi/savezhibiao",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> savezhibiao(HttpServletRequest request,HttpSession session, PageInfo info,Integer fenleiId,String name){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.savezhibiaoji(fenleiId,name);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/savezhibiaoJI",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> savezhibiaoJI(HttpServletRequest request,HttpSession session, PageInfo info,Integer fenleiId,String name){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.savezhibiaoJI(fenleiId,name);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/savezhibiaoJI_de",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> savezhibiaoJI_de(HttpServletRequest request,HttpSession session, PageInfo info,Integer id,String name){
	       Map<String, Object> map=new HashMap<String, Object>();
	      // String result=kpiService.savezhibiaoJI(fenleiId,name);
	       String result=kpiService.savezhibiaoJI_de(id,name);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/showMoban",method=RequestMethod.GET)
	     @ResponseBody
	  	 public Map<String, Object> showMoban(HttpServletRequest request,HttpSession session,Integer type){
	       Map<String, Object> map=new HashMap<String, Object>();
	      List<Moban> result=kpiService.showAllMoban(type);
	      map.put("result", result);
	       return map;
	  		}
	     
	     
	     /***
	      * 删除指标集和对象的指标
	      */
	     @RequestMapping(value="kpi/deleteZhiBiaoji",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> deleteZhiBiaoji(HttpServletRequest request,HttpSession session,Integer id ){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.deleteZhiBiaoJi(id);
	      map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/saveZhiboji_hcy",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> saveZhiboji_hcy(HttpServletRequest request,HttpSession session,String zid,String znname,Integer zhibiaoji_id ){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.savezhibiaoji_hcy(zid,znname,zhibiaoji_id);
	      map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/updateformulafname",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> updateformulafname(HttpServletRequest request,HttpSession session,String fname,Integer id){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.uptdateformulaFname(id,fname);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/updateformulaForfid",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> updateformulaForfid(HttpServletRequest request,HttpSession session,String fid,Integer id){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.uptdateformulafid(id,fid);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="kpi/updateformularemark",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> updateformularemark(HttpServletRequest request,HttpSession session,String remark,Integer id){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.uptdateformularemark(remark,id);
	       map.put("result", result);
	       return map;
	  		}
	     
	     @RequestMapping(value="kpi/saveformula",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> saveformula(HttpServletRequest request,HttpSession session,String fname,String fid,Integer zhibiaoji_id){
	       Map<String, Object> map=new HashMap<String, Object>();
	      String result=kpiService.saveformula(fid,zhibiaoji_id,fname);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
	     @RequestMapping(value="del/formula",method=RequestMethod.POST)
	     @ResponseBody
	  	 public Map<String, Object> deleteFormula(HttpServletRequest request,HttpSession session,Integer id){
	       Map<String, Object> map=new HashMap<String, Object>();
	       String result=kpiService.deleteformula(id);
	       map.put("result", result);
	       return map;
	  		}
	     
	     
}
