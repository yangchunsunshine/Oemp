package com.wb.model.entity.computer;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * MntTaxcategories entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "mnt_taxcategories")
public class MntTaxcategories implements java.io.Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8035687424337494703L;

    // Fields
    
    private Integer id;
    
    private Integer reid;
    
    private Integer shownum;
    
    private String name;
    
    private double balance;
    
    // Constructors
    
    /** default constructor */
    public MntTaxcategories()
    {
    }
    
    /** minimal constructor */
    public MntTaxcategories(Integer reid, Integer shownum)
    {
        this.reid = reid;
        this.shownum = shownum;
    }
    
    /** full constructor */
    public MntTaxcategories(Integer reid, Integer shownum, String name, double balance)
    {
        this.reid = reid;
        this.shownum = shownum;
        this.name = name;
        this.balance = balance;
    }
    
    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Integer getId()
    {
        return this.id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    @Column(name = "REID", nullable = false)
    public Integer getReid()
    {
        return this.reid;
    }
    
    public void setReid(Integer reid)
    {
        this.reid = reid;
    }
    
    @Column(name = "SHOWNUM", nullable = false)
    public Integer getShownum()
    {
        return this.shownum;
    }
    
    public void setShownum(Integer shownum)
    {
        this.shownum = shownum;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "BALANCE", precision = 50)
    public double getBalance()
    {
        return this.balance;
    }
    
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
    
}