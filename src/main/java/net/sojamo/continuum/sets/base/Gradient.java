package net.sojamo.continuum.sets.base;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PGraphics;
import net.sojamo.continuum.render.IScene;

public class Gradient implements IScene {

	private final Map< Integer , IScene > scenes;

	private int current;

	Gradient( ) {
		scenes = new HashMap< Integer , IScene >();
	}

	public void setup( ) {
//		scenes.put( 0 , new GradientTopLeft( color( 255 , 0 , 0 ) , color( 255 , 255 , 0 ) ) );
//		scenes.put( 1 , new GradientLeftRight( color( 255 , 0 , 0 ) , color( 255 , 255 , 0 ) ) );
//		scenes.put( 2 , new GradientTopDown( color( 0 , 0 , 50 ) , color( 0 , 200 , 255 ) ) );
//		scenes.put( 3 , new GradientBottomLeft( color( 0 , 200 , 255 ) , color( 0 , 0 , 50 ) ) );
	}

	public void display( PGraphics g ) {
		scenes.get( current ).update();
		scenes.get( current ).display( g );
	}

	public void update( ) {
	}

	public void activate( PGraphics g ) {
	}

	void change( ) {
		current++;
		current %= 3;
	}

	void set( int theValue ) {
		current = theValue;
	}
}

abstract class AbstractGradient implements IScene {

	int[] colors;

	int speed = 4;

	int cnt = 0;

	int itr = 1280;

	AbstractGradient( int c1 , int c2 ) {
		set( c1 , c2 );
	}

	public void setup( ) {
	}

	void set( int c1 , int c2 ) {
		colors = new int[ 1280 * 2 ];
		int from = c1;
		int to = c2;
		float s = 1.0f / colors.length;
		float n = 0;
		for ( int i = 0; i < colors.length; i++ ) {
//			colors[ i ] = lerpColor( from , to , n += s );
		}
	}

	public void update( ) {
		cnt += speed;
		cnt %= colors.length - itr;
	}

	public void activate( PGraphics g ) {
	}
}

class GradientTopDown extends AbstractGradient {

	GradientTopDown( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i );
			g.stroke( colors[ f ] );
			g.line( 0 , g.height - i , g.width , g.height - i );
		}
	}
}

class GradientBottomUp extends AbstractGradient {

	GradientBottomUp( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i );
			g.stroke( colors[ f ] );
			g.line( 0 , i , g.width , i );
		}
	}
}

class GradientLeftRight extends AbstractGradient {

	GradientLeftRight( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i );
			g.stroke( colors[ f ] );
			g.line( g.width - i , 0 , g.width - i , g.height );
		}
	}
}

class GradientRightLeft extends AbstractGradient {

	GradientRightLeft( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i ) % colors.length;
			g.stroke( colors[ f ] );
			g.line( i , 0 , i , g.height );
		}
	}
}

class GradientTopLeft extends AbstractGradient {

	GradientTopLeft( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		g.pushMatrix();
		g.rotate( -PApplet.HALF_PI * 0.5f );
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i ) % colors.length;
			g.stroke( colors[ f ] );
			g.line( -i , i , i * 2 , i );
		}
		g.popMatrix();
	}
}

class GradientBottomLeft extends AbstractGradient {

	GradientBottomLeft( int c1 , int c2 ) {
		super( c1 , c2 );
	}

	public void display( PGraphics g ) {
		g.pushMatrix();
		g.translate( g.width , 0 );
		g.rotate( PApplet.HALF_PI * 0.5f );
		for ( int i = 0; i < itr; i++ ) {
			int f = ( cnt + i ) % colors.length;
			g.stroke( colors[ f ] );
			g.line( -i , i , i * 2 , i );
		}
		g.popMatrix();
	}
}
