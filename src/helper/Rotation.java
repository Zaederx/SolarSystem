package helper;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

public class Rotation {
	
	TransformGroup rotTG;
	Alpha rotAlpha;
	Transform3D yAxis;
	RotationInterpolator rotator;
	BoundingSphere bounds;
	Transform3D t;
	
	
	
	/**
	 * Constuctor for default rotation settings
	 * @param bounds - bounds of the rotation
	 * @param tilt - whether to apply a default tilt or not
	 */
	public Rotation (BoundingSphere bounds, boolean tilt) {
		rotTG = new TransformGroup();
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotAlpha = new Alpha(-1,180000);
		yAxis = new Transform3D();
		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, 0.0f, (float) Math.PI*2.0f);
		rotator.setSchedulingBounds(bounds);
		this.bounds = bounds;
		Transform3D t = new Transform3D();
		
		if (tilt) {
			tilt();
		} else {
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
		}
	}
	
	
	/**
	 * Consturctor for more customer settings
	 * @param bounds - bounds of the rotation
	 * @param rotAplha - how long to rotate around the set angle
	 * @param minAngle - tends to control whether is spirals at an angle
	 * @param maxAngle - tends to control how far it rotates in one cycle
	 */
	public Rotation (BoundingSphere bounds,int rotAplha, float minAngle, float maxAngle, double rotY, boolean tilt) {
		rotTG = new TransformGroup();
		rotAlpha = new Alpha(-1,rotAplha);
		yAxis = new Transform3D();
		yAxis.rotY(rotY);
		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, minAngle, (float) Math.PI*maxAngle);
		rotator.setSchedulingBounds(bounds);
		
		this.bounds = bounds;
        
		if (tilt) {
			tilt();
		} else {
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
		}
	}

	/**
	 * Default tilt method.
	 * Set planet at an angle.
	 */
	public void tilt() {
		Transform3D t = new Transform3D();
		t.setScale(new Vector3d(2.0,2.0,2.0));
		t.setTranslation(new Vector3d(0.0,0.0,-0.5));
		Transform3D helperT3D3 = new Transform3D();
		helperT3D3.rotZ(Math.PI/6);
		t.mul(helperT3D3);
		helperT3D3.rotX(Math.PI/9);//rotation about X tilts it forward or backward
		t.mul(helperT3D3);
		rotTG = new TransformGroup(t);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
	}
	
	/**
	 * Tilts rotation at and anlge.
	 * @param x controls x rotation 
	 * @param y controls y rotation
	 * @param z controls z rotation
	 */
	public void tilt(int x, int y, int z) {
		setScale();
		setTranslation();
		Transform3D helperT3D = new Transform3D();
		helperT3D.rotX(Math.PI/x);//rotation about X tilts it forward or backward
		t.mul(helperT3D);
		helperT3D.rotY(Math.PI/y);
		t.mul(helperT3D);
		helperT3D.rotZ(Math.PI/z);
		t.mul(helperT3D);
		
		rotTG = new TransformGroup(t);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
	}
	
	/**
	 * Sets transfrom t scale to default values.
	 */
	public void setScale() {
		t.setScale(new Vector3d(2.0,2.0,2.0));
		
	}
	
	/**
	 * Set transform t to speciffies values.
	 * @param s1
	 * @param s2
	 * @param s3
	 */
	public void setScale(double s1, double s2, double s3) {
		t.setScale(new Vector3d(s1,s2,s3));
	}
	
	public void setTranslation() {
		t.setTranslation(new Vector3d(0.0,0.0,-5));
	}
	
	public void setTranslation(double d1, double d2, double d3) {
		t.setTranslation(new Vector3d(d1,d2,d3));
	}

	public TransformGroup getRotTG() {
		return rotTG;
	}


	public void setRotTG(TransformGroup rotTG) {
		this.rotTG = rotTG;
		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, 0.0f, (float) Math.PI*2.0f);
		rotator.setSchedulingBounds(bounds);
		rotTG = new TransformGroup(t);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
	}


	public Alpha getRotAlpha() {
		return rotAlpha;
	}


	public void setRotAlpha(Alpha rotAlpha) {
		this.rotAlpha = rotAlpha;
		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, 0.0f, (float) Math.PI*2.0f);
		rotator.setSchedulingBounds(bounds);
		rotTG = new TransformGroup(t);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
	}


	public Transform3D getyAxis() {
		return yAxis;
	}


	public void setyAxis(Transform3D yAxis) {
		this.yAxis = yAxis;
//		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, 0.0f, (float) Math.PI*2.0f);
//		rotator.setSchedulingBounds(bounds);
//		rotTG = new TransformGroup(t);
//		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//		rotTG.addChild(rotator);
	}


	public RotationInterpolator getRotator() {
		return rotator;
	}


	public void setRotator(RotationInterpolator rotator) {
		this.rotator = rotator;
		rotator = new RotationInterpolator(rotAlpha, rotTG, yAxis, 0.0f, (float) Math.PI*2.0f);
		rotator.setSchedulingBounds(bounds);
		rotTG = new TransformGroup(t);
		rotTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		rotTG.addChild(rotator);
	}
	
	
	

}
