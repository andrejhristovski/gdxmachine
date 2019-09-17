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
uniform int normal_exist;

void main()
{
    // vec4 greyColor = vec4(vec3(dot(texColor.rgb, vec3(0.299, 0.587, 0.114))), texColor.a);
    vec4 texColor = texture2D(u_texture, v_texCoords);
    vec4 normalColor = vec4(.5, .5, 1, texColor.a);
    if (normal_exist == 1) {
        normalColor = vec4(texture2D(u_texture_normal, v_texCoords_normal).rgb, texColor.a);
    }
    gl_FragColor = normalColor;

}