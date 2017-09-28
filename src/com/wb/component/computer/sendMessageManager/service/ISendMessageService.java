package com.wb.component.computer.sendMessageManager.service;

import java.util.List;
import java.util.Map;

import util.PageModel;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.SendMessage;



public interface ISendMessageService {

	PageUtil getAllOrganization(Integer id, PageInfo info);

	int getNumCount(Integer id);

	PageUtil getAllOrganizationByChose(Integer id, PageInfo info, String orgName);

	int updateSendMessage(Integer orgId, String topname);

	String add_sendRightNow(String mytext, String array,Integer id,String orgName);

	int savewaitSendMessage(String mytext, String array, String dateTime,Integer id);

	List<SendMessage> getAllMessage(Integer id);

	PageUtil getFenyeResult(Integer id, PageInfo info);

	PageModel findList(int pageNum, int pageSize,Integer id);
	
	SendMessage findMessageById(Integer id);
	int updateMessage(SendMessage sendMessage,Integer id);

	Map<String, String> getkeys(Integer id);

	void saveTuiSong(String content, String tel, Integer id);
	


}
