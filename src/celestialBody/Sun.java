package celestialBody;

import java.awt.Component;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;

public class Sun extends CelestialBody{
	float red;
	float green;
	float blue;

	public Sun (float sphereSize) {
		super(sphereSize,0,0,0);
		setTexture();
	}
	/*
	 * Contrustor.
	 */
	public Sun (float sphereSize, float red, float green, float blue) {
		super(sphereSize, red, green, blue);
	}
	
	
	public void setTexture () {
	TextureLoader loader = new TextureLoader("src/solarsystem/sun.jpg", null);
//	ImageComponent2D image = loader.getImage();
	ImageComponent2D image = loader.getScaledImage(256, 256);
	Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(),image.getHeight());

//	Texture3D texture = new Texture3D(Texture3D.BASE_LEVEL, Texture3D.RGB, image.getWidth(),image.getHeight(), 1 );
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
