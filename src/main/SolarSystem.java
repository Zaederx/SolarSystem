package main;



import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.util.Hashtable;

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
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Texture3D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.jogamp.opengl.util.texture.TextureCoords;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import celestialBody.Earth;
import celestialBody.Jupiter;
import celestialBody.Mars;
import celestialBody.Mercury;
import celestialBody.Neptune;
import celestialBody.Saturn;
import celestialBody.Sun;
import celestialBody.Uranus;
import celestialBody.Venus;
import helper.Rotation;

/**
 * This class is the main class used to run the application.
 * It generates a a solar system scene graph and 
 * runs a simulation.
 * @author zacharyishmael
 *
 */
public class SolarSystem extends JFrame {

	public Canvas3D myCanvas3D;
	
	/**
	 * Set's up a solar system on the canvas.
	 */
	public SolarSystem () {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		getContentPane().add("Center", myCanvas3D);
		SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
		simpUniv.getViewingPlatform().setNominalViewingTransform();
		
		OrbitBehavior orbit = new OrbitBehavior(myCanvas3D);
		orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE));
		orbit.setReverseTranslate(true);
//		orbit.setZoomFactor(arg0);
		simpUniv.getViewingPlatform().setViewPlatformBehavior(orbit);
	
		//viewing platform
		TransformGroup cameraTG = simpUniv.getViewingPlatform().getViewPlatformTransform();
		
		//position of viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
		//move along z axis by 25.0f ("move away from the screen")
		translate.set(-0.5f, 0.3f, 15f);


		T3D.setTranslation(translate);

		cameraTG.setTransform(T3D);
		
		//Add scene to Universe
		BranchGroup scene = createSceneGraph();
		simpUniv.addBranchGraph(scene);
		addLight(simpUniv);
		
//		addSideLightLeft(simpUniv);
		setTitle("Zach's  UNIVERSE!!!");
		setSize(700,700);
		setVisible(true);
	}

	public void addLight (SimpleUniverse su) {
		BranchGroup amLightGroup = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0),100);
		Color3f lightC = new Color3f(1,1,1);
		Vector3f dir = new Vector3f(-1f,0f,-.5f);
		AmbientLight amLight = new AmbientLight(lightC);
		
		amLight.setInfluencingBounds(bounds);
		amLightGroup.addChild(amLight);
