attribute vec3 a_position;
attribute vec3 a_position_relative;
attribute vec3 a_size;

uniform mat4 u_projectionTrans;

varying vec2 v_size;
varying vec2 v_position_relative;

void main()
{
    v_size = (u_projectionTrans * vec4(a_size, 1)).xy;
    v_position_relative = (u_projectionTrans * vec4(a_position_relative, 1)).xy;
    gl_Position =  u_projectionTrans * vec4(a_position, 1);

}