attribute vec3 a_position0;
attribute vec4 a_color0;
attribute vec2 a_texCoord0;

uniform mat4 u_projectionTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

void main()
{
    v_color = a_color0;
    v_texCoords = a_texCoord0;
    gl_Position =  u_projectionTrans * vec4(a_position0, 1);
}