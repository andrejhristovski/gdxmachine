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
        vec3 sepiaColor = vec3(dot(texture.rgb, vec3(0.299, 0.587, 0.114))) * vec3(1.2, 1.0, 0.8);
        gl_FragColor = vec4(mix(texture.rgb, sepiaColor, 0.75), texture.a * mask.a) * v_color;
    } else {
        gl_FragColor = texture;
    }
}