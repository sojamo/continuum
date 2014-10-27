package sojamo.continuum.sets.liquid;

import processing.core.PGraphics;
import sojamo.continuum.Continuum;
import sojamo.continuum.render.AScene;
import controlP5.Knob;
import controlP5.Slider;

public class Modernist extends AScene {

	public Modernist( Continuum theContinuum ) {
		super( theContinuum );
	}

	int modernist_audioInLEFT = 0;
	int modernist_audioInRIGHT = 0;

	int modernist_weight = 1;

	int VL_range = 48;
	int VR_range = 48;
	int HT_range = 64;
	int HB_range = 64;

	int VL_translate = 1;
	int VR_translate = 1;
	int HT_translate = 1;
	int HB_translate = 1;

	int VL_opacity = 255;
	int VR_opacity = 255;
	int HT_opacity = 255;
	int HB_opacity = 255;

	boolean vertLeft = false;
	boolean vertRight = false;
	boolean horiTop = false;
	boolean horiBottom = false;

	//verticalLeft color
	boolean VL_W = false;
	boolean VL_R = false;
	boolean VL_Y = false;
	boolean VL_B = false;

	//verticalRight color
	boolean VR_W = false;
	boolean VR_R = false;
	boolean VR_Y = false;
	boolean VR_B = false;

	//horiTop color
	boolean HT_W = false;
	boolean HT_R = false;
	boolean HT_Y = false;
	boolean HT_B = false;

	//horiBottom color
	boolean HB_W = false;
	boolean HB_R = false;
	boolean HB_Y = false;
	boolean HB_B = false;

