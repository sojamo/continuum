package sojamo.continuum;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;
import sojamo.continuum.render.AScene;
import sojamo.continuum.sets.base.Idle;
import codeanticode.syphon.SyphonServer;

public class Graphics {

	private final Continuum continuum;
	private PShader opaque;
	final PGraphics current;
	final PGraphics preview;
	final PGraphics audio;
	final SyphonServer syphon;
	private AScene scene;

	public Graphics( Continuum theContinuum ) {
		continuum = theContinuum;
		opaque = continuum.loadShader( continuum.dataPath("opaque.glsl") );
		current = continuum.createGraphics( Continuum.RENDER_WIDTH, Continuum.RENDER_HEIGHT , continuum.P3D );
		preview = continuum.createGraphics( Continuum.RENDER_WIDTH , Continuum.RENDER_HEIGHT , continuum.P3D );
		audio = continuum.createGraphics( 512 , 100 , continuum.P3D );
		syphon = new SyphonServer( continuum , "continuum-renderer" );
		scene = new Idle( continuum );
	}

	public void addScene( String theName , AScene theScene ) {

	}

	public void setScene( Scenes theScenes , String theName ) {
		if ( theScenes.get( theName ) != null ) {
			scene = theScenes.get( theName );
			scene.activate( current );
		}
	}

	public void update( ) {
		current.beginDraw( );
		PApplet app = ( PApplet ) scene;
		app.frameCount = continuum.frameCount; // force frameCount to update which is usually done inside PApplet.handleDraw();
		app.mouseX = continuum.mouseX;
		app.mouseY = continuum.mouseY;
		app.pmouseX = continuum.pmouseX;
		app.pmouseY = continuum.pmouseY;
		app.g = current;
		app.draw( );
		current.filter( opaque );
		current.endDraw( );
		syphon.sendImage( current );
	}

}
