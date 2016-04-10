/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Bean.exceptions.IllegalOrphanException;
import Bean.exceptions.NonexistentEntityException;
import Bean.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Location;
import Entities.Buylog;
import Entities.Event;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author pedro
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EventDAOBean implements Serializable {

    @Resource
    private UserTransaction utx;
    
    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;
    
    @EJB private LocationDAOBean locationController;
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void create(Event event) throws RollbackFailureException, Exception {
       
        
        try {
            utx.begin();
            
            em = getEntityManager();
            
            Location location = null;
            
            if(event.getLocationIdTransient() != null){
                location = locationController.findLocation(event.getLocationIdTransient());
            } else {
                location = event.getLocationId();
                locationController.create(location);
            }
            
            event.setLocationId(location);
            
            em.persist(event);
            
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
//                em.close();
            }
        }
    }

    public List<Event> findEventEntities() {
        return findEventEntities(true, -1, -1);
    }

    public List<Event> findEventEntities(int maxResults, int firstResult) {
        return findEventEntities(false, maxResults, firstResult);
    }

    private List<Event> findEventEntities(boolean all, int maxResults, int firstResult) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Event.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
//            em.close();
        }
    }

    public Event findEvent(Long id) {
        try {
            return em.find(Event.class, id);
        } finally {
//            em.close();
        }
    }
    
}
