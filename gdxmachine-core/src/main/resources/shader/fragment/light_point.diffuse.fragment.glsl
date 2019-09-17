#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_size;
varying vec2 v_position_relative;
varying float v_generic_radius;
varying float v_generic_intensity;

void main()
{
    vec2 light_direction = v_position_relative / (v_size / 12.0);
    vec3 normal = normalize((vec3(light_direction, 0.075) + 1.0) / 2.0);
    gl_FragColor = vec4(normal.x, normal.y, normal.z, .8);
}