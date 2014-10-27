package net.sojamo.continuum.sets.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;


import net.sojamo.continuum.Continuum;
import net.sojamo.continuum.render.AScene;

public class ReadScene {

	final private Continuum continuum;

	public ReadScene( Continuum theContinuum ) {
		continuum = theContinuum;
	}

	
	public AScene compile( String thePath , String theName , String thePackage ) {
		final String myPackage = thePackage == null ? "sojamo.continuum.sets.test" : thePackage;
		final String myName = theName == null ? "Test" : theName;
		String myBody = "";
		StringBuffer src = new StringBuffer( );
		src.append( String.format( "package %s;\n\n" , myPackage ) );
		src.append( String.format( "import %s;\n" , "sojamo.continuum.*" ) );
		src.append( String.format( "import %s;\n" , "sojamo.continuum.render.*" ) );
		src.append( String.format( "import %s;\n" , "processing.core.*" ) );
		src.append( "\n" );
		try {
			final String myOriginal = new String( Files.readAllBytes( Paths.get( thePath + "/convertToContinuum_a.pde" ) ) );
			myBody = new String( myOriginal );
			myBody = myBody.trim( ).replaceAll( "(?m)^import.*" , "" ); /* remove all lines that start with import */
		} catch ( IOException e ) {
			return new AScene( continuum );
		}

		src.append( String.format( "public class %s extends AScene {\n\n" , myName ) );
		src.append( String.format( "public %s( Continuum theContinuum ) {\n" , myName ) );
		src.append( "super( theContinuum );\n" );
		src.append( "}\n" );
		// filter myBody for imports and append on top 
		src.append( String.format( "\n%s\n" , myBody ) );
		src.append( "}" );

		String fullName = String.format( "%s.%s" , myPackage , myName );
		String userDir = System.getProperty( "user.dir" );
		String delimiter = ":";

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler( );
		JavaFileManager fileManager = new ClassFileManager( compiler.getStandardFileManager( null , null , null ) );
		List< JavaFileObject > jfiles = new ArrayList< JavaFileObject >( );
		jfiles.add( new CharSequenceJavaFileObject( fullName , src ) );

		StringBuffer classpath = new StringBuffer( );
		classpath.append( userDir ).append( "/" ).append( "target/classes" ).append( delimiter );
		classpath.append( userDir ).append( "/" ).append( "lib/communication/oscP5.jar" ).append( delimiter );
		classpath.append( userDir ).append( "/" ).append( "lib/processing/core.jar" ).append( delimiter );
		classpath.append( userDir ).append( "/" ).append( "lib/sound/*" ).append( delimiter );
		classpath.append( userDir ).append( "/" ).append( "lib/visual/controlP5.jar" ).append( delimiter );
		List< String > optionList = new ArrayList< String >( );
		optionList.addAll( Arrays.asList( "-classpath" , classpath.toString( ) ) );

		compiler.getTask( null , fileManager , null , optionList , null , jfiles ).call( );

		try {
			return ( AScene ) fileManager.getClassLoader( null ).loadClass( fullName ).getConstructor( new Class[] { Continuum.class } ).newInstance( continuum );
		} catch ( InstantiationException e ) {
			e.printStackTrace( );
		} catch ( IllegalAccessException e ) {
			e.printStackTrace( );
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace( );
		} catch ( InvocationTargetException e ) {
			e.printStackTrace( );
		} catch ( NoSuchMethodException e ) {
			e.printStackTrace( );
		}
		return new AScene( continuum );
	}

	public static void main( String ... args ) {
		new ReadScene( new Continuum( ) );
	}

	/* dynamic in memory compilation http://www.javablogging.com/dynamic-in-memory-compilation/ */

	public class ClassFileManager extends ForwardingJavaFileManager {
		/**
		* Instance of JavaClassObject that will store the
		* compiled bytecode of our class
		*/
		private JavaClassObject jclassObject;

		/**
		* Will initialize the manager with the specified
		* standard java file manager
		*
		* @param standardManger
		*/
		public ClassFileManager( StandardJavaFileManager standardManager ) {
			super( standardManager );
		}

		/**
		* Will be used by us to get the class loader for our
		* compiled class. It creates an anonymous class
		* extending the SecureClassLoader which uses the
		* byte code created by the compiler and stored in
		* the JavaClassObject, and returns the Class for it
		*/
		@Override public ClassLoader getClassLoader( Location location ) {
			return new SecureClassLoader( ) {
				@Override protected Class< ? > findClass( String name ) throws ClassNotFoundException {
					byte[] b = jclassObject.getBytes( );
					return super.defineClass( name , jclassObject.getBytes( ) , 0 , b.length );
				}
			};
		}

		/**
		* Gives the compiler an instance of the JavaClassObject
		* so that the compiler can write the byte code into it.
		*/
		public JavaFileObject getJavaFileForOutput( Location location , String className , JavaFileObject.Kind kind , FileObject sibling ) throws IOException {
			jclassObject = new JavaClassObject( className , kind );
			return jclassObject;
		}
	}

	public class JavaClassObject extends SimpleJavaFileObject {

		/**
		* Byte code created by the compiler will be stored in this
		* ByteArrayOutputStream so that we can later get the
		* byte array out of it
		* and put it in the memory as an instance of our class.
		*/
		protected final ByteArrayOutputStream bos = new ByteArrayOutputStream( );

		/**
		* Registers the compiled class object under URI
		* containing the class full name
		*
		* @param name
		*            Full name of the compiled class
		* @param kind
		*            Kind of the data. It will be CLASS in our case
		*/
		public JavaClassObject( String name , Kind kind ) {
			super( URI.create( "string:///" + name.replace( '.' , '/' ) + kind.extension ) , kind );
		}

		/**
		* Will be used by our file manager to get the byte code that
		* can be put into memory to instantiate our class
		*
		* @return compiled byte code
		*/
		public byte[] getBytes( ) {
			return bos.toByteArray( );
		}

		/**
		* Will provide the compiler with an output stream that leads
		* to our byte array. This way the compiler will write everything
		* into the byte array that we will instantiate later
		*/
		@Override public OutputStream openOutputStream( ) throws IOException {
			return bos;
		}
	}

	public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

		/**
		* CharSequence representing the source code to be compiled
		*/
		private CharSequence content;

		/**
		* This constructor will store the source code in the
		* internal "content" variable and register it as a
		* source code, using a URI containing the class full name
		*
		* @param className
		*            name of the public class in the source code
		* @param content
		*            source code to compile
		*/
		public CharSequenceJavaFileObject( String className , CharSequence content ) {
			super( URI.create( "string:///" + className.replace( '.' , '/' ) + Kind.SOURCE.extension ) , Kind.SOURCE );
			this.content = content;
		}

		/**
		* Answers the CharSequence to be compiled. It will give
		* the source code stored in variable "content"
		*/
		@Override public CharSequence getCharContent( boolean ignoreEncodingErrors ) {
			return content;
		}
	}
}
