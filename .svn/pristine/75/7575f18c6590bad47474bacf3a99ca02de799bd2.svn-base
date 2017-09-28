package com.wb.framework.commonEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.minidev.json.JSONStyle;
import net.minidev.json.JSONValue;

import org.springframework.web.servlet.support.RequestContext;

/**
 * 
 * 状态枚举类
 * 
 * @author 郝洋
 * @version [版本号, 2016-3-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SelectModule
{
    // 记账人员在线状态GROUP
    public enum CLERK_STATE
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), CLERKONLINE("CLERK_STATE_ONLINE", "CLERK_STATE_ONLINE_NAME"), CLERKOFFLINE("CLERK_STATE_OFFLINE", "CLERK_STATE_OFFLINE_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private CLERK_STATE(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if (flag && (CLERK_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if (flag && (CLERK_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (CLERK_STATE pm : CLERK_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 记账人员管理GROUP
    public enum CLERK_MANAGE_STATE
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), DEALING("CLERK_MANAGE_STATE_DEALING", "CLERK_MANAGE_STATE_DEALING_NAME"), ACCEPT("CLERK_MANAGE_STATE_ACCEPT", "CLERK_MANAGE_STATE_ACCEPT_NAME"), REFUSED("CLERK_MANAGE_STATE_REFUSED", "CLERK_MANAGE_STATE_REFUSED_NAME"), BLACKMENU(
            "CLERK_MANAGE_STATE_BLACKMENU", "CLERK_MANAGE_STATE_BLACKMENU_NAME"), DROPED("CLERK_MANAGE_STATE_DROPED", "CLERK_MANAGE_STATE_DROPED_NAME"), GIVEUP("CLERK_MANAGE_STATE_GIVEUP", "CLERK_MANAGE_STATE_GIVEUP_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private CLERK_MANAGE_STATE(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if (flag && (CLERK_MANAGE_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if (flag && (CLERK_MANAGE_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (CLERK_MANAGE_STATE pm : CLERK_MANAGE_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 记账人员是否报税状态GROUP
    public enum ORG_TAX_STATE
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), PAYTAX("ORG_TAX_STATE_PAYTAX", "ORG_TAX_STATE_PAYTAX_NAME"), DISTAX("ORG_TAX_STATE_DISTAX", "ORG_TAX_STATE_DISTAX_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private ORG_TAX_STATE(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if (flag && (ORG_TAX_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if (flag && (ORG_TAX_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (ORG_TAX_STATE pm : ORG_TAX_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 记账人员是否短信通知GROUP
    public enum ORG_MSG_STATE
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), IDSEND("ORG_MSG_STATE_IDSEND", "ORG_MSG_STATE_IDSEND_NAME"), NTSEND("ORG_MSG_STATE_NTSEND", "ORG_MSG_STATE_NTSEND_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private ORG_MSG_STATE(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if (flag && (ORG_MSG_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if (flag && (ORG_MSG_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (ORG_MSG_STATE pm : ORG_MSG_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 催费频率
    public enum ORG_DETAIL_STATE
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), PERMONTH("ORG_DETAIL_STATE_PERMONTH", "ORG_DETAIL_STATE_PERMONTH_NAME"), PERWEEK("ORG_DETAIL_STATE_PERWEEK", "ORG_DETAIL_STATE_PERWEEK_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private ORG_DETAIL_STATE(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if (flag && (ORG_DETAIL_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if (flag && (ORG_DETAIL_STATE.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (ORG_DETAIL_STATE pm : ORG_DETAIL_STATE.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 是否结账
    public enum ORG_IS_SETTLED
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), ISSETTLED("ORG_IS_SETTLED_ISSETTLED", "ORG_IS_SETTLED_ISSETTLED_NAME"), NTSETTLED("ORG_IS_SETTLED_NTSETTLED", "ORG_IS_SETTLED_NTSETTLED_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private ORG_IS_SETTLED(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if (flag && (ORG_IS_SETTLED.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if (flag && (ORG_IS_SETTLED.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (ORG_IS_SETTLED pm : ORG_IS_SETTLED.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
    
    // 企业资质认证GROUP
    public enum ENTERPRISE_QUALIFICATION
    {
        ALL("STATE_ALL_INCLUDE", "STATE_ALL_INCLUDE_NAME"), UNRECOGNIZED("ENTERPRISE_QUALIFICATION_UNRECOGNIZED", "ENTERPRISE_QUALIFICATION_UNRECOGNIZED_NAME"), PENDINGAPPROVAL("ENTERPRISE_QUALIFICATION_PENDINGAPPROVAL", "ENTERPRISE_QUALIFICATION_PENDINGAPPROVAL_NAME"), AUTHENTICATION(
            "ENTERPRISE_QUALIFICATION_AUTHENTICATION", "ENTERPRISE_QUALIFICATION_AUTHENTICATION_NAME"), REJECTED("ENTERPRISE_QUALIFICATION_REJECTED", "ENTERPRISE_QUALIFICATION_REJECTED_NAME");
        
        private String groupKey;
        
        private String groupName;
        
        // 构造方法
        private ENTERPRISE_QUALIFICATION(String groupKey, String groupName)
        {
            this.groupKey = groupKey;
            this.groupName = groupName;
        }
        
        // 获取json格式字符串
        public static String getJsonString(boolean flag, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if (flag && (ENTERPRISE_QUALIFICATION.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
            }
            return JSONValue.toJSONString(map, JSONStyle.NO_COMPRESS);
        }
        
        // 获取一个组合
        public static HashMap<String, String> getItemOfEnum(String key, HttpServletRequest request)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if (pm.groupKey.equals(key))
                {
                    map.put((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                    break;
                }
                
            }
            return map;
        }
        
        // 获取一个key对应的名称
        public static String getItemNameOfEnum(String key, HttpServletRequest request)
        {
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取一个key值对应的keyName的值
        public static String getItemNameByKeyVal(String keyValue, HttpServletRequest request)
        {
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if ((new RequestContext(request)).getMessage(pm.groupKey).equals(keyValue))
                {
                    return (new RequestContext(request)).getMessage(pm.groupName);
                }
            }
            return null;
        }
        
        // 获取List<EnumBean>的下拉选数据
        public static List<EnumBean> getList(boolean flag, HttpServletRequest request)
        {
            List<EnumBean> list = new ArrayList<EnumBean>();
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if (flag && (ENTERPRISE_QUALIFICATION.values()[0].name().equals(pm.name())))
                {
                    continue;
                }
                EnumBean eb = new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                list.add(eb);
            }
            return list;
        }
        
        // 获取一个组合类型为EnumBean
        public static EnumBean getEnumBean(String key, HttpServletRequest request)
        {
            for (ENTERPRISE_QUALIFICATION pm : ENTERPRISE_QUALIFICATION.values())
            {
                if (pm.groupKey.equals(key))
                {
                    return new EnumBean((new RequestContext(request)).getMessage(pm.groupKey), (new RequestContext(request)).getMessage(pm.groupName));
                }
            }
            return null;
        }
    }
}
