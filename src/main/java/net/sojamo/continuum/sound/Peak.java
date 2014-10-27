package net.sojamo.continuum.sound;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PGraphics;

public interface Peak extends Iterable< Float > {

	public void set( int theIndex , float theValue );
	public float get( int theIndex );
	public Iterator< Float > iterator( );
	public int size( );
	public void update( );
	public void draw( PGraphics g );

	public class PeakHigh implements Peak {

		private final ArrayList< Float > peak;
		private final int[] age;
		private final int num;
		private final int lifetime = 10;
		private final int range;
		private int decay = 1;
		
		public PeakHigh( int theNum , int theRange ) {
			range = theRange;
			peak = new ArrayList< Float >( );
			for ( int n = 0 ; n < theNum ; n++ )
				peak.add( 0.0f );
			age = new int[ theNum ];
			num = range / theNum;
		}

		public void set( int theIndex , float theValue ) {
			int n = PApplet.max( 0 , PApplet.min( size( ) - 1 , theIndex / num ) );
			if ( peak.get( n ) < theValue ) {
				peak.set( n , theValue );
				age[ n ] = 0;
			}
		}

		public int size( ) {
			return peak.size( );
		}
		
		public void setDecay(int theDecay) {
			decay = theDecay;
		}

		public Iterator< Float > iterator( ) {
			Iterator< Float > f = peak.iterator( );
			return f;
		}

		public float get( int theIndex ) {
			return peak.get( theIndex );
		}

		public void update( ) {
			for ( int i = 0 ; i < age.length ; i++ ) {
				if ( age[ i ] < lifetime ) {
					++age[ i ];
				} else {
					float n = PApplet.max( 0 , peak.get( i ) - decay );
					peak.set( i , n );
				}
			}
		}

		public void draw( PGraphics g ) {
			int s = range / size( );
			g.pushMatrix( );
			for ( float f : this ) {
				g.fill( 255 , 200 );
				g.rect( 0 , -f , s , 1 + f );
				g.translate( s , 0 );
			}
			g.popMatrix( );
		}
	}

	public class PeakRaw implements Peak {

		private final ArrayList< Float > peak;

		public PeakRaw( int theNum ) {
			peak = new ArrayList< Float >( );
			for ( int n = 0 ; n < theNum ; n++ )
				peak.add( 0.0f );
		}

		public void set( int theIndex , float theValue ) {
			peak.set( theIndex , theValue );
		}

		public float get( int theIndex ) {
			return peak.get( theIndex );
		}

		public Iterator< Float > iterator( ) {
			Iterator< Float > f = peak.iterator( );
			return f;
		}

		public void update( ) {
		}

		public void draw( PGraphics g ) {
			g.pushMatrix( );
			g.fill( 255 );
			g.noStroke( );
			for ( float f : this ) {
				g.translate( 2 , 0 );
				g.pushMatrix( );
				g.fill( 255 );
				g.rect( 0 , -f , 1 , f );
				g.popMatrix( );
			}
			g.popMatrix( );
		}

		public int size( ) {
			return peak.size( );
		}

	}

}
