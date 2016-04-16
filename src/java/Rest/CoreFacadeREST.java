/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.CoreSessionBean;
import Bean.UserDAOBean;
import Entities.Cart;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author pedro
 */
@Path("core")
public class CoreFacadeREST extends AbstractREST {

    
    @EJB private CoreSessionBean core;
    @EJB private UserDAOBean userController;
    
    @Context HttpServletRequest request;
    /*
    public CoreFacadeREST() {
        super(request);
    }
*/
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSession(){
        
        session = request.getSession();
        
        Entities.User user = null;
        Cart cart = new Entities.Cart();
        
        if(session.getAttribute("user") != null)
            user = userController.findUser((Long) session.getAttribute("user"));
        
        if(session.getAttribute("cart") != null)
            cart = (Cart) session.getAttribute("cart");
        
        core.setUser(user);
        core.setCart(cart);
        
        result.setSuccess(core.getSession());
        
        return jsonSuccess();
    }
}
