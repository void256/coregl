#version 150 core

in vec2 aVertex;
in vec3 aStarPos;

out float vColor;

uniform mat4 uModelViewProjection; 

void main() {
  gl_Position = uModelViewProjection * vec4(
    aVertex.x + aStarPos.x,
    aVertex.y + aStarPos.y,
          0.0 + aStarPos.z,
                       1.0);
  float z = gl_Position.z / gl_Position.w;
  vColor = 1.0 - z;
}
