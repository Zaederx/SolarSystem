package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;

public class Saturn extends CelestialBody{
	static float defaultSize = 0.5f;
	static float defaultRed = 1f;
	static float defaultGreen = 0f;
	static float defaultBlue = 0f;
	static Appearance appearance = new Appearance();
	static String texImage = "src/textures/saturn.jpg";
	//Create box and add the whiteApp
	Cylinder planetaryRing;

	public Saturn () {
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		setTexture(texImage);
		setRing();
		
	}
	
	public Cylinder getPlanetaryRing() {
		return planetaryRing;
	}
	
	public void setRing() {
		TextureLoader loader = new TextureLoader("src/textures/saturn_ring.png",null);
		ImageComponent2D image = loader.getScaledImage(256,256);
		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(), image.getHeight());
		appearance.setTexture(texture);
		Cylinder planetaryRing = new Cylinder(0.8f,0.1f, appearance);//(how thin/thick, how high, whiteApp)
		
	}
	
}
