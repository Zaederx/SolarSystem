package celestialBody;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;

import com.sun.j3d.utils.image.TextureLoader;
/**
 * Class extending from CelestialBody, used to create the Mercury 3D shape.
 * @author zacharyishmael
 *
 */
public class Mercury extends CelestialBody{
		static float defaultSize = 0.1f;
		static float defaultRed = 1f;
		static float defaultGreen = 0f;
		static float defaultBlue = 0f;
		static String texImage = "src/textures/mercury.jpg";
		
		/**
		 * Creates new Mercury object.
		 * @param textured - whether you would like it to be textured.
		 */
		public Mercury (boolean textured) {
			super(defaultSize,defaultRed, defaultGreen, defaultBlue);
			if (textured) {
				setTexture(texImage);
				}
		}
}
