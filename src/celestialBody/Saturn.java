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
	static float defaultSize = .1f;
	static float defaultRed = 1f;
	static float defaultGreen = 0f;
	static float defaultBlue = 0f;
	static Appearance appearance;
	static Appearance ringApp;
	static String texImage = "src/textures/saturn.jpg";
	//Create box and add the whiteApp
	static Cylinder planetaryRing;

	public Saturn (boolean textured) {
		
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		appearance = new Appearance();
		ringApp = new Appearance();
		planetaryRing = new Cylinder();
		if (textured) {
			setTexture(texImage);
			
			setRing();//default textured ring for saturn
			}
	
	}
	
	public Cylinder getPlanetaryRing() {
		return planetaryRing;
	}
	
	/**
	 * Creates a default textured ring for Saturn.
	 */
	public void setRing() {
		TextureLoader loader = new TextureLoader("src/textures/saturn_ring.png",null);
		ImageComponent2D image = loader.getScaledImage(64,64);
		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		ringApp = new Appearance();
		ringApp.setTexture(texture);
		//(how thin/thick, how high, whiteApp)	
		TextureAttributes textureAttr = new TextureAttributes();
		textureAttr.setTextureMode(TextureAttributes.REPLACE);
		ringApp.setTextureAttributes(textureAttr);
		
		Material material = new Material();
		material.setShininess(0f);
		
		
		ringApp.setMaterial(material);
		
		TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
				TexCoordGeneration.TEXTURE_COORDINATE_3);
		
		ringApp.setTexCoordGeneration(tcg);
//		getCelestialBody().setAppearance(whiteApp);
		planetaryRing = new Cylinder(.2f,.01f,ringApp);
	
		
	}
	
}
