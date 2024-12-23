package fr.dauphine.robombastic.gui.drawing;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.ImageIcon;

import fr.dauphine.robombastic.util.ImageUtils;

public abstract class AElementGraphique implements IElementGraphique, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5769224356448812753L;
	
	private Color lineColor;
	private Color backgroundColor;

	private int			x;
	private int 		y;
	private int 		width;
	private int 		height;
	
	private ImageIcon 	image = null;
	
	public Color getLineColor() {
		return lineColor;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public int getHeight() {
		return this.height;
	}

	public ImageIcon getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void	setLineColor(Color lineColor){
		this.lineColor = lineColor;
	}
	
	public void	setBackgroundColor(Color backgroundColor){
		this.backgroundColor = backgroundColor;
	}
	
	public void updateImageSize(){
		if (this.image == null){
			return;
		}
		
		if (image.getIconWidth() != this.getWidth() || image.getIconHeight() != this.getHeight()){
			this.image = ImageUtils.resizeIconTo(image, this.getWidth(), this.getHeight());
		}
	}
}
