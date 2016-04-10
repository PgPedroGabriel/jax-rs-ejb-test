/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author pedro
 */
public class Cart extends BaseEntity{
    
    @Expose
    private Collection<CartEvent> events = new ArrayList<CartEvent>();
        
    public Collection<CartEvent> getEvents() {
        return events;
    }

    public void setEvents(Collection<CartEvent> events) {
        this.events = events;
    }
    
    public void add(Event event, Integer quantity){
        CartEvent c = this.getCartEvent(event);
        if(c == null){
            
            c = new CartEvent();
            c.setEvent(event);
            c.setQuantity(quantity);
            
            events.add(c);
        }
        else {
            c.addQuantity(quantity);
        }
    }
    
    public void remove(Event event, Integer quantity){
        CartEvent c = this.getCartEvent(event);
        if(c == null){
            return;
        }
        
        c.removeQuantity(quantity);
    }
    
    public void removeEvent(Event e){
        CartEvent c = this.getCartEvent(e);
        
        if(c == null)
            return;
        
        events.remove(c);
    }
    
    public CartEvent getCartEvent(Event e){
        for(CartEvent cartEvent : events){
            if(cartEvent.isEvent(e)){
                return cartEvent;
            }
        }
        
        return null;
    }
}
