package com.wb.model.pojo.computer;

import java.util.List;

public class TrendData
{
    
    private List<String> strArray;
    
    private List<Object> intArray;
    
    @SuppressWarnings("unused")
    private Object totalCount;
    
    public List<String> getStrArray()
    {
        return strArray;
    }
    
    public void setStrArray(List<String> strArray)
    {
        this.strArray = strArray;
    }
    
    public List<Object> getIntArray()
    {
        return intArray;
    }
    
    public void setIntArray(List<Object> intArray)
    {
        this.intArray = intArray;
    }
    
    public Object getTotalCount()
    {
        if (intArray.size() > 0)
        {
            Double total = 0.0;
            for (int i = 0; i < intArray.size(); i++)
            {
                total += Double.parseDouble(intArray.get(i).toString());
            }
            return total;
        }
        else
        {
            return 0;
        }
    }
    
    public void setTotalCount(Object totalCount)
    {
        this.totalCount = totalCount;
    }
}
