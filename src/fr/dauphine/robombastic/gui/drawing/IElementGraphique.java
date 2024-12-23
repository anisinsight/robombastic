package fr.dauphine.robombastic.gui.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public interface IElementGraphique {
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	
	public void setPosition(int x, int y);
	public void setX(int x);
	public void setY(int y);
	
	public void setWidth(int width);
	public void setHeight(int height);
	public void setSize(int width, int height);
	
	public Color	getLineColor();
	public Color	getBackgroundColor();
	
	public void		setLineColor(Color lineColor);
	public void		setBackgroundColor(Color backgroundColor);
	
	public ImageIcon getImage();
	public void		 setImage(ImageIcon image);

	public void updateImageSize();
	
	public void draw(Graphics2D buffer);
}
