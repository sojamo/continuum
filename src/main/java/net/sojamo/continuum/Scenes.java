package net.sojamo.continuum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sojamo.continuum.render.AScene;
import net.sojamo.continuum.sets.base.Idle;

public class Scenes {

	private final Continuum continuum;
	private final Map< String , AScene > scenes;

	Scenes( Continuum theContinuum ) {
		continuum = theContinuum;
		scenes = new LinkedHashMap< String , AScene >( );
		addScene( "idle" , new Idle( continuum ) );
		activate( "idle" );
	}

	public Scenes activate( String theId ) {
		AScene scene = get( theId );
		if ( scene != null ) {
			continuum.graphics.setScene( this , theId );
			continuum.gui.set( scene );
		}
		return this;
	}

	public Scenes addScene( String theName , AScene theScene ) {
		theScene.setup( );
		scenes.put( theName , theScene );
		return this;
	}

	public AScene get( String theId ) {
		return scenes.get( theId );
	}

	public List< String > getScenes( ) {
		return new ArrayList< String >( scenes.keySet( ) );
	}

}
