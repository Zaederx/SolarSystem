package solarsystem;



import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.ImageComponent3D;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Texture3D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.util.texture.TextureCoords;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import celestialBody.Earth;
import celestialBody.Mars;
import celestialBody.Mercury;
import celestialBody.Sun;
import celestialBody.Venus;

public class SolarSystem extends JFrame {

	public Canvas3D myCanvas3D;
	
	/**
	 * Set's up a solar system on the canvas.
	 */
	public SolarSystem () {
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Container container = getContentPane();
//		BorderLayout bL = new BorderLayout();
//		container.setLayout(bL);
//		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
//		container.add("Center",canvas);
//		BranchGroup scene = createSceneGraph();
//		SimpleUniverse universe = new SimpleUniverse(canvas);
//		universe.getViewingPlatform().setNominalViewingTransform();
//		universe.addBranchGraph(scene);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		getContentPane().add("Center", myCanvas3D);
		SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
		simpUniv.getViewingPlatform().setNominalViewingTransform();
		//viewing platform
		TransformGroup cameraTG = simpUniv.getViewingPlatform().getViewPlatformTransform();
		
		//position of viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
		//move along z axis by 10.0f ("move away from the screen")
		translate.set(0.0f, 0.0f, 25.0f);
		T3D.setTranslation(translate);
		//add translate transform to cameraTG
		cameraTG.setTransform(T3D);
		
		//Add scene to Universe
		BranchGroup scene = createSceneGraph();
		simpUniv.addBranchGraph(scene);
		
		//addLight(simpUniv);
		setTitle("Step 1: A simple cube");
		setSize(700,700);
		setVisible(true);
	}

	/**
	 * Adds light to the scene.
	 * @param su - universe you want to add light to
	 */
	public void addLight(SimpleUniverse su) {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
		
		//set up light in the scene
		Color3f ambientLColor = new Color3f(0.6f,0.6f,0.6f);
		AmbientLight amLight = new AmbientLight(ambientLColor);
		amLight.setBounds(bounds);
		bgLight.addChild(amLight);
		
		//directionaLight
		Color3f lightColor = new Color3f(1.0f,1.0f,1.0f);
		Vector3f direction = new Vector3f(-1.0f,0.0f,-0.5f);
		DirectionalLight light = new DirectionalLight(lightColor,direction);
		
		//light works only within set bounds
		light.setInfluencingBounds(bounds);
		bgLight.addChild(light);
		
		Vector3f direction2 = new Vector3f(1.0f,-1.0f,0.5f);
		DirectionalLight light2 = new DirectionalLight(lightColor,direction2);
		light2.setInfluencingBounds(bounds);
		bgLight.addChild(light2);
		
		su.addBranchGraph(bgLight);
	}
	
	/**
	 * Creates a new SceneGraph.
	 * @return
	 */
	public BranchGroup createSceneGraph () {
		//creates the bound of the universe
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0);
		TextureLoader textureLoader = new TextureLoader("src/textures/staryBackground.jpg", null);
		Background bgImage = new Background(textureLoader.getImage());
		bgImage.setApplicationBounds(bounds);
		
		//single branch group
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(bgImage);
		//transform group for the branch group
		TransformGroup sunTG = new TransformGroup();
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//create 3D shapes and appearances
		Sun sun = new Sun();
		Mercury mercury = new Mercury();
		Venus venus = new Venus();
		Earth earth = new Earth();
		Mars mars = new Mars();
		
		
		//TransformGroup
		Transform3D mercuryT = new Transform3D();
		TransformGroup mercuryTG = createTG(0.0,0.0,-5, 2.0,2.0,2.0,mercuryT);
		mercuryTG.addChild(mercury.getCelestialBody());
		
		
		Transform3D venusT = new Transform3D();
		TransformGroup venusTG = createTG(0.0,0.0,-10, 2.0,2.0,2.0,venusT);
		venusTG.addChild(venus.getCelestialBody());
		
		Transform3D earthT = new Transform3D();
		TransformGroup earthTG = createTG(0.0,0.0,-15, 2.0,2.0,2.0,earthT);
		earthTG.addChild(earth.getCelestialBody());
		
