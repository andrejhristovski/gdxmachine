attribute vec3 a_position;
attribute vec3 a_position_size;
attribute vec3 a_position_relative;
attribute float a_generic_radius;
attribute float a_generic_intensity;
attribute vec4 a_color;

uniform mat4 u_projectionTrans;

varying vec4 v_color;
varying vec2 v_size;
varying vec2 v_position_relative;
varying float v_generic_radius;
varying float v_generic_intensity;

void main()
{
    v_color = a_color;
    v_generic_radius = a_generic_radius;
    v_generic_intensity = a_generic_intensity;
    v_size = (u_projectionTrans * vec4(a_position_size, 1)).xy;
    v_position_relative = (u_projectionTrans * vec4(a_position_relative, 1)).xy;
    gl_Position =  u_projectionTrans * vec4(a_position, 1);

}