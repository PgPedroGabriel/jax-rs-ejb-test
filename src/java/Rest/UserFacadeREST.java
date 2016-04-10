/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.CoreSessionBean;
import Bean.UserDAOBean;
import Entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonRequest.Login;
import jsonResponse.ResultDefault;

/**
 *
 * @author pedro
 */
@Path("entities.user")
public class UserFacadeREST extends AbstractFacadeREST{

    @EJB private UserDAOBean controller;
    @EJB private CoreSessionBean core;
    
    public UserFacadeREST() {
    }
    
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(User user){
        
        try{
            controller.create(user);
            
            result.setSuccess(user);

        } catch (Exception e){
            return jsonError("Falha em criar usuario");
        }

        return jsonSuccess();
    }
    
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String doLogin(Login loginRequest){
        
        if(core.isLogged()){
            return jsonError("Já existe uma sessão criada.");
        }
        
        try {
            User user = controller.verifyLogin(loginRequest.getLogin());

            if(user.getPassword().equals(loginRequest.getPassword())){
                
                result.setSuccess(user);
                core.setUser(user);
                
            } else {
                return jsonError("Login/Senha inválido");
            }
            
        } catch(Exception e){
            return jsonError("Login/Senha inválido");
        }
        
        return jsonSuccess();
    }
    
}
