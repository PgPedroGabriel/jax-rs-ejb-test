/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.google.gson.annotations.Expose;
import java.util.Objects;

/**
 *
 * @author pedro
 */
public class CartEvent {
    
    @Expose
    private Event event;
    
    @Expose
    private Integer quantity;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public void removeQuantity(){
        this.quantity--;
    }
    
    public void addQuantity(){
        this.quantity++;
    }
    
    public void addQuantity(Integer quantity){
        this.quantity = quantity;
    }
    
    public void removeQuantity(Integer quantity){
        if(this.quantity < quantity){
            this.quantity = 0;
        } else {
            this.quantity -= quantity;
        }
    }
    
    public boolean isEvent(Event e){
        return e.equals(event);
    }
}
