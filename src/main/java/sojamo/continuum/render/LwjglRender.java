package sojamo.continuum.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

import jsyphon.JSyphonClient;
import jsyphon.JSyphonImage;

public class LwjglRender {

	private JSyphonClient client;

	public LwjglRender( ) throws LWJGLException {
		client = new JSyphonClient( );
		client.init( );
		System.setProperty( "org.lwjgl.opengl.Window.undecorated" , "true" );
	}

	public void start( ) {
		try {
			Display.setDisplayMode( new DisplayMode( 1024 , 768 ) );
			Display.create( );
			Display.setTitle( "ClientTest" );
		} catch ( LWJGLException e ) {
			e.printStackTrace( );
			System.exit( 0 );
		}

		// client.setApplicationName("PGraphics_opaque_syphon_c");
		client.setServerName( "Continuum Renderer" );
		while ( !Display.isCloseRequested( ) ) {
			try {
				Thread.sleep( 5 );
			} catch ( Exception e ) {

			}
			// This copies the back buffer of LWJGL

			if ( !client.hasNewFrame( ) )
				continue;

			GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );

			JSyphonImage img = client.newFrameImageForContext( );

			int texId = img.textureName( );
			int texWidth = img.textureWidth( );
			int texHeight = img.textureHeight( );

			// Old-style immediate mode rendering...
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glLoadIdentity( );
			GL11.glOrtho( 0 , 800 , 0 , 600 , 1 , -1 );
			GL11.glMatrixMode( GL11.GL_MODELVIEW );

			GL11.glColor4f( 1 , 1 , 1 , 1 );
			GL11.glEnable( GL31.GL_TEXTURE_RECTANGLE );
			GL11.glBindTexture( GL31.GL_TEXTURE_RECTANGLE , texId );

			GL11.glBegin( GL11.GL_TRIANGLES );

			GL11.glTexCoord2f( 0 , 0 );
			GL11.glVertex2f( 0 , 0 );
			GL11.glTexCoord2f( texWidth , 0 );
			GL11.glVertex2f( 800 , 0 );
			GL11.glTexCoord2f( texWidth , texHeight );
			GL11.glVertex2f( 800 , 600 );

			GL11.glTexCoord2f( 0 , 0 );
			GL11.glVertex2f( 0 , 0 );
			GL11.glTexCoord2f( 0 , texHeight );
			GL11.glVertex2f( 0 , 600 );
			GL11.glTexCoord2f( texWidth , texHeight );
			GL11.glVertex2f( 800 , 600 );

			GL11.glEnd( );

			// Always call this so LWJGL can swap buffers, etc.
			Display.update( );
		}

		client.stop( );
		Display.destroy( );
	}

	/**
	 * @param args
	 * @throws LWJGLException
	 */
	public static void main( String[] args ) throws LWJGLException {
		LwjglRender t = new LwjglRender( );
		t.start( );
	}
}
