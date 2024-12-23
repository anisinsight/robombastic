package fr.dauphine.robombastic.main;

import javax.swing.SwingUtilities;

import fr.dauphine.robombastic.gui.GameFrame;

/**
 * 
 * @author Anis TLILANE
 *
 *La classe principal du programme (Entree du programme)
 *contenant la methode principale main(String[])...
 */
public class Game {
	public static void main(String[] args) {
		final fr.dauphine.robombastic.model.Game game = new fr.dauphine.robombastic.model.Game();
		game.extractParameters(args);
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GameFrame.showGUI(game);
			}
		});
	}
}