	public void setup( ) {

		//sliders for audio in
		cp5.addSlider( "modernist_audioInLEFT" ).plugTo( this ).setPosition( 10 , 0 ).setLabel( "audioInLEFT" ).setSize( 75 , 20 ).setRange( 0 , 512 ).setValue( 0 ).setNumberOfTickMarks( 8 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "modernist_audioInRIGHT" ).plugTo( this ).setPosition( 160 , 0 ).setLabel( "audioInRIGHT" ).setSize( 75 , 20 ).setRange( 0 , 512 ).setValue( 0 ).setNumberOfTickMarks( 8 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "modernist_weight" ).plugTo( this ).setPosition( 300 , 0 ).setLabel( "weight" ).setSize( 75 , 20 ).setRange( 1 , 5 ).setValue( 1 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );

		//----------------------------------------------------------------------------------                 

		//knobs
		cp5.addKnob( "VL_opacity" ).plugTo( this ).setRange( 0 , 255 ).setLabel( "VL_opacity" ).setValue( 255 ).setPosition( 430 , 50 ).setRadius( 20 ).setDragDirection( Knob.VERTICAL );
		cp5.addKnob( "VR_opacity" ).plugTo( this ).setRange( 0 , 255 ).setLabel( "VR_opacity" ).setValue( 255 ).setPosition( 430 , 130 ).setRadius( 20 ).setDragDirection( Knob.VERTICAL );
		cp5.addKnob( "HT_opacity" ).plugTo( this ).setRange( 0 , 255 ).setLabel( "HT_opacity" ).setValue( 255 ).setPosition( 430 , 210 ).setRadius( 20 ).setDragDirection( Knob.VERTICAL );
		cp5.addKnob( "HB_opacity" ).plugTo( this ).setRange( 0 , 255 ).setLabel( "HB_opacity" ).setValue( 255 ).setPosition( 430 , 290 ).setRadius( 20 ).setDragDirection( Knob.VERTICAL );

		//----------------------------------------------------------------------------------  

		//toggle line orientation
		cp5.addToggle( "vertLeft" ).plugTo( this ).setPosition( 10 , 80 ).setLabel( "vertLeft" ).setSize( 50 , 20 );
		cp5.addToggle( "vertRight" ).plugTo( this ).setPosition( 10 , 160 ).setLabel( "vertRight" ).setSize( 50 , 20 );
		cp5.addToggle( "horiTop" ).plugTo( this ).setPosition( 10 , 240 ).setLabel( "horiTop" ).setSize( 50 , 20 );
		cp5.addToggle( "horiBottom" ).plugTo( this ).setPosition( 10 , 320 ).setLabel( "horiBottom" ).setSize( 50 , 20 );

		//----------------------------------------------------------------------------------        

		cp5.addSlider( "VL_range" ).plugTo( this ).setPosition( 80 , 80 ).setLabel( "VL_range" ).setSize( 75 , 20 ).setRange( 0 , height / 4 ).setValue( 48 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "VR_range" ).plugTo( this ).setPosition( 80 , 160 ).setLabel( "VR_range" ).setSize( 75 , 20 ).setRange( 0 , height / 4 ).setValue( 48 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "HT_range" ).plugTo( this ).setPosition( 80 , 240 ).setLabel( "HT_range" ).setSize( 75 , 20 ).setRange( 0 , width / 4 ).setValue( 64 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "HB_range" ).plugTo( this ).setPosition( 80 , 320 ).setLabel( "HB_range" ).setSize( 75 , 20 ).setRange( 0 , width / 4 ).setValue( 64 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );

		//----------------------------------------------------------------------------------

		//sliders for translate
		cp5.addSlider( "VL_translate" ).plugTo( this ).setPosition( 240 , 80 ).setLabel( "VL_translate" ).setSize( 75 , 20 ).setRange( 1 , 16 ).setValue( 1 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "VR_translate" ).plugTo( this ).setPosition( 240 , 80 + 80 ).setLabel( "VR_translate" ).setSize( 75 , 20 ).setRange( 1 , 16 ).setValue( 1 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "HT_translate" ).plugTo( this ).setPosition( 240 , 80 + 160 ).setLabel( "HT_translate" ).setSize( 75 , 20 ).setRange( 1 , 16 ).setValue( 1 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );
		cp5.addSlider( "HB_translate" ).plugTo( this ).setPosition( 240 , 80 + 240 ).setLabel( "HB_translate" ).setSize( 75 , 20 ).setRange( 1 , 16 ).setValue( 1 ).setNumberOfTickMarks( 5 ).setSliderMode( Slider.FLEXIBLE );

		//----------------------------------------------------------------------------------

		//toggle lines color for vertLeft
		cp5.addToggle( "VL_W" ).plugTo( this ).setPosition( 80 , 80 - 40 ).setLabel( "VL_W" ).setSize( 20 , 20 );
		cp5.addToggle( "VL_R" ).plugTo( this ).setPosition( 80 + 40 , 80 - 40 ).setLabel( "VL_R" ).setSize( 20 , 20 );
		cp5.addToggle( "VL_Y" ).plugTo( this ).setPosition( 80 + 80 , 80 - 40 ).setLabel( "VL_Y" ).setSize( 20 , 20 );
		cp5.addToggle( "VL_B" ).plugTo( this ).setPosition( 80 + 120 , 80 - 40 ).setLabel( "VL_B" ).setSize( 20 , 20 );

		//toggle lines color for vertRight
		cp5.addToggle( "VR_W" ).plugTo( this ).setPosition( 80 , 160 - 40 ).setLabel( "VR_W" ).setSize( 20 , 20 );
		cp5.addToggle( "VR_R" ).plugTo( this ).setPosition( 80 + 40 , 160 - 40 ).setLabel( "VR_R" ).setSize( 20 , 20 );
		cp5.addToggle( "VR_Y" ).plugTo( this ).setPosition( 80 + 80 , 160 - 40 ).setLabel( "VR_Y" ).setSize( 20 , 20 );
		cp5.addToggle( "VR_B" ).plugTo( this ).setPosition( 80 + 120 , 160 - 40 ).setLabel( "VR_B" ).setSize( 20 , 20 );

		//toggle lines color for horiTop
		cp5.addToggle( "HT_W" ).plugTo( this ).setPosition( 80 , 240 - 40 ).setLabel( "HT_W" ).setSize( 20 , 20 );
		cp5.addToggle( "HT_R" ).plugTo( this ).setPosition( 80 + 40 , 240 - 40 ).setLabel( "HT_R" ).setSize( 20 , 20 );
		cp5.addToggle( "HT_Y" ).plugTo( this ).setPosition( 80 + 80 , 240 - 40 ).setLabel( "HT_Y" ).setSize( 20 , 20 );
		cp5.addToggle( "HT_B" ).plugTo( this ).setPosition( 80 + 120 , 240 - 40 ).setLabel( "HT_B" ).setSize( 20 , 20 );

		//toggle lines color for horiBottom
		cp5.addToggle( "HB_W" ).plugTo( this ).setPosition( 80 , 320 - 40 ).setLabel( "HB_W" ).setSize( 20 , 20 );
		cp5.addToggle( "HB_R" ).plugTo( this ).setPosition( 80 + 40 , 320 - 40 ).setLabel( "HB_R" ).setSize( 20 , 20 );
		cp5.addToggle( "HB_Y" ).plugTo( this ).setPosition( 80 + 80 , 320 - 40 ).setLabel( "HB_Y" ).setSize( 20 , 20 );
		cp5.addToggle( "HB_B" ).plugTo( this ).setPosition( 80 + 120 , 320 - 40 ).setLabel( "HB_B" ).setSize( 20 , 20 );
	}

