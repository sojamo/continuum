package net.sojamo.continuum;

import processing.core.PApplet;
import processing.core.PGraphics;
import net.sojamo.continuum.sound.Peak.PeakHigh;
import controlP5.CallbackEvent;
import controlP5.CallbackListener;
import controlP5.ControlGroup;
import controlP5.ControlP5;

public class Setup {

	private final Continuum continuum;

	public Setup( Continuum theContinuum ) {
		continuum = theContinuum;
		setupView( );
	}

	private void setupView( ) {

		ControlP5 cp5 = continuum.gui.cp5( );
		PGraphics current = continuum.graphics.current;
		PGraphics audio = continuum.graphics.audio;

		RenderViewGroup r1 = new RenderViewGroup( cp5 , "current" , current );
		r1.setPosition( 10 , 30 );
		r1.setHeight( 20 );
		r1.setWidth( 400 );
		r1.setBackgroundHeight( 300 );
		r1.getCaptionLabel( ).align( ControlP5.LEFT , ControlP5.CENTER );

		AudioViewGroup r2 = new AudioViewGroup( cp5 , "audio" , audio );
		r2.setPosition( continuum.width - 512 , 50 ).setHeight( 20 ).setWidth( 512 ).setBackgroundHeight( 280 ).getCaptionLabel( ).align( ControlP5.LEFT , ControlP5.CENTER );

		CallbackListener listener = new CallbackListener( ) {
			@Override public void controlEvent( CallbackEvent c ) {
				PeakHigh peak = ( PeakHigh ) continuum.audio.getPeak( "band" );
				peak.setDecay( ( int ) ( c.getController( ).getValue( ) ) );
			}

		};
		cp5.addSlider( "peakDecay" ).setSize( 100 , 20 ).setPosition( 10 , 200 ).moveTo( r2 ).setRange( 1 , 20 ).setValue( 1 ).onChange( listener );
	}

	class RenderViewGroup extends ControlGroup< RenderViewGroup > {

		float scl = 1f;
		boolean enlarged;
		PGraphics texture;

		public RenderViewGroup( final ControlP5 cp5 , final String theName , final PGraphics theGraphics ) {
			super( cp5 , theName );
			texture = theGraphics;
		}

		public void preDraw( PGraphics pg ) {
			float w = texture.width * scl;
			float ox = ( texture.width / 2.0f ) * ( 1.0f - scl );
			float h = texture.height * scl;
			float oy = ( texture.height / 2.0f ) * ( 1.0f - scl );
			pg.beginShape( );
			pg.texture( texture );
			pg.vertex( 0 , 0 , ox , oy );
			pg.vertex( getWidth( ) , 0 , ox + w , oy );
			pg.vertex( getWidth( ) , getBackgroundHeight( ) , ox + w , oy + h );
			pg.vertex( 0 , getBackgroundHeight( ) , ox , oy + h );
			pg.endShape( );
		}

		@Override public void mousePressed( ) {
			enlarged = !enlarged;
			if ( enlarged ) {
				setWidth( continuum.width );
				setBackgroundHeight( continuum.height - 20 );
				setPosition( 0 , 30 );
				setBarHeight( 30 );
			} else {
				setWidth( 400 );
				setBackgroundHeight( 300 );
				setPosition( 10 , 30 );
				setBarHeight( 20 );
			}
		}

		public void onScroll( int theValue ) {
			scl += theValue * 0.01f;
			scl = PApplet.constrain( scl , 0.01f , 1 );
		}

	}

	class AudioViewGroup extends ControlGroup< AudioViewGroup > {

		float scl = 1f;
		boolean enlarged;
		PGraphics texture;

		public AudioViewGroup( final ControlP5 cp5 , final String theName , final PGraphics theGraphics ) {
			super( cp5 , theName );
			texture = theGraphics;
		}

		public void preDraw( PGraphics pg ) {
			if ( isOpen( ) ) {
				pg.fill( 0 , 160 );
				pg.rect( 0 , 0 , getWidth( ) , getBackgroundHeight( ) );
				pg.image( texture , 0 , 0 );
			}
		}

	}

}
