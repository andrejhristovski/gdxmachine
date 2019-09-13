#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords;
varying vec2 v_texCoords_normal;

uniform sampler2D u_texture;
uniform sampler2D u_texture_normal;

uniform vec3 light_position;
uniform vec4 light_color;
uniform vec2 resolution;
uniform vec4 ambient_color;
uniform vec3 falloff;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec3 normalMapColor = texture2D(u_texture_normal, v_texCoords_normal).rgb;

    vec3 lightDirection = vec3(light_position.xy - (gl_FragCoord.xy / resolution.xy), light_position.z);
    //Correct for aspect ratio
    lightDirection.x *= resolution.x / resolution.y;

    //Determine distance (used for attenuation) BEFORE we normalize our LightDir
    float D = length(lightDirection);

    //normalize our vectors
    vec3 N = normalize(normalMapColor * 2.0 - 1.0);
    vec3 L = normalize(lightDirection);

    //Pre-multiply light color with intensity
    //Then perform "N dot L" to determine our diffuse term
    vec3 diffuse = (light_color.rgb * light_color.a) * max(dot(N, L), 0.0);

    vec3 ambientColor = ambient_color.rgb * ambient_color.a;

    float attenuation = 1.0 / ( falloff.x + (falloff.y * D) + (falloff.z * D * D));
    vec3 intensity = ambientColor + diffuse * attenuation;
    vec3 finalColor = texColor.rgb * intensity;

    gl_FragColor = vec4(finalColor, texColor.a);
}