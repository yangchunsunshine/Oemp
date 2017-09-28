package com.wb.component.mobile.common.verification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 格式验证
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-12]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MobileCheckEnum
{
    
    // 枚举各种要求验证的类型
    public enum VERIFY
    {
        // 手机号码验证
        VERIFY_TELPHONE_MODE("(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}", 0),
        // 座机号码验证
        VERIFY_SolidTel_MODE("^(0\\d{2})-?\\d{8}$|^(0\\d{3}-?(\\d{7,8}))$", 0),
        // 邮箱验证
        VERIFY_EMAIL_MODE("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$", Pattern.CASE_INSENSITIVE),
        // 姓名验证
        VERIFY_NAME_MODE("[\u4e00-\u9fa5]*", 0),
        // 身份证验证
        VERIFY_IDCARD_MODE("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", Pattern.CASE_INSENSITIVE),
        // 银行卡验证
        VERIFY_CARDNO_MODE("[0-9]{19}", 0),
        // 金额验证
        VERIFY_Amount_MODE("(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?", 0),
        // 数字验证
        VERIFY_NUMBER_MODE("[0-9]*", Pattern.CASE_INSENSITIVE),
        // 密码验证
        VERIFY_PASSWORD_MODE("[a-z0-9_]*", Pattern.CASE_INSENSITIVE),
        // 时间格式验证 HH:mm
        VERIFY_TIME_MODE("([0-1][0-9]|2[0-3])\\:[0-5][0-9]", 0);
        
        private String regex;
        
        private int mode;
        
        // 构造方法
        private VERIFY(String regex, int mode)
        {
            this.regex = regex;
            this.mode = mode;
        }
        
        // 验证录入字符串是否匹配
        public boolean isVerificated(String inputStr)
        {
            if (inputStr == null)
            {
                return true;
            }
            Pattern pattern = Pattern.compile(regex, mode);
            Matcher matcher = pattern.matcher(inputStr);
            if (!matcher.find())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
}
