package celestialBody;

import java.awt.Component;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.image.TextureLoader;
/**
 * Class extending from CelestialBody, used to create the Sun 3D shape.
 * @author zacharyishmael
 *
 */
public class Sun extends CelestialBody{
	private static float defaultSize = 1.0f;
	private static float defaultRed = 0f;
	private static float defaultGreen = 0f;
	private static float defaultBlue = 0f;
	static String texImage = "src/textures/sun.jpg";
	
	/**
	 * Constructor for Sun Class
	 * @param bounds
	 * @param emit
	 */
	public Sun (BoundingSphere bounds, boolean emit, boolean textured) {
		super(defaultSize,defaultRed,defaultGreen, defaultBlue);
		if (emit) {
		emitLight(bounds);
		}
		if (textured) {
			setTexture(texImage);
			}
	}
	
	
	
	/**
	 * Set sets the sun to emit light within certain bounds.
	 * @param bounds - the bounds of the light
	 */
	public void emitLight (BoundingSphere bounds) {
		BranchGroup sunLight = new BranchGroup();
		
		//set up light in the scene
		Color3f pointLColor = new Color3f(0.6f,0.6f,0.6f);
		Point3f position = new Point3f();
		Point3f attenuation = new Point3f(1,0,0);
		PointLight pointLight = new PointLight(true, pointLColor, position,attenuation);
		pointLight.setEnable(true);
		pointLight.setInfluencingBounds(bounds);
		pointLight.setBounds(bounds);
		sunLight.addChild(pointLight);
		getCelestialBody().addChild(sunLight);
	}
}
