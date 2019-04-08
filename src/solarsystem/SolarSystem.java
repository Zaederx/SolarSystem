package solarsystem;



import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SolarSystem extends JFrame {

	public Canvas3D myCanvas3D;
	
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
		
		//viewing platform
		TransformGroup cameraTG = simpUniv.getViewingPlatform().getViewPlatformTransform();
		
		//position of viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
	
		BranchGroup scene = createSceneGraph();
		simpUniv.addBranchGraph(scene);
		//move along z axis by 10.0f ("move away from the screen")
		translate.set(0.0f, 0.0f, 10.0f);
		T3D.setTranslation(translate);
		cameraTG.setTransform(T3D);
		addLight(simpUniv);
		setTitle("Step 1: A simple cube");
		setSize(700,700);
		setVisible(true);
	}

	public void addLight(SimpleUniverse su) {
		BranchGroup bgLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
		
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
	
	public BranchGroup createSceneGraph () {
		//creates the bound of the universe
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10.0);
		
		//single branch group
		BranchGroup objRoot = new BranchGroup();
		
		//transform group for the branch group
		TransformGroup sunTG = new TransformGroup();
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		sunTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		
		//another transform and transform group
		Transform3D t1 = new Transform3D();
		
		t1.setTranslation(new Vector3d(0.0,0.0,-5));
		t1.setScale(new Vector3d(2.0,2.0,2.0));
		//matrix for translation t1
		Matrix4d matrix = new Matrix4d();
		t1.get(matrix);
		
		TransformGroup mercuryTG = new TransformGroup(t1);
		
		
		
		//create appearance for box
		Appearance greenApp = new Appearance();
		Color3f greenColor = new Color3f(0.0f, 1.0f,0.0f);
		ColoringAttributes greenCA = new ColoringAttributes();
		greenCA.setColor(greenColor);
		greenApp.setColoringAttributes(greenCA);
		
		//Create box and add the appearance
		Cylinder planetaryRing = new Cylinder(0.8f,0.1f, greenApp);
		
		
		//create 3D shapes and appearances
		Sphere sun = new Sphere(.5f);
		Appearance sunApp = new Appearance();
		Color3f sunColor = new Color3f( 1f, 1f, 0f);
		ColoringAttributes sunCA = new ColoringAttributes();
		sunCA.setColor(sunColor);
		sunApp.setColoringAttributes(sunCA);
		sun.setAppearance(sunApp);
		
		
		Appearance mercuryApp = new Appearance();
		Color3f mercuryColor = new Color3f( 1f, 0f, 0f);
		ColoringAttributes mercuryCA = new ColoringAttributes();
		mercuryCA.setColor(mercuryColor);
		mercuryApp.setColoringAttributes(mercuryCA);
		Sphere mercury = new Sphere(.5f);
		mercury.setAppearance(mercuryApp);
		
		Transform3D venusT = new Transform3D();
		venusT.setTranslation(new Vector3d(0.0,0.0,-5));
		venusT.setScale(new Vector3d(2.0,2.0,2.0));
		TransformGroup venusTG = new TransformGroup(venusT);
//		Sphere venus = new Sphere(0.5f);
//		Appearance venusApp = new Appearance();
//		Color3f venusColor = new Color3f(1f,.7f,.5f);
//		ColoringAttributes venusCA = new ColoringAttributes();
//		venusCA.setColor(venusColor);
//		venus.setAppearance(venusApp);
		
		Sphere venus = celestialBody(0.5f, 1f, .7f, .5f);
		//make edge relations with the scene graph nodes
		//cube 1 translated -5 along z axis
		objRoot.addChild(sunTG);
		sunTG.addChild(sun);
		sunTG.addChild(mercuryTG);
		mercuryTG.addChild(mercury);
		mercuryTG.addChild(planetaryRing);
		mercuryTG.addChild(venusTG);
		venusTG.addChild(venus);
		
		
		
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
	
	public static void main (String [] args) {
		SolarSystem  solarSystem = new SolarSystem();
	}
	
	
}
