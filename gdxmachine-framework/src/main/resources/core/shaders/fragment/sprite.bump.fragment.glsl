#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

varying LOWP vec4 v_color;
varying vec2 v_texCoords;
varying vec2 v_maskCoords;

uniform sampler2D u_texture;
uniform sampler2D u_texture_mask;
uniform int u_texture_exists;

void main() {
    vec4 texture = texture2D(u_texture, v_texCoords);
    vec4 mask = texture2D(u_texture_mask, v_maskCoords);

    if (u_texture_exists == 1) {
        gl_FragColor.rgb = texture.rgb;
        gl_FragColor.a = texture.a * mask.a * v_color.a;
    } else {
        gl_FragColor = texture;
    }
}