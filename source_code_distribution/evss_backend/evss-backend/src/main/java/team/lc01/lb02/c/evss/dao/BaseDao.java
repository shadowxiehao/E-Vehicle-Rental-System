package team.lc01.lb02.c.evss.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import team.lc01.lb02.c.evss.domain.BaseDomain;
import team.lc01.lb02.c.evss.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseDao<T extends BaseDomain> {

    @PersistenceContext
    EntityManager em;

    private Class<T> clazz;

    public BaseDao(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public List<T> findAll() {
        return em.createNamedQuery(clazz.getSimpleName() + ".findAll", clazz).getResultList();
    }

    public Optional<T> find(long id) {
        return Optional.of(em.find(clazz, id));
    }

    @Transactional
    public T save(T entity) {
        if (entity.isNew()) {
            em.persist(entity);
            em.flush();
        } else {
            entity = em.merge(entity);
        }
        return entity;
    }

    @Transactional
    public T update(T entity) {
        entity = em.merge(entity);
        return entity;
    }

    @Transactional
    public boolean delete(long id) {
        T item = em.find(clazz, id);
        if (Objects.isNull(item)) {
            throw new NotFoundException(clazz.getSimpleName());
        }
        em.remove(item);
        return true;
    }
}
