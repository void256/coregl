package de.lessvoid.coregl;

import java.util.Hashtable;
import java.util.Map;

/**
 * Helper class to manage CoreShaders (programs). This class will cache
 * CoreShader instances and will remember the active CoreShader (if you use the
 * activate() method of this class to activate them).
 *
 * @author void
 */
public class CoreShaderManager {
  private final Map<String, CoreShader> lookup = new Hashtable<String, CoreShader>();
  private CoreShader current;

  /**
   * Register a CoreShader for later use.
   */
  public void register(final String name, final CoreShader coreShader) {
    lookup.put(name, coreShader);
  }

  /**
   * Activate the CoreShader with the given name.
   * 
   * @param name
   *          the shader to activate
   */
  public CoreShader activate(final String name) {
    final CoreShader newShader = lookup.get(name);
    if (current == newShader) {
      return current;
    }
    current = newShader;
    current.activate();
    return current;
  }

  public CoreShader get(final String name) {
    return lookup.get(name);
  }
}
