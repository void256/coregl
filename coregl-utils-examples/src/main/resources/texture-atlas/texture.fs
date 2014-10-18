#version 150 core

uniform sampler2D uTexture;
in vec2 vUV;
out vec4 color;

void main() {
  color = texture(uTexture, vUV);
}
