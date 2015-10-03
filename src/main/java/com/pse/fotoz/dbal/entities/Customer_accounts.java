/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.dbal.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author 310054544
 */
@Entity
@Table(name="customer_accounts")
public class Customer_accounts implements HibernateEntity {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Customers customer;

    @Basic
    @Column(name = "login", unique = true)
    private String login;

    //TODO: hashen wellicht handig
    @Basic
    @Column(name = "passwordHash")
    private String passwordHash;

    public int getId() {
        return id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    
    
}
