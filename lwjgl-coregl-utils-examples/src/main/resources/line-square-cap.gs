#version 150

layout (lines) in;
layout (triangle_strip, max_vertices = 8) out;

uniform mat4 uMvp;
uniform float totalWidth;// (2*r + w)
uniform float halfWidth; // (2*r + w) / 2.f
uniform float r2;// (2*r)

out vec2 uv;

void main(void) {
  float x0 = gl_in[0].gl_Position.x;
  float y0 = gl_in[0].gl_Position.y;
  float x1 = gl_in[1].gl_Position.x;
  float y1 = gl_in[1].gl_Position.y;

  vec2 start = vec2( x0, y0 );
  vec2 end = vec2( x1, y1 );
  vec2 diffN = normalize(end - start) * halfWidth;
  vec2 flip = vec2(-diffN.y, diffN.x);

  vec2 p0 = start - diffN + flip;
  vec2 p1 = start - diffN - flip;
  vec2 p2 = start + flip;
  vec2 p3 = start - flip;
  vec2 p4 = end + flip;
  vec2 p5 = end - flip;
  vec2 p6 = end + diffN + flip;
  vec2 p7 = end + diffN - flip;

  gl_Position = uMvp * vec4( p0.x, p0.y, 0., 1.);
  uv = vec2(-halfWidth, halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p1.x, p1.y, 0., 1.);
  uv = vec2(-halfWidth, -halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p2.x, p2.y, 0., 1.);
  uv = vec2(0.0, halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p3.x, p3.y, 0., 1.);
  uv = vec2(0.0, -halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p4.x, p4.y, 0., 1.);
  uv = vec2(0.0, halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p5.x, p5.y, 0., 1.);
  uv = vec2(0.0, -halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p6.x, p6.y, 0., 1.);
  uv = vec2(halfWidth, halfWidth);
  EmitVertex();

  gl_Position = uMvp * vec4( p7.x, p7.y, 0., 1.);
  uv = vec2(halfWidth, -halfWidth);
  EmitVertex();

  EndPrimitive();
}
