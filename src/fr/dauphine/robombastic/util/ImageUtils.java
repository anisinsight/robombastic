package fr.dauphine.robombastic.util;

import java.awt.Image;

import javax.swing.ImageIcon;

public final class ImageUtils {
	private ImageUtils(){
	}
	
	public static ImageIcon resizeIconTo(ImageIcon icon, int newWidth, int newHeight) {
		if (newWidth == 0 || newHeight == 0){
			return icon;
		}
		return new ImageIcon(resizeImageTo(icon.getImage(), newWidth, newHeight));
	}
	
	public static Image resizeImageTo(Image image, int newWidth, int newHeight) {
		if (newWidth == 0 || newHeight == 0){
			return image;
		}
		return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	}
}
