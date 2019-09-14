attribute vec3 a_position;
attribute vec2 a_texCoord;
attribute vec2 a_texCoord_normal;

uniform mat4 u_projectionTrans;

varying vec2 v_texCoords;
varying vec2 v_texCoords_normal;

void main()
{
    v_texCoords = a_texCoord;
    v_texCoords_normal = a_texCoord_normal;
    gl_Position =  u_projectionTrans * vec4(a_position, 1);
}