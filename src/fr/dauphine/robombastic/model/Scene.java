package fr.dauphine.robombastic.model;

import java.io.Serializable;
import java.util.*;

import fr.dauphine.robombastic.ArenaItem;
import fr.dauphine.robombastic.Bot;
import fr.dauphine.robombastic.Position;
import fr.dauphine.robombastic.graphicalobjects.Bomb;
import fr.dauphine.robombastic.graphicalobjects.GraphicBot;
import fr.dauphine.robombastic.graphicalobjects.Empty;
import fr.dauphine.robombastic.graphicalobjects.Wall;
import fr.dauphine.robombastic.gui.GameFrame;
import fr.dauphine.robombastic.gui.drawing.ACell;
import fr.dauphine.robombastic.implementation.ImpBotContext;
import fr.dauphine.robombastic.implementation.ImpPosition;
import fr.dauphine.robombastic.implementation.Robot;

/**
 * 
 * @author Anis TLILANE
 * 
 *         La representation de la arene sous form d'une matrice ...
 */
public class Scene implements Serializable{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 2467957197975851280L;

	private ACell[][] arena = null;

	private Map<Bot, LinkedList<Integer>> mapGuiBomb = new HashMap<>();

	private Map<Bomb, Integer> mapBombs = new HashMap<>();

	private Random radom = new Random();
	private static String enemyName ;
	private int cellWidth;
	private int cellHeight;
	private int delayBomb;
	private Map<Bot, Position> mapBotPosition = new HashMap<Bot, Position>();

	public Scene(int height, int width) {
		arena = new ACell[height][width];
	}
	public void setEnemy(String enemyName)
	{
		Scene.enemyName = enemyName;
	}

	public ACell[][] getListElementsGraphiques() {
		return arena;
	}

	public ACell get(int row, int col) {
		if (row < 0 || row >= arena.length) {
			return null;
		}

		if (col < 0 || col >= arena[row].length) {
			return null;
		}

		return arena[row][col];
	}

	public void put(int row, int col, ArenaItem arenaItem) {
		if (row < 0 || row >= arena.length) {
			return;
		}

		if (col < 0 || col >= arena[row].length) {
			return;
		}

		ACell cell = null;

		if (arenaItem.equals(ArenaItem.EMPTY)) {
			cell = new Empty(row * cellWidth, col * cellHeight, row, col);
		} else if (arenaItem.equals(ArenaItem.WALL)) {
			cell = new Wall(row * cellWidth, col * cellHeight, row, col);
		}

		if (cell != null) {
			cell.setSize(cellWidth, cellHeight);
			cell.setRow(row);
			cell.setCol(col);

			arena[row][col] = cell;
		}
	}

	public void addBot(Bot bot, int numOrdre) {
		int[] rowCol = getRandomRowCol();

		int row = rowCol[0];
		int col = rowCol[1];

		GraphicBot graphicBot = new GraphicBot(bot, numOrdre, row * cellWidth,
				col * cellHeight, row, col);

		this.arena[row][col] = graphicBot;

		mapBotPosition.put(bot, new ImpPosition(row, col));

		graphicBot.updateImageSize();
	}

	public int[] getRandomRowCol() {
		int[] rowCol = new int[2];

		boolean positionFound = false;
		do {
			int row = radom.nextInt(arena.length);
			int col = radom.nextInt(arena[0].length);

			// si l'arene contient un vide dans les coordonnées (row, col)
			if (this.arena[row][col] instanceof Empty) {
				positionFound = true;

				rowCol[0] = row;
				rowCol[1] = col;
			}
		} while (!positionFound);

		return rowCol;
	}

	public void setCellSize(int cellWidth, int cellHeight) {
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		updateCellSizes();
	}

	public void updateCellSizes() {
		for (ACell[] rowCell : arena) {
			for (ACell cell : rowCell) {
				cell.setSize(cellWidth, cellHeight);
				cell.updateImageSize();

				cell.setPosition(cell.getRow() * cellWidth, cell.getCol()
						* cellHeight);
			}
		}
	}

	public Map<Bot, Position> getMapBotPosition() {
		return mapBotPosition;
	}

	public Position getPositionForBot(Bot bot) {
		return mapBotPosition.get(bot);
	}

	public ArenaItem getArenaItem(Bot bot, Position position) {
		return this.getArenaItem(bot, position.getX(), position.getY());
	}

