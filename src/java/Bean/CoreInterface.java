/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.util.Map;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.json.JsonObject;

/**
 *
 * @author pedro
 */
@Remote
public interface CoreInterface {
    public Map<String, Entities.BaseEntity> getSession();
    public boolean isLogged();
    public void setUser(Entities.User user);
    public Entities.User getUser();
    public Entities.Cart getCart();
    public void setCart(Entities.Cart cart);
    public void addInCart(Entities.Event e, Integer quantity);
    public void removeInCart(Entities.Event e, Integer quantity);
}
