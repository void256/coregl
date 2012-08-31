#version 150 core

uniform sampler2D uTex;

in vec4 gColor;
in vec2 gUV;

out vec4 color;

void main() {
  color = gColor * texture(uTex, gUV);
}
