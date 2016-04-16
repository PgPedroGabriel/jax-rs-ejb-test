/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import jsonResponse.ResultDefault;

/**
 *
 * @author pedro
 */
@RequestScoped
public abstract class AbstractREST {
    
    protected final Gson gson;
    protected final ResultDefault result;
    
    HttpSession session;
    
    public AbstractREST() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        
        result = new ResultDefault();
    }
    
    public ResultDefault setError(String message){
        
        result.setError(message);
        return result;
    }
    
    public String jsonError(String message){
        return gson.toJson(setError(message));
    }
    
    public String jsonSuccess(){
        return gson.toJson(result);
    }
}