		Transform3D marsT = new Transform3D();
		TransformGroup marsTG = createTG(0.0,0.0,-20, 2.0,2.0,2.0,marsT);
		Transform3D jupiterT = new Transform3D();
		TransformGroup jupiterTG = createTG(0.0,0.0,-25, 2.0,2.0,2.0,earthT);
//		Sphere sun = celestialBody(.5f, 1f, 1f, 0f);
		
		
		
//		//create appearance for box
//		Appearance greenApp = new Appearance();
//		Color3f greenColor = new Color3f(0.0f, 1.0f,0.0f);
//		ColoringAttributes greenCA = new ColoringAttributes();
//		greenCA.setColor(greenColor);
//		greenApp.setColoringAttributes(greenCA);
//		
//		
//		//Create box and add the appearance
//		Cylinder planetaryRing = new Cylinder(0.8f,0.1f, greenApp);
//		
		//*****Mercury
		//matrix for translation t1
//		Matrix4d matrix = new Matrix4d();
//		Transform3D mercuryT = new Transform3D();
//		TransformGroup mercuryTG = createTG(0.0,0.0,-5, 2.0,2.0,2.0,mercuryT, matrix);
//		Sphere mercury = celestialBody(0.5f, 1f, 0f, 0f);

//		Texture texImage = new TextureLoader("src/solarsystem/mercury.jpg", this).getTexture();
//		File pic = new File("src/solarsystem/mercury.jpg");
//	
//		TextureLoader loader = new TextureLoader("src/textures/mercury.jpg", this);
////		ImageComponent2D image = loader.getImage();
//		ImageComponent2D image = loader.getScaledImage(256, 256);
//		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(),image.getHeight());
//
////		Texture3D texture = new Texture3D(Texture3D.BASE_LEVEL, Texture3D.RGB, image.getWidth(),image.getHeight(), 1 );
//		texture.setImage(0, image);
//		
//		Appearance mTextureApp = new Appearance();
//		mTextureApp.setTexture(texture);
//		
//		TextureAttributes textureAttr = new TextureAttributes();
//		textureAttr.setTextureMode(TextureAttributes.REPLACE);
//		mTextureApp.setTextureAttributes(textureAttr);
//		
//		Material material = new Material();
//		material.setShininess(0f);
//		
//		mTextureApp.setMaterial(material);
//		
//		TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
//				TexCoordGeneration.TEXTURE_COORDINATE_2);
//		
//		mTextureApp.setTexCoordGeneration(tcg);
//		mercury.setAppearance(mTextureApp);
	
		//***Mercury*** END
		
		
		//**Venus***
//		Transform3D venusT = new Transform3D();//replace with space ship later - has and interesting path - create new path for venus
//		TransformGroup venusTG = createTG(0.0,0.0,-5, 2.0,2.0,2.0, venusT); 
//		Sphere venus = celestialBody(0.5f, 1f, .7f, .5f);
		
		
		//**Venus *** END
		
		
		//**Space ship***
		Appearance c1App = new Appearance();
		Cone cone1 = new Cone(0.5f,1f,c1App);
		Appearance c2App = new Appearance();
		Cone cone2 = new Cone(0.4f, 0.5f, c2App);
		
		
		//***Spaceship end***
		//*****Rotation*****
		TransformGroup rotTG0 = new TransformGroup();
		rotTG0.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha1 = new Alpha(-1, 18000);
		Transform3D yAxis = new Transform3D();
		
		//float variable controls how much of a rotation 2.0f = full rotation
		RotationInterpolator rotator0 = new RotationInterpolator(rotAlpha1, rotTG0, yAxis, 0.0f, (float) Math.PI * (2.0f));
		rotator0.setSchedulingBounds(bounds);	
		
		
		Transform3D t = new Transform3D();
		t.setScale(new Vector3d(2.0,2.0,2.0));
		t.setTranslation(new Vector3d(0.0,0.0,-5));
		Transform3D helperT3D = new Transform3D();
		helperT3D.rotZ(Math.PI/6);
		t.mul(helperT3D);
		helperT3D.rotX(Math.PI/4);//rotation about X tilts it forward or backward
		t.mul(helperT3D);
		TransformGroup rotTG1 = new TransformGroup(t);
		//*****Rotation*** END
		
		//*****Rotation2*********
		TransformGroup rotTG2 = new TransformGroup();
		rotTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha2 = new Alpha(-1, 18000);
		Transform3D yAxis2 = new Transform3D();
		RotationInterpolator rotator2 = new RotationInterpolator(rotAlpha2, rotTG2, yAxis2,0.0f, (float) Math.PI * 2.0f);
		rotator2.setSchedulingBounds(bounds);
		//****Rotation2******
		
		
		//***Rotation3
		TransformGroup rotTG3 = new TransformGroup();
		rotTG3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha3 = new Alpha(-1,18000);
		Transform3D yAxis3 = new Transform3D();
		RotationInterpolator rotator3 = new RotationInterpolator(rotAlpha3, rotTG3, yAxis3, 0.0f, (float) Math.PI*2.0f);
		rotator3.setSchedulingBounds(bounds);
		//****End Rotation3***
		
	
//		BranchGroup planets = new BranchGroup();
//		planets.addChild(mercuryTG);
//		planets.addChild(venusTG);
//		planets.addChild(earthTG);
//		planets.addChild(marsTG);
//		planets.addChild(jupiterTG);
		mercuryTG.addChild(rotator0);
//		mercuryBG.addChildAll(mercury.getCelestialBody(),ro);
		//make edge relations with the scene graph nodes
		//cube 1 translated -5 along z axis
		objRoot.addChild(sunTG);
		sunTG.addChild(rotTG0);
//		rotTG0.addChild(rotator0);
		rotTG0.addChild(mercuryTG);
//		rotTG0.addChild();
//			rotTG2.addChild(mercuryTG);
//			mercuryTG.addChild(mercury);
//			rotTG1.addChild(mercuryTG);
//			rotTG1.addChild(rotator2);
//			rotTG1.addChild(rotTG2);
			rotTG2.addChild(rotator2);
//			rotTG1.addChild(planetaryRing);
//				rotTG2.addChild(venus.getCelestialBody());
//				rotTG2.addChild(rotator2);
				rotTG2.addChild(rotTG3);
				
//				rotTG2.addChild(venusTG);
//				venusTG.addChild(venus);
//		rotTG3.addChild(rotator3);
					rotTG3.addChild(mars.getCelestialBody());
			
