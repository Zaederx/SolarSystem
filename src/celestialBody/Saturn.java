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
	static Appearance appearance;
	//Create box and add the appearance
	Cylinder planetaryRing;

	public Saturn () {
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		setTexture();
		setRing();
		
	}
	
	/**
	 * Sets the texture of Jupiter.
	 */
	public void setTexture () {
	
		TextureLoader loader = new TextureLoader("src/textures/saturn.jpg", null);
		ImageComponent2D image = loader.getScaledImage(256, 256);
		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(),image.getHeight());
		texture.setImage(0, image);
		
		Appearance mTextureApp = new Appearance();
		mTextureApp.setTexture(texture);
		
		TextureAttributes textureAttr = new TextureAttributes();
		textureAttr.setTextureMode(TextureAttributes.REPLACE);
		mTextureApp.setTextureAttributes(textureAttr);
		
		Material material = new Material();
		material.setShininess(0f);
		
		mTextureApp.setMaterial(material);
		
		TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.SPHERE_MAP,
				TexCoordGeneration.TEXTURE_COORDINATE_3);
		
		mTextureApp.setTexCoordGeneration(tcg);
		getCelestialBody().setAppearance(mTextureApp);
	}
	
	public Cylinder getPlanetaryRing() {
		return planetaryRing;
	}
	
	public void setRing() {
		TextureLoader loader = new TextureLoader("src/textures/saturn_rin.png",null);
		ImageComponent2D image = loader.getScaledImage(256,256);
		Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(), image.getHeight());
		appearance.setTexture(texture);
		Cylinder planetaryRing = new Cylinder(0.8f,0.1f, appearance);
		
	}
	
}
