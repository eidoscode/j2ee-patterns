package com.eidoscode.javax.ws.rs.core;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Charset Media Type. This class was designed to help and to create constants
 * to be used with the {@link Produces} and {@link Consumes} classes or other
 * classes that expect to receive such kind of parameters.
 * 
 * @author eantonini
 * 
 * @version 1.0
 * @since 1.3.1
 */
public class CharsetMediaType extends MediaType {

  /**
   * "application/json; charset=UTF-8"
   * 
   * @since 1.0
   */
  public final static String APPLICATION_JSON_UTF_8 = "application/json; charset=UTF-8";

  /**
   * "application/json; charset=UTF-8"
   * 
   * @since 1.0
   */
  public final static MediaType APPLICATION_JSON_TYPE_UTF_8 = new MediaType(
      "application", "json", generateMap("charset=UTF-8"));

  /**
   * "application/json; charset=ISO-8859-1"
   * 
   * @since 1.0
   */
  public final static String APPLICATION_JSON_ISO_8859_1 = "application/json; charset=ISO-8859-1";

  /**
   * "application/json; charset=ISO-8859-1"
   * 
   * @since 1.0
   */
  public final static MediaType APPLICATION_JSON_TYPE_ISO_8859_1 = new MediaType(
      "application", "json", generateMap("charset=ISO-8859-1"));

  /**
   * Internal method responsible for generating the MAP to the {@link MediaType}
   * .
   * 
   * @param lines
   *          Lines with the parameter to generate the {@link HashMap}.
   * @return Generated {@link HashMap} with the desired parameters.
   * @since 1.0
   */
  private static final HashMap<String, String> generateMap(String... lines) {
    HashMap<String, String> hashMap = new HashMap<String, String>();
    if (lines != null && lines.length > 0) {
      for (String line : lines) {
        if (line != null && (line = line.trim()).length() > 0) {
          String[] values = line.split("\\=");
          String key = values[0];
          String value = null;
          if (values.length > 1) {
            value = values[1];
          }
          hashMap.put(key, value);
        }
      }
    }
    return hashMap;
  }

}
