/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.CoreSessionBean;
import Bean.EventDAOBean;
import Entities.Event;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import jsonRequest.Cart;

/**
 *
 * @author pedro
 */
@Path("entities.cart")
public class CartFacadeREST extends AbstractREST {
    
    @EJB private EventDAOBean eventController;
  
    @EJB private CoreSessionBean core;
    
    @Context HttpServletRequest request;
    
/*    
    public CartFacadeREST(@Context HttpServletRequest request) {;
        super(request);
    }
   */ 
    @Path("add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addInCart(Cart cart){
        try{
            Event event =  eventController.findEvent(cart.getEventId());
            
            if(event == null)
                return jsonError("Evento inválido");
            
            session = request.getSession();
            
            if(session.getAttribute("cart") != null){
                core.setCart((Entities.Cart) session.getAttribute("cart"));
            }
            
            core.addInCart(event, cart.getQuantity());
            
            //session.setAttribute("cart", core.getCart());
            
            result.setSuccess(core.getCart());
            
            return jsonSuccess();
            
        }catch(Exception e){
            return jsonError("Falha em adicionar no carrinho");
        }
    }
    
        
    @Path("remove")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String removeInCart(Cart cart){
        try{
            
            Event event =  eventController.findEvent(cart.getEventId());
            
            if(event == null)
                return jsonError("Evento inválido");
            
            session = request.getSession();
            
            if(session.getAttribute("cart") != null){
                core.setCart((Entities.Cart) session.getAttribute("cart"));
            }
            
            core.removeInCart(event, cart.getQuantity());
            
            //session.setAttribute("cart", core.getCart());
            
            result.setSuccess(core.getCart());
            
            return jsonSuccess();
            
        }catch(Exception e){
            return jsonError("Falha em adicionar no carrinho");
        }
    }
    
}
