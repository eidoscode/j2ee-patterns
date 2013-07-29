package com.eidoscode.framework.persistence.bo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.eidoscode.framework.persistence.dao.DataAccessObject;
import com.eidoscode.framework.persistence.model.Model;

/**
 * Main interface of the Business Object.
 * 
 * @author eantonini
 * @version 1.0
 * @since 1.0
 * @param <Key>
 *          Type of the Key of the Entity (Must implements the interface
 *          {@link Serializable}).
 * @param <Bean>
 *          The Bean (Must implements the interface {@link Model} ).
 * @param <DAO>
 *          The Data Access Object Interface (Must implements the interface
 *          {@link DataAccessObject}).
 */
public interface BusinessObject<Key extends Serializable, Bean extends Model<Key>, DAO extends DataAccessObject<Key, Bean>> {

  /**
   * Saves the desired entity. It means it will persist a new entity or merge an
   * existent entity.
   * 
   * @since 1.0
   * @param bean
   *          desired entity.
   * @return entity stored
   */
  Bean save(Bean bean);

  /**
   * Saves the desired entity. It means it will persist a new entity or merge an
   * existent entity.
   * 
   * @since 1.0
   * @param bean
   *          desired entity.
   * @param flush
   *          If <code>true</code> the method {@link EntityManager#flush()} will
   *          be called.
   * @return entity stored
   */
  Bean save(Bean bean, boolean flush);

  /**
   * Removes a desired entity.
   * 
   * @since 1.0
   * @param bean
   *          Desired entity.
   */
  void remove(Bean bean);

  /**
   * Removes a desired entity by an id.
   * 
   * @since 1.0
   * @param bean
   *          Desired entity.
   * @param flush
   *          If <code>true</code> the method {@link EntityManager#flush()} will
   *          be called.
   */
  void remove(Bean bean, boolean flush);

  /**
   * Removes a desired entity.
   * 
   * @since 1.0
   * @param key
   *          Entity key.
   */
  void removeById(Key key);

  /**
   * Removes a desired entity by an id.
   * 
   * @since 1.0
   * @param key
   *          Entity key.
   * @param flush
   *          If <code>true</code> the method {@link EntityManager#flush()} will
   *          be called.
   */
  void removeById(Key key, boolean flush);

  /**
   * Brings all the entities.
   * 
   * @since 1.0
   * @return {@link List} with the entities.
   */
  List<Bean> findAll();

  /**
   * Count all the entities.
   * 
   * @since 1.0
   * @return the amount of entities.
   */
  Long countAll();

  /**
   * Brings an entity by its key.
   * 
   * @since 1.0
   * @param id
   *          Entity key.
   * @return Entity.
   */
  Bean findByKey(Key id);

}