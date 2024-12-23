package fr.dauphine.robombastic.gui.drawing;

import fr.dauphine.robombastic.ArenaItem;

public abstract class ACell extends AElementGraphique {
	private int row;
	private int col;
	
	private ArenaItem arenaItem = null;
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}

	public void setRow(int row){
		this.row = row;
	}
	
	public void setCol(int col){
		this.col = col;
	}
	
	public ArenaItem getArenaItem() {
		return arenaItem;
	}
	
	public void setArenaItem(ArenaItem arenaItem) {
		this.arenaItem = arenaItem;
	}

	@Override
	public String toString(){
		return arenaItem+"";
	}
}
