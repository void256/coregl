#version 150 core

in vec3 aVertex;
in vec2 aUV;
in vec3 aNormal;

out vec2 vUV;

uniform mat4 uModelViewProjection; 

void main() {
  gl_Position = uModelViewProjection * vec4(aVertex, 1.0);
  vUV = aUV;
}
