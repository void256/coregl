#version 150 core

out vec4 color;
in vec3 vColor;

void main() {
  color.rgb = vColor;
  color.a = 1.0;
}
