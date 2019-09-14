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

    gl_FragColor = vec4(normalMapColor, texColor.a);
}