package com.wb.framework.commonUtil;

import java.math.BigDecimal;

public class BigOperation
{
    /**
     * 通用加减法
     * 
     * @param first
     * @param second
     * @param how
     * @return
     */
    public static double bOperation(Object first, Object second, String how)
    {
        String tempFirst = "0.0";
        String tempSecond = "0.0";
        tempFirst = String.format("%.3f", Double.parseDouble(String.valueOf(first)));
        tempSecond = String.format("%.3f", Double.parseDouble(String.valueOf(second)));
        if ("-".equals(how))
        {
            BigDecimal firstB = new BigDecimal(tempFirst);
            BigDecimal secondB = new BigDecimal(tempSecond);
            return firstB.subtract(secondB).doubleValue();
        }
        if ("+".equals(how))
        {
            BigDecimal firstB = new BigDecimal(tempFirst);
            BigDecimal secondB = new BigDecimal(tempSecond);
            return firstB.add(secondB).doubleValue();
        }
        return 0.0;
    }
}