package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Repository<T extends ProjectEntity>
        implements IRepository<T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public Repository() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public Long saveOrUpdate(T e) {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                if (e.getId() == null || e.getId() == 0) {
                    em.persist(e);
                } else {
                    em.merge(e);
                }
                tx.commit();
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                throw ex;
            }
        }
        return e.getId();
    }

    private String getAliasFromJpql(String jpql) {
        return jpql.split(" ")[1];
    }

    @Override
    public List<T> findAll() {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            String jpql = getJpqlFindAll();
            String alias = getAliasFromJpql(jpql);
            String jpqlFiltred = jpql + " WHERE " + alias + ".excluido = false";
            TypedQuery<T> query = em.createQuery(jpqlFiltred, persistentClass);
            return query.getResultList();
        }
    }

    @Override
    public T findById(Long id) {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            T entity = em.find(persistentClass, id);
            if (entity != null && !entity.isExcluido()) {
                return entity;
            }
            return null;
        }
    }

    @Override
    public boolean deleteByEntity(T e) {
        if (e == null || e.getId() == null) return false;
        return setExcluidoFlag(e.getId(), true);
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;
        return setExcluidoFlag(id, true);
    }

    @Override
    public List<T> findAllInTrash() {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            String jpql = getJpqlFindAll();
            String alias = getAliasFromJpql(jpql);
            String jpqlFiltred = jpql + " WHERE " + alias + ".excluido = true";
            TypedQuery<T> query = em.createQuery(jpqlFiltred, persistentClass);
            return query.getResultList();
        }
    }

    @Override
    public boolean recoverFromTrash(Long id) {
        if (id == null) return false;
        return setExcluidoFlag(id, false);
    }

    private boolean setExcluidoFlag(Long id, boolean flag) {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                T entity = em.find(persistentClass, id);
                if (entity != null) {
                    entity.setExcluido(flag);
                    em.merge(entity);
                    tx.commit();
                    return true;
                }
                if (tx.isActive()) tx.rollback();
                return false;
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public boolean deletePermanently(Long id) {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                T entity = em.find(persistentClass, id);
                if (entity != null) {
                    em.remove(entity);
                    tx.commit();
                    return true;
                }
                tx.rollback();
                return false;
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public int emptyTrash() {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            List<T> itemsInTrash = findAllInTrash();
            int count = 0;
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                for (T item : itemsInTrash) {
                    em.remove(em.contains(item) ? item : em.merge(item));
                    count++;
                }
                tx.commit();
                return count;
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) tx.rollback();
                throw ex;
            }
        }
    }

    public abstract String getJpqlFindAll();
    public abstract String getJpqlFindById();
    public abstract String getJpqlDeleteById();
}