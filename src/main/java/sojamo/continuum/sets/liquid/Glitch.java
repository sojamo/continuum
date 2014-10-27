package sojamo.continuum.sets.liquid;

import processing.core.PApplet;
import processing.core.PGraphics;
import sojamo.continuum.Continuum;
import sojamo.continuum.render.AScene;

public class Glitch extends AScene {

	int t = 0;
	int c = 0;
	int v;
	int p;
	int s;
	float f;
	int myMode = 4;
	int cnt;

	public Glitch( Continuum theContinuum ) {
		super( theContinuum );
	}

	int g1 = 1;
	int g2 = 1;
	int g3 = 1;
	int g4 = 1;
	int p1 = 1;
	int p2 = 1;
	float gm = 1;
	int res1 = 1;
	int s1 = 100;
	float s2 = 0.01f;
	boolean res0 = false;
	int mul = 100;

	public void setup( ) {
		cp5.addSlider( "s" ).plugTo( this ).setPosition( 10 , 0 ).setSize( 100 , 20 ).setRange( 1 , 200 ).setValue( 100 );
		cp5.addSlider( "res" ).plugTo( this ).setPosition( 10 , 30 ).setSize( 100 , 20 ).setRange( 2 , 10 ).setValue( 1 );

		cp5.addSlider( "gm" ).plugTo( this ).setPosition( 180 , 0 ).setSize( 200 , 20 ).setRange( 0.01f , 0.9f ).setValue( 0.2f );
		cp5.addSlider( "g1" ).plugTo( this ).setPosition( 180 , 30 ).setSize( 200 , 20 ).setRange( 0 , 100 ).setValue( 2 );
		cp5.addSlider( "g2" ).plugTo( this ).setPosition( 180 , 60 ).setSize( 200 , 20 ).setRange( 0 , 100 ).setValue( 2 );
		cp5.addSlider( "g3" ).plugTo( this ).setPosition( 180 , 90 ).setSize( 200 , 20 ).setRange( 0 , 100 ).setValue( 2 );

		cp5.addSlider( "p1" ).plugTo( this ).setPosition( 180 , 140 ).setSize( 200 , 20 ).setRange( 0 , 128 ).setValue( 1 );
		cp5.addSlider( "p2" ).plugTo( this ).setPosition( 180 , 170 ).setSize( 200 , 20 ).setRange( 1 , 2550 ).setValue( 255 );

		cp5.addSlider( "s1" ).plugTo( this ).setPosition( 180 , 230 ).setSize( 200 , 20 ).setRange( 10 , 100 ).setValue( 10 );
		cp5.addSlider( "s2" ).plugTo( this ).setPosition( 180 , 260 ).setSize( 200 , 20 ).setDecimalPrecision( 3 ).setRange( 0.001f , 0.1f ).setValue( 0.05f );

		cp5.addSlider( "mul" ).plugTo( this ).setPosition( 180 , 300 ).setSize( 200 , 20 ).setRange( 0 , 10000 ).setValue( 100 );
	}

	public void res( float theValue ) {
		if ( ( int ) ( theValue ) != res1 ) {
			res1 = ( int ) theValue;
			res0 = true;
		}

	}

	public void display( PGraphics pg ) {

		if ( res0 ) {
			pg.background( 0 );
			res0 = false;
		}

		pg.loadPixels( );
		int n = width * height;

		f += s2;

		int i = ( int ) PApplet.abs( ( sin( f ) * s1 ) );

		int step = res1;
		int col = 0;
		int size = getEqualizer( ).getSize( ) / 2;

		for ( t = 0 ; t < n ; t += step ) {
			int in = ( int ) PApplet.abs( getEqualizer( ).getLeft( t % size ) * mul );
			int v = t % ( max( 1 , in ) );
			int p = v + in + i * ( int ) ( gm * ( in >> g1 | t >> g2 ) ) | ( s >> g3 ); // make changes here; make changes to any number here
			int c = ( p1 + p % p2 ); // make changes here; change 1 to 1000, change 255 to 25500 to add some color
			int mode = 4; // make changes here: use 0 , 1 , 2 , 3 , 4 

			if ( mode == 0 ) {
				pg.pixels[ t ] = color( 255 - c , 255 - c , 255 ); //  make changes here;
			} else if ( mode == 1 ) {
				pg.pixels[ t ] = color( 0 , c , c );
			} else if ( mode == 2 ) {
				pg.pixels[ t ] = color( c , 0 , c );
			} else if ( mode == 3 ) {
				pg.pixels[ t ] = color( 255 - c );
			} else if ( mode == 4 ) {
				col = c;
				pg.pixels[ t ] = color( col , col , col );
			}
		}

		pg.updatePixels( );
	}

	public void update( ) {
	}

	public void activate( PGraphics g ) {
		g.beginDraw( );
		g.background( 0 );
		g.endDraw( );
	}

}