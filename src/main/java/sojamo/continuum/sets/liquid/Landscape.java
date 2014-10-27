package sojamo.continuum.sets.liquid;

import processing.core.PGraphics;
import sojamo.continuum.Continuum;
import sojamo.continuum.render.AScene;
import sojamo.continuum.sound.Peak;
import controlP5.ControlEvent;
import controlP5.Knob;
import controlP5.Toggle;
import de.looksgood.ani.Ani;

public class Landscape extends AScene {

	public Landscape( Continuum theContinuum ) {
		super( theContinuum );
	}

	private float[][][] coords;
	private final int resolution = 100;
	private float w = 10;
	private float h = 10;
	private float val = 0;

	private float xMul = 0.1f;
	private float yMul = 0.1f;

	float falloff = 0.01f , falloff0 = 0.01f;

	float noiseSpeed = 0.01f , noiseSpeed0 = 0.01f;
	float x = 0 , y = 0 , z = -1000;
	float rx = 0 , ry = 0 , rz = 0;
	float gridAlpha = 100;
	float a0 = 100;
	boolean gridMode = true;
	boolean gridOrientation = false;

	float peakIntensity = 0.1f , peakIntensity0 = 0.1f;

	int LS_R = 255;
	int LS_G = 255;
	int LS_B = 255;

	public void setup( ) {

		width = 1024;
		height = 768;

		float xx = width / resolution;
		float yy = height / resolution;
		coords = new float[ resolution ][ resolution ][ 3 ];
		for ( int x = 0 ; x < resolution ; x++ ) {
			for ( int y = 0 ; y < resolution ; y++ ) {
				coords[ x ][ y ] = new float[] { x * w - ( w * resolution / 2 ) , y * h - ( h * resolution / 2 ) , 0 };
			}
		}

		cp5.addSlider( "falloff" ).plugTo( this ).setRange( 0 , 1 ).setValue( 0.01f ).setPosition( 10 , 0 ).setSize( 200 , 20 );
		cp5.addSlider( "noiseSpeed" ).plugTo( this ).setRange( 0 , 0.02f ).setValue( 0.01f ).setPosition( 10 , 30 ).setSize( 200 , 20 ).setDecimalPrecision( 3 );
		cp5.addSlider( "peakIntensity" ).plugTo( this ).setRange( 0 , 4 ).setValue( 0.1f ).setPosition( 10 , 60 ).setSize( 200 , 20 );
		cp5.addSlider( "gridAlpha" ).plugTo( this ).setRange( 0 , 255 ).setValue( 100 ).setPosition( 10 , 90 ).setSize( 200 , 20 );

		cp5.addKnob( "LS_R" ).plugTo( this ).setLabel( "RED" ).setRange( 0 , 255 ).setValue( 255 ).setPosition( 10 , 570 ).setRadius( 30 ).setViewStyle( Knob.ARC ).setDragDirection( Knob.VERTICAL );
		cp5.addKnob( "LS_G" ).plugTo( this ).setLabel( "GREEN" ).setRange( 0 , 255 ).setValue( 255 ).setPosition( 80 , 570 ).setRadius( 30 ).setViewStyle( Knob.ARC ).setDragDirection( Knob.VERTICAL );
		cp5.addKnob( "LS_B" ).plugTo( this ).setLabel( "BLUE" ).setRange( 0 , 255 ).setValue( 255 ).setPosition( 150 , 570 ).setRadius( 30 ).setViewStyle( Knob.ARC ).setDragDirection( Knob.VERTICAL );

		cp5.addButton( "animation1" ).plugTo( this , "animation" ).setPosition( 310 , 0 ).setSize( 100 , 20 ).setValue( 0 );
		cp5.addButton( "animation2" ).plugTo( this , "animation" ).setPosition( 310 , 30 ).setSize( 100 , 20 ).setValue( 1 );
		cp5.addButton( "animation3" ).plugTo( this , "animation" ).setPosition( 310 , 60 ).setSize( 100 , 20 ).setValue( 2 );
		cp5.addButton( "animation4" ).plugTo( this , "animation" ).setPosition( 310 , 90 ).setSize( 100 , 20 ).setValue( 3 );

		cp5.addToggle( "gridMode" ).plugTo( this ).setPosition( 310 , 150 ).setSize( 100 , 20 ).setValue( false ).setMode( Toggle.SWITCH );
		cp5.addToggle( "gridOrientation" ).plugTo( this ).setPosition( 310 , 200 ).setSize( 100 , 20 ).setValue( false ).setMode( Toggle.SWITCH );

	}

	@Override public void controlEvent( ControlEvent c ) {
		if ( c.getName( ).indexOf( "animation" ) > -1 ) {
			int n = ( int ) ( c.getValue( ) );
			switch ( n ) {
			case ( 0 ):
				Ani.to( this , 20 , 0 , "rx:2.14,ry:0.51,rz:-0.5 x:200,y0,z:0" , Ani.QUAD_IN_OUT );
				break;
			case ( 1 ):
				Ani.to( this , 20 , 0 , "rx:0,ry:0,rz:-0.5 x:0,y:0,z:0" , Ani.QUAD_IN_OUT );
				break;
			case ( 2 ):
				Ani.to( this , 20 , 0 , "rx:-1.5,ry:2,rz:1.5 x:400,y:-100,z:100" , Ani.QUAD_IN_OUT );
				break;
			case ( 3 ):
				Ani.to( this , 20 , 0 , "rx:0,ry:0,rz:0 x:0,y:0,z:-1000" , Ani.QUAD_IN_OUT );
				break;
			}
		}
	}

