package sojamo.continuum.sound;

import java.util.HashMap;
import java.util.Map;

import processing.core.PApplet;
import processing.core.PGraphics;
import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;

public class FFTEqualizer {

	private final Minim minim;
	private final AudioInput in;
	private final FFT fft;
	private final Map< String , Peak > peaks;
	private final int spectrumWidth;
	private final int spectrumHeight;
	private final float[] spectrum;

	public FFTEqualizer( PApplet p , int theSpectrumWidth , int theSpectrumHeight ) {
		minim = new Minim( p );
		spectrumWidth = theSpectrumWidth;
		spectrumHeight = theSpectrumHeight;
		in = minim.getLineIn( );
		fft = new FFT( in.bufferSize( ) , in.sampleRate( ) );
		fft.window( FFT.HAMMING );
		spectrum = new float[ spectrumWidth ];
		peaks = new HashMap< String , Peak >( );

	}
	
	public FFTEqualizer setWindow(int theWindow) {
		return this;
	}
	public FFTEqualizer add( String theId , Peak thePeak ) {
		peaks.put( theId , thePeak );
		return this;
	}

	public Peak get( String theId ) {
		return peaks.get( theId );
	}

	public FFTEqualizer update( ) {

		fft.forward( in.mix );
		float dbScale = 4.0f;
		float gain = 20;

		for ( int i = 0 ; i < spectrumWidth ; i++ ) {

			float val = dbScale * ( 20 * ( ( float ) Math.log10( fft.getBand( i ) ) ) + gain );

			if ( fft.getBand( i ) == 0 ) {
				val = -spectrumHeight;
			}

			int y = spectrumHeight - Math.round( val );
			if ( y > spectrumHeight ) {
				y = spectrumHeight;
			}

			spectrum[ i ] = spectrumHeight - PApplet.max( 0 , PApplet.min( PApplet.abs( y ) , spectrumHeight ) );
			for ( Peak p : peaks.values( ) ) {
				p.set( i , spectrum[ i ] );
			}
		}
		for ( Peak p : peaks.values( ) ) {
			p.update( );
		}
		return this;
	}

	public int getSize( ) {
		return in.bufferSize( );
	}

	public float getLeft( int theIndex ) {
		return in.left.get( theIndex );
	}

	public float getRight( int theIndex ) {
		return in.right.get( theIndex );
	}

	public void draw( PGraphics g ) {
		g.pushMatrix( );
		g.pushMatrix( );
		g.noStroke( );
		g.fill( 255 );
		for ( float f : spectrum ) {
			g.rect( 0 , 0 , 1 , -f );
			g.translate( 1 , 0 );
		}
		g.popMatrix( );
		for ( Peak p : peaks.values( ) ) {
			p.draw( g );
		}
		g.popMatrix( );
	}
}
