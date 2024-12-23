package fr.dauphine.robombastic.util;

import javax.swing.ImageIcon;

public final class IconUtils {
	public static final String PATH_IMGS = "/fr/dauphine/robombastic/imgs/";
	
	private IconUtils(){
	}
	
	public static final ImageIcon WALL_IMAGE_ICON = new ImageIcon(IconUtils.class.getResource(PATH_IMGS+"mur.jpg"));
	public static final ImageIcon BOMB_IMAGE_ICON = new ImageIcon(IconUtils.class.getResource(PATH_IMGS+"bomb.jpeg"));
	public static final ImageIcon EMPTY_IMAGE_ICON = new ImageIcon(IconUtils.class.getResource(PATH_IMGS+"herbe.jpg"));

}
