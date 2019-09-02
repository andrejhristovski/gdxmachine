#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
uniform float u_intensity;

void main()
{
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec3 threshold = (1.0 - abs(texColor.rgb - v_color.rgb)) / 1.0;
    float similar = (threshold.r + threshold.g + threshold.b) / 3.0;
    if(similar > u_intensity) {
        gl_FragColor = texColor;
    } else {
        gl_FragColor = vec4(vec3(dot(texColor.rgb, vec3(0.299, 0.587, 0.114))), texColor.a);
    }
}