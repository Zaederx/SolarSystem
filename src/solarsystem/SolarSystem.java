package solarsystem;


import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class SolarSystem extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void setup () {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		BorderLayout bL = new BorderLayout();
		container.setLayout(bL);
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		container.add("Center",canvas);
		BranchGroup scene = createSceneGraph();
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(scene);
		
		//viewing platform
		TransformGroup cameraTG = universe.getViewingPlatform().getViewPlatformTransform();
		
		//position of viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
	
		//move along z axis by 10.0f ("move away from the screen")
		translate.set(0.0f, 0.0f, 10.0f);
		T3D.setTranslation(translate);
		cameraTG.setTransform(T3D);
		setTitle("Step 1: A simple cube");
		setSize(512,512);
		setVisible(true);
	}

	public BranchGroup createSceneGraph () {
		//creates the bound of the universe
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10.0);
		
		//single branch group
		BranchGroup objRoot = new BranchGroup();
		
		//transform group for the branch group
		TransformGroup mainTG = new TransformGroup();
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		//create 3D shape
		ColorCube colorCube = new ColorCube();
		
		//make edge relations with the scene graph nodes
		objRoot.addChild(mainTG);
		mainTG.addChild(colorCube);
		
		//Create rotation behaviour
		MouseRotate behaviourRot = new MouseRotate();
		behaviourRot.setTransformGroup(mainTG);
		objRoot.addChild(behaviourRot);
		behaviourRot.setSchedulingBounds(bounds);
		
		
		//MouseRotate behaviour node
		MouseZoom behaviourZoom = new MouseZoom();
		behaviourZoom.setTransformGroup(mainTG);
		objRoot.addChild(behaviourZoom);
		behaviourZoom.setSchedulingBounds(bounds);
		
		//Translate Behaviour
		MouseTranslate behaviourTrans = new MouseTranslate();
		behaviourTrans.setTransformGroup(mainTG);
		objRoot.addChild(behaviourTrans);
		behaviourTrans.setSchedulingBounds(bounds);
		
		objRoot.compile();
		return objRoot;
	}
	
	public static void main (String [] args) {
		SolarSystem  solarSystem = new SolarSystem();
	}
	
	
}
