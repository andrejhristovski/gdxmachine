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
    gl_FragColor = vec4(vec3(dot(texColor.rgb, vec3(0.299, 0.587, 0.114))), texColor.a);
}