#version 150

layout (lines) in;
layout (triangle_strip, max_vertices = 4) out;

uniform mat4 uMvp;
uniform float totalWidth;// (2*r + w)
uniform float halfWidth; // (2*r + w) / 2.f

noperspective out vec3 e0;
noperspective out vec3 e1;
noperspective out vec3 e2;
noperspective out vec3 e3;

void main(void) {
  float x0 = gl_in[0].gl_Position.x;
  float y0 = gl_in[0].gl_Position.y;
  float x1 = gl_in[1].gl_Position.x;
  float y1 = gl_in[1].gl_Position.y;

  float dx = x1-x0;
  float dy = y1-y0;
  float dxi = x0-x1;
  float dyi = y0-y1;
  float x0y1 = x0*y1;
  float x1y0 = x1*y0;
  float x0x0 = x0*x0;
  float y0y0 = y0*y0;
  float x1x1 = x1*x1;
  float y1y1 = y1*y1;
  float x0x1 = x0*x1;
  float y0y1 = y0*y1;
  float x0x1_y0y1 = x0x1 + y0y1;
  float k = 2.0 / (totalWidth * sqrt(dxi*dxi + dyi*dyi));
  e0 = vec3( k*dyi, k*dx,  1 + k*(x0y1 - x1y0));
  e1 = vec3( k*dx,  k*dy,  1 + k*(x0x0 + y0y0 - x0x1_y0y1));
  e2 = vec3( k*dy,  k*dxi, 1 + k*(x1y0 - x0y1));
  e3 = vec3( k*dxi, k*dyi, 1 + k*(x1x1 + y1y1 - x0x1_y0y1));

  vec2 start = vec2( x0, y0 );
  vec2 end = vec2( x1, y1 );
  vec2 diffN = normalize(end - start) * halfWidth;
  start = start - diffN;
  end = end + diffN;

  vec2 flip = vec2(-diffN.y, diffN.x);
  vec2 p0 = start + flip;
  vec2 p1 = end + flip;
  vec2 p2 = end - flip;
  vec2 p3 = start - flip;
        
  gl_Position = uMvp * vec4( p0.x, p0.y, 0., 1.);
  EmitVertex();
  gl_Position = uMvp * vec4( p1.x, p1.y, 0., 1.);
  EmitVertex();
  gl_Position = uMvp * vec4( p3.x, p3.y, 0., 1.);
  EmitVertex();
  gl_Position = uMvp * vec4( p2.x, p2.y, 0., 1.);
  EmitVertex();
  EndPrimitive();
}
