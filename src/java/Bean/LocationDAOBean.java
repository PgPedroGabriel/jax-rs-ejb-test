/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import Bean.exceptions.RollbackFailureException;
import java.io.Serializable;
import Entities.Location;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author pedro
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class LocationDAOBean implements Serializable {

    @Resource
    private UserTransaction utx;
    
    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void create(Location location) throws RollbackFailureException, Exception {

        try {
            utx.begin();
            
            em = getEntityManager();

            em.persist(location);

            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public Location findLocation(Long id) {
        try {
            return em.find(Location.class, id);
        } finally {
//            em.close();
        }
    } 
}
