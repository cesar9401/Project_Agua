/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.exceptions.NonexistentEntityException;
import object.Eventos;
import object.Socios;
import object.Administradores;
import object.SociosEventos;

/**
 *
 * @author julio
 */
public class SociosEventosJpaController implements Serializable {

    public SociosEventosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SociosEventos sociosEventos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos eventosIdEventos = sociosEventos.getEventosIdEventos();
            if (eventosIdEventos != null) {
                eventosIdEventos = em.getReference(eventosIdEventos.getClass(), eventosIdEventos.getIdEventos());
                sociosEventos.setEventosIdEventos(eventosIdEventos);
            }
            Socios sociosIdSocio = sociosEventos.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio = em.getReference(sociosIdSocio.getClass(), sociosIdSocio.getIdSocio());
                sociosEventos.setSociosIdSocio(sociosIdSocio);
            }
            Administradores administradoresIdAdministrador = sociosEventos.getAdministradoresIdAdministrador();
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador = em.getReference(administradoresIdAdministrador.getClass(), administradoresIdAdministrador.getIdAdministrador());
                sociosEventos.setAdministradoresIdAdministrador(administradoresIdAdministrador);
            }
            em.persist(sociosEventos);
            if (eventosIdEventos != null) {
                eventosIdEventos.getSociosEventosCollection().add(sociosEventos);
                eventosIdEventos = em.merge(eventosIdEventos);
            }
            if (sociosIdSocio != null) {
                sociosIdSocio.getSociosEventosCollection().add(sociosEventos);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador.getSociosEventosCollection().add(sociosEventos);
                administradoresIdAdministrador = em.merge(administradoresIdAdministrador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SociosEventos sociosEventos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SociosEventos persistentSociosEventos = em.find(SociosEventos.class, sociosEventos.getIdSociosEventos());
            Eventos eventosIdEventosOld = persistentSociosEventos.getEventosIdEventos();
            Eventos eventosIdEventosNew = sociosEventos.getEventosIdEventos();
            Socios sociosIdSocioOld = persistentSociosEventos.getSociosIdSocio();
            Socios sociosIdSocioNew = sociosEventos.getSociosIdSocio();
            Administradores administradoresIdAdministradorOld = persistentSociosEventos.getAdministradoresIdAdministrador();
            Administradores administradoresIdAdministradorNew = sociosEventos.getAdministradoresIdAdministrador();
            if (eventosIdEventosNew != null) {
                eventosIdEventosNew = em.getReference(eventosIdEventosNew.getClass(), eventosIdEventosNew.getIdEventos());
                sociosEventos.setEventosIdEventos(eventosIdEventosNew);
            }
            if (sociosIdSocioNew != null) {
                sociosIdSocioNew = em.getReference(sociosIdSocioNew.getClass(), sociosIdSocioNew.getIdSocio());
                sociosEventos.setSociosIdSocio(sociosIdSocioNew);
            }
            if (administradoresIdAdministradorNew != null) {
                administradoresIdAdministradorNew = em.getReference(administradoresIdAdministradorNew.getClass(), administradoresIdAdministradorNew.getIdAdministrador());
                sociosEventos.setAdministradoresIdAdministrador(administradoresIdAdministradorNew);
            }
            sociosEventos = em.merge(sociosEventos);
            if (eventosIdEventosOld != null && !eventosIdEventosOld.equals(eventosIdEventosNew)) {
                eventosIdEventosOld.getSociosEventosCollection().remove(sociosEventos);
                eventosIdEventosOld = em.merge(eventosIdEventosOld);
            }
            if (eventosIdEventosNew != null && !eventosIdEventosNew.equals(eventosIdEventosOld)) {
                eventosIdEventosNew.getSociosEventosCollection().add(sociosEventos);
                eventosIdEventosNew = em.merge(eventosIdEventosNew);
            }
            if (sociosIdSocioOld != null && !sociosIdSocioOld.equals(sociosIdSocioNew)) {
                sociosIdSocioOld.getSociosEventosCollection().remove(sociosEventos);
                sociosIdSocioOld = em.merge(sociosIdSocioOld);
            }
            if (sociosIdSocioNew != null && !sociosIdSocioNew.equals(sociosIdSocioOld)) {
                sociosIdSocioNew.getSociosEventosCollection().add(sociosEventos);
                sociosIdSocioNew = em.merge(sociosIdSocioNew);
            }
            if (administradoresIdAdministradorOld != null && !administradoresIdAdministradorOld.equals(administradoresIdAdministradorNew)) {
                administradoresIdAdministradorOld.getSociosEventosCollection().remove(sociosEventos);
                administradoresIdAdministradorOld = em.merge(administradoresIdAdministradorOld);
            }
            if (administradoresIdAdministradorNew != null && !administradoresIdAdministradorNew.equals(administradoresIdAdministradorOld)) {
                administradoresIdAdministradorNew.getSociosEventosCollection().add(sociosEventos);
                administradoresIdAdministradorNew = em.merge(administradoresIdAdministradorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sociosEventos.getIdSociosEventos();
                if (findSociosEventos(id) == null) {
                    throw new NonexistentEntityException("The sociosEventos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SociosEventos sociosEventos;
            try {
                sociosEventos = em.getReference(SociosEventos.class, id);
                sociosEventos.getIdSociosEventos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sociosEventos with id " + id + " no longer exists.", enfe);
            }
            Eventos eventosIdEventos = sociosEventos.getEventosIdEventos();
            if (eventosIdEventos != null) {
                eventosIdEventos.getSociosEventosCollection().remove(sociosEventos);
                eventosIdEventos = em.merge(eventosIdEventos);
            }
            Socios sociosIdSocio = sociosEventos.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio.getSociosEventosCollection().remove(sociosEventos);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            Administradores administradoresIdAdministrador = sociosEventos.getAdministradoresIdAdministrador();
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador.getSociosEventosCollection().remove(sociosEventos);
                administradoresIdAdministrador = em.merge(administradoresIdAdministrador);
            }
            em.remove(sociosEventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SociosEventos> findSociosEventosEntities() {
        return findSociosEventosEntities(true, -1, -1);
    }

    public List<SociosEventos> findSociosEventosEntities(int maxResults, int firstResult) {
        return findSociosEventosEntities(false, maxResults, firstResult);
    }

    private List<SociosEventos> findSociosEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SociosEventos.class));
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

    public SociosEventos findSociosEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SociosEventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSociosEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SociosEventos> rt = cq.from(SociosEventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
