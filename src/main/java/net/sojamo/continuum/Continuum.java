package net.sojamo.continuum;

import processing.core.PApplet;
import net.sojamo.continuum.sets.base.ReadScene;
import net.sojamo.continuum.sets.liquid.Glitch;
import net.sojamo.continuum.sets.liquid.GradientOpp;
import net.sojamo.continuum.sets.liquid.Landscape;
import net.sojamo.continuum.sets.liquid.Modernist;
import de.looksgood.ani.Ani;

public class Continuum extends PApplet {

	public Setup setup;
	public Audio audio;
	public Graphics graphics;
	public GUI gui;
	public Scenes scenes;
	public Midi midi;

	public static int RENDER_WIDTH = 1024;
	public static int RENDER_HEIGHT = 768;
	public static final String midi0 = "slider-knob-red";
	public static final String midi1 = "slider-knob-yellow";

	public void setup( ) {

		size( 1200 , 700 , P3D );
		sleep( 100 );
		Ani.init( this );
		audio = new Audio( this );
		sleep( 100 );
		graphics = new Graphics( this );
		sleep( 100 );
		gui = new GUI( this );
		setup = new Setup( this );
		midi = new Midi( this );
		try {
			midi.add( midi0 , "0" );
			midi.add( midi1 , "1" );
		} catch ( Exception e ) {
			println( "Problems with MIDI setup, cancelling MIDI suport." );
		}
		scenes = new Scenes( this );
		scenes.addScene( "landscape" , new Landscape( this ) );
		scenes.addScene( "gradient-opp" , new GradientOpp( this ) );
		scenes.addScene( "modernist" , new Modernist( this ) );
		scenes.addScene( "glitch" , new Glitch( this ) );
		ReadScene r = new ReadScene( this );
		// scenes.addScene( "Test" , r.compile( "~/Documents/Processing/continuum/test-conversion/convertToContinuum_a" , "Sketch" , "test.sketch" ) );
		gui.set( scenes );
		sleep( 100 );

	}

	public void draw( ) {
		background( 40 );
		audio.update( );
		audio.draw( graphics.audio );
		graphics.update( );
		gui.draw( );
	}

	static public void main( String args[] ) {
		PApplet.main( new String[] { Continuum.class.getName( ) } );
	}

	static public void sleep( long theMillis ) {
		try {
			Thread.sleep( theMillis );
		} catch ( Exception e ) {}
	}

	static public final float constrainValue( final float theValue , final float theMin , final float theMax ) {
		return theValue < theMin ? theMin : ( theValue > theMax ? theMax : theValue );
	}

	static public int i( final Object o ) {
		return i( o , Integer.MIN_VALUE );
	}

	static public int i( final Object o , final int theDefault ) {
		return ( o instanceof Number ) ? ( ( Number ) o ).intValue( ) : ( o instanceof String ) ? i( s( o ) ) : theDefault;
	}

	static public int i( final String o ) {
		return i( o , Integer.MIN_VALUE );
	}

	static public int i( final String o , final int theDefault ) {
		return isNumeric( o ) ? Integer.parseInt( o ) : theDefault;
	}
	
	static public boolean isNumeric( String str ) {
		return str.matches( "-?\\d+(\\.\\d+)?" );  //match a number with optional '-' and decimal.
	}
	
	static public String s( final Object o ) {
		return ( o != null ) ? o.toString( ) : "";
	}

	static public String s( final Number o , int theDec ) {
		return ( o != null ) ? String.format( "%." + theDec + "f" , o.floatValue( ) ) : "";
	}

	static public String s( final Object o , final String theDefault ) {
		return ( o != null ) ? o.toString( ) : theDefault;
	}

	

}
