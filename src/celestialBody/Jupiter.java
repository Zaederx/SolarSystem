package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;
/**
 * Class extending from CelestialBody, used to create the Jupiter 3D shape.
 * @author zacharyishmael
 *
 */
public class Jupiter extends CelestialBody{
	
	static float defaultSize = 0.6f;
	static float defaultRed = 1f;
	static float defaultGreen = 0f;
	static float defaultBlue = 0f;
	static String texImage = "src/textures/jupiter.jpg";

	/**
	 * Creates new Jupiter object.
	 * @param textured - whether you would like it to be textured.
	 */
	public Jupiter (boolean textured) {
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		if (textured) {
			setTexture(texImage);
			}
	}
	
	
	
}
