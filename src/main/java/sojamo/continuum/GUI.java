package sojamo.continuum;

import java.util.List;

import sojamo.continuum.render.AScene;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.RadioButton;

public class GUI {

	private final Continuum continuum;
	private final ControlP5 cp5;
	private ControlP5 gui;

	public GUI( Continuum theContinuum ) {
		continuum = theContinuum;
		cp5 = new ControlP5( continuum );
		cp5.setAutoDraw( false );
	}

	public ControlP5 cp5( ) {
		return cp5;
	}

	public void draw( ) {
		cp5.draw( );
		if ( gui != null ) {
			gui.draw( );
		}
	}
	
	public ControlP5 gui() {
		return gui;
	}

	public void set( AScene theScene ) {
		gui = theScene.cp5;
		gui.setPosition( 0 , 340 );
	}

	public void scenes( int theValue ) {
		String scene = continuum.scenes.getScenes( ).get( theValue );
		continuum.scenes.activate( scene );
	}

	public void set( Scenes theScenes ) {
		List< String > l = theScenes.getScenes( );
		RadioButton r = cp5.addRadioButton( "scenes" ).setPosition( 410 , 30 ).setItemWidth( 150 ).setItemHeight( 40 ).setSpacingRow( 1 );
		r.plugTo( this , "scenes" );
		r.align( ControlP5.CENTER , ControlP5.CENTER );
		for ( int n = 0 ; n < l.size( ) ; n++ ) {
			r.addItem( l.get( n ) , n );
		}

	}

}
