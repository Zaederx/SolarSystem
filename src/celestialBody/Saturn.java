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
	static float defaultSize = .4f;
	static float defaultRed = 1f;
	static float defaultGreen = 0f;
	static float defaultBlue = 0f;
	static Appearance appearance;
	
	static String texImage = "src/textures/saturn.jpg";
	//Create box and add the whiteApp

	public Saturn (boolean textured) {
		
		super(defaultSize,defaultRed, defaultGreen, defaultBlue);
		appearance = new Appearance();
		if (textured) {
			setTexture(texImage);
			
			setRing(texImage);//default textured ring for saturn
			}
	
	}
	
	
	
	
	
}
