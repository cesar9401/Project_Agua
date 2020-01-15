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
import object.PagosMorosos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.Cuotas;
import object.PagosSocios;

/**
 *
 * @author julio
 */
public class CuotasJpaController implements Serializable {

    public CuotasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuotas cuotas) {
        if (cuotas.getPagosMorososCollection() == null) {
            cuotas.setPagosMorososCollection(new ArrayList<PagosMorosos>());
        }
        if (cuotas.getPagosSociosCollection() == null) {
            cuotas.setPagosSociosCollection(new ArrayList<PagosSocios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<PagosMorosos> attachedPagosMorososCollection = new ArrayList<PagosMorosos>();
            for (PagosMorosos pagosMorososCollectionPagosMorososToAttach : cuotas.getPagosMorososCollection()) {
                pagosMorososCollectionPagosMorososToAttach = em.getReference(pagosMorososCollectionPagosMorososToAttach.getClass(), pagosMorososCollectionPagosMorososToAttach.getIdPagosMorosos());
                attachedPagosMorososCollection.add(pagosMorososCollectionPagosMorososToAttach);
            }
            cuotas.setPagosMorososCollection(attachedPagosMorososCollection);
            Collection<PagosSocios> attachedPagosSociosCollection = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionPagosSociosToAttach : cuotas.getPagosSociosCollection()) {
                pagosSociosCollectionPagosSociosToAttach = em.getReference(pagosSociosCollectionPagosSociosToAttach.getClass(), pagosSociosCollectionPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollection.add(pagosSociosCollectionPagosSociosToAttach);
            }
            cuotas.setPagosSociosCollection(attachedPagosSociosCollection);
            em.persist(cuotas);
            for (PagosMorosos pagosMorososCollectionPagosMorosos : cuotas.getPagosMorososCollection()) {
                Cuotas oldCuotasIdCuotasOfPagosMorososCollectionPagosMorosos = pagosMorososCollectionPagosMorosos.getCuotasIdCuotas();
                pagosMorososCollectionPagosMorosos.setCuotasIdCuotas(cuotas);
                pagosMorososCollectionPagosMorosos = em.merge(pagosMorososCollectionPagosMorosos);
                if (oldCuotasIdCuotasOfPagosMorososCollectionPagosMorosos != null) {
                    oldCuotasIdCuotasOfPagosMorososCollectionPagosMorosos.getPagosMorososCollection().remove(pagosMorososCollectionPagosMorosos);
                    oldCuotasIdCuotasOfPagosMorososCollectionPagosMorosos = em.merge(oldCuotasIdCuotasOfPagosMorososCollectionPagosMorosos);
                }
            }
            for (PagosSocios pagosSociosCollectionPagosSocios : cuotas.getPagosSociosCollection()) {
                Cuotas oldCuotasIdCuotasOfPagosSociosCollectionPagosSocios = pagosSociosCollectionPagosSocios.getCuotasIdCuotas();
                pagosSociosCollectionPagosSocios.setCuotasIdCuotas(cuotas);
                pagosSociosCollectionPagosSocios = em.merge(pagosSociosCollectionPagosSocios);
                if (oldCuotasIdCuotasOfPagosSociosCollectionPagosSocios != null) {
                    oldCuotasIdCuotasOfPagosSociosCollectionPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionPagosSocios);
                    oldCuotasIdCuotasOfPagosSociosCollectionPagosSocios = em.merge(oldCuotasIdCuotasOfPagosSociosCollectionPagosSocios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuotas cuotas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuotas persistentCuotas = em.find(Cuotas.class, cuotas.getIdCuotas());
            Collection<PagosMorosos> pagosMorososCollectionOld = persistentCuotas.getPagosMorososCollection();
            Collection<PagosMorosos> pagosMorososCollectionNew = cuotas.getPagosMorososCollection();
            Collection<PagosSocios> pagosSociosCollectionOld = persistentCuotas.getPagosSociosCollection();
            Collection<PagosSocios> pagosSociosCollectionNew = cuotas.getPagosSociosCollection();
            List<String> illegalOrphanMessages = null;
            for (PagosMorosos pagosMorososCollectionOldPagosMorosos : pagosMorososCollectionOld) {
                if (!pagosMorososCollectionNew.contains(pagosMorososCollectionOldPagosMorosos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagosMorosos " + pagosMorososCollectionOldPagosMorosos + " since its cuotasIdCuotas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PagosMorosos> attachedPagosMorososCollectionNew = new ArrayList<PagosMorosos>();
            for (PagosMorosos pagosMorososCollectionNewPagosMorososToAttach : pagosMorososCollectionNew) {
                pagosMorososCollectionNewPagosMorososToAttach = em.getReference(pagosMorososCollectionNewPagosMorososToAttach.getClass(), pagosMorososCollectionNewPagosMorososToAttach.getIdPagosMorosos());
                attachedPagosMorososCollectionNew.add(pagosMorososCollectionNewPagosMorososToAttach);
            }
            pagosMorososCollectionNew = attachedPagosMorososCollectionNew;
            cuotas.setPagosMorososCollection(pagosMorososCollectionNew);
            Collection<PagosSocios> attachedPagosSociosCollectionNew = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionNewPagosSociosToAttach : pagosSociosCollectionNew) {
                pagosSociosCollectionNewPagosSociosToAttach = em.getReference(pagosSociosCollectionNewPagosSociosToAttach.getClass(), pagosSociosCollectionNewPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollectionNew.add(pagosSociosCollectionNewPagosSociosToAttach);
            }
            pagosSociosCollectionNew = attachedPagosSociosCollectionNew;
            cuotas.setPagosSociosCollection(pagosSociosCollectionNew);
            cuotas = em.merge(cuotas);
            for (PagosMorosos pagosMorososCollectionNewPagosMorosos : pagosMorososCollectionNew) {
                if (!pagosMorososCollectionOld.contains(pagosMorososCollectionNewPagosMorosos)) {
                    Cuotas oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos = pagosMorososCollectionNewPagosMorosos.getCuotasIdCuotas();
                    pagosMorososCollectionNewPagosMorosos.setCuotasIdCuotas(cuotas);
                    pagosMorososCollectionNewPagosMorosos = em.merge(pagosMorososCollectionNewPagosMorosos);
                    if (oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos != null && !oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos.equals(cuotas)) {
                        oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos.getPagosMorososCollection().remove(pagosMorososCollectionNewPagosMorosos);
                        oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos = em.merge(oldCuotasIdCuotasOfPagosMorososCollectionNewPagosMorosos);
                    }
                }
            }
            for (PagosSocios pagosSociosCollectionOldPagosSocios : pagosSociosCollectionOld) {
                if (!pagosSociosCollectionNew.contains(pagosSociosCollectionOldPagosSocios)) {
                    pagosSociosCollectionOldPagosSocios.setCuotasIdCuotas(null);
                    pagosSociosCollectionOldPagosSocios = em.merge(pagosSociosCollectionOldPagosSocios);
                }
            }
            for (PagosSocios pagosSociosCollectionNewPagosSocios : pagosSociosCollectionNew) {
                if (!pagosSociosCollectionOld.contains(pagosSociosCollectionNewPagosSocios)) {
                    Cuotas oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios = pagosSociosCollectionNewPagosSocios.getCuotasIdCuotas();
                    pagosSociosCollectionNewPagosSocios.setCuotasIdCuotas(cuotas);
                    pagosSociosCollectionNewPagosSocios = em.merge(pagosSociosCollectionNewPagosSocios);
                    if (oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios != null && !oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios.equals(cuotas)) {
                        oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionNewPagosSocios);
                        oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios = em.merge(oldCuotasIdCuotasOfPagosSociosCollectionNewPagosSocios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuotas.getIdCuotas();
                if (findCuotas(id) == null) {
                    throw new NonexistentEntityException("The cuotas with id " + id + " no longer exists.");
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
            Cuotas cuotas;
            try {
                cuotas = em.getReference(Cuotas.class, id);
                cuotas.getIdCuotas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuotas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PagosMorosos> pagosMorososCollectionOrphanCheck = cuotas.getPagosMorososCollection();
            for (PagosMorosos pagosMorososCollectionOrphanCheckPagosMorosos : pagosMorososCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuotas (" + cuotas + ") cannot be destroyed since the PagosMorosos " + pagosMorososCollectionOrphanCheckPagosMorosos + " in its pagosMorososCollection field has a non-nullable cuotasIdCuotas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<PagosSocios> pagosSociosCollection = cuotas.getPagosSociosCollection();
            for (PagosSocios pagosSociosCollectionPagosSocios : pagosSociosCollection) {
                pagosSociosCollectionPagosSocios.setCuotasIdCuotas(null);
                pagosSociosCollectionPagosSocios = em.merge(pagosSociosCollectionPagosSocios);
            }
            em.remove(cuotas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuotas> findCuotasEntities() {
        return findCuotasEntities(true, -1, -1);
    }

    public List<Cuotas> findCuotasEntities(int maxResults, int firstResult) {
        return findCuotasEntities(false, maxResults, firstResult);
    }

    private List<Cuotas> findCuotasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuotas.class));
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

    public Cuotas findCuotas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuotas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuotasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuotas> rt = cq.from(Cuotas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
