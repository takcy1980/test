package com.pse.fotoz.dbal.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Robert
 */
@Entity
@Table(name="order_entries")
public class OrderEntry implements HibernateEntity {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;
    
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType type;
    
    @ManyToOne
    @JoinColumn(name = "customer_account")
    private ProductOption options;
    
    @Basic
    @Column(name = "amount")
    private int amount;
    
    @Basic
    @Column(name = "total_price")
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public ProductOption getOptions() {
        return options;
    }

    public void setOptions(ProductOption options) {
        this.options = options;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
