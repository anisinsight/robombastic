package fr.dauphine.robombastic.implementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dauphine.robombastic.ArenaItem;
import fr.dauphine.robombastic.Bot;
import fr.dauphine.robombastic.CurrentBotInfo;
import fr.dauphine.robombastic.DeadBotException;
import fr.dauphine.robombastic.Position;
import fr.dauphine.robombastic.model.Game;

/**
 * 
 * L'impl�mentation de l'interface CurrentBotInfo
 * 
 * Cette impl�mentation se base sur une instance de Game, une instance du Bot
 * (le bot conecerné) et une la nom de l'armée (le nom du jar qui contient
 * l'impl�mentation des classes)
 * 
 * @author Anis TLILANE & Sid Ahmed IMLOUL
 * 
 */
public class ImpCurrentBotInfo implements CurrentBotInfo, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4104172878381885644L;
	
	private Game game;
	private String armyName = "";
	private Bot bot = null;
	private boolean alive = true;

	public ImpCurrentBotInfo(Game game, String armyName) {
		this.game = game;
		this.armyName = armyName;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}

	public List<? extends Bot> getAllAliveFriendlyBots()
			throws DeadBotException {
		if (!isAlive()) {
			throw new DeadBotException("Ce robot: " + bot + " est mort");
		}

		List<Bot> listOfAllAliveFriendlyBots = new ArrayList<Bot>();

		List<Bot> listOfMyFriends = this.game.getMapNamesArmies().get(armyName);

		if (listOfMyFriends == null) {
			return listOfAllAliveFriendlyBots;
		}

		for (Bot otherBot : listOfMyFriends) {
			if (otherBot.equals(this.bot)) {
				continue;
			}

			if (this.game.getMapBotBotContext().get(otherBot)
					.getCurrentBotInfo().isAlive()) {
				listOfAllAliveFriendlyBots.add(otherBot);
			}
		}

		return listOfAllAliveFriendlyBots;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Map<? extends Position, ArenaItem> radar() throws DeadBotException {
		if (!isAlive()) {
			throw new DeadBotException("Ce robot" + bot + "est mort");

		} else {

			Map<Position, ArenaItem> map = new HashMap<Position, ArenaItem>();

			int radarSize = this.game.getGeneralInfo().getRadarSize();

			Position botPosition = this.game.getScene().getPositionForBot(
					this.bot);

			for (int i = 1; i <= radarSize; i++) {
				ArenaItem arenaItem = this.game.getScene().getArenaItem(
						this.bot, botPosition.getX() - i, botPosition.getY());
				if (arenaItem != null) {
					map.put(new ImpPosition(-i, 0), arenaItem);
				}

				arenaItem = this.game.getScene().getArenaItem(this.bot,
						botPosition.getX() + i, botPosition.getY());
				if (arenaItem != null) {
					map.put(new ImpPosition(+i, 0), arenaItem);
				}

				arenaItem = this.game.getScene().getArenaItem(this.bot,
						botPosition.getX(), botPosition.getY() - i);
				if (arenaItem != null) {
					map.put(new ImpPosition(0, -i), arenaItem);
				}

				arenaItem = this.game.getScene().getArenaItem(this.bot,
						botPosition.getX(), botPosition.getY() + i);
				if (arenaItem != null) {
					map.put(new ImpPosition(0, +i), arenaItem);
				}
			}

			return map;
		}
	}
}