	public ArenaItem getArenaItem(Bot bot, int x, int y) {
		if (x < 0 || x >= this.arena.length) {
			return null;
		}

		if (y < 0 || y >= this.arena[0].length) {
			return null;
		}

		ACell cell = this.arena[x][y];
		ArenaItem arenaItem = cell.getArenaItem();

		if (cell instanceof GraphicBot) {
			Bot otherBot = ((GraphicBot) cell).getBot();

			if (!otherBot.getClass().getSimpleName().equals(enemyName)) {
				arenaItem = ArenaItem.FRIEND_BOT;
			} else {
				arenaItem = ArenaItem.ENEMY_BOT;
			}
		}

		if (cell instanceof Bomb)
			arenaItem = ArenaItem.BOMB;

		return arenaItem;
	}

	synchronized public void putBomb(Bot bot, int x, int y) {
		LinkedList<Integer> l = new LinkedList<Integer>();
		l.add(x);
		l.add(y);
		mapGuiBomb.put(bot, l);
	}

	public synchronized void moveBot(Bot bot, int x, int y) {
		Position position = mapBotPosition.get(bot);
		
		int x0 = position.getX();
		int y0 = position.getY();

		ArenaItem arenaItem = getArenaItem(bot, x, y);
		if (arenaItem != null && arenaItem.equals(ArenaItem.EMPTY)) {
			((ImpPosition) mapBotPosition.get(bot)).setX(x);
			((ImpPosition) mapBotPosition.get(bot)).setY(y);

			ACell botCell = this.arena[x0][y0];
			ACell emptyCell = this.arena[x][y];

			botCell.setRow(x);
			botCell.setCol(y);
			botCell.setPosition(x * cellWidth, y * cellHeight);

			emptyCell.setRow(x0);
			emptyCell.setCol(y0);
			emptyCell.setPosition(x0 * cellWidth, y0 * cellHeight);

			this.arena[x0][y0] = emptyCell;
			this.arena[x][y] = botCell;

			this.arena[x0][y0].setArenaItem(ArenaItem.EMPTY);

			if (mapGuiBomb.containsKey(bot)) {
				x0 = mapGuiBomb.get(bot).get(0);
				y0 = mapGuiBomb.get(bot).get(1);

				ACell cell = null;
				Bomb b = new Bomb(x0 * cellWidth, y0 * cellHeight, x0, y0);
				
				mapBombs.put(b, delayBomb);
				cell = b;

				cell.setSize(cellWidth, cellHeight);
				cell.setRow(x0);
				cell.setCol(y0);

				arena[x0][y0] = cell;

				b.updateImageSize();
				GameFrame.getInstanceWithoutCreation().updateDrawing();
				this.arena[x0][y0].setArenaItem(ArenaItem.BOMB);
				mapGuiBomb.remove(bot);
			}

			GameFrame.getInstanceWithoutCreation().updateDrawing();
		}
	}

	public ACell[][] getArena() {
		return arena;
	}

	public void setDelayBomb(int delay) {
		this.delayBomb = delay;

	}

	public Map<Bomb, Integer> getMapBombs() {
		return mapBombs;
	}

	public void setMapBombs(Map<Bomb, Integer> mapBombs) {
		this.mapBombs = mapBombs;
	}
	
