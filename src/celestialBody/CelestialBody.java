package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
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
	 * Getter for appearance.
	 * @return
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * Setter for appearance.
	 * @param appearance
	 */
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
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
	 * @param color
	 */
	public void setColor(Color3f color) {
		this.color = color;
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
	}
	
}