		sunTG.addChild(sun.getCelestialBody());
		
		
		//Create rotation behaviour
		MouseRotate behaviourRot = new MouseRotate();
		behaviourRot.setTransformGroup(sunTG);
		objRoot.addChild(behaviourRot);
		behaviourRot.setSchedulingBounds(bounds);
		
		//MouseRotate behaviour node
		MouseZoom behaviourZoom = new MouseZoom();
		behaviourZoom.setTransformGroup(sunTG);
		objRoot.addChild(behaviourZoom);
		behaviourZoom.setSchedulingBounds(bounds);
		
		//Translate Behaviour
		MouseTranslate behaviourTrans = new MouseTranslate();
		behaviourTrans.setTransformGroup(sunTG);
		objRoot.addChild(behaviourTrans);
		behaviourTrans.setSchedulingBounds(bounds);
		
		objRoot.compile();
		return objRoot;
	}
	
//	/**
//	 * 
//	 * @param rotator
//	 * @param rotation
//	 * @param rotationAlpha
//	 * @param axis
//	 * @param bounds
//	 * @return
//	 */
//	public TransformGroup rotationTG(RotationInterpolator rotator, float rotation, Alpha rotationAlpha, Transform3D axis,  BoundingSphere bounds ) {
//		TransformGroup rotTG = new TransformGroup();
//		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		rotator.setSchedulingBounds(bounds);
//		Transform3D t = new Transform3D();
//		t.setScale(new Vector3d(2.0,2.0,2.0));
//		t.setTranslation(new Vector3d(0.0,0.0,-5));
//		Transform3D helperT3D = new Transform3D();
//		helperT3D.rotZ(Math.PI);
//		t.mul(helperT3D);
//		t.rotX(Math.PI/2);
//		
//		return rotTG;
//	}


	/**
	 * Helper method to create Transform groups.
	 * Helps to make groups that 
	 * @param transX
	 * @param transY
	 * @param transZ
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 * @param planet
	 * @return
	 */
	public TransformGroup createTG(double transX, double transY, double transZ,
									double scaleX, double scaleY,double scaleZ, Transform3D planetT) {
		planetT = new Transform3D();
		planetT.setTranslation(new Vector3d(transX,transY, transZ));
		planetT.setScale(new Vector3d(scaleX,scaleY,scaleZ));
		TransformGroup planetTG = new TransformGroup(planetT);
		
		return planetTG;
	}
	
	/**
	 * Helper method to create Transform groups and get it's matrix.
	 * Good for if you wanted to print the transforms's matrix.
	 * @param transX
	 * @param transY
	 * @param transZ
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 * @param planet
	 * @param matrix
	 * 
	 * @return
	 */
	public TransformGroup createTG(double transX, double transY, double transZ,
			double scaleX, double scaleY,double scaleZ,Transform3D planetT, Matrix4d matrix) {
			planetT = new Transform3D();
			planetT.setTranslation(new Vector3d(transX,transY, transZ));
			planetT.setScale(new Vector3d(scaleX,scaleY,scaleZ));
			planetT.get(matrix);
			TransformGroup planetTG = new TransformGroup(planetT);

			return planetTG;
}
	/**
	 * Creates a new celestialBody (planet, moon or star).
	 * @param sphereSize
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public Sphere celestialBody(float sphereSize, float red, float green, float blue) {
		Sphere celestialBody = new Sphere(sphereSize);
		Appearance appearance = new Appearance();
		Color3f color = new Color3f(red,green,blue);
		ColoringAttributes cAttributes = new ColoringAttributes();
		cAttributes.setColor(color);
		appearance.setColoringAttributes(cAttributes);
		celestialBody.setAppearance(appearance);
		
		return celestialBody;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main (String [] args) {
		SolarSystem  solarSystem = new SolarSystem();
	}
	
	
}
