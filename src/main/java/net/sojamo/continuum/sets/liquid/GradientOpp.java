package net.sojamo.continuum.sets.liquid;

import processing.core.PGraphics;
import net.sojamo.continuum.Continuum;
import net.sojamo.continuum.render.AScene;
import controlP5.CallbackEvent;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.Slider;

public class GradientOpp extends AScene {

	public GradientOpp( Continuum theContinuum ) {
		super( theContinuum );
	}

	int a1;
	int b1;
	int a2;
	int b2;
	int a3;
	int b3;
	int a4;
	int b4;
	boolean direction;

	int gradientSpeedH = 1;
	int gradientSpeedV = 1;

	int gradientSpeedVI = 1;
	int gradientSpeedHI = 1;

	boolean gradient_vIn;
	boolean gradient_hIn;
	boolean gradient_h;
	boolean gradient_v;

	int gOpacity = 255;

	int lineWEIGHT = 2;

	public void setup( ) {

		reset( 0 );

		cp5.addToggle( "gradient_vIn" ).plugTo( this ).setPosition( 10 , 0 ).setSize( 100 , 30 ).setValue( true );
		cp5.addToggle( "gradient_hIn" ).plugTo( this ).setPosition( 10 , 50 ).setSize( 100 , 30 ).setValue( false );
		cp5.addToggle( "gradient_h" ).plugTo( this ).setPosition( 10 , 100 ).setSize( 100 , 30 ).setValue( false );
		cp5.addToggle( "gradient_v" ).plugTo( this ).setPosition( 10 , 150 ).setSize( 100 , 30 ).setValue( false );

		cp5.addBang( "reset" ).plugTo( this ).setPosition( 10 , 250 ).setSize( 100 , 30 ).plugTo( this );

		cp5.addSlider( "gOpacity" ).plugTo( this ).setPosition( 200 , 0 ).setLabel( "Opacity" ).setSize( 100 , 30 ).setRange( 0 , 255 ).setValue( 255 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "lineWEIGHT" ).plugTo( this ).setPosition( 200 , 40 ).setLabel( "Line" ).setSize( 100 , 30 ).setRange( 2 , 10 ).setValue( 2 ).setNumberOfTickMarks( 10 ).snapToTickMarks( true ).setSliderMode( Slider.FLEXIBLE );

		cp5.addSlider( "gradientSpeedVI" ).plugTo( this ).setPosition( 200 , 120 ).setLabel( "SpeedVI" ).setSize( 100 , 20 ).setRange( 1 , 10 ).setValue( 1 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "gradientSpeedHI" ).plugTo( this ).setPosition( 200 , 150 ).setLabel( "SpeedHI" ).setSize( 100 , 20 ).setRange( 1 , 10 ).setValue( 1 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "gradientSpeedH" ).plugTo( this ).setPosition( 200 , 180 ).setLabel( "SpeedH" ).setSize( 100 , 20 ).setRange( 1 , 10 ).setValue( 1 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "gradientSpeedV" ).plugTo( this ).setPosition( 200 , 210 ).setLabel( "SpeedV" ).setSize( 100 , 20 ).setRange( 1 , 10 ).setValue( 1 ).setSliderMode( Slider.FLEXIBLE );

	}

	public void reset( int n ) {
		a1 = 0;
		b1 = Continuum.RENDER_WIDTH;
		a2 = 0;
		b2 = Continuum.RENDER_HEIGHT;
		a3 = 0;
		b3 = Continuum.RENDER_HEIGHT;
		a4 = 0;
		b4 = Continuum.RENDER_WIDTH;
		direction = true;
		gOpacity = 100;
		gradientSpeedH = 2;
	}

	public void activate( PGraphics g ) {

		continuum.midi.get( continuum.midi0 ).clear( );
		continuum.midi.get( continuum.midi1 ).clear( );

		continuum.midi.get( continuum.midi0 ).assign( 32 , "gradient_vIn" );
		continuum.midi.get( continuum.midi0 ).assign( 48 , "gradient_hIn" );
		continuum.midi.get( continuum.midi0 ).assign( 64 , "gradient_h" );
		continuum.midi.get( continuum.midi1 ).assign( 32 , "gradient_v" );
		continuum.midi.get( continuum.midi0 ).assign( 16 , "gOpacity" );
		continuum.midi.get( continuum.midi0 ).assign( 17 , "lineWEIGHT" );
		continuum.midi.get( continuum.midi0 ).assign( 18 , "gradientSpeedVI" );
		continuum.midi.get( continuum.midi0 ).assign( 19 , "gradientSpeedHI" );
		continuum.midi.get( continuum.midi0 ).assign( 20 , "gradientSpeedH" );
		continuum.midi.get( continuum.midi0 ).assign( 21 , "gradientSpeedV" );

		continuum.midi.get( continuum.midi1 ).assign( 16 , "gOpacity" );
		continuum.midi.get( continuum.midi1 ).assign( 17 , "lineWEIGHT" );
		continuum.midi.get( continuum.midi1 ).assign( 18 , "gradientSpeedVI" );
		continuum.midi.get( continuum.midi1 ).assign( 19 , "gradientSpeedHI" );
		continuum.midi.get( continuum.midi1 ).assign( 20 , "gradientSpeedH" );
		continuum.midi.get( continuum.midi1 ).assign( 21 , "gradientSpeedV" );

	}

