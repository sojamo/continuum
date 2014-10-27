package net.sojamo.continuum.midi;

import static net.sojamo.continuum.Continuum.*;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;


import processing.core.PApplet;
import net.sojamo.continuum.Continuum;
import controlP5.Bang;
import controlP5.Button;
import controlP5.CColor;
import controlP5.Controller;
import controlP5.Knob;
import controlP5.Slider;
import controlP5.Toggle;

public class MidiInterpreter {

	private final Map< String , String > midimapper = new HashMap< String , String >( );
	private final String device;
	private final Continuum continuum;

	public MidiInterpreter( final Continuum theContinuum , final String theDevice ) {

		continuum = theContinuum;
		device = theDevice;

		Receiver r = new Receiver( ) {

			public void send( MidiMessage msg , long timeStamp ) {

				byte[] b = msg.getMessage( );

				if ( b[ 0 ] != -48 ) {

					Object index = ( midimapper.get( ref( theDevice , b[ 1 ] ) ) );

					if ( index != null ) {

						CColor color = continuum.gui.gui( ).getColor( );
						Controller c = continuum.gui.gui( ).getController( index.toString( ) );
						if ( c instanceof Slider || c instanceof Knob ) {
							float min = c.getMin( );
							float max = c.getMax( );
							c.setValue( ( int ) ( b[ 2 ] ) > 125 ? max + ( max - min ) : PApplet.map( b[ 2 ] , 0 , 127 , min , max ) );
						} else if ( c instanceof Button ) {
							if ( b[ 2 ] > 0 ) {
								c.setValue( c.getValue( ) );
								c.setColorBackground( color.getActive( ) );
							} else {
								c.setColorBackground( color.getBackground( ) );
							}
						} else if ( c instanceof Bang ) {
							if ( b[ 2 ] > 0 ) {
								c.setValue( c.getValue( ) );
								c.setColorForeground( color.getForeground( ) );
							} else {
								c.setColorForeground( color.getBackground( ) );
							}
						} else if ( c instanceof Toggle ) {
							if ( b[ 2 ] > 0 ) {
								( ( Toggle ) c ).toggle( );
							}
						}
					}
				}
			}

			public void close( ) {
			}
		};

		if ( isNumeric( theDevice ) ) {
			new MidiSimple( i( theDevice ) , r );
		} else {
			new MidiSimple( theDevice , r );
		}
	}

	public void assign( int theId , String theControllerId ) {
		midimapper.put( ref( device , theId ) , theControllerId );
	}

	public void clear( ) {
		midimapper.clear( );
	}

	String ref( String theDevice , int theIndex ) {
		return theDevice + "-" + theIndex;
	}


}
