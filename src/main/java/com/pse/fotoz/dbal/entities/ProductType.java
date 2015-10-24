/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal.entities;

import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Gijs
 */
@Entity
@Table(name = "product_types")
public class ProductType implements HibernateEntity{

    public interface ValidationStepOne {
        // validation group marker interface

    }
    public interface ValidationStepTwo {
        // validation group marker interface
    }
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "name")
    @Size(min = 1, groups = {ValidationStepOne.class}, message="testbericht naam")
    private String name;
    
    @Basic
    @Column(name = "description")
    @Size(min = 1, groups = {ValidationStepOne.class}, message="testbericht desc")
    private String description;
            
    @Basic
    @Column(name = "price")
    @DecimalMin(value="0.01", groups = {ValidationStepOne.class}, message="test dec")
    private BigDecimal price;
         
    @Basic
    @Column(name = "stock")
    @NotNull @Min(value=13,groups = {ValidationStepOne.class}, message="iets")
    private Integer stock;
    
    @Basic
    @Column(name = "filename")
    @Size(min = 1, message="test")
    private String filename;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    
}
