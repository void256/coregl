#version 150 core

uniform sampler2D uTex;

in vec2 vUV;

out vec4 fColor;

void main() {
  fColor = texture(uTex, vUV);
}
