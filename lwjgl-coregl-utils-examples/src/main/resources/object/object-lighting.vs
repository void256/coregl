#version 150 core

in vec3 aVertex;
in vec2 aUV;
in vec3 aNormal;

out vec2 vUV;
out vec3 vVertex;
out vec3 vNormal;

uniform mat4 uModelViewProjectionMatrix;
uniform mat4 uModelViewMatrix;
uniform mat3 uNormalMatrix;

void main() {
    gl_Position = uModelViewProjectionMatrix * vec4(aVertex, 1.0);
    vUV = aUV;
    vVertex = vec3(uModelViewMatrix * vec4(aVertex, 1.0));
    vNormal = normalize(uNormalMatrix * aNormal);
}
