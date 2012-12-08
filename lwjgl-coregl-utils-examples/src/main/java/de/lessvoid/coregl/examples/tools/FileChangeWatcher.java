package de.lessvoid.coregl.examples.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileChangeWatcher {
  private List<Value> refresh = new ArrayList<Value>();

  public void add(final File file, final Callback callback) {
    refresh.add(new Value(file, callback));
  }

  public void poll() {
    for (Value value : refresh) {
      value.poll();
    }
  }

  public static interface Callback {
    void refresh(File file);
  }

  public static class Value {
    private File file;
    private Callback callback;
    private long lastModified;

    public Value(final File file, final Callback callback) {
      this.callback = callback;
      this.file = file;
      this.lastModified = file.lastModified();
    }

    public void poll() {
      if (file.lastModified() != lastModified) {
        refresh();
      }
    }

    private void refresh() {
      lastModified = file.lastModified();
      callback.refresh(file);
    }
  }
}
