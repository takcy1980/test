package com.pse.fotoz.dbal.entities;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Class describing a shopping cart.
 * Serves as a container for an order.
 * @author Robert
 */
public class Cart implements Serializable {
    private final Order order;

    /**
     * Creates an empty cart.
     */
    public Cart() {
        this.order = new Order();
        this.order.setEntries(new HashSet<>());
    }
    
    /**
     * Gives the order this cart contains.
     * @return the order
     */
    public Order getOrder() {
        return order;
    }
}
