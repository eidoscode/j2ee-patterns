package com.eidoscode.framework.persistence.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
 * @version 1.0
 * @param <Key>
 *            The type of the Id of the model. If it's a relational database, it
 *            probably will be something such as a {@link Long} or
 *            {@link Integer}.
 * @param <Bean>
 *            The entity that implements the {@link Model} interface and use the
 *            same key passed on the "Key"parameter.
 */
public abstract class DataAccessObjectImpl<Key extends Serializable, Bean extends Model<Key>>
		extends MinimalDataAccessObjectImpl implements
		DataAccessObject<Key, Bean> {

	private final Class<Bean> entityClass;
	private final Class<Key> keyClass;
	private final String entityName;

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
	 * Saves the desired entity. It means it will persist a new entity or merge
	 * an existent entity.
	 * 
	 * @since 1.0
	 * @param bean
	 *            desired entity.
	 * @return entity stored
	 */
	@Override
	public Bean save(Bean bean) {
		return save(bean, false);
	}

	/**
	 * Saves the desired entity. It means it will persist a new entity or merge
	 * an existent entity.
	 * 
	 * @since 1.0
	 * @param bean
	 *            desired entity.
	 * @param flush
	 *            If <code>true</code> the method {@link EntityManager#flush()}
	 *            will be called.
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
		if (flush) {
			getEntityManager().flush();
		}
		return bean;
	}

	/**
	 * Removes a desired entity.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Desired entity.
	 */
	@Override
	public void remove(Bean bean) {
		remove(bean, false);
	}

	/**
	 * Removes a desired entity.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Desired entity.
	 * @param flush
	 *            If <code>true</code> the method {@link EntityManager#flush()}
	 *            will be called.
	 */
	@Override
	public void remove(Bean bean, boolean flush) {
		getLogger().debug("Removing object: " + bean);
		bean = findByKey(bean.getId());
		getEntityManager().remove(bean);
		if (flush) {
			getEntityManager().flush();
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
	 *            position of the first result, numbered from 0.
	 * @param maxResult
	 *            maximum number of results to retrieve.
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
	 *            {@link TypedQuery} of the entity.
	 */
	protected void defaultFilterQuery(TypedQuery<Bean> typedQuery) {
	}

	/**
	 * Default filter to be used on the {@link #findAll()} method.<br/>
	 * This is a hook method. THe main implementation do nothing.
	 * 
	 * @since 1.0
	 * @param criteriaQuery
	 *            {@link CriteriaQuery} of the entity.
	 */
	protected void defaultFilterCriteria(CriteriaQuery<Bean> criteriaQuery) {
	}

	/**
	 * Generates a {@link CriteriaQuery} to a given type.
	 * 
	 * @since 1.0
	 * @param type
	 *            Type of the desired Criteria.
	 * @return {@link CriteriaQuery} specialized to the given type.
	 */
	protected final <E> CriteriaQuery<E> createCriteria(Class<E> type) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		return createCriteria(type, criteriaBuilder);
	}

	/**
	 * Generates a {@link CriteriaQuery} to a given type.
	 * 
	 * @since 1.0
	 * @param type
	 *            Type of the desired Criteria.
	 * @param criteriaBuilder
	 *            {@link CriteriaBuilder} to be used to create a
	 *            {@link CriteriaQuery}.
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
	 *            Entity key.
	 * @return Entity.
	 */
	@Override
	public Bean findByKey(Key id) {
		Bean bean = this.getEntityManager().find(this.getEntityClass(), id);
		return bean;
	}

}
