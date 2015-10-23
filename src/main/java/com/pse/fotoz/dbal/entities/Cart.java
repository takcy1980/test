package com.pse.fotoz.dbal.entities;

import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 * Class describing a shopping cart.
 * Serves as a container for an order.
 * @author Robert
 */
public class Cart {
    private final Order order;

    /**
     * Creates an empty cart.
     */
    public Cart() {
        this.order = new Order();
    }

    /**
     * Creates a cart containing a given order.
     * @param order the order
     */
    public Cart(Order order) {
        this.order = order;
    }
    
    /**
     * Adds an order entry to this cart.
     * @param e the order entry.
     */
    public void addOrderEntry(OrderEntry e) {
        order.setEntries(
                Stream.concat(
                        order.getEntries().stream(), 
                        Stream.of(e)
                ).
                collect(toSet()));
    }
    
    /**
     * Removes an order entry from this cart.
     * @param e the order entry
     */
    public void removeOrderEntry(OrderEntry e) {
        order.setEntries(
                order.getEntries().stream().
                        filter(e2 -> !e.equals(e2)).
                        collect(toSet()));
    }
    
    /**
     * Gives the order this cart contains.
     * @return the order
     */
    public Order getOrder() {
        return order;
    }
}
