#version 150 core

uniform sampler2D uTex;

in vec2 vUV;
in vec3 vVertex;
in vec3 vNormal;

out vec4 fColor;

uniform mat4 uModelViewMatrix;
uniform mat4 uModelViewProjectionMatrix;
uniform mat3 uNormalMatrix;

uniform vec4 uLightPosition;
uniform vec3 uKd;
uniform vec3 uLd;

void main() {
    vec3 n = normalize(vNormal);
    vec3 s = normalize(vec3(uLightPosition) - vVertex);
    vec3 v = normalize(vec3(-vVertex));

    vec3 lightIntensity = uLd * uKd * max(dot(s, n), 0.0);

    fColor = vec4(lightIntensity, 1.0) * 3 * texture(uTex, vUV);
    fColor = vec4(lightIntensity, 1.0);
}
