#version 150

layout (points) in;
layout (triangle_strip, max_vertices = 4) out;

in vec4 vColor1[];
in vec4 vColor2[];
in vec4 vColor3[];
in vec4 vColor4[];
in vec2 vWidthHeight[];
in vec2 vUV1[];
in vec2 vUV2[];
in vec2 vUV3[];
in vec2 vUV4[];

out vec4 gColor;
out vec2 gUV;

uniform float uWidth;
uniform float uHeight;

float convertX(float x) {
    return x / uWidth * 2.f;
}

float convertY(float y) {
    return y / uHeight * 2.f;
}

void main(void) {
    int i;
    for (i = 0; i < gl_in.length(); i++) {
        gl_Position = gl_in[i].gl_Position;
        gColor = vColor1[i];
        gUV = vUV1[i];
        EmitVertex();

        gl_Position = gl_in[i].gl_Position;
        gl_Position.x += convertX(vWidthHeight[i].x);
        gColor = vColor2[i];
        gUV = vUV2[i];
        EmitVertex();

        gl_Position = gl_in[i].gl_Position;
        gl_Position.y -= convertY(vWidthHeight[i].y);
        gColor = vColor3[i];
        gUV = vUV3[i];
        EmitVertex();

        gl_Position = gl_in[i].gl_Position;
        gl_Position.x += convertX(vWidthHeight[i].x);
        gl_Position.y -= convertY(vWidthHeight[i].y);
        gColor = vColor4[i];
        gUV = vUV4[i];
        EmitVertex();
    }
    EndPrimitive();
}