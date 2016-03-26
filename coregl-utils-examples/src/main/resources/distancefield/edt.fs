#version 150 core

uniform sampler2D uTexture;

in vec2 uv;
out vec4 color;

#define MAX 128
#define MAX_H (MAX / 2)

float infinity = 1.0 / 0.0;
float max = sqrt(MAX_H*MAX_H + MAX_H*MAX_H);

void main() {
  ivec2 currentPos = ivec2(uv);
  float current = texelFetch(uTexture, currentPos, 0).x;

  float target;
  if (current == 0.0) {
    target = 1.0;
  } else {
    target = 0.0;
  }
  float d;
  float result = max;
  for (int y=-MAX_H; y<MAX_H; y++) {
    for (int x=-MAX_H; x<MAX_H; x++) {
      ivec2 p = ivec2(uv.x + x, uv.y + y);
      float probe = texelFetch(uTexture, p, 0).x;
      if (probe == target) {
        d = distance(uv, p);
        if (d < result) {
          result = d;
        }
      }
    }
  }

  if (target == 0.0) {
    result = -result;
  }

  float v = 1.0 - ((result / max) + 1.0) / 2.0;
  color = vec4(v, v, v, 1.0);
}
