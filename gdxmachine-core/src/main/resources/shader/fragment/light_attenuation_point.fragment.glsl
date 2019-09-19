varying vec2 v_size;
varying vec2 v_position_relative;
varying float v_generic_intensity;

void main()
{
    vec2 light_direction = v_position_relative / (v_size / 2.0);
    float distance = length(light_direction);
    float attenuation = 1.0 / (0.8 * (1.0 - v_generic_intensity) + 0.2 + (0.5 * distance) + (10.0 * distance * distance));
    gl_FragColor = vec4(1, 1, 1, attenuation);
}