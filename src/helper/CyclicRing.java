package helper;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Texture2D;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;

public class CyclicRing {
Cylinder ringEdge;
Cylinder ringCenter;




Appearance whiteApp;
Appearance blackApp;

public CyclicRing() {
	whiteApp = new Appearance();
	Color3f white = new Color3f(1.0f,1.0f,1.0f);
	ColoringAttributes whiteCA = new ColoringAttributes();
	whiteCA.setColor(white);
	whiteApp.setColoringAttributes(whiteCA);
	ringEdge = new Cylinder(0.1f, 2.0f, whiteApp);
	blackApp = new Appearance();
	Color3f black = new Color3f(1.0f,1.0f,1.0f);
	ColoringAttributes blackCA = new ColoringAttributes();
	whiteCA.setColor(black);
	blackApp.setColoringAttributes(blackCA);
	ringEdge = new Cylinder(0.1f, 1.6f, blackApp);
}

/**
 * Returns CyclicRing ringEdge.
 * @return
 */
public Cylinder getCylinder() {
	return ringEdge;
}

/**
 * Returns CyclicRing rindCenter.
 * @return
 */
public Cylinder getRingEdge() {
	return ringEdge;
}

/**
 * Returns CyclicRing appearance.
 * @return
 */
public Appearance getWhiteApp() {
	return whiteApp;
}

}
