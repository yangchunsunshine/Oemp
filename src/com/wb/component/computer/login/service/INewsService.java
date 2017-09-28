package com.wb.component.computer.login.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.wb.framework.commonUtil.PageUtil;
import com.wb.framework.commonUtil.PageUtil.PageInfo;
import com.wb.model.entity.computer.news.MntNews;

/**
 * 公告发布接口
 * 
 * @author 姓名 工号
 * @version [版本号, 2016-3-31]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface INewsService
{
    /**
     * 发布公告
     * 
     * @param vo 公告实体
     * @param imageFile 上传图片附件
     * @param orgId 老板ID
     * @param creater 当前系统登录人
     * @param createrName 当前系统登录人姓名
     * @return int
     * @see [类、类#方法、类#成员]
     */
    int createPushMessage(MntNews vo, MultipartFile imageFile, int orgId, int creater, String createrName,String cusId);
    
    /**
     * 获取公告数据
     * 
     * @param limit 数据条数限制
     * @return List<Map<String, Object>>
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> getPushMessage(int orgId, int limit);
    
    /**
     * 下载公告图片
     * 
     * @param id 公告ID
     * @return ResponseEntity<byte[]>
     * @see [类、类#方法、类#成员]
     */
    ResponseEntity<byte[]> downLoadImage(int id);
    
    /**
     * 
     * 查询单条公告数据
     * 
     * @param orgId
     * @param msgId
     * @return
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> getPushMessage(int orgId, String msgId);
    
    /**
     * 删除公告
     * 
     * @param noticeId 公告ID
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    boolean delNotice(String noticeId);
    
    /**
     * 获取公告更多列表
     * 
     * @param userId 系统当前登录人ID
     * @return List<Map<String,Object>>
     */
    PageUtil getNewList(String title, String createDate, int orgId, PageInfo info);
    
    
    /**
     * 获取单条公告信息
     * 
     * @param newId 公告ID
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> getNewInfo(int newId);
    
    /**
     * 修改公告
     * 
     * @param vo 公告POJO类
     * @param imageFile 上传图片附件
     * @return int
     */
    int updateMessage(MntNews vo, MultipartFile imageFile);
    
    /**
     * 删除公告附件
     * 
     * @param newId 公告ID
     * @return boolean
     */
    boolean deleteImageFile(int newId);
    
    /**
     * 下载公告附件
     * 
     * @param newId 公告ID
     * @return ResponseEntity<byte[]>
     */
    ResponseEntity<byte[]> downLoadImageFile(int newId)
        throws UnsupportedEncodingException;
    
    /**
     * 通过代帐公司MntMember表中的ID找到它下面的公司
     * @author "孙旭"
     * @param mntMemberId
     * @return
     */
    List selectCompanyByMntMember(int mntMemberId);
    
}
