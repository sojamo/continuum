package sojamo.continuum.util;

import sojamo.continuum.Continuum;

public class Damper {

	private boolean active;
	private final float d0;
	private float d1;

	Damper( float theDamper ) {
		d0 = theDamper;
	}

	void setActive( boolean theValue ) {
		active = theValue;
	}

	Damper update( ) {
		d1 += ( active ? d0 : -d0 );
		d1 = Continuum.constrainValue( d1 , 0 , 1 );
		return this;
	}

	float get( ) {
		return d1;
	}
}