	public void update( ) {
	}

	public void draw( ) {
		display( g );
	}

	public void display( PGraphics g ) {
		g.colorMode( RGB , 1000 );
		g.fill( 0 , gOpacity );
		g.noStroke( );
		g.rect( 0 , 0 , g.width , g.height );

		if ( gradient_vIn ) {
			verticalInwardsGradient( g );
		}

		if ( gradient_hIn ) {
			horizontalInwardsGradient( g );
		}

		if ( gradient_v ) {
			verticalGradient( g );
		}

		if ( gradient_h ) {
			horizontalGradient( g );
		}
		g.colorMode( RGB , 255 );
	}

	void horizontalGradient( PGraphics g ) {

		g.strokeWeight( lineWEIGHT );
		//a1++;

		a1 += gradientSpeedH;

		if ( a1 > g.width ) {
			a1 = 0;
			direction = !direction;
		}
		if ( direction == true ) {
			g.stroke( a1 );
		} else {
			g.stroke( g.width - a1 );
		}
		g.line( a1 , 0 , a1 , g.height / 2 );

		//b1--;
		b1 -= gradientSpeedH;

		if ( b1 < 0 ) {
			b1 = g.width;
		}
		if ( direction == true ) {
			g.stroke( g.width - b1 );
		} else {
			g.stroke( b1 );
		}
		g.line( b1 , g.height / 2 , b1 , g.height );
	}

	void verticalGradient( PGraphics g ) {

		g.strokeWeight( lineWEIGHT );
		//a2++;
		a2 += gradientSpeedV;

		if ( a2 > g.height ) {
			a2 = 0;
			direction = !direction;
		}
		if ( direction == true ) {
			g.stroke( a2 );
		} else {
			g.stroke( g.height - a2 );
		}

		g.line( 0 , a2 , g.width / 2 , a2 );

		//b2--;
		b2 -= gradientSpeedV;

		if ( b2 < 0 ) {
			b2 = g.height;
		}
		if ( direction == true ) {
			g.stroke( g.height - b2 );
		} else {
			g.stroke( b2 );
		}
		g.translate( g.width / 2 , 0 );
		g.line( 0 , b2 , g.width / 2 , b2 );
	}

	void verticalInwardsGradient( PGraphics g ) {

		g.strokeWeight( lineWEIGHT );
		//a3++;
		a3 += gradientSpeedVI;

		if ( a3 > g.height / 2 ) {
			a3 = 0;
			direction = !direction;
		}
		if ( direction == true ) {
			g.stroke( a3 );
		} else {
			g.stroke( g.height - a3 );
		}

		g.line( 0 , a3 , g.width , a3 );

		//b3--;

		b3 -= gradientSpeedVI;

		if ( b3 < g.height / 2 ) {
			b3 = g.height;
		}
		if ( direction == true ) {
			g.stroke( g.height - b3 );
		} else {
			g.stroke( b3 );
		}
		g.line( 0 , b3 , g.width , b3 );
	}

	void horizontalInwardsGradient( PGraphics g ) {

		g.strokeWeight( lineWEIGHT );
		//a4++;
		a4 += gradientSpeedHI;

		if ( a4 > g.width / 2 ) {
			a4 = 0;
			direction = !direction;
		}
		if ( direction == true ) {
			g.stroke( a4 );
		} else {
			g.stroke( g.width - a4 );
		}
		g.line( a4 , 0 , a4 , g.height );

		//b4--;
		b4 -= gradientSpeedHI;

		if ( b4 < g.width / 2 ) {
			b4 = g.width;
		}
		if ( direction == true ) {
			g.stroke( g.width - b4 );
		} else {
			g.stroke( b4 );
		}
		g.line( b4 , 0 , b4 , g.height );
	}
}
