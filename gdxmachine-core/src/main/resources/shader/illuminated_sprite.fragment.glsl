#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_texCoords_normal_map;

uniform sampler2D u_texture;
uniform sampler2D u_texture_normal_map;

uniform vec3 light_position;
uniform vec4 light_color;
uniform vec2 resolution;
uniform vec4 ambient_color;
uniform vec3 falloff;

void main()
{
    //RGBA of our diffuse color
    vec4 texColor = texture2D(u_texture, v_texCoords);
    //RGB of our normal map
    vec3 normalMapColor = texture2D(u_texture_normal_map, v_texCoords_normal_map).rgb;

    vec3 lightDirection = vec3(light_position.xy - (gl_FragCoord.xy / resolution.xy), light_position.z);
    //Correct for aspect ratio
    lightDirection.x *= resolution.x / resolution.y;

    //Determine distance (used for attenuation) BEFORE we normalize our LightDir
    float distance = length(lightDirection);

    //normalize our vectors
    vec3 N = normalize(normalMapColor * 2.0 - 1.0);
    vec3 L = normalize(lightDirection);

    //Pre-multiply light color with intensity
    //Then perform "N dot L" to determine our diffuse term
    vec3 diffuse = (light_color.rgb * light_color.a) * max(dot(N, L), 0.0);

    vec3 ambientColor = ambient_color.rgb * ambient_color.a;

    float attenuation = 1.0 / ( falloff.x + (falloff.y * distance) + (falloff.z * distance * distance));
    vec3 intensity = ambientColor + diffuse * attenuation;
    vec3 finalColor = texColor.rgb * intensity;

    gl_FragColor = v_color * vec4(finalColor, texColor.a);
}