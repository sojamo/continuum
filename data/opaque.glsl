#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
varying vec4 vertTexCoord;

void main(void) {
	vec4 rgba = texture2D(texture, vertTexCoord.st);
	gl_FragColor = vec4(rgba.rgb + ( ( 1.0 - rgba.a ) ) * rgba.rgb , 1.0 );
}