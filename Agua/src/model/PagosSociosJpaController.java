/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import object.Administradores;
import object.Cuotas;
import object.Socios;
import object.Detalles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import object.PagosSocios;

/**
 *
 * @author julio
 */
public class PagosSociosJpaController implements Serializable {

    public PagosSociosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagosSocios pagosSocios) {
        if (pagosSocios.getDetallesCollection() == null) {
            pagosSocios.setDetallesCollection(new ArrayList<Detalles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administradores administradoresIdAdministrador = pagosSocios.getAdministradoresIdAdministrador();
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador = em.getReference(administradoresIdAdministrador.getClass(), administradoresIdAdministrador.getIdAdministrador());
                pagosSocios.setAdministradoresIdAdministrador(administradoresIdAdministrador);
            }
            Cuotas cuotasIdCuotas = pagosSocios.getCuotasIdCuotas();
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas = em.getReference(cuotasIdCuotas.getClass(), cuotasIdCuotas.getIdCuotas());
                pagosSocios.setCuotasIdCuotas(cuotasIdCuotas);
            }
            Socios sociosIdSocio = pagosSocios.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio = em.getReference(sociosIdSocio.getClass(), sociosIdSocio.getIdSocio());
                pagosSocios.setSociosIdSocio(sociosIdSocio);
            }
            Collection<Detalles> attachedDetallesCollection = new ArrayList<Detalles>();
            for (Detalles detallesCollectionDetallesToAttach : pagosSocios.getDetallesCollection()) {
                detallesCollectionDetallesToAttach = em.getReference(detallesCollectionDetallesToAttach.getClass(), detallesCollectionDetallesToAttach.getIdDetalles());
                attachedDetallesCollection.add(detallesCollectionDetallesToAttach);
            }
            pagosSocios.setDetallesCollection(attachedDetallesCollection);
            em.persist(pagosSocios);
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador.getPagosSociosCollection().add(pagosSocios);
                administradoresIdAdministrador = em.merge(administradoresIdAdministrador);
            }
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas.getPagosSociosCollection().add(pagosSocios);
                cuotasIdCuotas = em.merge(cuotasIdCuotas);
            }
            if (sociosIdSocio != null) {
                sociosIdSocio.getPagosSociosCollection().add(pagosSocios);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            for (Detalles detallesCollectionDetalles : pagosSocios.getDetallesCollection()) {
                PagosSocios oldPagosSociosIdPagosSociosOfDetallesCollectionDetalles = detallesCollectionDetalles.getPagosSociosIdPagosSocios();
                detallesCollectionDetalles.setPagosSociosIdPagosSocios(pagosSocios);
                detallesCollectionDetalles = em.merge(detallesCollectionDetalles);
                if (oldPagosSociosIdPagosSociosOfDetallesCollectionDetalles != null) {
                    oldPagosSociosIdPagosSociosOfDetallesCollectionDetalles.getDetallesCollection().remove(detallesCollectionDetalles);
                    oldPagosSociosIdPagosSociosOfDetallesCollectionDetalles = em.merge(oldPagosSociosIdPagosSociosOfDetallesCollectionDetalles);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagosSocios pagosSocios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PagosSocios persistentPagosSocios = em.find(PagosSocios.class, pagosSocios.getIdPagosSocios());
            Administradores administradoresIdAdministradorOld = persistentPagosSocios.getAdministradoresIdAdministrador();
            Administradores administradoresIdAdministradorNew = pagosSocios.getAdministradoresIdAdministrador();
            Cuotas cuotasIdCuotasOld = persistentPagosSocios.getCuotasIdCuotas();
            Cuotas cuotasIdCuotasNew = pagosSocios.getCuotasIdCuotas();
            Socios sociosIdSocioOld = persistentPagosSocios.getSociosIdSocio();
            Socios sociosIdSocioNew = pagosSocios.getSociosIdSocio();
            Collection<Detalles> detallesCollectionOld = persistentPagosSocios.getDetallesCollection();
            Collection<Detalles> detallesCollectionNew = pagosSocios.getDetallesCollection();
            if (administradoresIdAdministradorNew != null) {
                administradoresIdAdministradorNew = em.getReference(administradoresIdAdministradorNew.getClass(), administradoresIdAdministradorNew.getIdAdministrador());
                pagosSocios.setAdministradoresIdAdministrador(administradoresIdAdministradorNew);
            }
            if (cuotasIdCuotasNew != null) {
                cuotasIdCuotasNew = em.getReference(cuotasIdCuotasNew.getClass(), cuotasIdCuotasNew.getIdCuotas());
                pagosSocios.setCuotasIdCuotas(cuotasIdCuotasNew);
            }
            if (sociosIdSocioNew != null) {
                sociosIdSocioNew = em.getReference(sociosIdSocioNew.getClass(), sociosIdSocioNew.getIdSocio());
                pagosSocios.setSociosIdSocio(sociosIdSocioNew);
            }
            Collection<Detalles> attachedDetallesCollectionNew = new ArrayList<Detalles>();
            for (Detalles detallesCollectionNewDetallesToAttach : detallesCollectionNew) {
                detallesCollectionNewDetallesToAttach = em.getReference(detallesCollectionNewDetallesToAttach.getClass(), detallesCollectionNewDetallesToAttach.getIdDetalles());
                attachedDetallesCollectionNew.add(detallesCollectionNewDetallesToAttach);
            }
            detallesCollectionNew = attachedDetallesCollectionNew;
            pagosSocios.setDetallesCollection(detallesCollectionNew);
            pagosSocios = em.merge(pagosSocios);
            if (administradoresIdAdministradorOld != null && !administradoresIdAdministradorOld.equals(administradoresIdAdministradorNew)) {
                administradoresIdAdministradorOld.getPagosSociosCollection().remove(pagosSocios);
                administradoresIdAdministradorOld = em.merge(administradoresIdAdministradorOld);
            }
            if (administradoresIdAdministradorNew != null && !administradoresIdAdministradorNew.equals(administradoresIdAdministradorOld)) {
                administradoresIdAdministradorNew.getPagosSociosCollection().add(pagosSocios);
                administradoresIdAdministradorNew = em.merge(administradoresIdAdministradorNew);
            }
            if (cuotasIdCuotasOld != null && !cuotasIdCuotasOld.equals(cuotasIdCuotasNew)) {
                cuotasIdCuotasOld.getPagosSociosCollection().remove(pagosSocios);
                cuotasIdCuotasOld = em.merge(cuotasIdCuotasOld);
            }
            if (cuotasIdCuotasNew != null && !cuotasIdCuotasNew.equals(cuotasIdCuotasOld)) {
                cuotasIdCuotasNew.getPagosSociosCollection().add(pagosSocios);
                cuotasIdCuotasNew = em.merge(cuotasIdCuotasNew);
            }
            if (sociosIdSocioOld != null && !sociosIdSocioOld.equals(sociosIdSocioNew)) {
                sociosIdSocioOld.getPagosSociosCollection().remove(pagosSocios);
                sociosIdSocioOld = em.merge(sociosIdSocioOld);
            }
            if (sociosIdSocioNew != null && !sociosIdSocioNew.equals(sociosIdSocioOld)) {
                sociosIdSocioNew.getPagosSociosCollection().add(pagosSocios);
                sociosIdSocioNew = em.merge(sociosIdSocioNew);
            }
            for (Detalles detallesCollectionOldDetalles : detallesCollectionOld) {
                if (!detallesCollectionNew.contains(detallesCollectionOldDetalles)) {
                    detallesCollectionOldDetalles.setPagosSociosIdPagosSocios(null);
                    detallesCollectionOldDetalles = em.merge(detallesCollectionOldDetalles);
                }
            }
            for (Detalles detallesCollectionNewDetalles : detallesCollectionNew) {
                if (!detallesCollectionOld.contains(detallesCollectionNewDetalles)) {
                    PagosSocios oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles = detallesCollectionNewDetalles.getPagosSociosIdPagosSocios();
                    detallesCollectionNewDetalles.setPagosSociosIdPagosSocios(pagosSocios);
                    detallesCollectionNewDetalles = em.merge(detallesCollectionNewDetalles);
                    if (oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles != null && !oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles.equals(pagosSocios)) {
                        oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles.getDetallesCollection().remove(detallesCollectionNewDetalles);
                        oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles = em.merge(oldPagosSociosIdPagosSociosOfDetallesCollectionNewDetalles);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosSocios.getIdPagosSocios();
                if (findPagosSocios(id) == null) {
                    throw new NonexistentEntityException("The pagosSocios with id " + id + " no longer exists.");
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
            PagosSocios pagosSocios;
            try {
                pagosSocios = em.getReference(PagosSocios.class, id);
                pagosSocios.getIdPagosSocios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosSocios with id " + id + " no longer exists.", enfe);
            }
            Administradores administradoresIdAdministrador = pagosSocios.getAdministradoresIdAdministrador();
            if (administradoresIdAdministrador != null) {
                administradoresIdAdministrador.getPagosSociosCollection().remove(pagosSocios);
                administradoresIdAdministrador = em.merge(administradoresIdAdministrador);
            }
            Cuotas cuotasIdCuotas = pagosSocios.getCuotasIdCuotas();
            if (cuotasIdCuotas != null) {
                cuotasIdCuotas.getPagosSociosCollection().remove(pagosSocios);
                cuotasIdCuotas = em.merge(cuotasIdCuotas);
            }
            Socios sociosIdSocio = pagosSocios.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio.getPagosSociosCollection().remove(pagosSocios);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            Collection<Detalles> detallesCollection = pagosSocios.getDetallesCollection();
            for (Detalles detallesCollectionDetalles : detallesCollection) {
                detallesCollectionDetalles.setPagosSociosIdPagosSocios(null);
                detallesCollectionDetalles = em.merge(detallesCollectionDetalles);
            }
            em.remove(pagosSocios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PagosSocios> findPagosSociosEntities() {
        return findPagosSociosEntities(true, -1, -1);
    }

    public List<PagosSocios> findPagosSociosEntities(int maxResults, int firstResult) {
        return findPagosSociosEntities(false, maxResults, firstResult);
    }

    private List<PagosSocios> findPagosSociosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagosSocios.class));
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

    public PagosSocios findPagosSocios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagosSocios.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosSociosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagosSocios> rt = cq.from(PagosSocios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
