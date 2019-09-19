varying vec2 v_size;
varying vec2 v_position_relative;

void main()
{
    vec2 light_direction = -v_position_relative / (v_size / 2.0);
    vec3 normal = (normalize(vec3(light_direction, 0.075)) + 1.0) / 2.0;
    float distance = 1.0 - length(light_direction);
    gl_FragColor = vec4(normal.x, normal.y, normal.z, distance * 2.0);
}