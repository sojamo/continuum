package net.sojamo.continuum.render;

import processing.core.PApplet;
import processing.core.PGraphics;
import net.sojamo.continuum.Continuum;
import net.sojamo.continuum.sound.FFTEqualizer;
import controlP5.ControlEvent;
import controlP5.ControlP5;

public class AScene extends PApplet implements IScene {

	protected final Continuum continuum;
	public final ControlP5 cp5;

	public AScene( Continuum theContinuum ) {
		continuum = theContinuum;
		cp5 = new ControlP5( continuum );
		cp5.setAutoDraw( false );
		height = Continuum.RENDER_HEIGHT;
		width = Continuum.RENDER_WIDTH;
	}

	public FFTEqualizer getEqualizer( ) {
		return continuum.audio.getEqualizer( );
	}

	//	public MidiInterpreter getMidi(String theIndex ) {
	//		return null;
	//	}

	public void draw() {
		display(g);
	}
	
	@Override public void update( ) {
	}

	@Override public void display( PGraphics g ) {
	}

	@Override public void activate( PGraphics g ) {
	}

	public void controlEvent( ControlEvent c ) {
	}

}
