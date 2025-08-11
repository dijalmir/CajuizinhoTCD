/*
 * Copyright (C) 2025 Luis Guisso &lt;luis dot guisso at ifnmg dot edu dot br&gt;
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.edu.ifnmg.poo.trabalhofinalpoo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Generic repository
 *
 * @author Dijalmir Junior
 * @version 0.1
 * @param <T> Type of objects
 * @since 0.1, Jul 7, 2025
 */
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

        // try-with resources
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
                    throw ex;
                }
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

    //<editor-fold defaultstate="collapsed" desc="Lixeira (Trash) Implementation">
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
                Query query = em.createQuery(getJpqlDeleteById());
                query.setParameter("id", id);
                int deletions = query.executeUpdate();
                tx.commit();
                return deletions > 0;
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) tx.rollback();
                throw ex;
            }
        }
    }

    @Override
    public int emptyTrash() {
        try (EntityManager em = DataSourceFactory.getEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                String jpql = "DELETE FROM " + persistentClass.getSimpleName() + " e WHERE e.excluido = true";
                Query query = em.createQuery(jpql);
                int deletions = query.executeUpdate();
                tx.commit();
                return deletions;
            } catch (Exception ex) {
                if (tx != null && tx.isActive()) tx.rollback();
                throw ex;
            }
        }
    }
    //</editor-fold>
}