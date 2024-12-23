package fr.dauphine.robombastic.graphicalobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import fr.dauphine.robombastic.ArenaItem;
import fr.dauphine.robombastic.gui.drawing.ACell;
import fr.dauphine.robombastic.util.IconUtils;

public class Empty extends ACell {
	public Empty(int x, int y, int row, int col){
		//Pour la position du dessin
		this.setPosition(x, y);
		
		//Pour la posisiton sur l'arène
		this.setRow(row);
		this.setCol(col);
		
		this.setBackgroundColor(Color.WHITE);
		this.setLineColor(Color.WHITE);
		
		this.setArenaItem(ArenaItem.EMPTY);
		
		this.setBackgroundColor(Color.WHITE);

	}
	
	public void draw(Graphics2D buffer) {
		buffer.setColor(this.getBackgroundColor());
		buffer.fillRect(this.getY(), this.getX(), this.getWidth(), this.getHeight());
		
		buffer.setColor(this.getLineColor());
		buffer.drawRect(this.getY(), this.getX(), this.getWidth(), this.getHeight());

	}
}
