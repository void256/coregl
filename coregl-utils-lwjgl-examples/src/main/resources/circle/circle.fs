#version 150 core

in vec2 t;
out vec4 color;

// param.x = radius
// param.y = angle
uniform vec2 param;

void main() {
    float radius = param.x;
    float angle = param.y;

    float dist = distance(t, vec2(0.0, 0.0));
    float delta = fwidth(dist);
    float alpha = 1 - (smoothstep(radius - delta*2, radius, dist) *
                  (1 - smoothstep(radius + 0.2, radius + delta*2 + 0.2, dist)));

    float angleMix = smoothstep(angle - delta*2, angle, atan(t.y, t.x));
    color = mix(mix(vec4(1.0, 1.0, 1.0, 1.0), vec4(0.0, 0.0, 0.0, 0.0), alpha), vec4(0.0, 0.0, 0.0, 0.0), angleMix);
}
