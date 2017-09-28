package com.wb.model.pojo.computer;

import org.springframework.web.multipart.MultipartFile;

public class CustomAttachment implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5445797497112153247L;

    private String comments;
    
    private MultipartFile file;
    
    public String getComments()
    {
        return comments;
    }
    
    public void setComments(String comments)
    {
        this.comments = comments;
    }
    
    public MultipartFile getFile()
    {
        return file;
    }
    
    public void setFile(MultipartFile file)
    {
        this.file = file;
    }
    
}