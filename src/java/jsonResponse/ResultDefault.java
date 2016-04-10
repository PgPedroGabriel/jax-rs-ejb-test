/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonResponse;

import Entities.BaseEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pedro
 */
@XmlRootElement
public class ResultDefault{
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name = "status")
    @Expose
    private Boolean status;
    @XmlElement(name = "message")
    @Expose
    private String message;
    @XmlElement(name = "data", required = true)
    @Expose
    private Object data;
    
    public ResultDefault(){
    }
    
    public ResultDefault(Boolean status, String message){
        this.message = message;
        this.status  = status;
    }
    
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }
    
    public void setData(Object data){
        this.data = data;
    }

    public void setError(String message){
        this.message = message;
        this.status = false;
    }
    
    public void setSuccess(Object data){
        this.data = data;
        this.message = "Sucesso";
        this.status = true;
    }
}
