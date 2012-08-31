#version 150 core

// vertex attributes
in vec4 aVertex;
in vec4 aColor1;
in vec4 aColor2;
in vec4 aColor3;
in vec4 aColor4;
in vec4 aUV1;
in vec4 aUV2;

// output
out vec4 vColor1;
out vec4 vColor2;
out vec4 vColor3;
out vec4 vColor4;
out vec2 vWidthHeight;
out vec2 vUV1;
out vec2 vUV2;
out vec2 vUV3;
out vec2 vUV4;

// uniform
uniform mat4 uMvp;

void main() {
  gl_Position = uMvp * vec4(aVertex.x, aVertex.y, 0.0, 1.0);
  vColor1 = aColor1;
  vColor2 = aColor2;
  vColor3 = aColor3;
  vColor4 = aColor4;
  vWidthHeight = vec2(aVertex.z, aVertex.w);
  vUV1 = aUV1.xy;
  vUV2 = aUV1.zw;
  vUV3 = aUV2.xy;
  vUV4 = aUV2.zw;
}
