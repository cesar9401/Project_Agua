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
import object.SociosEventos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.Eventos;

/**
 *
 * @author julio
 */
public class EventosJpaController implements Serializable {

    public EventosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Eventos eventos) {
        if (eventos.getSociosEventosCollection() == null) {
            eventos.setSociosEventosCollection(new ArrayList<SociosEventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SociosEventos> attachedSociosEventosCollection = new ArrayList<SociosEventos>();
            for (SociosEventos sociosEventosCollectionSociosEventosToAttach : eventos.getSociosEventosCollection()) {
                sociosEventosCollectionSociosEventosToAttach = em.getReference(sociosEventosCollectionSociosEventosToAttach.getClass(), sociosEventosCollectionSociosEventosToAttach.getIdSociosEventos());
                attachedSociosEventosCollection.add(sociosEventosCollectionSociosEventosToAttach);
            }
            eventos.setSociosEventosCollection(attachedSociosEventosCollection);
            em.persist(eventos);
            for (SociosEventos sociosEventosCollectionSociosEventos : eventos.getSociosEventosCollection()) {
                Eventos oldEventosIdEventosOfSociosEventosCollectionSociosEventos = sociosEventosCollectionSociosEventos.getEventosIdEventos();
                sociosEventosCollectionSociosEventos.setEventosIdEventos(eventos);
                sociosEventosCollectionSociosEventos = em.merge(sociosEventosCollectionSociosEventos);
                if (oldEventosIdEventosOfSociosEventosCollectionSociosEventos != null) {
                    oldEventosIdEventosOfSociosEventosCollectionSociosEventos.getSociosEventosCollection().remove(sociosEventosCollectionSociosEventos);
                    oldEventosIdEventosOfSociosEventosCollectionSociosEventos = em.merge(oldEventosIdEventosOfSociosEventosCollectionSociosEventos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Eventos eventos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos persistentEventos = em.find(Eventos.class, eventos.getIdEventos());
            Collection<SociosEventos> sociosEventosCollectionOld = persistentEventos.getSociosEventosCollection();
            Collection<SociosEventos> sociosEventosCollectionNew = eventos.getSociosEventosCollection();
            List<String> illegalOrphanMessages = null;
            for (SociosEventos sociosEventosCollectionOldSociosEventos : sociosEventosCollectionOld) {
                if (!sociosEventosCollectionNew.contains(sociosEventosCollectionOldSociosEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SociosEventos " + sociosEventosCollectionOldSociosEventos + " since its eventosIdEventos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<SociosEventos> attachedSociosEventosCollectionNew = new ArrayList<SociosEventos>();
            for (SociosEventos sociosEventosCollectionNewSociosEventosToAttach : sociosEventosCollectionNew) {
                sociosEventosCollectionNewSociosEventosToAttach = em.getReference(sociosEventosCollectionNewSociosEventosToAttach.getClass(), sociosEventosCollectionNewSociosEventosToAttach.getIdSociosEventos());
                attachedSociosEventosCollectionNew.add(sociosEventosCollectionNewSociosEventosToAttach);
            }
            sociosEventosCollectionNew = attachedSociosEventosCollectionNew;
            eventos.setSociosEventosCollection(sociosEventosCollectionNew);
            eventos = em.merge(eventos);
            for (SociosEventos sociosEventosCollectionNewSociosEventos : sociosEventosCollectionNew) {
                if (!sociosEventosCollectionOld.contains(sociosEventosCollectionNewSociosEventos)) {
                    Eventos oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos = sociosEventosCollectionNewSociosEventos.getEventosIdEventos();
                    sociosEventosCollectionNewSociosEventos.setEventosIdEventos(eventos);
                    sociosEventosCollectionNewSociosEventos = em.merge(sociosEventosCollectionNewSociosEventos);
                    if (oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos != null && !oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos.equals(eventos)) {
                        oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos.getSociosEventosCollection().remove(sociosEventosCollectionNewSociosEventos);
                        oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos = em.merge(oldEventosIdEventosOfSociosEventosCollectionNewSociosEventos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventos.getIdEventos();
                if (findEventos(id) == null) {
                    throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.");
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
            Eventos eventos;
            try {
                eventos = em.getReference(Eventos.class, id);
                eventos.getIdEventos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<SociosEventos> sociosEventosCollectionOrphanCheck = eventos.getSociosEventosCollection();
            for (SociosEventos sociosEventosCollectionOrphanCheckSociosEventos : sociosEventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the SociosEventos " + sociosEventosCollectionOrphanCheckSociosEventos + " in its sociosEventosCollection field has a non-nullable eventosIdEventos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(eventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Eventos> findEventosEntities() {
        return findEventosEntities(true, -1, -1);
    }

    public List<Eventos> findEventosEntities(int maxResults, int firstResult) {
        return findEventosEntities(false, maxResults, firstResult);
    }

    private List<Eventos> findEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Eventos.class));
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

    public Eventos findEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Eventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Eventos> rt = cq.from(Eventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
