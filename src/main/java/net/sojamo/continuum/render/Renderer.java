package net.sojamo.continuum.render;

import processing.core.PApplet;
import processing.core.PGraphics;
import codeanticode.syphon.SyphonClient;

public class Renderer extends PApplet {

	private SyphonClient client;
	private PGraphics canvas;

	public void setup( ) {
		size( 1024 , 768 , P3D );
		client = new SyphonClient( this , "" , "continuum-renderer" );
	}

	public void draw( ) {
		if ( client.available( ) ) {
			canvas = client.getGraphics( canvas );
			image( canvas , 0 , 0 , width , height );
		}
	}

	public void init( ) {
		frame.removeNotify( );
		frame.setUndecorated( true );
		frame.addNotify( );
		super.init( );
	}

	public static void main( String args[] ) {
		PApplet.main( new String[] { Renderer.class.getName( ) } );
	}

}
