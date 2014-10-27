package sojamo.continuum.render;

import java.util.concurrent.Executors;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;

import jsyphon.JSyphonClient;
import jsyphon.JSyphonImage;

import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.Window;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

public class NewtRender {

	private final Window window;
	private final int width;
	private final int height;
	private int x;
	private int y;

	public NewtRender( int theX , int theY , int theWidth , int theHeight ) {

		x = theX;
		y = theY;
		width = theWidth;
		height = theHeight;

		GLCapabilities caps = new GLCapabilities( GLProfile.getDefault( ) );
		caps.setAlphaBits( 4 );
		caps.setSampleBuffers( true );
		caps.setNumSamples( 4 );

		window = NewtFactory.createWindow( caps );
		GLWindow glwindow = GLWindow.create( window );
		Screen screen = window.getScreen( );
		Display display = screen.getDisplay( );
		window.setUndecorated( true );
		window.setPosition( x , y );
		window.setSize( width , height );
		window.setVisible( true );
		final Animator animator = new Animator( );
		animator.setRunAsFastAsPossible( true );
		animator.add( glwindow );

		Executors.newSingleThreadExecutor( ).execute( new Runnable( ) {
			@Override public void run( ) {
				animator.start( );
			}
		} );

		glwindow.addGLEventListener( new SyphonScreen( ) );
	}

	
	class SyphonScreen implements GLEventListener {

		private JSyphonClient client;

		SyphonScreen( ) {
			client = new JSyphonClient( );
		}

		@Override public void display( GLAutoDrawable drawable ) {

			if ( client != null ) {
				if ( client.hasNewFrame( ) ) {

					JSyphonImage img = client.newFrameImageForContext( );

					if ( img != null ) {

						int texId = img.textureName( );
						int texWidth = img.textureWidth( );
						int texHeight = img.textureHeight( );

						GL2 gl2 = prepare( drawable );

						gl2.glColor4f( 1 , 1 , 1 , 1 );
						gl2.glEnable( GL2.GL_TEXTURE_RECTANGLE );
						gl2.glBindTexture( GL2.GL_TEXTURE_RECTANGLE , texId );

						gl2.glBegin( GL2.GL_TRIANGLES );

						gl2.glTexCoord2f( 0 , 0 );
						gl2.glVertex2f( 0 , 0 );
						gl2.glTexCoord2f( texWidth , 0 );
						gl2.glVertex2f( width , 0 );
						gl2.glTexCoord2f( texWidth , texHeight );
						gl2.glVertex2f( width , height );

						gl2.glTexCoord2f( 0 , 0 );
						gl2.glVertex2f( 0 , 0 );
						gl2.glTexCoord2f( 0 , texHeight );
						gl2.glVertex2f( 0 , height );
						gl2.glTexCoord2f( texWidth , texHeight );
						gl2.glVertex2f( width , height );

						gl2.glEnd( );
						gl2.glBindTexture( GL2.GL_TEXTURE_RECTANGLE , 0 );
					}
				}
			}
		}

		private GL2 prepare( GLAutoDrawable drawable ) {
			GL2 gl2 = drawable.getGL( ).getGL2( );
			gl2.glClearColor( 0.0f , 0.0f , 0.0f , 1.0f );
			gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
			gl2.glLoadIdentity( );
			gl2.glOrtho( 0 , width , 0 , height , 1 , -1 );
			return gl2;
		}

		@Override public void dispose( GLAutoDrawable arg0 ) {
			System.out.println( "dispose" );
			client.stop( );
		}

		@Override public void init( GLAutoDrawable arg0 ) {
			client.init( );
			client.setServerName( "continuum-renderer" );
		}

		@Override public void reshape( GLAutoDrawable arg0 , int arg1 , int arg2 , int arg3 , int arg4 ) {
		}

	}

	public static void main( String ... args ) {
		/* Crashes due to problems with disconnecting clients*/
		new NewtRender( 20 , 20 , 100 , 100 );
	}
}
