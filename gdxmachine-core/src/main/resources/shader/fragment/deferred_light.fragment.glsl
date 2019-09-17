#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying vec2 v_texCoords_diffuse;
varying vec2 v_texCoords_depth;

uniform sampler2D u_texture_diffuse;
uniform sampler2D u_texture_depth;

uniform vec3 light_position;
uniform vec4 light_color;
uniform vec2 resolution;
uniform vec4 ambient_color;
uniform vec3 falloff;

void main()
{
    vec4 diffuseColor = texture2D(u_texture_diffuse, v_texCoords_diffuse);
    vec3 depthColor = texture2D(u_texture_depth, v_texCoords_depth).rgb;

    gl_FragColor = vec4(depthColor, diffuseColor.a);
}