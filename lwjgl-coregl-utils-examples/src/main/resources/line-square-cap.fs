#version 150 core

uniform float halfWidth; // (2*r + w) / 2.f
uniform float halfWidthMinus2R; // (2*r + w) / 2.f - 2 * r
uniform float r2;// (2*r)
uniform vec4 lineColor;

in vec2 uv;

out vec4 color;

void main() {
  float distanceY = sqrt(uv.y*uv.y);
  float intensityY = 1.0 - smoothstep(halfWidthMinus2R, halfWidth, distanceY);

  float distanceX = sqrt(uv.x*uv.x);
  float intensityX = 1.0 - smoothstep(halfWidthMinus2R, halfWidth, distanceX);

  color = vec4(lineColor.r, lineColor.g, lineColor.b, intensityX*intensityY);
}
