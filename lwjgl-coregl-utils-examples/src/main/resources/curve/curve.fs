#version 150 core

in vec2 t;
out vec4 color;
uniform float r; 

void main() {
    float x = t.x;
    float y = t.y;
    float v = x*x + y*y;
    float width = 0.025;
    float r2 = (r-width)*(r-width);
    float r3 = (r+width)*(r+width);

    if ((v > r2) && (v < r3)) {
        float middle = r*r;
        float diff = abs(v - middle);
        float a = diff / (r3 - v);
        color = vec4(1.0, 1.0, 1.0, 0.8);
    } else {
        color = vec4(t.x, t.y, 0.0, 0.5);
    }
}
