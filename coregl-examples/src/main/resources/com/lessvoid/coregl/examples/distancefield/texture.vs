#version 150 core

uniform float scale;

// vertex attributes
in vec2 vVertex;
in vec2 vUV;

// output
out vec2 uv;

void main() {
  gl_Position = vec4(vVertex.x*scale, vVertex.y*scale, 0.0, 1.0);
  uv = vUV;
}
