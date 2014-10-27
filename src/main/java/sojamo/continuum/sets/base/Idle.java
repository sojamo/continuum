package sojamo.continuum.sets.base;

import processing.core.PGraphics;
import sojamo.continuum.Continuum;
import sojamo.continuum.render.AScene;

public class Idle extends AScene {

	public Idle( Continuum theContinuum ) {
		super( theContinuum );
	}

	public void setup( ) {
	}

	public void draw( ) {
		display( g );
	}

	public void display( PGraphics pg ) {
		pg.fill( 0, 40 );
		pg.noStroke( );
		pg.rect( 0 , 0 , 1024 , 768 );
	}

	public void update( ) {
	}

	public void activate( PGraphics g ) {
	}
}
