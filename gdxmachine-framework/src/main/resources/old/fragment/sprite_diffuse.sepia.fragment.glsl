#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec3 sepiaColor = vec3(dot(texColor.rgb, vec3(0.299, 0.587, 0.114))) * vec3(1.2, 1.0, 0.8);
    gl_FragColor = vec4(mix(texColor.rgb, sepiaColor, 0.75), texColor.a) * v_color;
}