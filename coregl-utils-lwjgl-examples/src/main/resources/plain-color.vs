#version 150 core

uniform vec2 uOffset;

in vec2 aVertex;
in vec4 aColor;

out vec4 vColor;

uniform mat4 uMvp;

void main() {
  gl_Position = uMvp * vec4(aVertex.xy + uOffset.xy, 0.0, 1.0);
  vColor = aColor;
}
