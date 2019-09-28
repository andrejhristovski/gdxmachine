#ifdef GL_ES
#define LOWP lowp
precision highp float;
#else
#define LOWP
#endif

struct Light {
    vec3 position;
    vec4 color;
};

varying LOWP vec4 v_ambient;
varying vec2 v_texCoords;

uniform mat4 u_projectionTrans;
uniform mat4 u_projectionTrans_inverse;
uniform sampler2D u_texture_diffuse;
uniform sampler2D u_texture_bump;

uniform vec4 viewport;

uniform vec4 ambient_light_color;
uniform Light light[64];
uniform int light_list_size;

void main()
{
    vec4 diffuseColor = texture2D(u_texture_diffuse, v_texCoords);
    vec3 bumpColor = texture2D(u_texture_bump, v_texCoords).rgb;
    vec3 ambient = ambient_light_color.rgb * .2;
    vec3 normal = normalize(bumpColor * 2.0 - 1.0);
    vec3 final = vec3(0);

    vec4 ndc_position;
    ndc_position.xy = ((2.0 * gl_FragCoord.xy) - (2.0 * viewport.xy)) / (viewport.zw) - 1.0;
    ndc_position.z = (2.0 * gl_FragCoord.z - gl_DepthRange.near - gl_DepthRange.far) / (gl_DepthRange.far - gl_DepthRange.near);
    ndc_position.w = 1.0;
    vec4 clip_position = ndc_position / gl_FragCoord.w;
    vec4 frag_world_position = u_projectionTrans_inverse * clip_position;

    for (int i = 0; i < light_list_size; i++) {
        vec4 light_pos = vec4(light[i].position, 1);
        vec4 light_color = light[i].color;
        vec3 light_direction = vec3(light_pos.xy - frag_world_position.xy, light[i].position.z);

        float distance = length(light_direction);
        vec3 light_normal = normalize(light_direction);

        vec3 diffuse = (light_color.rgb) * max(dot(normal, light_normal), .0);
        float attenuation = clamp( 70.0 / distance, 0.0, 1.0);
        vec3 intensity = ambient + diffuse * attenuation;
        final +=  diffuseColor.rgb * intensity;
    }

    gl_FragColor = vec4(final, 1);
}