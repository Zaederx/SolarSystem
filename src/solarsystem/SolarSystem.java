package solarsystem;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.vecmath.Point3d;

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
		
		
	}

	public BranchGroup createSceneGraph () {
		//creates the bound of the universe
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10.0);
		
		//single branch group
		BranchGroup objRoot = new BranchGroup();
		
		//transform group for the branch group
		
		
	}
	
	
}