	public void activate( PGraphics pg ) {
		x = 0;
		y = 0;
		z = -1000;
		rx = 0;
		ry = 0;
		rz = 0;
		continuum.midi.get( continuum.midi0 ).clear( );
		continuum.midi.get( continuum.midi1 ).clear( );

		continuum.midi.get( continuum.midi0 ).assign( 16 , "noiseFalloff" );
		continuum.midi.get( continuum.midi0 ).assign( 17 , "noiseSpeed" );
		continuum.midi.get( continuum.midi0 ).assign( 18 , "peakIntensity" );
		continuum.midi.get( continuum.midi0 ).assign( 19 , "gridAlpha" );
		continuum.midi.get( continuum.midi1 ).assign( 16 , "LS_R" );
		continuum.midi.get( continuum.midi1 ).assign( 17 , "LS_G" );
		continuum.midi.get( continuum.midi1 ).assign( 18 , "LS_B" );
		continuum.midi.get( continuum.midi0 ).assign( 45 , "gridMode" );
		continuum.midi.get( continuum.midi1 ).assign( 45 , "gridMode" );
		continuum.midi.get( continuum.midi0 ).assign( 32 , "animation1" );
		continuum.midi.get( continuum.midi0 ).assign( 33 , "animation2" );
		continuum.midi.get( continuum.midi0 ).assign( 34 , "animation3" );
		continuum.midi.get( continuum.midi0 ).assign( 35 , "animation4" );

		continuum.midi.get( continuum.midi1 ).assign( 32 , "animation1" );
		continuum.midi.get( continuum.midi1 ).assign( 33 , "animation2" );
		continuum.midi.get( continuum.midi1 ).assign( 34 , "animation3" );
		continuum.midi.get( continuum.midi1 ).assign( 35 , "animation4" );
	}

	public void update( ) {
		a0 += ( gridAlpha - a0 ) * 0.02;
		falloff0 += ( falloff - falloff0 ) * 0.02;
		noiseSpeed0 += ( noiseSpeed - noiseSpeed0 ) * 0.005;
		peakIntensity0 += ( peakIntensity - peakIntensity0 ) * 0.01;
	}

	public void draw( ) {
		update( );
		display( g );
	}

	public void display( PGraphics g ) {
		g.hint( DISABLE_DEPTH_TEST );
		g.smooth( 8 );
		g.background( 0 );
		g.translate( g.width / 2 + x , g.height / 2 + y , z );
		g.rotateY( ry );
		g.rotateX( rx );
		g.rotateX( rz );
		g.noFill( );
		g.stroke( LS_R , LS_G , LS_B , a0 );
		g.strokeWeight( 1.5f );
		drawMesh( g );
	}

	void drawMesh( PGraphics g ) {

		val += noiseSpeed0;
		noiseDetail( 8 , falloff0 );
		float ratio = 1.0f / ( resolution - 1 );
		int n = 0;

		Peak p = continuum.audio.getPeak( "raw" );

		for ( int x = 0 ; x < ( resolution ) ; x++ ) {
			for ( int y = 0 ; y < ( resolution ) ; y++ ) {
				n++;
				n %= p.size( );
				coords[ x ][ y ][ 2 ] = noise( x * xMul + val , y * yMul - val ) * 400 + p.get( n ) * peakIntensity;
			}
		}

		for ( int x = 0 ; x < ( resolution - 1 ) ; x++ ) {
			for ( int y = 0 ; y < ( resolution - 1 ) ; y++ ) {
				g.beginShape( LINES );

				if ( gridOrientation ) {
					g.vertex( coords[ x ][ y ][ 0 ] , coords[ x ][ y ][ 1 ] , coords[ x ][ y ][ 2 ] );
					g.vertex( coords[ x ][ y + 1 ][ 0 ] , coords[ x ][ y + 1 ][ 1 ] , coords[ x ][ y + 1 ][ 2 ] );
				} else {
					g.vertex( coords[ x ][ y ][ 0 ] , coords[ x ][ y ][ 1 ] , coords[ x ][ y ][ 2 ] );
					g.vertex( coords[ x + 1 ][ y ][ 0 ] , coords[ x + 1 ][ y ][ 1 ] , coords[ x + 1 ][ y ][ 2 ] );

				}

				if ( gridMode ) {
					g.vertex( coords[ x ][ y + 1 ][ 0 ] , coords[ x ][ y + 1 ][ 1 ] , coords[ x ][ y + 1 ][ 2 ] );
					g.vertex( coords[ x + 1 ][ y + 1 ][ 0 ] , coords[ x + 1 ][ y + 1 ][ 1 ] , coords[ x + 1 ][ y + 1 ][ 2 ] );
				}

				g.endShape( );
			}
		}
	}

}
