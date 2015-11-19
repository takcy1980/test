package com.pse.fotoz.dbal.entities;

import java.util.Map;
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
 * Entity describing an item of an order.
 * It currently encompasses a picture, options associated to that picture,
 * an amount and total price of this entry.
 * @author Robert
 */
@Entity
@Table(name="order_entries")
public class OrderEntry implements HibernateEntity {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    /**
     * Temporary identity value for when not (yet) persisted.
     */
    private transient int tempId;
    
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
    @JoinColumn(name = "product_options")
    private ProductOption options;
    
    @Basic
    @Column(name = "amount")
    private int amount;
    
    @Basic
    @Column(name = "total_price")
    private double totalPrice;

    /**
     * Gives the identity of this order entry.
     * If this entry has not yet been assigned a persisted identity, it will
     * instead provide a temporary identity, set through setTempId.
     * @return Identity of this entry.
     */
    public int getId() {
        return id == 0 ? tempId : id;
    }   

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Sets a temporary identity for this order entry.
     * This identity should be unique within an order, this is unchecked.
     * @param tempId The temporary identity.
     */
    public void setTempId(int tempId) {
        this.tempId = tempId;
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
