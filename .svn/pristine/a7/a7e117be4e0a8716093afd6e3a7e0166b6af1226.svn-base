package com.wb.model.entity.computer.accTableEntity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "biz_userbook")
public class BizUserBook implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7804330770368387007L;

    private Integer id;
    
    private Integer user;
    
    private Integer org;
    
    private Integer book;
    
    private Integer auditor;
    
    /**
     * @param user
     * @param org
     * @param book
     * @param auditor
     */
    public BizUserBook(Integer user, Integer org, Integer book, Integer auditor)
    {
        this.user = user;
        this.org = org;
        this.book = book;
        this.auditor = auditor;
    }
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "User")
    public Integer getUser()
    {
        return user;
    }
    
    public void setUser(Integer user)
    {
        this.user = user;
    }
    
    @Column(name = "Org")
    public Integer getOrg()
    {
        return org;
    }
    
    public void setOrg(Integer org)
    {
        this.org = org;
    }
    
    @Column(name = "Book")
    public Integer getBook()
    {
        return book;
    }
    
    public void setBook(Integer book)
    {
        this.book = book;
    }
    
    @Column(name = "Auditor")
    public Integer getAuditor()
    {
        return auditor;
    }
    
    public void setAuditor(Integer auditor)
    {
        this.auditor = auditor;
    }
}
