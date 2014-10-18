#version 150 core

in vec2 vVertex;

void main() {
  gl_Position = vec4(vVertex.x, vVertex.y, 0.0, 1.0);
}
