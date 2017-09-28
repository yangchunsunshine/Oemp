package com.wb.component.computer.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.wb.component.computer.login.service.INewsService;
import com.wb.component.mobile.push.PushUtil;
import com.wb.component.mobile.pushMessage.service.IPushMessageService;
import com.wb.framework.commonResponse.AjaxAction;
import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.MntMember;
import com.wb.model.entity.computer.news.MntNews;

/**
 * 公告发布
 * 
 * @author 姓名 郑炜
 * @version [版本号, 2016-3-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("supervisory")
public class News{
	/**
	 * 日志服务
	 */
	private static final Logger logger = Logger.getLogger(News.class);

	/**
	 * 公告ser
	 */
	@Autowired
	@Qualifier("newsService")
	private INewsService newsService;

	/**
	 * 公告ser
	 */
	@Autowired
	@Qualifier("pushMessageService")
	private IPushMessageService pushMessageService;

	/**
	 * 发布公告
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @param vo
	 *            公告实体
	 * @param imageFile
	 *            上传图片附件
	 * @return Map<String, Object>
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "asyn/pushMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pushMessage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, MntNews vo, MultipartFile imageFile) {

		final Map<String, Object> result = new HashMap<String, Object>();
		// 读取待推送消息--开始
		try {
			String notice = vo.getContext();
			// 读取待推送消息--结束
			// 获取memberId--开始
			final MntMember member = (MntMember) session.getAttribute("userInfo");
			final int creater = member.getOrgId();
			final String createrName = member.getUserName();
			// 获取memberId--结束
			// 配置listArgs开始
			ArrayList<Map> listArgs = new ArrayList<Map>();
			Map temp = new HashMap();
			temp.put("type", "5");
			listArgs.add(temp);
			// 配置listArgs结束
			// 读取memberId下的客户信息--开始
			List<Map<String, String>> companys = newsService.selectCompanyByMntMember(member.getOrgId());

			for (Map<String,String> tempmap : companys) {
				//保存公告信息旧接口--开始
				newsService.createPushMessage(vo, imageFile, creater, creater, createrName,tempmap.get("ORGID").toString());
				//保存公告信息旧接口--结束
				if (tempmap.get("iosDeviceToken") != null) {
					// 推送ios消息给客户--开始
					// 插入消息进数据库--开始
					String iosDeviceToken = tempmap.get("iosDeviceToken").toString();
					String bizOrgId=tempmap.get("ORGID").toString();
					pushMessageService.addIosMessage(iosDeviceToken, bizOrgId,"您有一条公告未查看，请及时查阅","公告",5);
					// 插入消息进数据库--结束
					// 查询消息总数badge--开始
					int badge = pushMessageService.countBadge(iosDeviceToken);
					// 查询消息总数badge--结束
					PushUtil.sendIOSUnicast(iosDeviceToken,"您有一条公告未查看，请及时查阅", listArgs, badge);
					// 推送ios消息给客户--结束
					result.put("code", 1);
				} else if (tempmap.get("androidDeviceToken") != null) {
					// 推送android消息给客户--开始
					String androidDeviceToken = tempmap.get("androidDeviceToken").toString();
					pushMessageService.addAndroidMessage(androidDeviceToken, tempmap.get("ORGID").toString(),"您有一条公告未查看，请及时查阅","公告",5);
					PushUtil.sendAndroidUnicast(androidDeviceToken, "通知栏提示文字", "理财金服", "您有一条公告未查看，请及时查阅", listArgs);
					// 推送android消息给客户--结束
					result.put("code", 1);
				}
			}
			// 读取memberId下的客户信息--结束

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "asyn/editMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editMessage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, MntNews vo, MultipartFile imageFile) {
		final Map<String, Object> result = new HashMap<String, Object>();
		final int code = newsService.updateMessage(vo, imageFile);
		result.put("code", code);
		return result;
	}

	/**
	 * 获取公告数据
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @return Map<String, Object>
	 * @see [类、类#方法、类#成员]
	 */
	/*@RequestMapping(value = "asyn/getPushMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPushMessage(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		final MntMember member = (MntMember) session.getAttribute("userInfo");
		final int orgId = member.getOrgId();
		final Map<String, Object> result = new HashMap<String, Object>();
		int getNum = 6;
		if (member.isAdmin()) {
			getNum = 6;
		}
		final List<Map<String, Object>> messageList = newsService.getPushMessage(orgId, getNum);
		result.put("messageList", messageList);
		return result;
	}*/

	/**
	 * 获取公告更多列表
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @return
	 */
	@RequestMapping(value = "asyn/getNewList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getNewList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			PageInfo info) {
		final MntMember member = (MntMember) session.getAttribute("userInfo");
		final int orgId = member.getOrgId();
		final PageUtil util = newsService.getNewList(null, null, orgId, info);
		return util.initResult();
	}

	/**
	 * 删除公告
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @param noticeId
	 *            公告ID
	 * @return Map<String, Object>
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "asyn/delNotice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delNotice(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String noticeId) {
		final Map<String, Object> result = new HashMap<String, Object>();
		final boolean code = newsService.delNotice(noticeId);
		result.put("code", code);
		return result;
	}

	/**
	 * 查询公告列表
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @param title
	 *            标题
	 * @param createDate
	 *            创建时间
	 * @return List<Map<String, Object>>
	 */
	@RequestMapping(value = "asyn/queryNewList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryNewList(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, String title, String createDate, PageInfo info) {
		final MntMember member = (MntMember) session.getAttribute("userInfo");
		final int orgId = member.getOrgId();
		final PageUtil util = newsService.getNewList(title, createDate, orgId, info);
		return util.initResult();
	}

	/**
	 * 删除公告附件
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param session
	 *            session
	 * @param newId
	 *            公告ID
	 * @return
	 */
	@RequestMapping(value = "asyn/deleteImageFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteContractFile(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, String newId) {
		final Map<String, Object> result = new HashMap<String, Object>();
		final boolean code = newsService.deleteImageFile(Integer.parseInt(newId));
		result.put("code", code);
		return result;
	}

	/**
	 * 下载公告附件
	 * 
	 * @param newId
	 *            公告ID
	 * @return ResponseEntity<byte[]>
	 * @throws IOException
	 */
	@RequestMapping("asyn/downLoadImageFile")
	public ResponseEntity<byte[]> downLoadImageFile(String newId) throws IOException {
		final ResponseEntity<byte[]> outFile = newsService.downLoadImageFile(Integer.parseInt(newId));
		return outFile;
	}

	/**
	 * 页面跳转 公告推送页面
	 * 
	 * @param request
	 *            request
	 * @param session
	 *            session
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "forward/gotoPushMessage", method = RequestMethod.GET)
	public String gotoPushMessage(HttpServletRequest request, HttpSession session) {
		return "login/news/pushMessage";
	}

	/**
	 * 页面跳转 公告查看页面
	 * 
	 * @param request
	 *            request
	 * @param session
	 *            session
	 * @return String
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "forward/gotoLookMessage", method = RequestMethod.GET)
	public String gotoLookMessage(HttpServletRequest request, HttpSession session, Model model, String msgId) {
		MntMember member = (MntMember) session.getAttribute("userInfo");
		Map<String, Object> result = newsService.getPushMessage(member.getOrgId(), msgId);
		model.addAttribute("result", result);
		return "login/news/lookMessage";
	}

	@RequestMapping(value = "asyn/getMessageImage", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getMessageImage_ws(HttpServletResponse response, HttpServletRequest request, int id)
			throws IOException {
		final ResponseEntity<byte[]> outFile = newsService.downLoadImage(id);
		return outFile;
	}

	/**
	 * 页面跳转 公告编辑页面
	 * 
	 * @param request
	 *            request
	 * @param session
	 *            session
	 * @param newId
	 *            公告ID
	 * @return String
	 */
	@RequestMapping(value = "forward/gotoEditNews", method = RequestMethod.GET)
	public String gotoEditNews(HttpServletRequest request, HttpSession session, String newId) {
		List<Map<String, Object>> newInfo = newsService.getNewInfo(Integer.parseInt(newId));
		request.setAttribute("newInfo", newInfo);
		return "login/news/editNews";
	}
}
