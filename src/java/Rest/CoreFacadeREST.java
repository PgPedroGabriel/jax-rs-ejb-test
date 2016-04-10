/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.CoreSessionBean;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author pedro
 */
@Path("core")
public class CoreFacadeREST extends AbstractFacadeREST {

    @EJB private CoreSessionBean core;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSession(){
        
        result.setSuccess(core.getSession());
        
        return jsonSuccess();
    }
}
