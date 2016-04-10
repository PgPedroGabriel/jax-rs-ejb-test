/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import Bean.EventDAOBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonResponse.ResultDefault;
import Entities.Event;
import java.nio.charset.Charset;
import javax.ws.rs.PathParam;
import jsonRequest.EventLocation;

/**
 *
 * @author pedro
 */
@Path("entities.event")
public class EventFacedeREST extends AbstractFacadeREST{
    
    @EJB private EventDAOBean controller;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvents(){
        
        List<Event> events = this.controller.findEventEntities();
    
        result.setSuccess(events);
        
        return jsonSuccess();
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getEvent(@PathParam("id") Long id){
        
        Event event = this.controller.findEvent(id);
        
        if(event == null)
            return jsonError("Evento invalido");
        
        result.setSuccess(event);
        
        return jsonSuccess();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8" )
    @Consumes(MediaType.APPLICATION_JSON)
    public String createEvent(EventLocation eventLocation){
        try{
            
            Event event = new Event(eventLocation);
            
            controller.create(event);
            
            result.setSuccess(event);
            
        } catch(Exception e){
            
            return jsonError("Falha ao criar evento");
        
        }
    
        return jsonSuccess();
    }
}
