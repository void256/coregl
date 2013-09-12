#version 150 core

uniform float sx; // r / ((2*r + w) / 2);

noperspective in vec3 e0;
noperspective in vec3 e1;
noperspective in vec3 e2;
noperspective in vec3 e3;

out vec4 color;

void main() {
  vec3 pos = vec3(gl_FragCoord.x, 768-gl_FragCoord.y, 1);
  vec4 d = vec4(
    dot(e0, pos),
    dot(e1, pos),
    dot(e2, pos),
    dot(e3, pos));

  if (d.x < 0 || d.y < 0 || d.z < 0 || d.w < 0)
    discard;

  float d1 = min(d.x, d.z);
  float d2 = min(d.y, d.w);
  
  // 1. original code - not used because we don't have a lookup table
  //
  //  color.w = tex1D(table, min(d.x, d.z)).x *
  //            tex1D(table, min(d.y, d.w)).x;

  // 2. use exp2 function to calculate intensity from the distance
  //    this works well but not for wide lines (> 5 px or so)
  // I only get beams with a kinda linear falloff to black
  //color = vec4(1., 1., 1., (1.0 - exp2(-2*d1*d1)) * (1.0 - exp2(-2*d2*d2)));

  // 3. my custom version ;) in s is stored the distance where
  //    the edge is. this is the area between the actual width
  //    of the line and the "filter" radius. I smoothstep this
  //    edge area which yields a smooth edge and a plain colored
  //    line area and works well for wider lines.
  color = vec4(1.0, 1.0, 1.0, smoothstep(0.0, sx, d1) * smoothstep(0.0, sx, d2));
}
