package sojamo.continuum;

import processing.core.PGraphics;
import sojamo.continuum.sound.FFTEqualizer;
import sojamo.continuum.sound.Peak;
import sojamo.continuum.sound.Peak.PeakHigh;
import sojamo.continuum.sound.Peak.PeakRaw;

public class Audio {

	private final Continuum continuum;
	private FFTEqualizer equalizer;

	public Audio( Continuum theContinuum ) {
		continuum = theContinuum;
		equalizer = new FFTEqualizer( continuum , 512 , 100 );
		equalizer.add( "band" , new PeakHigh( 10 , 512 ) );
		equalizer.add( "raw" , new PeakRaw( 512 ) );
	}

	public void update( ) {
		equalizer.update( );
	}

	public void draw( PGraphics pg ) {
		pg.beginDraw( );
		pg.background( 0 , 0 );
		pg.translate( 0 , 120 - 1 );
		equalizer.draw( pg );
		pg.endDraw( );
	}

	public Peak getPeak( String theId ) {
		return equalizer.get( theId );
	}

	public FFTEqualizer getEqualizer( ) {
		return equalizer;
	}
}
