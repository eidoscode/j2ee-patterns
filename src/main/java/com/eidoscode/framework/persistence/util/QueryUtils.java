package com.eidoscode.framework.persistence.util;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Class that contains some utilitaries to work with persistence framework.
 * 
 * @author eantonini
 * @since 1.0
 * @version 1.0
 * 
 */
public class QueryUtils {

  /**
   * Brings a single result based on a {@link TypedQuery}. If there's no result,
   * it will brings a <code>null</code> value.<br/>
   * Remember: The unique treated case is that if no record was found it will
   * returns null. If any other exception was thrown, such as
   * {@link NonUniqueResultException}, it will be threw.
   * 
   * @since 1.0
   * @param query
   *          {@link TypedQuery} to be executed.
   * @return It will returns the desired object or <code>null</code> if no
   *         record was found.
   */
  @SuppressWarnings("unchecked")
  public static <E> E findSingleResult(TypedQuery<E> query) {
    return (E) findSingleResult((Query) query);

  }

  /**
   * Brings a single result based on a regular {@link Query}. If there's no
   * result, it will brings a <code>null</code> value.<br/>
   * Remember: The unique treated case is that if no record was found it will
   * returns null. If any other exception was thrown, such as
   * {@link NonUniqueResultException}, it will be threw.
   * 
   * @since 1.0
   * @param query
   *          {@link Query} to be executed.
   * @return It will returns the desired object or <code>null</code> if no
   *         record was found.
   */
  public static Object findSingleResult(Query query) {
    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}
