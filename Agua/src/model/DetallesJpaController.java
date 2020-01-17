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
import object.Comprobantes;
import object.Detalles;
import object.PagosSocios;

/**
 *
 * @author julio
 */
public class DetallesJpaController implements Serializable {

    public DetallesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalles detalles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comprobantes comprobantesIdComprobantes = detalles.getComprobantesIdComprobantes();
            if (comprobantesIdComprobantes != null) {
                comprobantesIdComprobantes = em.getReference(comprobantesIdComprobantes.getClass(), comprobantesIdComprobantes.getIdComprobantes());
                detalles.setComprobantesIdComprobantes(comprobantesIdComprobantes);
            }
            PagosSocios pagosSociosIdPagosSocios = detalles.getPagosSociosIdPagosSocios();
            if (pagosSociosIdPagosSocios != null) {
                pagosSociosIdPagosSocios = em.getReference(pagosSociosIdPagosSocios.getClass(), pagosSociosIdPagosSocios.getIdPagosSocios());
                detalles.setPagosSociosIdPagosSocios(pagosSociosIdPagosSocios);
            }
            em.persist(detalles);
            if (comprobantesIdComprobantes != null) {
                comprobantesIdComprobantes.getDetallesCollection().add(detalles);
                comprobantesIdComprobantes = em.merge(comprobantesIdComprobantes);
            }
            if (pagosSociosIdPagosSocios != null) {
                pagosSociosIdPagosSocios.getDetallesCollection().add(detalles);
                pagosSociosIdPagosSocios = em.merge(pagosSociosIdPagosSocios);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalles detalles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalles persistentDetalles = em.find(Detalles.class, detalles.getIdDetalles());
            Comprobantes comprobantesIdComprobantesOld = persistentDetalles.getComprobantesIdComprobantes();
            Comprobantes comprobantesIdComprobantesNew = detalles.getComprobantesIdComprobantes();
            PagosSocios pagosSociosIdPagosSociosOld = persistentDetalles.getPagosSociosIdPagosSocios();
            PagosSocios pagosSociosIdPagosSociosNew = detalles.getPagosSociosIdPagosSocios();
            if (comprobantesIdComprobantesNew != null) {
                comprobantesIdComprobantesNew = em.getReference(comprobantesIdComprobantesNew.getClass(), comprobantesIdComprobantesNew.getIdComprobantes());
                detalles.setComprobantesIdComprobantes(comprobantesIdComprobantesNew);
            }
            if (pagosSociosIdPagosSociosNew != null) {
                pagosSociosIdPagosSociosNew = em.getReference(pagosSociosIdPagosSociosNew.getClass(), pagosSociosIdPagosSociosNew.getIdPagosSocios());
                detalles.setPagosSociosIdPagosSocios(pagosSociosIdPagosSociosNew);
            }
            detalles = em.merge(detalles);
            if (comprobantesIdComprobantesOld != null && !comprobantesIdComprobantesOld.equals(comprobantesIdComprobantesNew)) {
                comprobantesIdComprobantesOld.getDetallesCollection().remove(detalles);
                comprobantesIdComprobantesOld = em.merge(comprobantesIdComprobantesOld);
            }
            if (comprobantesIdComprobantesNew != null && !comprobantesIdComprobantesNew.equals(comprobantesIdComprobantesOld)) {
                comprobantesIdComprobantesNew.getDetallesCollection().add(detalles);
                comprobantesIdComprobantesNew = em.merge(comprobantesIdComprobantesNew);
            }
            if (pagosSociosIdPagosSociosOld != null && !pagosSociosIdPagosSociosOld.equals(pagosSociosIdPagosSociosNew)) {
                pagosSociosIdPagosSociosOld.getDetallesCollection().remove(detalles);
                pagosSociosIdPagosSociosOld = em.merge(pagosSociosIdPagosSociosOld);
            }
            if (pagosSociosIdPagosSociosNew != null && !pagosSociosIdPagosSociosNew.equals(pagosSociosIdPagosSociosOld)) {
                pagosSociosIdPagosSociosNew.getDetallesCollection().add(detalles);
                pagosSociosIdPagosSociosNew = em.merge(pagosSociosIdPagosSociosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalles.getIdDetalles();
                if (findDetalles(id) == null) {
                    throw new NonexistentEntityException("The detalles with id " + id + " no longer exists.");
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
            Detalles detalles;
            try {
                detalles = em.getReference(Detalles.class, id);
                detalles.getIdDetalles();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalles with id " + id + " no longer exists.", enfe);
            }
            Comprobantes comprobantesIdComprobantes = detalles.getComprobantesIdComprobantes();
            if (comprobantesIdComprobantes != null) {
                comprobantesIdComprobantes.getDetallesCollection().remove(detalles);
                comprobantesIdComprobantes = em.merge(comprobantesIdComprobantes);
            }
            PagosSocios pagosSociosIdPagosSocios = detalles.getPagosSociosIdPagosSocios();
            if (pagosSociosIdPagosSocios != null) {
                pagosSociosIdPagosSocios.getDetallesCollection().remove(detalles);
                pagosSociosIdPagosSocios = em.merge(pagosSociosIdPagosSocios);
            }
            em.remove(detalles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalles> findDetallesEntities() {
        return findDetallesEntities(true, -1, -1);
    }

    public List<Detalles> findDetallesEntities(int maxResults, int firstResult) {
        return findDetallesEntities(false, maxResults, firstResult);
    }

    private List<Detalles> findDetallesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalles.class));
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

    public Detalles findDetalles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalles.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalles> rt = cq.from(Detalles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
