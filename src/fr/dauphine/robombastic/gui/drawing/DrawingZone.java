package fr.dauphine.robombastic.gui.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import fr.dauphine.robombastic.model.Game;

/**
 * 
 * @author Anis TLILANE
 *
 */
public class DrawingZone extends JPanel {
	private static final long serialVersionUID = 1L;

	private Color	backgroundColor = Color.WHITE;

	private Graphics2D 	buffer;
	private Image 		image;
	
	private Game game;
	
	public DrawingZone(Game game) {
		this.game = game;
	}
	
	public void updateSize(int width, int height){
		this.setSize(width, height);
		
		this.buffer = null;
		this.refresh();
	}
	
	public void refresh(){
		refresh(null);
	}
	
	public void refresh(Rectangle rectangle){
		if (rectangle == null){
			repaint();
		}
		else{
			repaint(rectangle);
		}
	}
	
	public void update(Graphics g){
		paint(g);
	}
	
	
	@Override
	public void paint(Graphics g) {
		if (buffer == null) {
			image = createImage(this.getWidth(), this.getHeight());
			buffer = (Graphics2D) image.getGraphics();
			
			buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			buffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		buffer.setColor( backgroundColor );
		buffer.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		IElementGraphique[][] matEGs = game.getScene().getListElementsGraphiques();
		for (IElementGraphique[] rowEGs : matEGs){
			for (IElementGraphique eg : rowEGs){
				eg.draw(buffer);
			}
		}
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(image, 0, 0, this);
	}
}
