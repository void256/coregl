#version 150 core

uniform float halfWidth; // (2*r + w) / 2.f
uniform float halfWidthMinus2R; // (2*r + w) / 2.f - 2 * r
uniform vec4 lineColor;

in vec2 uv;

out vec4 color;

void main() {
  float distance = sqrt(uv.x*uv.x + uv.y*uv.y);
  float intensity = 1.0 - smoothstep (halfWidthMinus2R, halfWidth, distance);
  color = vec4(lineColor.r, lineColor.g, lineColor.b, intensity);
}
