/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.CoreSessionBean;
import Bean.UserDAOBean;
import Entities.User;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import jsonRequest.Login;

/**
 *
 * @author pedro
 */
@Path("entities.user")
public class UserFacadeREST extends AbstractREST{
    
    @EJB private UserDAOBean controller;
    
    @EJB private CoreSessionBean core;
    
    @EJB private UserDAOBean userBean;
    
    @Context HttpServletRequest request;
    
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(User user){
        
        try{ 
            User userExists = controller.verifyLogin(user.getLogin());
            
            if(userExists != null){
                return jsonError("Login já existe, informe outro");
            }
            
        } catch(Exception e) {}
           
        try {    
            controller.create(user);
            
            session = request.getSession();
            session.setAttribute("user", user.getId());
            
            result.setSuccess(user);

        } catch (Exception e){
            return jsonError(e.getMessage());
        }

        return jsonSuccess();
    }
    
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String doLogin(Login loginRequest){
        
        try {
            User user = controller.verifyLogin(loginRequest.getLogin());

            if(user.getPassword().equals(loginRequest.getPassword())){
                
                result.setSuccess(user);
                session = request.getSession();
                session.setAttribute("user", user.getId());
                core.setUser(user);
                
            } else {
                return jsonError("Login/Senha inválido");
            }
            
        } catch(Exception e){
            return jsonError("Login/Senha inválido");
        }
        
        return jsonSuccess();
    }

    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String doLogout(){
        session = request.getSession();
        
        if(session != null && session.getAttribute("user") != null){
            session.removeAttribute("user");
        }
        
        return jsonSuccess();
    }
    
}
