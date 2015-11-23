package com.pse.fotoz.dbal.entities;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity describing an order of one or more items (OrderEntries).
 * An order is associated to a customer account.
 * @author Robert
 */
@Entity
@Table(name="orders")
public class Order implements HibernateEntity {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "customer_account_id")
    private CustomerAccount account;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    
    @OneToMany(mappedBy="order")
    private Set<OrderEntry> entries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerAccount getAccount() {
        return account;
    }

    public void setAccount(CustomerAccount account) {
        this.account = account;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<OrderEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<OrderEntry> entries) {
        this.entries = entries;
    }
    
    public static enum OrderStatus {
        PLACED, PAID, PROCESSED
    }
}
