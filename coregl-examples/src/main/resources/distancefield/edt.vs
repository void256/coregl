#version 150 core

// vertex attributes
in vec2 vVertex;
in vec2 vUV;

// output
out vec2 uv;

void main() {
  gl_Position = vec4(vVertex.x, vVertex.y, 0.0, 1.0);
  uv = vUV;
}
