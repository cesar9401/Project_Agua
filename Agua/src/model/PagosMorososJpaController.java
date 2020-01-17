/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import object.Cuotas;
import object.PagosMorosos;
import object.Socios;

/**
 *
 * @author julio
 */
public class PagosMorososJpaController implements Serializable {

    public PagosMorososJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagosMorosos pagosMorosos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuotas cuotasIdCuotas = pagosMorosos.getCuotasIdCuotas();
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas = em.getReference(cuotasIdCuotas.getClass(), cuotasIdCuotas.getIdCuotas());
                pagosMorosos.setCuotasIdCuotas(cuotasIdCuotas);
            }
            Socios sociosIdSocio = pagosMorosos.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio = em.getReference(sociosIdSocio.getClass(), sociosIdSocio.getIdSocio());
                pagosMorosos.setSociosIdSocio(sociosIdSocio);
            }
            em.persist(pagosMorosos);
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas.getPagosMorososCollection().add(pagosMorosos);
                cuotasIdCuotas = em.merge(cuotasIdCuotas);
            }
            if (sociosIdSocio != null) {
                sociosIdSocio.getPagosMorososCollection().add(pagosMorosos);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagosMorosos pagosMorosos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagosMorosos persistentPagosMorosos = em.find(PagosMorosos.class, pagosMorosos.getIdPagosMorosos());
            Cuotas cuotasIdCuotasOld = persistentPagosMorosos.getCuotasIdCuotas();
            Cuotas cuotasIdCuotasNew = pagosMorosos.getCuotasIdCuotas();
            Socios sociosIdSocioOld = persistentPagosMorosos.getSociosIdSocio();
            Socios sociosIdSocioNew = pagosMorosos.getSociosIdSocio();
            if (cuotasIdCuotasNew != null) {
                cuotasIdCuotasNew = em.getReference(cuotasIdCuotasNew.getClass(), cuotasIdCuotasNew.getIdCuotas());
                pagosMorosos.setCuotasIdCuotas(cuotasIdCuotasNew);
            }
            if (sociosIdSocioNew != null) {
                sociosIdSocioNew = em.getReference(sociosIdSocioNew.getClass(), sociosIdSocioNew.getIdSocio());
                pagosMorosos.setSociosIdSocio(sociosIdSocioNew);
            }
            pagosMorosos = em.merge(pagosMorosos);
            if (cuotasIdCuotasOld != null && !cuotasIdCuotasOld.equals(cuotasIdCuotasNew)) {
                cuotasIdCuotasOld.getPagosMorososCollection().remove(pagosMorosos);
                cuotasIdCuotasOld = em.merge(cuotasIdCuotasOld);
            }
            if (cuotasIdCuotasNew != null && !cuotasIdCuotasNew.equals(cuotasIdCuotasOld)) {
                cuotasIdCuotasNew.getPagosMorososCollection().add(pagosMorosos);
                cuotasIdCuotasNew = em.merge(cuotasIdCuotasNew);
            }
            if (sociosIdSocioOld != null && !sociosIdSocioOld.equals(sociosIdSocioNew)) {
                sociosIdSocioOld.getPagosMorososCollection().remove(pagosMorosos);
                sociosIdSocioOld = em.merge(sociosIdSocioOld);
            }
            if (sociosIdSocioNew != null && !sociosIdSocioNew.equals(sociosIdSocioOld)) {
                sociosIdSocioNew.getPagosMorososCollection().add(pagosMorosos);
                sociosIdSocioNew = em.merge(sociosIdSocioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosMorosos.getIdPagosMorosos();
                if (findPagosMorosos(id) == null) {
                    throw new NonexistentEntityException("The pagosMorosos with id " + id + " no longer exists.");
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
            PagosMorosos pagosMorosos;
            try {
                pagosMorosos = em.getReference(PagosMorosos.class, id);
                pagosMorosos.getIdPagosMorosos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosMorosos with id " + id + " no longer exists.", enfe);
            }
            Cuotas cuotasIdCuotas = pagosMorosos.getCuotasIdCuotas();
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas.getPagosMorososCollection().remove(pagosMorosos);
                cuotasIdCuotas = em.merge(cuotasIdCuotas);
            }
            Socios sociosIdSocio = pagosMorosos.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio.getPagosMorososCollection().remove(pagosMorosos);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            em.remove(pagosMorosos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagosMorosos> findPagosMorososEntities() {
        return findPagosMorososEntities(true, -1, -1);
    }

    public List<PagosMorosos> findPagosMorososEntities(int maxResults, int firstResult) {
        return findPagosMorososEntities(false, maxResults, firstResult);
    }

    private List<PagosMorosos> findPagosMorososEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagosMorosos.class));
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

    public PagosMorosos findPagosMorosos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagosMorosos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosMorososCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagosMorosos> rt = cq.from(PagosMorosos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
