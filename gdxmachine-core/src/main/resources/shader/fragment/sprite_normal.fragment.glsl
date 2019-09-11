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
    float alpha = texture2D(u_texture, v_texCoords).a;
    if (normal_exist == 0) {
        gl_FragColor = vec4(.5, .5, 1, alpha);
    } else {
        vec4 normalColor = texture2D(u_texture_normal, v_texCoords_normal);
        gl_FragColor = vec4(normalColor.rgb, alpha);
    }
}