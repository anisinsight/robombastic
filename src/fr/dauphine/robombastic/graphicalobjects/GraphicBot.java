package fr.dauphine.robombastic.graphicalobjects;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import fr.dauphine.robombastic.ArenaItem;
import fr.dauphine.robombastic.Bot;
import fr.dauphine.robombastic.gui.drawing.ACell;
import fr.dauphine.robombastic.util.IconUtils;

public class GraphicBot extends ACell {
	private Bot	bot;
	
	private int numOrdre;
	
	public GraphicBot(Bot robot, int numOrdre, int x, int y, int row, int col){
		this.setPosition(x, y);
		
		this.setRow(row);
		this.setCol(col);
		
		this.setBackgroundColor(Color.WHITE);
		this.setLineColor(Color.WHITE);
		
		this.setArenaItem(ArenaItem.FRIEND_BOT);
		
		this.bot = robot;
		this.numOrdre = numOrdre;
		
		this.setImage(new ImageIcon(IconUtils.class.getResource(this.bot.getImageName())));
	}
	
	public Bot getBot() {
		return bot;
	}
	
	public void draw(Graphics2D buffer) {
		buffer.setColor(this.getBackgroundColor());
		buffer.fillRect(this.getY(), this.getX(), this.getWidth(), this.getHeight());
		
		buffer.setColor(this.getLineColor());
		buffer.drawRect(this.getY(), this.getX(), this.getWidth(), this.getHeight());
		
		buffer.drawImage(this.getImage().getImage(), this.getY(), this.getX(), null);
		
		buffer.drawString(String.valueOf(numOrdre), this.getY()+5, this.getX()+15);
	}
}
