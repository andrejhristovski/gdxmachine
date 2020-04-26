attribute vec3 a_position0;
attribute vec4 a_color0;
attribute vec2 a_texCoord0;
attribute vec2 a_texCoord1;

uniform mat4 u_projectionTrans;

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_maskCoords;

void main() {
    v_color = a_color0;
    v_texCoords = a_texCoord0;
    v_maskCoords = a_texCoord1;
    gl_Position =  u_projectionTrans * vec4(a_position0, 1);
}