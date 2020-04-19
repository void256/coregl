#version 150 core

uniform sampler2D uTexture;

in vec2 uv;
out vec4 color;

void main() {
  float a = texture(uTexture, uv).r;
  float delta = fwidth(distance(uv, vec2(0.0))) * 10;
  float c = smoothstep(0.5 - delta, 0.5, a);
  color = vec4(1.0, 1.0, 1.0, c);
}
