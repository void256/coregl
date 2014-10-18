#version 150 core

in vec2 aVertex;
in vec3 aColor;

out vec3 vColor;

uniform mat4 uMvp;

void main() {
  gl_Position = uMvp * vec4(aVertex.xy, 0.0, 1.0);
  vColor = aColor;
}
