attribute vec3 a_position;
attribute vec4 a_color_ambient;
attribute vec2 a_texCoord;

uniform mat4 u_projectionTrans;

varying vec4 v_ambient;
varying vec2 v_texCoords;

void main()
{
    v_ambient = a_color_ambient;
    v_texCoords = a_texCoord;
    gl_Position =  u_projectionTrans * vec4(a_position, 1);
}