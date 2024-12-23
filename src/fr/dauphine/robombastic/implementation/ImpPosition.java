package fr.dauphine.robombastic.implementation;

import fr.dauphine.robombastic.Position;

public class ImpPosition implements Position{
	int x;
	int y;
	
	public ImpPosition() {
		this(0, 0);
	}
	
	public ImpPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return "("+this.getX()+", "+this.getY()+")";
	}
}
