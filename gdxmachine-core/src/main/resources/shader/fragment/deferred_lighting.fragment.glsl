#ifdef GL_ES
#define LOWP lowp
precision mediump float;
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
uniform sampler2D u_texture_diffuse;
uniform sampler2D u_texture_bump;

uniform vec4 ambient_light_color;
uniform vec4 viewport;

uniform Light light[2];



void main()
{
    vec4 diffuseColor = texture2D(u_texture_diffuse, v_texCoords);
    vec3 bumpColor = texture2D(u_texture_bump, v_texCoords).rgb;
    vec3 ambient = ambient_light_color.rgb * .2;
    vec3 normal = normalize(bumpColor * 2.0 - 1.0);
    vec3 final = vec3(0);

    for (int i = 0; i < 2; i++) {
        vec4 light_pos = vec4(light[i].position, 1) * u_projectionTrans;
        vec2 light_frag_pos = light_pos.xy * (viewport.zw) + (viewport.zw / 2.0);
        vec3 light_direction = vec3(light_frag_pos - gl_FragCoord.xy, light[i].position.z);

        float distance = 1.0 / (length(light_direction)) / viewport.z;
        vec3 light_normal = normalize(light_direction);
        vec3 diffuse = (light[i].color.rgb * light[i].color.a) * max(dot(normal, light_normal), .0);
        float attenuation = 1.0 / ( .4 + (3.0 * distance) + (0.1 * distance * distance));
        vec3 intensity = ambient + diffuse * attenuation;
        final +=  diffuseColor.rgb * intensity;
    }

    gl_FragColor = vec4(final, 1);
}