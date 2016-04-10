/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import jsonRequest.EventLocation;

/**
 *
 * @author pedro
 */
@Entity
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")})
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    @Expose
    private Long id;
    
    @Column(name = "FULLDESCRIPTION")
    @Expose
    private String fulldescription;
    
    @Column(name = "IMAGEPATH")
    @Expose
    private String imagepath;
    
    @Column(name = "NAME")
    @Expose
    private String name;
    
    @Column(name = "PRICE", columnDefinition="Decimal(10,2)")
    @Expose
    private Double price;
    
    @Column(name = "SHORTDESCRIPTION")
    @Expose
    private String shortdescription;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<Buylog> buylogCollection = new ArrayList<Buylog>();
    
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    @Expose
    private Location locationId;
    
    @Transient
    private Long locationIdTransient;



    public Event() {
    }
    
    public Event(EventLocation request){
        
        if(request.locationId != null){
            locationIdTransient = request.locationId;
        
        } else {
            locationId = new Location();
        
            locationId.setAddress(request.locationAddress);
            locationId.setName(request.locationName);
        }
        
        name              = request.eventName;
        fulldescription   = request.eventFullDescription;
        shortdescription  = request.eventShortdescription;
        imagepath         = request.eventImagePath;
        price             = request.eventPrice;
        
        
    }
    
    public Long getLocationIdTransient() {
        return locationIdTransient;
    }

    public void setLocationIdTransient(Long locationIdTransient) {
        this.locationIdTransient = locationIdTransient;
    }

    public Event(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFulldescription() {
        return fulldescription;
    }

    public void setFulldescription(String fulldescription) {
        this.fulldescription = fulldescription;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    @XmlTransient
    public Collection<Buylog> getBuylogCollection() {
        return buylogCollection;
    }

    public void setBuylogCollection(Collection<Buylog> buylogCollection) {
        this.buylogCollection = buylogCollection;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Event[ id=" + id + " ]";
    }
    
}
