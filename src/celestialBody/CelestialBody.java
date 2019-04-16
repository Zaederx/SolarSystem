package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
/**
 * Class used to create new planets or stars (also known as celestial bodies).
 * @author zacharyishmael
 *
 */

public class CelestialBody {
	Sphere celestialBody;
	Appearance appearance;
	Color3f color;
	ColoringAttributes cAttributes;
	
	/**
	 * Default Constructor.
	 */
	public  CelestialBody() {
		celestialBody = new Sphere();
		appearance = new Appearance();
		color = new Color3f();
		cAttributes = new ColoringAttributes();
	}
	
	/**
	 * Constructor.
	 * For case plain colour is used instead of textures.
	 * Creates a new celestialBody (planet, moon or star).
	 * @param sphereSize
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public CelestialBody(float sphereSize, float red, float green, float blue) {
		celestialBody = new Sphere(sphereSize);
		appearance = new Appearance();
		color = new Color3f(red,green,blue);
		cAttributes = new ColoringAttributes();
		cAttributes.setColor(color);
		appearance.setColoringAttributes(cAttributes);
		celestialBody.setAppearance(appearance);
		
	}
	
	/**
	 * Sets the texture of a celestialBody.
	 */
	public void setTexture (String textureImage) {
	TextureLoader loader = new TextureLoader( textureImage, null);
	ImageComponent2D image = loader.getScaledImage(256, 256);
	Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture2D.RGB, image.getWidth(),image.getHeight());
	texture.setImage(0, image);
	
	appearance = new Appearance();
	appearance.setTexture(texture);
	
	TextureAttributes textureAttr = new TextureAttributes();
	textureAttr.setTextureMode(TextureAttributes.REPLACE);
	appearance.setTextureAttributes(textureAttr);
	
	Material material = new Material();
	material.setShininess(0f);
	
	appearance.setMaterial(material);
	
	TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.SPHERE_MAP,
			TexCoordGeneration.TEXTURE_COORDINATE_3);
	
	appearance.setTexCoordGeneration(tcg);
//	getCelestialBody().setAppearance(whiteApp);
	setAppearance(appearance);
	}
	
	/**
	 * Getter for celestial body.
	 * @return
	 */
	public Sphere getCelestialBody() {
		return celestialBody;
	}

	/**
	 * Setter for celestial body.
	 * @param celestialBody
	 */
	public void setCelestialBody(Sphere celestialBody) {
		this.celestialBody = celestialBody;
	}
	
	/**
	 * Getter for whiteApp.
	 * @return
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * Setter for whiteApp.
	 * @param whiteApp
	 */
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
		this.getCelestialBody().setAppearance(appearance);
	}

	/**
	 * Getter for color.
	 * @return
	 */
	public Color3f getColor() {
		return color;
	}

	/**
	 * Setter for color.
	 * For cases where texture is not being used.
	 * @param color
	 */
	public void setColor(Color3f color) {
		this.color = color;
		cAttributes.setColor(color);
		appearance.setColoringAttributes(cAttributes);
		this.setAppearance(appearance);
	}

	/**
	 * Getter for cAttribute.
	 * @return
	 */
	public ColoringAttributes getcAttributes() {
		return cAttributes;
	}

	/**
	 * Setter for cAttribute.
	 * @param cAttributes
	 */
	public void setcAttributes(ColoringAttributes cAttributes) {
		this.cAttributes = cAttributes;
		appearance.setColoringAttributes(cAttributes);
		this.setAppearance(appearance);
	}
	
}
