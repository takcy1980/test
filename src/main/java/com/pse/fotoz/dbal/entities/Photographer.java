package com.pse.fotoz.dbal.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robert
 */
@Entity
@Table(name="photographers")
public class Photographer implements HibernateEntity {
    
    @Id
    @Column(name="id")
    private int id;
    
    @Basic
    @Column(name="name")
    private String name;
    
    @Basic
    @Column(name="address")
    private String address;
    
    @Basic
    @Column(name="city")
    private String city;
    
    @Basic
    @Column(name="phone")
    private String phone;
    
    @Basic
    @Column(name="email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