//		
		su.addBranchGraph(amLightGroup);
	} 

	
	/**
	 * Can be used to add directional light to scene.
	 * (Tends to look better without this though :))
	 * Instead of the sun illumminating the scene,
	 * an ethereal light from the galaxy lights the scene from the left.
	 * @param su
	 */
	public void addSideLightLeft(SimpleUniverse su) {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
		//*****Light2
				//light works only within set bounds	
		Color3f lightColor = new Color3f(0.6f,0.6f,0.6f);
		Vector3f direction2 = new Vector3f(-10f,-0.5f,-1f);
//		Vector3f direction2 = new Vector3f(0.0f,0.0f,0.0f); // light comes side on
		DirectionalLight light2 = new DirectionalLight(lightColor,direction2);
		//***Light 2 End
		light2.setInfluencingBounds(bounds);
		bgLight.addChild(light2);
		
		su.addBranchGraph(bgLight);
	}
	
	/**
	 * Can be used to add a directional light to the scene.
	 * (Tends to look better without this though :))
	 * Instead of the sun ilumminating the scene,
	 * an ethereal light from the galaxy lights the scene fomr the right.
	 */
	public void addSideLightRight(SimpleUniverse su) {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
//		//directionaLight
		Color3f lightColor = new Color3f(0.6f,0.6f,0.6f);
		Vector3f direction = new Vector3f(-1.0f,0.0f,-0.5f);
		DirectionalLight light = new DirectionalLight(lightColor,direction);
		
		light.setInfluencingBounds(bounds);
		bgLight.addChild(light);
		
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
//		Background bgImage = new Background(textureLoader.getImage());
//		bgImage.setApplicationBounds(bounds);
		
		
		Appearance app = new Appearance();
		Texture2D texture = (Texture2D) textureLoader.getTexture();
		app.setTexture(texture);
		
		PolygonAttributes poly = new PolygonAttributes();
		poly.setCullFace(PolygonAttributes.CULL_NONE);
		poly.setBackFaceNormalFlip(true);
		app.setPolygonAttributes(poly);
		
		Sphere skySphere = new Sphere(100.0f,Sphere.GENERATE_NORMALS_INWARD|Sphere.GENERATE_TEXTURE_COORDS, 62, app);		
		
		BranchGroup skyBranch = new BranchGroup();
		
		skyBranch.addChild(skySphere);
		
		Background bgImage = new Background(skyBranch);
		bgImage.setApplicationBounds(bounds);
		bgImage.setGeometry(skyBranch);
		//single branch group
		BranchGroup objRoot = new BranchGroup();
//		objRoot.addChild(bgImage);
		
		
		//create 3D shapes and appearances
		Sun sun = new Sun(bounds, true, true);
		Mercury mercury = new Mercury(true);
		Venus venus = new Venus(true);
		Earth earth = new Earth(true);
		Mars mars = new Mars(true);
		Jupiter jupiter = new Jupiter(true);
		Saturn saturn = new Saturn(true);
		Uranus uranus = new Uranus(true);
		Neptune neptune = new Neptune(true);
		
		
		ObjectFile fileLoader = new ObjectFile(ObjectFile.RESIZE);
		Scene shipScene = null;
		File file = new File ("src/textures/SpaceShip.obj");
//		file.setBasePath("src/textures");
		try {
			shipScene = fileLoader.load(file.toURI().toURL());
			
		}
		catch (Exception e) {
			System.out.println("Object failed to load not goood"+e);
		}
		
		
		


	
		//TransformGroup
		
		//transform group for the branch group
		
		//Setting up ambientLight BranchGroup
		BranchGroup amLightGroup = new BranchGroup();
		Color3f lightC = new Color3f(1,1,1);
		Vector3f dir = new Vector3f(-1f,0f,-.5f);
		AmbientLight amLight = new AmbientLight(lightC);
		amLight.setInfluencingBounds(bounds);
		amLightGroup.addChild(amLight);
		
		//Setting up space ship transform
		Transform3D shipT = new Transform3D();
		TransformGroup shipTG = createTG(2,2,2, 0.6,0.6,0.6, shipT);
		shipTG.addChild(shipScene.getSceneGroup());
		shipTG.addChild(amLightGroup);
		shipTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up sun transform
		Transform3D sunT = new Transform3D();
		TransformGroup sunTG = createTG(0.0,0.0,0, 0.1,0.1,0.1, sunT);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//Setting up Mercury Transform
		Transform3D mercuryT = new Transform3D();
		TransformGroup mercuryTG = createTG(0.0,0.0,-1.5, 2.0,2.0,2.0,mercuryT);
		mercuryTG.addChild(mercury.getCelestialBody());
		mercuryTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Venus Transform
		Transform3D venusT = new Transform3D();
		TransformGroup venusTG = createTG(0.0,0.0,-2.5, 2.0,2.0,2.0,venusT);
		venusTG.addChild(venus.getCelestialBody());
		venusTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Earth Transform
		Transform3D earthT = new Transform3D();
		TransformGroup earthTG = createTG(0.0,0.0,-4, 2.0,2.0,2.0,earthT);
		earthTG.addChild(earth.getCelestialBody());
		earthTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Mars Transform
		Transform3D marsT = new Transform3D();
		TransformGroup marsTG = createTG(0.0,0.0,-9, 2.0,2.0,2.0,marsT);
		marsTG.addChild(mars.getCelestialBody());
		marsTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Jupiter Transform
		Transform3D jupiterT = new Transform3D();
		TransformGroup jupiterTG = createTG(0.0,0.0,-14, 2.0,2.0,2.0,jupiterT);
		jupiterTG.addChild(jupiter.getCelestialBody());
		jupiterTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Saturn Transform and it's Ring Transform
		Transform3D saturnT = new Transform3D();
		Transform3D ringT = new Transform3D();
		TransformGroup saturnTG = createTG(0.0,0.0,-20, 2.0,2.0,2.0,saturnT);
		TransformGroup ringTG = createTG(0.0,0.0,0, 4,4,3,ringT);
		saturnTG.addChild(saturn.getCelestialBody());
		ringTG.addChild(saturn.getPlanetaryRing());
		saturnTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Uranus Transform
		Transform3D uranusT = new Transform3D();
		TransformGroup uranusTG = createTG(0.0,0.0,-27, 2.0,2.0,2.0,uranusT);
		uranusTG.addChild(uranus.getCelestialBody());
		uranusTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		//Setting up Neptune Transform
		Transform3D neptuneT = new Transform3D();
		TransformGroup neptuneTG = createTG(0.0,0.0,-30, 2.0,2.0,2.0,neptuneT);
		neptuneTG.addChild(neptune.getCelestialBody());
		neptuneTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		
		
		
		//Setting up Roations
		Rotation r0, r1, r2, r3, r4, r5 ,r6, r7, r8, r9, rShip;
	
		r0 = new Rotation(bounds, true);
		r1 = new Rotation(bounds, 9000, 0f, 2f, Math.PI,false);
		r2 = new Rotation(bounds, 10000, 0f, 2f, Math.PI/4,false);
		r3 = new Rotation(bounds, 20000, 0f, 2f, Math.PI*2,false);
		r4 = new Rotation(bounds, 25000, 0f, 2f, Math.PI/8,false);
		r5 = new Rotation(bounds, 30005, 0f, 2f, Math.PI*3,false);
		r6 = new Rotation(bounds, 90000, 0f, 2f, Math.PI/6 ,false);
		r7 = new Rotation(bounds, 100099, 0f, 2f,Math.PI,false);
		r8 = new Rotation(bounds, 200000, 0f, 2f,Math.PI/12,false);
		rShip = new Rotation(bounds, 1800,0.0f,-2f, 0.0, false);
		
		//Setting Axis tilt for each rotation group 
		//(for each celestialbody / object)
		r1.getyAxis().rotY(Math.PI);
		r2.getyAxis().rotY(Math.PI/4);
		r3.getyAxis().rotY(Math.PI*2);
		r4.getyAxis().rotY(Math.PI/8);
		r5.getyAxis().rotY(Math.PI/6);
		r6.getyAxis().rotY(Math.PI*3);
		r7.getyAxis().rotY(Math.PI/12);
		r8.getyAxis().rotY(Math.PI);
		

		//****making edge relations with the scene graph nodes****
		
		//Adding rotation groups
		objRoot.addChild(r0.getRotTG());
		r0.getRotTG().addChild(sunTG);
		sunTG.addChild(r1.getRotTG());
		sunTG.addChild(r2.getRotTG());
		sunTG.addChild(r3.getRotTG());
		sunTG.addChild(r4.getRotTG());
		sunTG.addChild(r5.getRotTG());
		sunTG.addChild(r6.getRotTG());
		sunTG.addChild(r7.getRotTG());
		sunTG.addChild(r8.getRotTG());
		sunTG.addChild(rShip.getRotTG());
		sunTG.addChild(skyBranch);

		//Adding children to roations groups.
		r1.getRotTG().addChild(mercuryTG);
		r2.getRotTG().addChild(venusTG);
		r3.getRotTG().addChild(earthTG);
		r4.getRotTG().addChild(marsTG);
		r5.getRotTG().addChild(jupiterTG);
		r6.getRotTG().addChild(saturnTG); saturnTG.addChild(ringTG);
		r7.getRotTG().addChild(uranusTG);
		r8.getRotTG().addChild(neptuneTG);
		
		sunTG.addChild(sun.getCelestialBody());

		
		try {
		rShip.getRotTG().addChild(shipTG);
		}
		catch (Exception e) {
			System.out.println("space ship problem "+e);
		}
		objRoot.compile();
		return objRoot;
	}
	


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
	 * Main method.
	 * @param args
	 */
	public static void main (String [] args) {
		SolarSystem  solarSystem = new SolarSystem();
	}
	
	
}