	public void display( PGraphics g ) {

		background( 0 );

		if ( vertLeft == true ) {
			vertLeft_Lines( g );
			noStroke( );
		}

		if ( vertRight == true ) {
			vertRight_Lines( g );
			noStroke( );
		}

		if ( horiTop == true ) {
			horiTop_Lines( g );
			noStroke( );
		}

		if ( horiBottom == true ) {
			horiBottom_Lines( g );
			noStroke( );
		}
	}

	void vertLeft_Lines( PGraphics g ) {

		// draw the waveforms
		for ( int i = 0 ; i < VL_range ; i++ ) {
			//translate(0, VL_translate );
			g.noFill( );

			if ( VL_W == true ) {
				g.stroke( 255 , VL_opacity );
			}
			if ( VL_R == true ) {
				g.stroke( 255 , 0 , 0 , VL_opacity );
			}
			if ( VL_Y == true ) {
				g.stroke( 255 , 255 , 0 , VL_opacity );
			}
			if ( VL_B == true ) {
				g.stroke( 0 , 0 , 255 , VL_opacity );
			}

			g.strokeWeight( modernist_weight );

			g.line( 0 , i * VL_translate , g.width / 2 + getEqualizer( ).getLeft( i ) * modernist_audioInLEFT , i * VL_translate );
		}
	}

	void vertRight_Lines( PGraphics g ) {

		g.pushMatrix( );

		if ( VL_translate > 0 ) {
			g.translate( 0 , ( - ( g.height - ( VL_range * VL_translate ) ) % VL_translate ) );
		}
		// draw the waveforms
		for ( int i = 0 ; i < VR_range ; i++ ) {

			g.noFill( );

			if ( VR_W == true ) {
				g.stroke( 255 , VR_opacity );
			}
			if ( VR_R == true ) {
				g.stroke( 255 , 0 , 0 , VR_opacity );
			}
			if ( VR_Y == true ) {
				g.stroke( 255 , 255 , 0 , VR_opacity );
			}
			if ( VR_B == true ) {
				g.stroke( 0 , 0 , 255 , VR_opacity );
			}

			g.strokeWeight( modernist_weight );
			g.line( width / 2 + getEqualizer( ).getRight( i ) * modernist_audioInRIGHT , ( i * -VR_translate ) + g.height , g.width , ( i * -VR_translate ) + g.height );
		}
		g.popMatrix( );
	}

	void horiTop_Lines( PGraphics g ) {

		// draw the waveforms
		for ( int i = 0 ; i < HT_range ; i++ ) {
			g.noFill( );

			if ( HT_W == true ) {
				g.stroke( 255 , HT_opacity );
			}
			if ( HT_R == true ) {
				g.stroke( 255 , 0 , 0 , HT_opacity );
			}
			if ( HT_Y == true ) {
				g.stroke( 255 , 255 , 0 , HT_opacity );
			}
			if ( HT_B == true ) {
				g.stroke( 0 , 0 , 255 , HT_opacity );
			}
			g.strokeWeight( modernist_weight );
			g.line( i * HT_translate , 0 , i * HT_translate , g.height / 2 + getEqualizer( ).getLeft( i ) * modernist_audioInLEFT ); //top (line appears left to right)
		}
	}

