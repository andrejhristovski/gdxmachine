attribute vec3 a_position;
attribute vec2 a_texCoord_diffuse;
attribute vec2 a_texCoord_depth;

uniform mat4 u_projectionTrans;

varying vec2 v_texCoords_diffuse;
varying vec2 v_texCoords_depth;

void main()
{
    v_texCoords_diffuse = a_texCoord_diffuse;
    v_texCoords_depth = a_texCoord_depth;
    gl_Position =  u_projectionTrans * vec4(a_position, 1);
}