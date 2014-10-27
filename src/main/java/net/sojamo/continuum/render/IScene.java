package net.sojamo.continuum.render;

import processing.core.PGraphics;

public interface IScene {

	public void setup( );

	public void update( );

	public void display( PGraphics g );

	public void activate( PGraphics g );
}
