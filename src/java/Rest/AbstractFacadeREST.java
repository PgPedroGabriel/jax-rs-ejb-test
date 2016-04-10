/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonResponse.ResultDefault;

/**
 *
 * @author pedro
 */
public abstract class AbstractFacadeREST {
    
    protected final Gson gson;
    protected final ResultDefault result;
    
    public AbstractFacadeREST() {
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
