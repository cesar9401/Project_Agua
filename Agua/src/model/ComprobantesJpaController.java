/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import object.Detalles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.Comprobantes;

/**
 *
 * @author julio
 */
public class ComprobantesJpaController implements Serializable {

    public ComprobantesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comprobantes comprobantes) {
        if (comprobantes.getDetallesCollection() == null) {
            comprobantes.setDetallesCollection(new ArrayList<Detalles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Detalles> attachedDetallesCollection = new ArrayList<Detalles>();
            for (Detalles detallesCollectionDetallesToAttach : comprobantes.getDetallesCollection()) {
                detallesCollectionDetallesToAttach = em.getReference(detallesCollectionDetallesToAttach.getClass(), detallesCollectionDetallesToAttach.getIdDetalles());
                attachedDetallesCollection.add(detallesCollectionDetallesToAttach);
            }
            comprobantes.setDetallesCollection(attachedDetallesCollection);
            em.persist(comprobantes);
            for (Detalles detallesCollectionDetalles : comprobantes.getDetallesCollection()) {
                Comprobantes oldComprobantesIdComprobantesOfDetallesCollectionDetalles = detallesCollectionDetalles.getComprobantesIdComprobantes();
                detallesCollectionDetalles.setComprobantesIdComprobantes(comprobantes);
                detallesCollectionDetalles = em.merge(detallesCollectionDetalles);
                if (oldComprobantesIdComprobantesOfDetallesCollectionDetalles != null) {
                    oldComprobantesIdComprobantesOfDetallesCollectionDetalles.getDetallesCollection().remove(detallesCollectionDetalles);
                    oldComprobantesIdComprobantesOfDetallesCollectionDetalles = em.merge(oldComprobantesIdComprobantesOfDetallesCollectionDetalles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comprobantes comprobantes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobantes persistentComprobantes = em.find(Comprobantes.class, comprobantes.getIdComprobantes());
            Collection<Detalles> detallesCollectionOld = persistentComprobantes.getDetallesCollection();
            Collection<Detalles> detallesCollectionNew = comprobantes.getDetallesCollection();
            List<String> illegalOrphanMessages = null;
            for (Detalles detallesCollectionOldDetalles : detallesCollectionOld) {
                if (!detallesCollectionNew.contains(detallesCollectionOldDetalles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalles " + detallesCollectionOldDetalles + " since its comprobantesIdComprobantes field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Detalles> attachedDetallesCollectionNew = new ArrayList<Detalles>();
            for (Detalles detallesCollectionNewDetallesToAttach : detallesCollectionNew) {
                detallesCollectionNewDetallesToAttach = em.getReference(detallesCollectionNewDetallesToAttach.getClass(), detallesCollectionNewDetallesToAttach.getIdDetalles());
                attachedDetallesCollectionNew.add(detallesCollectionNewDetallesToAttach);
            }
            detallesCollectionNew = attachedDetallesCollectionNew;
            comprobantes.setDetallesCollection(detallesCollectionNew);
            comprobantes = em.merge(comprobantes);
            for (Detalles detallesCollectionNewDetalles : detallesCollectionNew) {
                if (!detallesCollectionOld.contains(detallesCollectionNewDetalles)) {
                    Comprobantes oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles = detallesCollectionNewDetalles.getComprobantesIdComprobantes();
                    detallesCollectionNewDetalles.setComprobantesIdComprobantes(comprobantes);
                    detallesCollectionNewDetalles = em.merge(detallesCollectionNewDetalles);
                    if (oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles != null && !oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles.equals(comprobantes)) {
                        oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles.getDetallesCollection().remove(detallesCollectionNewDetalles);
                        oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles = em.merge(oldComprobantesIdComprobantesOfDetallesCollectionNewDetalles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comprobantes.getIdComprobantes();
                if (findComprobantes(id) == null) {
                    throw new NonexistentEntityException("The comprobantes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobantes comprobantes;
            try {
                comprobantes = em.getReference(Comprobantes.class, id);
                comprobantes.getIdComprobantes();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comprobantes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detalles> detallesCollectionOrphanCheck = comprobantes.getDetallesCollection();
            for (Detalles detallesCollectionOrphanCheckDetalles : detallesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comprobantes (" + comprobantes + ") cannot be destroyed since the Detalles " + detallesCollectionOrphanCheckDetalles + " in its detallesCollection field has a non-nullable comprobantesIdComprobantes field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comprobantes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comprobantes> findComprobantesEntities() {
        return findComprobantesEntities(true, -1, -1);
    }

    public List<Comprobantes> findComprobantesEntities(int maxResults, int firstResult) {
        return findComprobantesEntities(false, maxResults, firstResult);
    }

    private List<Comprobantes> findComprobantesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comprobantes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Comprobantes findComprobantes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comprobantes.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprobantesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comprobantes> rt = cq.from(Comprobantes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
