package com.eidoscode.framework.persistence.bo.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.eidoscode.framework.persistence.bo.BusinessObject;
import com.eidoscode.framework.persistence.dao.DataAccessObject;
import com.eidoscode.framework.persistence.model.Model;

/**
 * Business Object class that defines the generic method to find, save, remove a
 * bean.
 * 
 * @author eantonini
 * @version 1.0
 * @since 1.0
 * 
 * @param <Key>
 *            Type of the Key of the Entity (Must implements the interface
 *            {@link Serializable}).
 * @param <Bean>
 *            The Bean (Must implements the interface {@link Model} ).
 * @param <DAO>
 *            The Data Access Object Interface (Must implements the interface
 *            {@link DataAccessObject}).
 */
public abstract class BusinessObjectImpl<Key extends Serializable, Bean extends Model<Key>, DAO extends DataAccessObject<Key, Bean>>
		implements BusinessObject<Key, Bean, DAO> {

	private final Logger logger;

	/**
	 * Main constructor.
	 * 
	 * @since 1.0
	 */
	public BusinessObjectImpl() {
		logger = Logger.getLogger(getClass());
	}

	public Logger getLogger() {
		return logger;
	}

	/**
	 * The purpose of this method is to bring the DAO instance.
	 * 
	 * @since 1.0
	 * @return DAO instance.
	 */
	protected abstract DAO getDAO();

	/**
	 * Brings all the entities.
	 * 
	 * @since 1.0
	 * @return {@link List} with the entities.
	 */
	@Override
	public List<Bean> findAll() {
		return getDAO().findAll();
	}

	/**
	 * Count all the entities.
	 * 
	 * @since 1.0
	 * @return the amount of entities.
	 */
	@Override
	public Long countAll() {
		return getDAO().countAll();
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
		Bean retValue = null;
		if (id != null) {
			retValue = getDAO().findByKey(id);
		}
		return retValue;
	}

	/**
	 * This is a hook so can be used to customize an action before the save
	 * operation is successfully.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Bean.
	 */
	protected void beforeSave(Bean bean) {
	}

	/**
	 * This is a hook so can be used to customize an action after the save
	 * operation is successfully.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Bean.
	 */
	protected void afterSave(Bean bean) {
	}

	/**
	 * This is a hook so can be used to customize an action before the remove
	 * operation is successfully.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Bean.
	 */
	protected void beforeRemove(Bean bean) {
	}

	/**
	 * This is a hook so can be used to customize an action after the remove
	 * operation is successfully.
	 * 
	 * @since 1.0
	 * @param bean
	 *            Bean.
	 */
	protected void afterRemove(Bean bean) {
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Bean save(Bean bean, boolean flush) {
		if (bean == null) {
			throw new NullPointerException("The bean parameter is mandatory.");
		}
		beforeSave(bean);
		bean = getDAO().save(bean, flush);
		afterSave(bean);
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(Bean bean) {
		this.remove(bean, false);
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(Bean bean, boolean flush) {
		if (bean == null) {
			throw new NullPointerException("The bean parameter is mandatory.");
		}
		beforeRemove(bean);
		getDAO().remove(bean, flush);
		afterRemove(bean);
	}
}
