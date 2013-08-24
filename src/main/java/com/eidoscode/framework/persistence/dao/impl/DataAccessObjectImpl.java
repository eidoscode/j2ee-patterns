package com.eidoscode.framework.persistence.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.eidoscode.framework.persistence.dao.DataAccessObject;
import com.eidoscode.framework.persistence.model.Model;
import com.eidoscode.generics.utils.GenericsUtils;

/**
 * Main implementation of the Interface {@link DataAccessObject}. It provides
 * all the main methods to maintain an entity.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.2
 * @param <Key>
 *          The type of the Id of the model. If it's a relational database, it
 *          probably will be something such as a {@link Long} or {@link Integer}
 *          .
 * @param <Bean>
 *          The entity that implements the {@link Model} interface and use the
 *          same key passed on the "Key"parameter.
 */
public abstract class DataAccessObjectImpl<Key extends Serializable, Bean extends Model<Key>>
    extends MinimalDataAccessObjectImpl implements DataAccessObject<Key, Bean> {

  private final Class<Bean> entityClass;
  private final Class<Key> keyClass;
  private final String entityName;

  /**
   * Default amount that will be used on the batch save. This will be used on
   * the methods {@link #getAmountSaveBatchRecords()}.
   */
  public static final int DEFAULT_AMOUNT_SAVE_BATCH_RECORDS = 50;

  /**
   * Main constructor. It collects the Key of the entity, the entity type and
   * the name of the entity.
   * 
   * @since 1.0
   */
  public DataAccessObjectImpl() {
    String entityName = "";
    this.keyClass = GenericsUtils.getSuperClassGenericType(getClass(),
        DataAccessObjectImpl.class, 0);
    this.entityClass = GenericsUtils.getSuperClassGenericType(getClass(),
        DataAccessObjectImpl.class, 1);

    Annotation[] annotations = this.getEntityClass().getAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation instanceof Entity) {
        entityName = ((Entity) annotation).name();
        break;
      }
    }

    if (entityName.equals("")) {
      entityName = this.entityClass.getSimpleName();
    }
    this.entityName = entityName;

  }

  /**
   * Brings the Class of the key of the entity.
   * 
   * @since 1.0
   * @return Class of the key of the entity.
   */
  protected Class<Key> getKeyClass() {
    return this.keyClass;
  }

  /**
   * Brings the Class of the key of the entity.
   * 
   * @since 1.0
   * @return Class of the key of the entity.
   */
  protected Class<Bean> getEntityClass() {
    return this.entityClass;
  }

  /**
   * Brings the entity name.
   * 
   * @since 1.0
   * @return Entity name.
   */
  protected String getEntityName() {
    return this.entityName;
  }

  /**
   * Saves the desired entity. It means it will persist a new entity or merge an
   * existent entity.
   * 
   * @since 1.0
   * @param bean
   *          desired entity.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   * @return entity stored
   */
  @Override
  public Bean save(Bean bean, boolean flush) {
    if (bean.getId() == null) {
      getLogger().debug("Adding object: " + bean);
      getEntityManager().persist(bean);
    } else {
      getLogger().debug("Updating object: " + bean);
      bean = getEntityManager().merge(bean);
    }

    flushEntityManager(flush);
    return bean;
  }

  /**
   * Saves a list of the desired entities. It means it will persist a new entity
   * or merge an existent entity.
   * 
   * @since 1.3
   * @param beans
   *          desired entities.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   * @return entities stored.
   */
  @Override
  public <E extends Collection<Bean>> E save(E beans, boolean flush) {
    if (beans != null) {
      int savedBeans = 0;
      Iterator<Bean> iterator = beans.iterator();
      while (iterator.hasNext()) {
        Bean bean = iterator.next();
        save(bean, false);
        savedBeans++;
        if (flush && savedBeans % getAmountSaveBatchRecords() == 0) {
          flushEntityManager(true);
        }
      }
    }
    return beans;
  }

  /**
   * Merges the desired entity. It will merge an existent entity.
   * 
   * @since 1.5
   * @param bean
   *          desired entity.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   * @return entity stored
   */
  @Override
  public Bean merge(Bean bean, boolean flush) {
    getLogger().debug("Updating object: " + bean);
    bean = getEntityManager().merge(bean);

    flushEntityManager(flush);
    return bean;
  }

  /**
   * Merges the desired entity. It will merge an existent entity.
   * 
   * @since 1.5
   * @param beans
   *          desired entities.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   * @return entities stored.
   */
  @Override
  public <E extends Collection<Bean>> E merge(E beans, boolean flush) {
    if (beans != null) {
      int mergedBeans = 0;
      Iterator<Bean> iterator = beans.iterator();
      while (iterator.hasNext()) {
        Bean bean = iterator.next();
        merge(bean, false);
        mergedBeans++;
        if (flush && mergedBeans % getAmountSaveBatchRecords() == 0) {
          flushEntityManager(true);
        }
      }
    }
    return beans;
  }

  /**
   * Return the amount of records to be used on a batch save. <br/>
   * It is default used on these methods {@link #save(List)},
   * {@link #save(List, boolean)}, {@link #removeById(Collection)} and
   * {@link #removeById(Collection, boolean)}.
   * 
   * @return Amount of records to be used on the batch record.
   */
  protected int getAmountSaveBatchRecords() {
    return DEFAULT_AMOUNT_SAVE_BATCH_RECORDS;
  }

  /**
   * Removes a desired entity.
   * 
   * @since 1.0
   * @param bean
   *          Desired entity.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   */
  @Override
  public void remove(Bean bean, boolean flush) {
    getLogger().debug("Removing object: " + bean);
    bean = findByKey(bean.getId());
    getEntityManager().remove(bean);
    flushEntityManager(flush);
  }

  /**
   * Remove an entity by it's Id.
   * 
   * @since 1.3
   * @param key
   *          The Key of the entity that want to be removed.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   */
  @Override
  public void removeById(Key key, boolean flush) {
    // TODO Need to implements this on future using the JPA 2.1
    // http://en.wikibooks.org/wiki/Java_Persistence/Criteria#CriteriaDelete_.28JPA_2.1.29
    // cb.createCriteriaDelete();

    StringBuilder sb = new StringBuilder();
    sb.append("DELETE FROM ").append(getEntityName());
    sb.append(" a WHERE a.id = :id");

    Query query = getEntityManager().createQuery(sb.toString());
    query.setParameter("id", key);

    query.executeUpdate();

    flushEntityManager(flush);
  }

  /**
   * Removes all entities by an ID.
   * 
   * @since 1.3
   * @param keys
   *          Desired keys to be removed.
   * @param flush
   *          If <code>true</code> the method
   *          {@link #flushEntityManager(boolean)} will be called.
   * @return entities stored.
   */
  @Override
  public void removeById(Collection<Key> keys, boolean flush) {
    if (keys != null) {
      int removedBeans = 0;
      Iterator<Key> iterator = keys.iterator();
      while (iterator.hasNext()) {
        Key key = iterator.next();
        removeById(key, false);
        removedBeans++;
        if (flush && removedBeans % getAmountSaveBatchRecords() == 0) {
          flushEntityManager(true);
        }
      }
    }
  }

  /**
   * If received true as a parameter, it will get the current entity manager and
   * flush it and perform the clear of the session.
   * 
   * @param flush
   *          If true, it means that the session will be flushed and clean.
   */
  protected void flushEntityManager(boolean flush) {
    if (flush) {
      this.getEntityManager().flush();
      this.getEntityManager().clear();
    }
  }

  /**
   * Brings all the entities.
   * 
   * @since 1.0
   * @return {@link List} with the entities.
   */
  @Override
  public List<Bean> findAll() {
    TypedQuery<Bean> q = this.getEntityManager().createQuery(
        "from " + this.getEntityName(), this.getEntityClass());
    return q.getResultList();
  }

  /**
   * Brings all the entities paged.
   * 
   * @since 1.0
   * @param startPosition
   *          position of the first result, numbered from 0.
   * @param maxResult
   *          maximum number of results to retrieve.
   * @return {@link List} with the found entities.
   */
  public List<Bean> findAllPaginate(int startPosition, int maxResult) {
    CriteriaQuery<Bean> criteria = createCriteriaSelect();
    defaultFilterCriteria(criteria);
    TypedQuery<Bean> query = getEntityManager().createQuery(criteria);
    defaultFilterQuery(query);
    query.setFirstResult(startPosition);
    query.setMaxResults(maxResult);
    return query.getResultList();
  }

  /**
   * Default filter to be used on the {@link #findAll()} method.<br/>
   * This is a hook method. THe main implementation do nothing.
   * 
   * @since 1.0
   * @param typedQuery
   *          {@link TypedQuery} of the entity.
   */
  protected void defaultFilterQuery(TypedQuery<Bean> typedQuery) {
  }

  /**
   * Default filter to be used on the {@link #findAll()} method.<br/>
   * This is a hook method. THe main implementation do nothing.
   * 
   * @since 1.0
   * @param criteriaQuery
   *          {@link CriteriaQuery} of the entity.
   */
  protected void defaultFilterCriteria(CriteriaQuery<Bean> criteriaQuery) {
  }

  /**
   * Generates a {@link CriteriaQuery} to a given type.
   * 
   * @since 1.0
   * @param type
   *          Type of the desired Criteria.
   * @return {@link CriteriaQuery} specialized to the given type.
   */
  protected final <E> CriteriaQuery<E> createCriteria(Class<E> type) {
    CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
    return createCriteria(type, criteriaBuilder);
  }

  /**
   * Generates a {@link CriteriaQuery} to a given type.
   * 
   * @since 1.0
   * @param type
   *          Type of the desired Criteria.
   * @param criteriaBuilder
   *          {@link CriteriaBuilder} to be used to create a
   *          {@link CriteriaQuery}.
   * @return {@link CriteriaQuery} specialized to the given type.
   */
  protected final <E> CriteriaQuery<E> createCriteria(Class<E> type,
      CriteriaBuilder criteriaBuilder) {
    CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(type);
    return criteriaQuery;
  }

  /**
   * Create a {@link CriteriaQuery} that brings all the records of the entity.
   * 
   * @since 1.0
   * @return {@link CriteriaQuery} of the entity.
   */
  protected CriteriaQuery<Bean> createCriteriaSelect() {
    CriteriaQuery<Bean> criteriaQuery = createCriteria(getEntityClass());
    Root<Bean> from = criteriaQuery.from(getEntityClass());
    criteriaQuery.select(from);

    return criteriaQuery;
  }

  /**
   * Create a {@link CriteriaQuery} that his main goal is to return the amount
   * of records.
   * 
   * @since 1.0
   * @return {@link CriteriaQuery} of the entity.
   */
  protected CriteriaQuery<Long> createCriteriaCount() {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = createCriteria(Long.class, cb);
    criteriaQuery.select(cb.count(criteriaQuery.from(getEntityClass())));
    return criteriaQuery;
  }

  /**
   * Count all the entities.
   * 
   * @since 1.0
   * @return the amount of entities.
   */
  @Override
  public Long countAll() {
    CriteriaQuery<Long> cqL = createCriteriaCount();
    TypedQuery<Long> countQuery = getEntityManager().createQuery(cqL);
    return countQuery.getSingleResult();
  }

  /**
   * Brings an entity by its key.
   * 
   * @since 1.0
   * @param id
   *          Entity key.
   * @return Entity.
   */
  @Override
  public Bean findByKey(Key id) {
    Bean bean = this.getEntityManager().find(this.getEntityClass(), id);
    return bean;
  }

}
