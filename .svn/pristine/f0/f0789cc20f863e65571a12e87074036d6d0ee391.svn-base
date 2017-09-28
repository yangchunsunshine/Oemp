package com.wb.component.computer.common.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * 管理平台录入验证枚举
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ComputerCheckEnum
{
    /**
     * 日志服务
     */
    private static final Logger log = Logger.getLogger(ComputerCheckEnum.class);
    
    /**
     * 验证类型枚举
     * 
     * @author 郝洋
     * @version [版本号, 2016-3-21]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public enum VERIFY
    {
        /**
         * 手机号码验证
         */
        VERIFY_TELPHONE_MODE("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}", 0),
        /**
         * 座机号码验证
         */
        VERIFY_SolidTel_MODE("^(0\\d{2})-?\\d{8}$|^(0\\d{3}-?(\\d{7,8}))$", 0),
        /**
         * 邮箱验证
         */
        VERIFY_EMAIL_MODE("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$", Pattern.CASE_INSENSITIVE),
        /**
         * 姓名验证
         */
        VERIFY_NAME_MODE("[\u4e00-\u9fa5]*", 0),
        /**
         * 身份证验证
         */
        VERIFY_IDCARD_MODE("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", Pattern.CASE_INSENSITIVE),
        /**
         * 银行卡验证
         */
        VERIFY_CARDNO_MODE("[0-9]{19}", 0),
        /**
         * 金额验证
         */
        VERIFY_Amount_MODE("(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?", 0),
        /**
         * 数字验证
         */
        VERIFY_NUMBER_MODE("[0-9]*", Pattern.CASE_INSENSITIVE),
        /**
         * 密码验证
         */
        VERIFY_PASSWORD_MODE("[a-z0-9_]*", Pattern.CASE_INSENSITIVE),
        /**
         * 时间格式验证 HH:mm
         */
        VERIFY_TIME_MODE("([0-1][0-9]|2[0-3])\\:[0-5][0-9]", 0);
        
        /**
         * regex regex
         */
        private String regex;
        
        /**
         * mode mode
         */
        private int mode;
        
        /** 
         * <默认构造函数>
         * @param regex regex
         * @param mode mode
         */
        private VERIFY(String regex, int mode)
        {
            this.regex = regex;
            this.mode = mode;
        }
        
        /**
         * 验证录入字符串是否匹配
         * 
         * @param inputStr 录入字符串
         * @return 是否匹配
         * @see [类、类#方法、类#成员]
         */
        public boolean isVerificated(String inputStr)
        {
            if (inputStr == null)
            {
                return true;
            }
            final Pattern pattern = Pattern.compile(regex, mode);
            final Matcher matcher = pattern.matcher(inputStr);
            return matcher.find();
        }
    }
}