	public  void ex(Bomb bomb){
		bomb.setExploded(true);
		ImpBotContext.nbBomb--;
		int x = bomb.getRow();
		int y = bomb.getCol();
		Bomb b;
		
		for (int kUp =x-1; kUp>=0; kUp--){
			if (arena[kUp][y].getArenaItem().equals(ArenaItem.WALL)){
				break;
			}
			if (arena[kUp][y].getArenaItem().equals(ArenaItem.BOMB)){
				b = ((Bomb) arena[kUp][y]);
				if(b.isExploded()==false)
					ex(b);
			}
			if (arena[kUp][y].getArenaItem().equals(ArenaItem.FRIEND_BOT)){
				ACell botCell = arena[kUp][y];
				Robot deadBot = (Robot) ((GraphicBot) botCell).getBot();
				mapBotPosition.remove(deadBot);
				deadBot.destroy();
				
				//UPGRADE CELL 
				ACell emptyCell = new Empty(kUp*cellWidth,y*cellHeight,kUp,y);
				
				emptyCell.updateImageSize();
				arena[kUp][y] = emptyCell;
				arena[kUp][y].setArenaItem(ArenaItem.EMPTY);
				GameFrame.getInstanceWithoutCreation().updateDrawing();
				
			}		
		}

		for (int kDown = x+1; kDown<arena.length; kDown++){

			if (arena[kDown][y].getArenaItem().equals(ArenaItem.WALL)){
				break;
			}
			if (arena[kDown][y].getArenaItem().equals(ArenaItem.BOMB)){
				b = (Bomb) arena[kDown][y];
				if(b.isExploded()==false)
					ex(b);
			}
			if (arena[kDown][y].getArenaItem().equals(ArenaItem.FRIEND_BOT)){
				ACell botCell = arena[kDown][y];
				Robot	deadBot =(Robot) ((GraphicBot) botCell).getBot();
				deadBot.destroy();
				mapBotPosition.remove(deadBot);
				
				
				//UPGRADE CELL 
				ACell emptyCell = new Empty(kDown*cellWidth,y*cellHeight,kDown,y);
				
				emptyCell.updateImageSize();
				arena[kDown][y] = emptyCell;
				arena[kDown][y].setArenaItem(ArenaItem.EMPTY);
				GameFrame.getInstanceWithoutCreation().updateDrawing();
				
			}
		}

		for (int kLeft = y-1; kLeft>=0; kLeft--){

			//System.out.println("Left : "+arena[x][kLeft].getArenaItem());

			if (arena[x][kLeft].getArenaItem().equals(ArenaItem.WALL)){
				break;
			}
			if (arena[x][kLeft].getArenaItem().equals(ArenaItem.BOMB)){
				b = ((Bomb) arena[x][kLeft]);
				if(b.isExploded()==false)
					ex(b);
			}
			if (arena[x][kLeft].getArenaItem().equals(ArenaItem.FRIEND_BOT)){
				ACell botCell = arena[x][kLeft];
				Robot deadBot =(Robot) ((GraphicBot) botCell).getBot();
				mapBotPosition.remove(deadBot);
				deadBot.destroy();
				
				
				
				//UPGRADE CELL 
				ACell emptyCell = new Empty(x*cellWidth,kLeft*cellHeight,x,kLeft);
				
				emptyCell.updateImageSize();
				arena[x][kLeft] = emptyCell;
				arena[x][kLeft].setArenaItem(ArenaItem.EMPTY);
				GameFrame.getInstanceWithoutCreation().updateDrawing();
				
			}
		}

		for (int kRight = y+1; kRight<arena[0].length; kRight++){

			//System.out.println("Right : "+arena[x][kRight].getArenaItem());

			if (arena[x][kRight].getArenaItem().equals(ArenaItem.WALL)){
				break;
			}
			if (arena[x][kRight].getArenaItem().equals(ArenaItem.BOMB)){
				b =(Bomb) arena[x][kRight]; 
				if(b.isExploded()==false)
					ex(b);
			}
			if (arena[x][kRight].getArenaItem().equals(ArenaItem.FRIEND_BOT)){
				ACell botCell = arena[x][kRight];
				Robot	deadBot =(Robot) ((GraphicBot) botCell).getBot();
				mapBotPosition.remove(deadBot);
				deadBot.destroy();
				
				
				//UPGRADE CELL 
				ACell emptyCell = new Empty(x*cellWidth,kRight*cellHeight,x,kRight);
				
				emptyCell.updateImageSize();
				arena[x][kRight] = emptyCell;
				arena[x][kRight].setArenaItem(ArenaItem.EMPTY);
				GameFrame.getInstanceWithoutCreation().updateDrawing();
				
			}
		}


		ACell emptyCell = new Empty(x*cellWidth,y*cellHeight,x,y);
		
		emptyCell.updateImageSize();
		
		this.arena[x][y].setArenaItem(ArenaItem.EMPTY);
		this.arena[x][y] = emptyCell;

					
		GameFrame.getInstanceWithoutCreation().updateDrawing();
	}

	synchronized public int getBombNotExploded(){

		int cpt = 0;

		for (Map.Entry<Bomb, Integer> b : mapBombs.entrySet() ){
			if (b.getKey().isExploded() == false){
				cpt++;
			}
		}
		return cpt;
	}



}
