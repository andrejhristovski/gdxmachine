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
    texColor.rgb = 1.0 - texColor.rgb;
    texColor.a = texColor.a * v_color.a;
    gl_FragColor = texColor;
}