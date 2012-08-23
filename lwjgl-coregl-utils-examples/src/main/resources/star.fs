#version 150 core

out vec4 fColor;
in float vColor;

void main() {
  fColor = vec4(1.0, 1.0, 1.0, vColor);
}
