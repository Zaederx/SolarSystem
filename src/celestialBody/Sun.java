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

public class Sun extends CelestialBody{
	private static float defaultSize = .5f;
	private static float defaultRed = 0f;
	private static float defaultGreen = 0f;
	private static float defaultBlue = 0f;
	static String texImage = "src/textures/sun.jpg";
	public Sun () {
		super(defaultSize,defaultRed,defaultGreen, defaultBlue);
		setTexture(texImage);
		emitLight();
	}
	
	
	
	
	public void emitLight () {
		BranchGroup sunLight = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),10000.0);
		
		//set up light in the scene
		Color3f pointLColor = new Color3f(0.6f,0.6f,0.6f);
		Point3f position = new Point3f();
		Point3f attenuation = new Point3f(1,0,0);
		PointLight pointLight = new PointLight(true, pointLColor, position,attenuation);
		pointLight.setBounds(bounds);
		sunLight.addChild(pointLight);
		
		//directionaLight
//		Color3f lightColor = new Color3f(1.0f,1.0f,1.0f);
//		Vector3f direction = new Vector3f(-1.0f,0.0f,-0.5f);
//		DirectionalLight light = new DirectionalLight(lightColor,direction);
		
//		//light works only within set bounds
//		light.setInfluencingBounds(bounds);
//		sunLight.addChild(light);
//		
//		Vector3f direction2 = new Vector3f(1.0f,-1.0f,0.5f);
//		DirectionalLight light2 = new DirectionalLight(lightColor,direction2);
//		light2.setInfluencingBounds(bounds);
//		sunLight.addChild(light2);
		
		getCelestialBody().addChild(sunLight);;
//		su.addBranchGraph(bgLight);
	}
}
