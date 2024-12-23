package fr.dauphine.robombastic.implementation;

import java.io.Serializable;

import fr.dauphine.robombastic.GeneralInfo;

public class ImpGeneralInfo implements GeneralInfo,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7170393949424769862L;

	/**
	 * La heuteur de l'arene en cellule
	 */
	private int arenaHieght = 0;
	
	/**
	 * La largeur de l'arene en cellule
	 */
	private int arenaWidth = 0;
	
	/**
	 * 
	 */
	private int	bombDuration 	= 3;
	
	/**
	 * 
	 */
	private int bombSize		= 0;
	
	/**
	 * 
	 */
	private int maxBombs		= 4;
	
	/**
	 * 
	 */
	private int radarSize		= 3;
	
	/**
	 * 
	 */
	private int	tourTime		= 1000;
	
	public ImpGeneralInfo(int arenaWidth, int arenaHeight, int radarSize, int turnDuration, int bombDuration) {
		this.arenaWidth = arenaWidth;
		this.arenaHieght = arenaHeight;
		this.radarSize = radarSize;
		this.tourTime = turnDuration;
		this.bombDuration = bombDuration;
	}

	public int getArenaWidth() {
		return arenaWidth;
	}
	
	public int getArenaHeight() {
		return arenaHieght;
	}

	public int getBombDuration() {
		return bombDuration;
	}

	public int getBombSize() {
		return bombSize;
	}

	public int getMaxBombs() {
		return maxBombs;
	}

	public int getRadarSize() {
		return radarSize;
	}

	public long getTourTime() {
		return tourTime;
	}

}
