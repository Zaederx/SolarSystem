package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;
/**
 * Class used to creat Mecury for SoloarSystem model.
 * 
 * @author zacharyishmael
 *
 */
public class Mercury extends CelestialBody{
		static float defaultSize = 0.5f;
		static float defaultRed = 1f;
		static float defaultGreen = 0f;
		static float defaultBlue = 0f;

		
		public Mercury () {
			super(defaultSize,defaultRed, defaultGreen, defaultBlue);
			setTexture();
		}
		/*
		 * Constructor.
		 */
		public Mercury (float sphereSize, float red, float green, float blue) {
			super(sphereSize, red, green, blue);
			
		}
		
	
		/**
		 * Sets the texture of Mercury.
		 */
		public void setTexture () {
		TextureLoader loader = new TextureLoader("src/textures/mercury.jpg", null);
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
	

}
