package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;

public class Mars extends CelestialBody {

	static float defaultSize = 0.5f;
	static float defaultRed = 1f;
	static float defaultGreen = 0f;
	static float defaultBlue = 0f;

	public Mars () {
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		setTexture();
	}
	
	/**
	 * Sets the texture of Jupiter.
	 */
	public void setTexture () {
	TextureLoader loader = new TextureLoader("src/textures/mars.jpg", null);
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
	
	TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
			TexCoordGeneration.TEXTURE_COORDINATE_2);
	
	mTextureApp.setTexCoordGeneration(tcg);
	getCelestialBody().setAppearance(mTextureApp);
	}
}
