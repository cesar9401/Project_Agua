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
import object.Socios;
import object.Administradores;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.exceptions.IllegalOrphanException;
import model.exceptions.NonexistentEntityException;
import object.PagosMorosos;
import object.PagosSocios;
import object.SociosEventos;

/**
 *
 * @author julio
 */
public class SociosJpaController implements Serializable {

    public SociosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Socios socios) {
        if (socios.getAdministradoresCollection() == null) {
            socios.setAdministradoresCollection(new ArrayList<Administradores>());
        }
        if (socios.getPagosMorososCollection() == null) {
            socios.setPagosMorososCollection(new ArrayList<PagosMorosos>());
        }
        if (socios.getPagosSociosCollection() == null) {
            socios.setPagosSociosCollection(new ArrayList<PagosSocios>());
        }
        if (socios.getSociosCollection() == null) {
            socios.setSociosCollection(new ArrayList<Socios>());
        }
        if (socios.getSociosEventosCollection() == null) {
            socios.setSociosEventosCollection(new ArrayList<SociosEventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios sociosIdSocio = socios.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio = em.getReference(sociosIdSocio.getClass(), sociosIdSocio.getIdSocio());
                socios.setSociosIdSocio(sociosIdSocio);
            }
            Collection<Administradores> attachedAdministradoresCollection = new ArrayList<Administradores>();
            for (Administradores administradoresCollectionAdministradoresToAttach : socios.getAdministradoresCollection()) {
                administradoresCollectionAdministradoresToAttach = em.getReference(administradoresCollectionAdministradoresToAttach.getClass(), administradoresCollectionAdministradoresToAttach.getIdAdministrador());
                attachedAdministradoresCollection.add(administradoresCollectionAdministradoresToAttach);
            }
            socios.setAdministradoresCollection(attachedAdministradoresCollection);
            Collection<PagosMorosos> attachedPagosMorososCollection = new ArrayList<PagosMorosos>();
            for (PagosMorosos pagosMorososCollectionPagosMorososToAttach : socios.getPagosMorososCollection()) {
                pagosMorososCollectionPagosMorososToAttach = em.getReference(pagosMorososCollectionPagosMorososToAttach.getClass(), pagosMorososCollectionPagosMorososToAttach.getIdPagosMorosos());
                attachedPagosMorososCollection.add(pagosMorososCollectionPagosMorososToAttach);
            }
            socios.setPagosMorososCollection(attachedPagosMorososCollection);
            Collection<PagosSocios> attachedPagosSociosCollection = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionPagosSociosToAttach : socios.getPagosSociosCollection()) {
                pagosSociosCollectionPagosSociosToAttach = em.getReference(pagosSociosCollectionPagosSociosToAttach.getClass(), pagosSociosCollectionPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollection.add(pagosSociosCollectionPagosSociosToAttach);
            }
            socios.setPagosSociosCollection(attachedPagosSociosCollection);
            Collection<Socios> attachedSociosCollection = new ArrayList<Socios>();
            for (Socios sociosCollectionSociosToAttach : socios.getSociosCollection()) {
                sociosCollectionSociosToAttach = em.getReference(sociosCollectionSociosToAttach.getClass(), sociosCollectionSociosToAttach.getIdSocio());
                attachedSociosCollection.add(sociosCollectionSociosToAttach);
            }
            socios.setSociosCollection(attachedSociosCollection);
            Collection<SociosEventos> attachedSociosEventosCollection = new ArrayList<SociosEventos>();
            for (SociosEventos sociosEventosCollectionSociosEventosToAttach : socios.getSociosEventosCollection()) {
                sociosEventosCollectionSociosEventosToAttach = em.getReference(sociosEventosCollectionSociosEventosToAttach.getClass(), sociosEventosCollectionSociosEventosToAttach.getIdSociosEventos());
                attachedSociosEventosCollection.add(sociosEventosCollectionSociosEventosToAttach);
            }
            socios.setSociosEventosCollection(attachedSociosEventosCollection);
            em.persist(socios);
            if (sociosIdSocio != null) {
                sociosIdSocio.getSociosCollection().add(socios);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            for (Administradores administradoresCollectionAdministradores : socios.getAdministradoresCollection()) {
                Socios oldSociosIdSocioOfAdministradoresCollectionAdministradores = administradoresCollectionAdministradores.getSociosIdSocio();
                administradoresCollectionAdministradores.setSociosIdSocio(socios);
                administradoresCollectionAdministradores = em.merge(administradoresCollectionAdministradores);
                if (oldSociosIdSocioOfAdministradoresCollectionAdministradores != null) {
                    oldSociosIdSocioOfAdministradoresCollectionAdministradores.getAdministradoresCollection().remove(administradoresCollectionAdministradores);
                    oldSociosIdSocioOfAdministradoresCollectionAdministradores = em.merge(oldSociosIdSocioOfAdministradoresCollectionAdministradores);
                }
            }
            for (PagosMorosos pagosMorososCollectionPagosMorosos : socios.getPagosMorososCollection()) {
                Socios oldSociosIdSocioOfPagosMorososCollectionPagosMorosos = pagosMorososCollectionPagosMorosos.getSociosIdSocio();
                pagosMorososCollectionPagosMorosos.setSociosIdSocio(socios);
                pagosMorososCollectionPagosMorosos = em.merge(pagosMorososCollectionPagosMorosos);
                if (oldSociosIdSocioOfPagosMorososCollectionPagosMorosos != null) {
                    oldSociosIdSocioOfPagosMorososCollectionPagosMorosos.getPagosMorososCollection().remove(pagosMorososCollectionPagosMorosos);
                    oldSociosIdSocioOfPagosMorososCollectionPagosMorosos = em.merge(oldSociosIdSocioOfPagosMorososCollectionPagosMorosos);
                }
            }
            for (PagosSocios pagosSociosCollectionPagosSocios : socios.getPagosSociosCollection()) {
                Socios oldSociosIdSocioOfPagosSociosCollectionPagosSocios = pagosSociosCollectionPagosSocios.getSociosIdSocio();
                pagosSociosCollectionPagosSocios.setSociosIdSocio(socios);
                pagosSociosCollectionPagosSocios = em.merge(pagosSociosCollectionPagosSocios);
                if (oldSociosIdSocioOfPagosSociosCollectionPagosSocios != null) {
                    oldSociosIdSocioOfPagosSociosCollectionPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionPagosSocios);
                    oldSociosIdSocioOfPagosSociosCollectionPagosSocios = em.merge(oldSociosIdSocioOfPagosSociosCollectionPagosSocios);
                }
            }
            for (Socios sociosCollectionSocios : socios.getSociosCollection()) {
                Socios oldSociosIdSocioOfSociosCollectionSocios = sociosCollectionSocios.getSociosIdSocio();
                sociosCollectionSocios.setSociosIdSocio(socios);
                sociosCollectionSocios = em.merge(sociosCollectionSocios);
                if (oldSociosIdSocioOfSociosCollectionSocios != null) {
                    oldSociosIdSocioOfSociosCollectionSocios.getSociosCollection().remove(sociosCollectionSocios);
                    oldSociosIdSocioOfSociosCollectionSocios = em.merge(oldSociosIdSocioOfSociosCollectionSocios);
                }
            }
            for (SociosEventos sociosEventosCollectionSociosEventos : socios.getSociosEventosCollection()) {
                Socios oldSociosIdSocioOfSociosEventosCollectionSociosEventos = sociosEventosCollectionSociosEventos.getSociosIdSocio();
                sociosEventosCollectionSociosEventos.setSociosIdSocio(socios);
                sociosEventosCollectionSociosEventos = em.merge(sociosEventosCollectionSociosEventos);
                if (oldSociosIdSocioOfSociosEventosCollectionSociosEventos != null) {
                    oldSociosIdSocioOfSociosEventosCollectionSociosEventos.getSociosEventosCollection().remove(sociosEventosCollectionSociosEventos);
                    oldSociosIdSocioOfSociosEventosCollectionSociosEventos = em.merge(oldSociosIdSocioOfSociosEventosCollectionSociosEventos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Socios socios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Socios persistentSocios = em.find(Socios.class, socios.getIdSocio());
            Socios sociosIdSocioOld = persistentSocios.getSociosIdSocio();
            Socios sociosIdSocioNew = socios.getSociosIdSocio();
            Collection<Administradores> administradoresCollectionOld = persistentSocios.getAdministradoresCollection();
            Collection<Administradores> administradoresCollectionNew = socios.getAdministradoresCollection();
            Collection<PagosMorosos> pagosMorososCollectionOld = persistentSocios.getPagosMorososCollection();
            Collection<PagosMorosos> pagosMorososCollectionNew = socios.getPagosMorososCollection();
            Collection<PagosSocios> pagosSociosCollectionOld = persistentSocios.getPagosSociosCollection();
            Collection<PagosSocios> pagosSociosCollectionNew = socios.getPagosSociosCollection();
            Collection<Socios> sociosCollectionOld = persistentSocios.getSociosCollection();
            Collection<Socios> sociosCollectionNew = socios.getSociosCollection();
            Collection<SociosEventos> sociosEventosCollectionOld = persistentSocios.getSociosEventosCollection();
            Collection<SociosEventos> sociosEventosCollectionNew = socios.getSociosEventosCollection();
            List<String> illegalOrphanMessages = null;
            for (Administradores administradoresCollectionOldAdministradores : administradoresCollectionOld) {
                if (!administradoresCollectionNew.contains(administradoresCollectionOldAdministradores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Administradores " + administradoresCollectionOldAdministradores + " since its sociosIdSocio field is not nullable.");
                }
            }
            for (PagosMorosos pagosMorososCollectionOldPagosMorosos : pagosMorososCollectionOld) {
                if (!pagosMorososCollectionNew.contains(pagosMorososCollectionOldPagosMorosos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagosMorosos " + pagosMorososCollectionOldPagosMorosos + " since its sociosIdSocio field is not nullable.");
                }
            }
            for (PagosSocios pagosSociosCollectionOldPagosSocios : pagosSociosCollectionOld) {
                if (!pagosSociosCollectionNew.contains(pagosSociosCollectionOldPagosSocios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PagosSocios " + pagosSociosCollectionOldPagosSocios + " since its sociosIdSocio field is not nullable.");
                }
            }
            for (Socios sociosCollectionOldSocios : sociosCollectionOld) {
                if (!sociosCollectionNew.contains(sociosCollectionOldSocios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Socios " + sociosCollectionOldSocios + " since its sociosIdSocio field is not nullable.");
                }
            }
            for (SociosEventos sociosEventosCollectionOldSociosEventos : sociosEventosCollectionOld) {
                if (!sociosEventosCollectionNew.contains(sociosEventosCollectionOldSociosEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SociosEventos " + sociosEventosCollectionOldSociosEventos + " since its sociosIdSocio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sociosIdSocioNew != null) {
                sociosIdSocioNew = em.getReference(sociosIdSocioNew.getClass(), sociosIdSocioNew.getIdSocio());
                socios.setSociosIdSocio(sociosIdSocioNew);
            }
            Collection<Administradores> attachedAdministradoresCollectionNew = new ArrayList<Administradores>();
            for (Administradores administradoresCollectionNewAdministradoresToAttach : administradoresCollectionNew) {
                administradoresCollectionNewAdministradoresToAttach = em.getReference(administradoresCollectionNewAdministradoresToAttach.getClass(), administradoresCollectionNewAdministradoresToAttach.getIdAdministrador());
                attachedAdministradoresCollectionNew.add(administradoresCollectionNewAdministradoresToAttach);
            }
            administradoresCollectionNew = attachedAdministradoresCollectionNew;
            socios.setAdministradoresCollection(administradoresCollectionNew);
            Collection<PagosMorosos> attachedPagosMorososCollectionNew = new ArrayList<PagosMorosos>();
            for (PagosMorosos pagosMorososCollectionNewPagosMorososToAttach : pagosMorososCollectionNew) {
                pagosMorososCollectionNewPagosMorososToAttach = em.getReference(pagosMorososCollectionNewPagosMorososToAttach.getClass(), pagosMorososCollectionNewPagosMorososToAttach.getIdPagosMorosos());
                attachedPagosMorososCollectionNew.add(pagosMorososCollectionNewPagosMorososToAttach);
            }
            pagosMorososCollectionNew = attachedPagosMorososCollectionNew;
            socios.setPagosMorososCollection(pagosMorososCollectionNew);
            Collection<PagosSocios> attachedPagosSociosCollectionNew = new ArrayList<PagosSocios>();
            for (PagosSocios pagosSociosCollectionNewPagosSociosToAttach : pagosSociosCollectionNew) {
                pagosSociosCollectionNewPagosSociosToAttach = em.getReference(pagosSociosCollectionNewPagosSociosToAttach.getClass(), pagosSociosCollectionNewPagosSociosToAttach.getIdPagosSocios());
                attachedPagosSociosCollectionNew.add(pagosSociosCollectionNewPagosSociosToAttach);
            }
            pagosSociosCollectionNew = attachedPagosSociosCollectionNew;
            socios.setPagosSociosCollection(pagosSociosCollectionNew);
            Collection<Socios> attachedSociosCollectionNew = new ArrayList<Socios>();
            for (Socios sociosCollectionNewSociosToAttach : sociosCollectionNew) {
                sociosCollectionNewSociosToAttach = em.getReference(sociosCollectionNewSociosToAttach.getClass(), sociosCollectionNewSociosToAttach.getIdSocio());
                attachedSociosCollectionNew.add(sociosCollectionNewSociosToAttach);
            }
            sociosCollectionNew = attachedSociosCollectionNew;
            socios.setSociosCollection(sociosCollectionNew);
            Collection<SociosEventos> attachedSociosEventosCollectionNew = new ArrayList<SociosEventos>();
            for (SociosEventos sociosEventosCollectionNewSociosEventosToAttach : sociosEventosCollectionNew) {
                sociosEventosCollectionNewSociosEventosToAttach = em.getReference(sociosEventosCollectionNewSociosEventosToAttach.getClass(), sociosEventosCollectionNewSociosEventosToAttach.getIdSociosEventos());
                attachedSociosEventosCollectionNew.add(sociosEventosCollectionNewSociosEventosToAttach);
            }
            sociosEventosCollectionNew = attachedSociosEventosCollectionNew;
            socios.setSociosEventosCollection(sociosEventosCollectionNew);
            socios = em.merge(socios);
            if (sociosIdSocioOld != null && !sociosIdSocioOld.equals(sociosIdSocioNew)) {
                sociosIdSocioOld.getSociosCollection().remove(socios);
                sociosIdSocioOld = em.merge(sociosIdSocioOld);
            }
            if (sociosIdSocioNew != null && !sociosIdSocioNew.equals(sociosIdSocioOld)) {
                sociosIdSocioNew.getSociosCollection().add(socios);
                sociosIdSocioNew = em.merge(sociosIdSocioNew);
            }
            for (Administradores administradoresCollectionNewAdministradores : administradoresCollectionNew) {
                if (!administradoresCollectionOld.contains(administradoresCollectionNewAdministradores)) {
                    Socios oldSociosIdSocioOfAdministradoresCollectionNewAdministradores = administradoresCollectionNewAdministradores.getSociosIdSocio();
                    administradoresCollectionNewAdministradores.setSociosIdSocio(socios);
                    administradoresCollectionNewAdministradores = em.merge(administradoresCollectionNewAdministradores);
                    if (oldSociosIdSocioOfAdministradoresCollectionNewAdministradores != null && !oldSociosIdSocioOfAdministradoresCollectionNewAdministradores.equals(socios)) {
                        oldSociosIdSocioOfAdministradoresCollectionNewAdministradores.getAdministradoresCollection().remove(administradoresCollectionNewAdministradores);
                        oldSociosIdSocioOfAdministradoresCollectionNewAdministradores = em.merge(oldSociosIdSocioOfAdministradoresCollectionNewAdministradores);
                    }
                }
            }
            for (PagosMorosos pagosMorososCollectionNewPagosMorosos : pagosMorososCollectionNew) {
                if (!pagosMorososCollectionOld.contains(pagosMorososCollectionNewPagosMorosos)) {
                    Socios oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos = pagosMorososCollectionNewPagosMorosos.getSociosIdSocio();
                    pagosMorososCollectionNewPagosMorosos.setSociosIdSocio(socios);
                    pagosMorososCollectionNewPagosMorosos = em.merge(pagosMorososCollectionNewPagosMorosos);
                    if (oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos != null && !oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos.equals(socios)) {
                        oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos.getPagosMorososCollection().remove(pagosMorososCollectionNewPagosMorosos);
                        oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos = em.merge(oldSociosIdSocioOfPagosMorososCollectionNewPagosMorosos);
                    }
                }
            }
            for (PagosSocios pagosSociosCollectionNewPagosSocios : pagosSociosCollectionNew) {
                if (!pagosSociosCollectionOld.contains(pagosSociosCollectionNewPagosSocios)) {
                    Socios oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios = pagosSociosCollectionNewPagosSocios.getSociosIdSocio();
                    pagosSociosCollectionNewPagosSocios.setSociosIdSocio(socios);
                    pagosSociosCollectionNewPagosSocios = em.merge(pagosSociosCollectionNewPagosSocios);
                    if (oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios != null && !oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios.equals(socios)) {
                        oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios.getPagosSociosCollection().remove(pagosSociosCollectionNewPagosSocios);
                        oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios = em.merge(oldSociosIdSocioOfPagosSociosCollectionNewPagosSocios);
                    }
                }
            }
            for (Socios sociosCollectionNewSocios : sociosCollectionNew) {
                if (!sociosCollectionOld.contains(sociosCollectionNewSocios)) {
                    Socios oldSociosIdSocioOfSociosCollectionNewSocios = sociosCollectionNewSocios.getSociosIdSocio();
                    sociosCollectionNewSocios.setSociosIdSocio(socios);
                    sociosCollectionNewSocios = em.merge(sociosCollectionNewSocios);
                    if (oldSociosIdSocioOfSociosCollectionNewSocios != null && !oldSociosIdSocioOfSociosCollectionNewSocios.equals(socios)) {
                        oldSociosIdSocioOfSociosCollectionNewSocios.getSociosCollection().remove(sociosCollectionNewSocios);
                        oldSociosIdSocioOfSociosCollectionNewSocios = em.merge(oldSociosIdSocioOfSociosCollectionNewSocios);
                    }
                }
            }
            for (SociosEventos sociosEventosCollectionNewSociosEventos : sociosEventosCollectionNew) {
                if (!sociosEventosCollectionOld.contains(sociosEventosCollectionNewSociosEventos)) {
                    Socios oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos = sociosEventosCollectionNewSociosEventos.getSociosIdSocio();
                    sociosEventosCollectionNewSociosEventos.setSociosIdSocio(socios);
                    sociosEventosCollectionNewSociosEventos = em.merge(sociosEventosCollectionNewSociosEventos);
                    if (oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos != null && !oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos.equals(socios)) {
                        oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos.getSociosEventosCollection().remove(sociosEventosCollectionNewSociosEventos);
                        oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos = em.merge(oldSociosIdSocioOfSociosEventosCollectionNewSociosEventos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = socios.getIdSocio();
                if (findSocios(id) == null) {
                    throw new NonexistentEntityException("The socios with id " + id + " no longer exists.");
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
            Socios socios;
            try {
                socios = em.getReference(Socios.class, id);
                socios.getIdSocio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The socios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Administradores> administradoresCollectionOrphanCheck = socios.getAdministradoresCollection();
            for (Administradores administradoresCollectionOrphanCheckAdministradores : administradoresCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Administradores " + administradoresCollectionOrphanCheckAdministradores + " in its administradoresCollection field has a non-nullable sociosIdSocio field.");
            }
            Collection<PagosMorosos> pagosMorososCollectionOrphanCheck = socios.getPagosMorososCollection();
            for (PagosMorosos pagosMorososCollectionOrphanCheckPagosMorosos : pagosMorososCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the PagosMorosos " + pagosMorososCollectionOrphanCheckPagosMorosos + " in its pagosMorososCollection field has a non-nullable sociosIdSocio field.");
            }
            Collection<PagosSocios> pagosSociosCollectionOrphanCheck = socios.getPagosSociosCollection();
            for (PagosSocios pagosSociosCollectionOrphanCheckPagosSocios : pagosSociosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the PagosSocios " + pagosSociosCollectionOrphanCheckPagosSocios + " in its pagosSociosCollection field has a non-nullable sociosIdSocio field.");
            }
            Collection<Socios> sociosCollectionOrphanCheck = socios.getSociosCollection();
            for (Socios sociosCollectionOrphanCheckSocios : sociosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the Socios " + sociosCollectionOrphanCheckSocios + " in its sociosCollection field has a non-nullable sociosIdSocio field.");
            }
            Collection<SociosEventos> sociosEventosCollectionOrphanCheck = socios.getSociosEventosCollection();
            for (SociosEventos sociosEventosCollectionOrphanCheckSociosEventos : sociosEventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Socios (" + socios + ") cannot be destroyed since the SociosEventos " + sociosEventosCollectionOrphanCheckSociosEventos + " in its sociosEventosCollection field has a non-nullable sociosIdSocio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Socios sociosIdSocio = socios.getSociosIdSocio();
            if (sociosIdSocio != null) {
                sociosIdSocio.getSociosCollection().remove(socios);
                sociosIdSocio = em.merge(sociosIdSocio);
            }
            em.remove(socios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Socios> findSociosEntities() {
        return findSociosEntities(true, -1, -1);
    }

    public List<Socios> findSociosEntities(int maxResults, int firstResult) {
        return findSociosEntities(false, maxResults, firstResult);
    }

    private List<Socios> findSociosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Socios.class));
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

    public Socios findSocios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Socios.class, id);
        } finally {
            em.close();
        }
    }

    public int getSociosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Socios> rt = cq.from(Socios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
