package com.eidoscode.framework.persistence.bo;

import java.io.Serializable;
import java.util.Collection;
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
   * Saves a list of the desired entities. It means it will persist a new entity
   * or merge an existent entity.
   * 
   * @since 1.3
   * @param beans
   *          desired entities.
   * @return entities stored.
   */
  public <E extends Collection<Bean>> E save(E beans);

  /**
   * Saves a list of the desired entities. It means it will persist a new entity
   * or merge an existent entity.
   * 
   * @since 1.3
   * @param beans
   *          desired entities.
   * @param flush
   *          If <code>true</code> the method {@link EntityManager#flush()} will
   *          be called.
   * @return entities stored.
   */
  public <E extends Collection<Bean>> E save(E beans, boolean flush);

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
  boolean removeById(Key key);

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
  boolean removeById(Key key, boolean flush);

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
  public void removeById(Collection<Key> keys);

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
  public void removeById(Collection<Key> keys, boolean flush);

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