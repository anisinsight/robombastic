package fr.dauphine.robombastic.main;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

import fr.dauphine.robombastic.gui.GameFrame;
import fr.dauphine.robombastic.model.Game;
import fr.dauphine.robombastic.util.SerializationUtil;

/**
 * 
 * @author Anis TLILANE
 * 
 *         La classe principal du programme (Entree du programme) contenant la
 *         methode principale main(String[])...
 */

public class MainGame {
	static private String savedGamesPathFile = System.getProperty("user.dir")
			+ "/src/arena.ser";

	public static void main(String[] args) {
		final Game game;
		File fSaves = new File(savedGamesPathFile);

		// Si le file existe
		if (fSaves.exists() && !fSaves.isDirectory()) {
			try {
				game = deserialiseGame(savedGamesPathFile);
				//Delete the file just after recuperer the saved game
				fSaves.delete();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GameFrame.showGUI(game);
					}
				});
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {

			game = new Game();
			game.extractParameters(args);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					GameFrame.showGUI(game);
				}
			});
		}

		
	}

	public static Game deserialiseGame(String savedGamesPathFile)
			throws ClassNotFoundException, IOException {
		Game gameCopy = (Game) SerializationUtil
				.deserialize(savedGamesPathFile);

		System.err.println(gameCopy.getMapNamesArmies() + "  V2");
		return gameCopy;
	}

	public static String getSavedGamesPathFile() {
		return MainGame.savedGamesPathFile;
	}
}
