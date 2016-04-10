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
import Entities.Buy;
import Entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
public class UserDAOBean {
    
    @Resource
    private UserTransaction utx;
    
    @PersistenceContext(unitName = "WebApplication2PU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getBuyCollection() == null) {
            user.setBuyCollection(new ArrayList<Buy>());
        }
        try {
            utx.begin();
            Collection<Buy> attachedBuyCollection = new ArrayList<Buy>();
            for (Buy buyCollectionBuyToAttach : user.getBuyCollection()) {
                buyCollectionBuyToAttach = em.getReference(buyCollectionBuyToAttach.getClass(), buyCollectionBuyToAttach.getId());
                attachedBuyCollection.add(buyCollectionBuyToAttach);
            }
            user.setBuyCollection(attachedBuyCollection);
            em.persist(user);
            for (Buy buyCollectionBuy : user.getBuyCollection()) {
                User oldUserIdOfBuyCollectionBuy = buyCollectionBuy.getUserId();
                buyCollectionBuy.setUserId(user);
                buyCollectionBuy = em.merge(buyCollectionBuy);
                if (oldUserIdOfBuyCollectionBuy != null) {
                    oldUserIdOfBuyCollectionBuy.getBuyCollection().remove(buyCollectionBuy);
                    oldUserIdOfBuyCollectionBuy = em.merge(oldUserIdOfBuyCollectionBuy);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            User persistentUser = em.find(User.class, user.getId());
            Collection<Buy> buyCollectionOld = persistentUser.getBuyCollection();
            Collection<Buy> buyCollectionNew = user.getBuyCollection();
            List<String> illegalOrphanMessages = null;
            for (Buy buyCollectionOldBuy : buyCollectionOld) {
                if (!buyCollectionNew.contains(buyCollectionOldBuy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Buy " + buyCollectionOldBuy + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Buy> attachedBuyCollectionNew = new ArrayList<Buy>();
            for (Buy buyCollectionNewBuyToAttach : buyCollectionNew) {
                buyCollectionNewBuyToAttach = em.getReference(buyCollectionNewBuyToAttach.getClass(), buyCollectionNewBuyToAttach.getId());
                attachedBuyCollectionNew.add(buyCollectionNewBuyToAttach);
            }
            buyCollectionNew = attachedBuyCollectionNew;
            user.setBuyCollection(buyCollectionNew);
            user = em.merge(user);
            for (Buy buyCollectionNewBuy : buyCollectionNew) {
                if (!buyCollectionOld.contains(buyCollectionNewBuy)) {
                    User oldUserIdOfBuyCollectionNewBuy = buyCollectionNewBuy.getUserId();
                    buyCollectionNewBuy.setUserId(user);
                    buyCollectionNewBuy = em.merge(buyCollectionNewBuy);
                    if (oldUserIdOfBuyCollectionNewBuy != null && !oldUserIdOfBuyCollectionNewBuy.equals(user)) {
                        oldUserIdOfBuyCollectionNewBuy.getBuyCollection().remove(buyCollectionNewBuy);
                        oldUserIdOfBuyCollectionNewBuy = em.merge(oldUserIdOfBuyCollectionNewBuy);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Buy> buyCollectionOrphanCheck = user.getBuyCollection();
            for (Buy buyCollectionOrphanCheckBuy : buyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Buy " + buyCollectionOrphanCheckBuy + " in its buyCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        List<User> users = new ArrayList<User>();
        try {
            System.out.println("DAOs.UserJpaController.findUserEntities()");
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            users = q.getResultList();
        } finally {
        }
        
        return users;
    }

    public User findUser(Long id) {
        try {
            return em.find(User.class, id);
        } finally {
        }
    }

    public int getUserCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }
    
    public User verifyLogin(String login){
        
        Query query = em.createNamedQuery("User.hasLogin", User.class);
        
        query.setParameter("login", login);
        
        User user = (User) query.getSingleResult();
        
        return user;
    }
}