	void horiBottom_Lines( PGraphics g ) {

		g.pushMatrix( );
		// draw the waveforms

		if ( HT_translate > 0 ) {
			g.translate( ( - ( g.width - ( HT_range * HT_translate ) ) % HT_translate ) , 0 );
		}

		for ( int i = 0 ; i < HB_range ; i++ ) {

			g.noFill( );

			if ( HB_W == true ) {
				g.stroke( 255 , HB_opacity );
			}
			if ( HB_R == true ) {
				g.stroke( 255 , 0 , 0 , HB_opacity );
			}
			if ( HB_Y == true ) {
				g.stroke( 255 , 255 , 0 , HB_opacity );
			}
			if ( HB_B == true ) {
				g.stroke( 0 , 0 , 255 , HB_opacity );
			}

			g.strokeWeight( modernist_weight );
			g.line( ( i * -HB_translate ) + g.width , g.height / 2 + getEqualizer( ).getRight( i ) * modernist_audioInRIGHT , ( i * -HB_translate ) + g.width , g.height );
		}
		g.popMatrix( );
	}

	public void update( ) {
	}

	public void activate( PGraphics g ) {

		continuum.midi.get( continuum.midi0 ).clear( );
		continuum.midi.get( continuum.midi1 ).clear( );

		continuum.midi.get( continuum.midi1 ).assign( 21 , "modernist_audioInLEFT" );
		continuum.midi.get( continuum.midi1 ).assign( 22 , "modernist_audioInRIGHT" );
		continuum.midi.get( continuum.midi1 ).assign( 23 , "modernist_weight" );

		continuum.midi.get( continuum.midi0 ).assign( 32 , "vertLeft" );
		continuum.midi.get( continuum.midi0 ).assign( 48 , "vertRight" );
		continuum.midi.get( continuum.midi0 ).assign( 64 , "horiTop" );
		continuum.midi.get( continuum.midi1 ).assign( 32 , "horiBottom" );

		continuum.midi.get( continuum.midi0 ).assign( 33 , "VL_W" );
		continuum.midi.get( continuum.midi0 ).assign( 34 , "VL_R" );
		continuum.midi.get( continuum.midi0 ).assign( 35 , "VL_Y" );
		continuum.midi.get( continuum.midi0 ).assign( 36 , "VL_B" );

		continuum.midi.get( continuum.midi0 ).assign( 16 , "VL_range" );
		continuum.midi.get( continuum.midi0 ).assign( 17 , "VL_translate" );

		continuum.midi.get( continuum.midi1 ).assign( 16 , "VL_opacity" );

		continuum.midi.get( continuum.midi0 ).assign( 49 , "VR_W" );
		continuum.midi.get( continuum.midi0 ).assign( 50 , "VR_R" );
		continuum.midi.get( continuum.midi0 ).assign( 51 , "VR_Y" );
		continuum.midi.get( continuum.midi0 ).assign( 52 , "VR_B" );

		continuum.midi.get( continuum.midi0 ).assign( 18 , "VR_range" );
		continuum.midi.get( continuum.midi0 ).assign( 19 , "VR_translate" );

		continuum.midi.get( continuum.midi1 ).assign( 17 , "VR_opacity" );

		continuum.midi.get( continuum.midi0 ).assign( 65 , "HT_W" );
		continuum.midi.get( continuum.midi0 ).assign( 66 , "HT_R" );
		continuum.midi.get( continuum.midi0 ).assign( 67 , "HT_Y" );
		continuum.midi.get( continuum.midi0 ).assign( 68 , "HT_B" );

		continuum.midi.get( continuum.midi0 ).assign( 20 , "HT_range" );
		continuum.midi.get( continuum.midi0 ).assign( 21 , "HT_translate" );

		continuum.midi.get( continuum.midi1 ).assign( 18 , "HT_opacity" );

		continuum.midi.get( continuum.midi1 ).assign( 33 , "HB_W" );
		continuum.midi.get( continuum.midi1 ).assign( 34 , "HB_R" );
		continuum.midi.get( continuum.midi1 ).assign( 35 , "HB_Y" );
		continuum.midi.get( continuum.midi1 ).assign( 36 , "HB_B" );

		continuum.midi.get( continuum.midi0 ).assign( 22 , "HB_range" );
		continuum.midi.get( continuum.midi0 ).assign( 23 , "HB_translate" );

		continuum.midi.get( continuum.midi1 ).assign( 19 , "HB_opacity" );

	}
}
