package com.wb.component.computer.feedBackManage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wb.component.computer.feedBackManage.service.IFeedBackManageService;
import com.wb.component.computer.processManage.service.IProcessManageService;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.processManage.MntFeedBack;

@Controller
@RequestMapping("supervisory")
public class FeedBackManage
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(FeedBackManage.class);
    
    /**
     * 客户评价Service层实例
     */
    @Autowired
    @Qualifier("feedBackManageService")
    private IFeedBackManageService feedBackManageService;
    
    /**
     * 流程管理Service层实例
     */
    @Autowired
    @Qualifier("processManageService")
    private IProcessManageService processManageService;
    
    /**
     * 获取评论节点列表
     * 
     * @param orgProId
     * @return
     */
    @RequestMapping(value = "asyn/getOrgProNodeList", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getOrgProNodeList(int orgProId)
    {
        List<Map<String, Object>> result = processManageService.getOrgProNodeList(orgProId);
        return result;
    }
    
    /**
     * 保存评论信息
     * 
     * @param session session
     * @param feedBack 评论信息实体
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/saveFeedBack", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveFeedBack(HttpSession session, MntFeedBack feedBack, int mngId)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        Map<String, Object> result = new HashMap<String, Object>();
        final boolean code = feedBackManageService.saveFeedBack(feedBack, mngId);
        result.put("code", code);
        return result;
    }
    
    /**
     * 获取评论列表
     * 
     * @param session session
     * @param info 分页信息
     * @return Map<String, Object>
     */
    @RequestMapping(value = "asyn/getFeedBackList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFeedBackList(HttpSession session, String queryName, String queryCreatorName, String queryScore, PageInfo info)
    {
        final MntMember member = (MntMember)session.getAttribute("userInfo");
        final int mngId = member.getOrgId();
        final int userId = member.getId();
        final boolean isAdmin = member.isAdmin();
        final PageUtil util = feedBackManageService.getFeedBackList(isAdmin, userId, mngId, queryName, queryCreatorName, queryScore, info);
        return util.initResult();
    }
    
    /**
     * 页面跳转 客户评价页面
     * 
     * @param request request
     * @param orgId 客户ID
     * @param orgProId 客户流程ID
     * @param mngId 所属公司ID
     * @return String
     */
    @RequestMapping(value = "forward/gotoFeedBackManage", method = RequestMethod.GET)
    public String gotoFeedBackManage(HttpServletRequest request, String orgid, @RequestParam(required = false) String orgProId, String tel)
    {
        
        String mngId = feedBackManageService.getMngIdByOrgId(orgid);
        orgProId = feedBackManageService.getNearOrgProId(orgid, mngId);
        request.setAttribute("orgId", orgid);
        request.setAttribute("tel", tel);
        request.setAttribute("orgProId", orgProId);
        request.setAttribute("mngId", mngId);
        return "feedBackManage/feedBackManage";
    }
    
    /**
     * 页面跳转 客户评价查询页面
     * 
     * @return String
     */
    @RequestMapping(value = "forward/gotoFeedBackQuery", method = RequestMethod.GET)
    public String gotoFeedBackQuery()
    {
        return "feedBackManage/feedBackQuery";
    }
}
