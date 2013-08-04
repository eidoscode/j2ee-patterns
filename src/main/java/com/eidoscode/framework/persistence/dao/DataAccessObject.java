package com.eidoscode.framework.persistence.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.eidoscode.framework.persistence.model.Model;

/**
 * Main interface of a Data Access Object layer. This layer is used as a main
 * definition of what a DAO to a specific Bean should have.
 * 
 * @author eantonini
 * @version 1.3
 * @since 1.0
 * @param <Key>
 *          Key of the Bean. It must implements the {@link Serializable}
 *          interface.
 * @param <Bean>
 *          The Bean that is going to be persisted. This bean must implements
 *          the interface {@link Model}.
 */
public interface DataAccessObject<Key extends Serializable, Bean extends Model<Key>> {

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
   * Removes a desired entity.
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