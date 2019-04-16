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
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
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
import javax.vecmath.Point3f;
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
import celestialBody.Jupiter;
import celestialBody.Mars;
import celestialBody.Mercury;
import celestialBody.Neptune;
import celestialBody.Saturn;
import celestialBody.Sun;
import celestialBody.Uranus;
import celestialBody.Venus;
import helper.Rotation;

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
//		simpUniv.getViewingPlatform().setNominalViewingTransform();
		//viewing platform
		TransformGroup cameraTG = simpUniv.getViewingPlatform().getViewPlatformTransform();
		
		//position of viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
		//move along z axis by 25.0f ("move away from the screen")
		translate.set(-0.5f, 0.3f, 10f);
//		translate.angle(new Vector3f(45.0f, 30.0f,30.0f));
		
//		Transform3D t = new Transform3D();
		T3D.setTranslation(translate);
//		T3D.rotX(Math.PI);
//		T3D.perspective(0.1, 20.0, 0.1, 10.0);
//		t.mul(T3D);
		
		//add translate transform to cameraTG
		cameraTG.setTransform(T3D);
		
		

		
		//Add scene to Universe
		BranchGroup scene = createSceneGraph();
		simpUniv.addBranchGraph(scene);
		
		
//		addSideLightLeft(simpUniv);
		setTitle("Step 1: A simple cube");
		setSize(700,700);
		setVisible(true);
	}


	
	/**
	 * Changes the direction of the light.
	 * Instead of the sun ilumminating the scene,
	 * an ethereal light from the galaxy lights the scene from the left.
	 * @param su
	 */
	public void addSideLightLeft(SimpleUniverse su) {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
		//*****Light2
				//light works only within set bounds	
		Color3f lightColor = new Color3f(1.0f,1.0f,1.0f);
		Vector3f direction2 = new Vector3f(1.0f,-1.0f,0.5f);
//		Vector3f direction2 = new Vector3f(0.0f,0.0f,0.0f); // light comes side on
		DirectionalLight light2 = new DirectionalLight(lightColor,direction2);
		//***Light 2 End
		light2.setInfluencingBounds(bounds);
		bgLight.addChild(light2);
		
		su.addBranchGraph(bgLight);
	}
	
	/**
	 * Changes the direction of the light.
	 * Instead of the sun ilumminating the scene,
	 * an ethereal light from the galaxy lights the scene fomr the right.
	 */
	public void addSideLightRight() {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
//		//directionaLight
		Color3f lightColor = new Color3f(1.0f,1.0f,1.0f);
		Vector3f direction = new Vector3f(-1.0f,0.0f,-0.5f);
		DirectionalLight light = new DirectionalLight(lightColor,direction);
		
		light.setInfluencingBounds(bounds);
		bgLight.addChild(light);

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
		
		
		
		//TransformGroup
		
		//transform group for the branch group
		Transform3D sunT = new Transform3D();
		TransformGroup sunTG = createTG(0.0,0.0,0, 0.1,0.1,0.1, sunT);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Transform3D mercuryT = new Transform3D();
		TransformGroup mercuryTG = createTG(0.0,0.0,-1, 2.0,2.0,2.0,mercuryT);
		mercuryTG.addChild(mercury.getCelestialBody());
		mercuryTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D venusT = new Transform3D();
		TransformGroup venusTG = createTG(0.0,0.0,-2.5, 2.0,2.0,2.0,venusT);
		venusTG.addChild(venus.getCelestialBody());
		venusTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D earthT = new Transform3D();
		TransformGroup earthTG = createTG(0.0,0.0,-4, 2.0,2.0,2.0,earthT);
		earthTG.addChild(earth.getCelestialBody());
		earthTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D marsT = new Transform3D();
		TransformGroup marsTG = createTG(0.0,0.0,-8, 2.0,2.0,2.0,marsT);
		marsTG.addChild(mars.getCelestialBody());
		marsTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		
		Transform3D jupiterT = new Transform3D();
		TransformGroup jupiterTG = createTG(0.0,0.0,-13, 2.0,2.0,2.0,jupiterT);
		jupiterTG.addChild(jupiter.getCelestialBody());
		jupiterTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D saturnT = new Transform3D();
		TransformGroup saturnTG = createTG(0.0,0.0,-14, 2.0,2.0,2.0,saturnT);
		saturnTG.addChild(saturn.getCelestialBody());
		saturnTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D uranusT = new Transform3D();
		TransformGroup uranusTG = createTG(0.0,0.0,-16, 2.0,2.0,2.0,uranusT);
		uranusTG.addChild(uranus.getCelestialBody());
		uranusTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		
		Transform3D neptuneT = new Transform3D();
		TransformGroup neptuneTG = createTG(0.0,0.0,-18, 2.0,2.0,2.0,neptuneT);
		neptuneTG.addChild(neptune.getCelestialBody());
		neptuneTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		

		
		
		//**Space ship***
		Appearance c1App = new Appearance();
		Cone cone1 = new Cone(0.5f,1f,c1App);
		Appearance c2App = new Appearance();
		Cone cone2 = new Cone(0.4f, 0.5f, c2App);
		
		
		//***Spaceship end***
		//*****Rotation*****
		TransformGroup rotTG1 = new TransformGroup();
		rotTG1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha1 = new Alpha(-1, 18000);
		Transform3D yAxis = new Transform3D();
		//0.0F controls how much it spirals to the center (how offeset it is to spiral to the center)
		//(float) Math.PI variable controls how much of a rotation 2.0f = full rotation
		RotationInterpolator rotator1 = new RotationInterpolator(rotAlpha1, rotTG1, yAxis, 0.0f, (float) Math.PI * (2.0f));
		rotator1.setSchedulingBounds(bounds);	
		

		
		//*****Rotation2*********
		TransformGroup rotTG2 = new TransformGroup();
		rotTG2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha2 = new Alpha(-1, 18000);
		Transform3D yAxis2 = new Transform3D();
		RotationInterpolator rotator2 = new RotationInterpolator(rotAlpha2, rotTG2, yAxis2,0.0f, (float) Math.PI * 4.0f);
		rotator2.setSchedulingBounds(bounds);
		

		
//		***Rotation3
		TransformGroup rotTG3 = new TransformGroup();
		rotTG3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotAlpha3 = new Alpha(-1,18000);
		Transform3D yAxis3 = new Transform3D();
		RotationInterpolator rotator3 = new RotationInterpolator(rotAlpha3, rotTG3, yAxis3, 0.0f, (float) Math.PI*2.0f);
		rotator3.setSchedulingBounds(bounds);

		Rotation r0, r1, r2, r3, r4, r5 ,r6, r7, r8, r9;
		r0 = new Rotation(bounds, true);
		r1 = new Rotation(bounds, false);
		r2 = new Rotation(bounds, false);
		r3 = new Rotation(bounds, false);
		r4 = new Rotation(bounds, false);
		r5 = new Rotation(bounds, false);
		r6 = new Rotation(bounds, false);
		r7 = new Rotation(bounds, false);
		r8 = new Rotation(bounds, false);
		r9 = new Rotation(bounds, false);

		//Creating cyclic rings
		Cylinder c1, c1b, c2, c2b, c3, c3b, c4, c4b, c5,c5b ,c6, c6b, c7, c7b, c8, c8b ,c9, c9b;
		

		BranchGroup stars = new BranchGroup();
		//Star star = new Start();
		for (int i = 0; i < 100 ; i++ ) {
			//Star star = new Star();
//			stars.addChild(star);
		}
//		c1 = new Cylinder(0.8,0.1,);
//		mercuryBG.addChildAll(mercury.getCelestialBody(),ro);
		//make edge relations with the scene graph nodes
		//cube 1 translated -5 along z axis
		objRoot.addChild(r0.getRotTG());
		r0.getRotTG().addChild(sunTG);
		sunTG.addChild(r1.getRotTG());
		sunTG.addChild(skyBranch);
		r1.getRotTG().addChild(r2.getRotTG());
		r2.getRotTG().addChild(r3.getRotTG());
		r3.getRotTG().addChild(r4.getRotTG());
		r4.getRotTG().addChild(r5.getRotTG());
		r5.getRotTG().addChild(r6.getRotTG());
		r6.getRotTG().addChild(r7.getRotTG());
		r7.getRotTG().addChild(r8.getRotTG());
		
		r1.getRotTG().addChild(mercuryTG);
		r2.getRotTG().addChild(venusTG);
		r3.getRotTG().addChild(earthTG);
		r4.getRotTG().addChild(marsTG);
		r5.getRotTG().addChild(jupiterTG);
		r6.getRotTG().addChild(saturnTG);
		r7.getRotTG().addChild(uranusTG);
		r8.getRotTG().addChild(neptuneTG);
			
		sunTG.addChild(sun.getCelestialBody());
		
		
		//Create rotation behaviour
		MouseRotate behaviourRot = new MouseRotate();
		behaviourRot.setTransformGroup(r0.getRotTG());
		objRoot.addChild(behaviourRot);
		behaviourRot.setSchedulingBounds(bounds);
		
		//MouseRotate behaviour node
		MouseZoom behaviourZoom = new MouseZoom();
		behaviourZoom.setTransformGroup(r0.getRotTG());
		behaviourZoom.setFactor(0.01);//0.01 makes mouse translation smooth as curren universe scale.
		objRoot.addChild(behaviourZoom);
		behaviourZoom.setSchedulingBounds(bounds);
		
		//Translate Behaviour
		MouseTranslate behaviourTrans = new MouseTranslate();
		behaviourTrans.setTransformGroup(r0.getRotTG());
		behaviourTrans.setFactor(0.006);//0.006 makes mouse translation smooth at such as current universe scale.
		objRoot.addChild(behaviourTrans);
		behaviourTrans.setSchedulingBounds(bounds);
		
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
	 * 
	 * @param args
	 */
	public static void main (String [] args) {
		SolarSystem  solarSystem = new SolarSystem();
	}
	
	
}
