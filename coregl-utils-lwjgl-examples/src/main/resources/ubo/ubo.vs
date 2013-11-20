#version 150 core

uniform TransformBlock {
  float off[4];
} transform;

// vertex attributes
in vec2 vVertex;
in vec4 vColor;

// output
out vec4 c;

float off[4] = float[]( 0.1, 0.2, 0.3, 0.4 );

void main() {
  gl_Position = vec4(vVertex.xy + vec2(
  // works
  //  off[gl_InstanceID],
  //  off[gl_InstanceID]
  
  // does not work
    transform.off[gl_InstanceID],
    transform.off[gl_InstanceID]
    ), 0.0, 1.0);
  c = vColor;
}
