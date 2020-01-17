/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import model.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import object.Socios;
import object.PagosSocios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import object.Administradores;

/**
 *
 * @author julio
 */
public class AdministradoresJpaController implements Serializable {

    public AdministradoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administradores administradores) throws PreexistingEntityException, Exception {
        if (administradores.getPagosSociosCollection() == null) {
            administradores.setPagosSociosCollection(new ArrayList<PagosSocios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios sociosIdSocio = administradores.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio = em.getReference(sociosIdSocio.getClass(), sociosIdSocio.getIdSocio());
                administradores.setSociosIdSocio(sociosIdSocio);
            }
            Collection<PagosSocios> attachedPagosSociosCollection = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionPagosSociosToAttach : administradores.getPagosSociosCollection()) {
                pagosSociosCollectionPagosSociosToAttach = em.getReference(pagosSociosCollectionPagosSociosToAttach.getClass(), pagosSociosCollectionPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollection.add(pagosSociosCollectionPagosSociosToAttach);
            }
            administradores.setPagosSociosCollection(attachedPagosSociosCollection);
            em.persist(administradores);
            if (sociosIdSocio != null) {
                sociosIdSocio.getAdministradoresCollection().add(administradores);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            for (PagosSocios pagosSociosCollectionPagosSocios : administradores.getPagosSociosCollection()) {
                Administradores oldAdministradoresIdAdministradorOfPagosSociosCollectionPagosSocios = pagosSociosCollectionPagosSocios.getAdministradoresIdAdministrador();
                pagosSociosCollectionPagosSocios.setAdministradoresIdAdministrador(administradores);
                pagosSociosCollectionPagosSocios = em.merge(pagosSociosCollectionPagosSocios);
                if (oldAdministradoresIdAdministradorOfPagosSociosCollectionPagosSocios != null) {
                    oldAdministradoresIdAdministradorOfPagosSociosCollectionPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionPagosSocios);
                    oldAdministradoresIdAdministradorOfPagosSociosCollectionPagosSocios = em.merge(oldAdministradoresIdAdministradorOfPagosSociosCollectionPagosSocios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdministradores(administradores.getIdAdministrador()) != null) {
                throw new PreexistingEntityException("Administradores " + administradores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administradores administradores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administradores persistentAdministradores = em.find(Administradores.class, administradores.getIdAdministrador());
            Socios sociosIdSocioOld = persistentAdministradores.getSociosIdSocio();
            Socios sociosIdSocioNew = administradores.getSociosIdSocio();
            Collection<PagosSocios> pagosSociosCollectionOld = persistentAdministradores.getPagosSociosCollection();
            Collection<PagosSocios> pagosSociosCollectionNew = administradores.getPagosSociosCollection();
            List<String> illegalOrphanMessages = null;
            for (PagosSocios pagosSociosCollectionOldPagosSocios : pagosSociosCollectionOld) {
                if (!pagosSociosCollectionNew.contains(pagosSociosCollectionOldPagosSocios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagosSocios " + pagosSociosCollectionOldPagosSocios + " since its administradoresIdAdministrador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sociosIdSocioNew != null) {
                sociosIdSocioNew = em.getReference(sociosIdSocioNew.getClass(), sociosIdSocioNew.getIdSocio());
                administradores.setSociosIdSocio(sociosIdSocioNew);
            }
            Collection<PagosSocios> attachedPagosSociosCollectionNew = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionNewPagosSociosToAttach : pagosSociosCollectionNew) {
                pagosSociosCollectionNewPagosSociosToAttach = em.getReference(pagosSociosCollectionNewPagosSociosToAttach.getClass(), pagosSociosCollectionNewPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollectionNew.add(pagosSociosCollectionNewPagosSociosToAttach);
            }
            pagosSociosCollectionNew = attachedPagosSociosCollectionNew;
            administradores.setPagosSociosCollection(pagosSociosCollectionNew);
            administradores = em.merge(administradores);
            if (sociosIdSocioOld != null && !sociosIdSocioOld.equals(sociosIdSocioNew)) {
                sociosIdSocioOld.getAdministradoresCollection().remove(administradores);
                sociosIdSocioOld = em.merge(sociosIdSocioOld);
            }
            if (sociosIdSocioNew != null && !sociosIdSocioNew.equals(sociosIdSocioOld)) {
                sociosIdSocioNew.getAdministradoresCollection().add(administradores);
                sociosIdSocioNew = em.merge(sociosIdSocioNew);
            }
            for (PagosSocios pagosSociosCollectionNewPagosSocios : pagosSociosCollectionNew) {
                if (!pagosSociosCollectionOld.contains(pagosSociosCollectionNewPagosSocios)) {
                    Administradores oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios = pagosSociosCollectionNewPagosSocios.getAdministradoresIdAdministrador();
                    pagosSociosCollectionNewPagosSocios.setAdministradoresIdAdministrador(administradores);
                    pagosSociosCollectionNewPagosSocios = em.merge(pagosSociosCollectionNewPagosSocios);
                    if (oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios != null && !oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios.equals(administradores)) {
                        oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionNewPagosSocios);
                        oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios = em.merge(oldAdministradoresIdAdministradorOfPagosSociosCollectionNewPagosSocios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = administradores.getIdAdministrador();
                if (findAdministradores(id) == null) {
                    throw new NonexistentEntityException("The administradores with id " + id + " no longer exists.");
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
            Administradores administradores;
            try {
                administradores = em.getReference(Administradores.class, id);
                administradores.getIdAdministrador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administradores with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PagosSocios> pagosSociosCollectionOrphanCheck = administradores.getPagosSociosCollection();
            for (PagosSocios pagosSociosCollectionOrphanCheckPagosSocios : pagosSociosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Administradores (" + administradores + ") cannot be destroyed since the PagosSocios " + pagosSociosCollectionOrphanCheckPagosSocios + " in its pagosSociosCollection field has a non-nullable administradoresIdAdministrador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Socios sociosIdSocio = administradores.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio.getAdministradoresCollection().remove(administradores);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            em.remove(administradores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administradores> findAdministradoresEntities() {
        return findAdministradoresEntities(true, -1, -1);
    }

    public List<Administradores> findAdministradoresEntities(int maxResults, int firstResult) {
        return findAdministradoresEntities(false, maxResults, firstResult);
    }

    private List<Administradores> findAdministradoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administradores.class));
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

    public Administradores findAdministradores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administradores.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administradores> rt = cq.from(Administradores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
