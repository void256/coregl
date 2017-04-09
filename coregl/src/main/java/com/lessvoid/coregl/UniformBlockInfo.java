package com.lessvoid.coregl;

public class UniformBlockInfo {
  public String name;
  int offset;
  int arrayStride;
  int matrixStride;

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("[");
    builder.append("name: ").append(name).append(", ");
    builder.append("offset: ").append(offset).append(", ");
    builder.append("arrayStride: ").append(arrayStride).append(", ");
    builder.append("matrixStride: ").append(matrixStride);
    builder.append("]");
    return builder.toString();
  }
}