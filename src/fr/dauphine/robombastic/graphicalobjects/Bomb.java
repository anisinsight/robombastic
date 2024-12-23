package fr.dauphine.robombastic.graphicalobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import fr.dauphine.robombastic.ArenaItem;
import fr.dauphine.robombastic.gui.drawing.ACell;
import fr.dauphine.robombastic.util.IconUtils;

public class Bomb extends ACell {
	private boolean exploded =false;

	public Bomb(int x, int y, int row, int col){
		//Pour la position du dessin
		this.setPosition(x, y);
		
		//Pour la posisiton sur l'arène
		this.setRow(row);
		this.setCol(col);
		
		this.setBackgroundColor(Color.WHITE);
		this.setLineColor(Color.WHITE);

		this.setArenaItem(ArenaItem.BOMB);

		this.setImage( IconUtils.BOMB_IMAGE_ICON );
	}
	
	public void draw(Graphics2D buffer) {
		buffer.setColor(this.getBackgroundColor());
		buffer.fillRect( this.getY(), this.getX(), this.getWidth(), this.getHeight());
		
		buffer.setColor(this.getLineColor());
		buffer.drawRect(this.getY(), this.getX(), this.getWidth(), this.getHeight());
		
		buffer.drawImage(this.getImage().getImage(), this.getY(), this.getX(), null);
	}

	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
	

}
