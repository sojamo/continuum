package sojamo.continuum;

import java.util.LinkedHashMap;
import java.util.Map;

import sojamo.continuum.midi.MidiInterpreter;


public class Midi {

	private final Continuum continuum;
	private final Map< String , MidiInterpreter > midi = new LinkedHashMap< String , MidiInterpreter >( );

	public Midi( Continuum theContinuum ) {
		continuum = theContinuum;
	}

	public Midi add( String theId , String theDevice ) {
		midi.put( theId , new MidiInterpreter( continuum , theDevice ) );
		return this;
	}

	public MidiInterpreter get( String theId ) {
		return midi.get( theId );
	}

}
