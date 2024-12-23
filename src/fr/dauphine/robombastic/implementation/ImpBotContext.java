package fr.dauphine.robombastic.implementation;

import java.util.*;

import fr.dauphine.robombastic.*;
import fr.dauphine.robombastic.graphicalobjects.Bomb;
import fr.dauphine.robombastic.model.Game;

public class ImpBotContext implements BotContext {

	public static boolean paused = false;

	static Queue<Bot> fifoAction = new LinkedList<>();
	static final Object Monitor = new Object();
	
	private Game game;
	
	private String armyName = "";
	
	private CurrentBotInfo	currentBotInfo;
	private GeneralInfo		generalInfo;

	public static int nbBomb;
	
	public ImpBotContext(Game game, String armyName){
		this.game = game;
		this.armyName = armyName;
		
		generalInfo = this.game.getGeneralInfo();
		currentBotInfo = new ImpCurrentBotInfo(this.game, this.armyName);
	}
	
	public void setBot(Bot bot) {
		((ImpCurrentBotInfo)this.currentBotInfo).setBot(bot);
	}
	
	public CurrentBotInfo getCurrentBotInfo() {
		return currentBotInfo;
	}

	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	public <I extends Info> I info(Class<I> infoType) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<? extends Class<? extends Info>> infoTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addBot(Bot bot){
		synchronized (Monitor){
			fifoAction.add(bot);
		}
	}

	public Bot peekBot(){
		synchronized (Monitor){
			return fifoAction.peek();
		}
	}

	public Bot pollBot(){
		synchronized (Monitor){
			return fifoAction.poll();
		}
	}

	public boolean equal(Bot b1, Bot b2) {

		boolean b1Bot1 = b1 instanceof Robot01;
		boolean b1Bot2 = b1 instanceof Robot02;
		boolean b2Bot1 = b2 instanceof Robot01;
		boolean b2Bot2 = b2 instanceof Robot02;


		if (	(b1Bot1 && b2Bot2) || (b1Bot2 && b2Bot1 )	) return false;

		if (b1Bot1)
			return ((Robot01)b1).getIdBot() == ((Robot01)b2).getIdBot() ? true : false;
		else
			return ((Robot02)b1).getIdBot() == ((Robot02)b2).getIdBot() ? true : false;
	}

	public <R extends ActionResponse, A extends Action<R>> R nextTurn(A action) throws DeadBotException {
		if (!this.currentBotInfo.isAlive()){
			throw new DeadBotException("Ce robot est mort");
		}

		ActionResponse response = null;

		if (action == null){
			throw new IllegalArgumentException("Not a legal action.");
		}

			Bot bot = ((ImpCurrentBotInfo)this.currentBotInfo).getBot();

//			System.out.println(bot+" = "+peekBot()+" "+String.valueOf(action));
			while (	!equal(peekBot(), bot) );
			System.out.println(peekBot()+"  validate "+String.valueOf(action));

			Position position = this.game.getScene().getPositionForBot(bot);

			/**
			 * si l'action demandée est un déplacement
			 */
			if (action instanceof ArenaAction){
				int x = position.getX();
				int y = position.getY();

				if (action.equals(ArenaAction.UP)){
					y--;
				}
				else if (action.equals(ArenaAction.DOWN)){
					y++;
				}
				else if (action.equals(ArenaAction.LEFT)){
					x--;
				}
				else if (action.equals(ArenaAction.RIGHT)){
					x++;
				}

				this.game.getScene().moveBot(bot, x, y);
				this.pollBot();
			}

		if (action instanceof BombAction){

			int max = game.getGeneralInfo().getMaxBombs();

			System.err.println(bot+" MaxBomb = "+this.game.getScene().getBombNotExploded() + " < " + max );
			if (nbBomb < max){
				nbBomb++;
				System.err.println(bot+" MaxBomb = "+this.game.getScene().getBombNotExploded() + " < " + max +" nbBomb = "+nbBomb);


				int x = position.getX();
				int y = position.getY();

				this.game.getScene().putBomb(bot, x,y);
				response = BombAction.BombResponse.BOMB_DROPPED;
				this.pollBot();
			}else{
				response = BombAction.BombResponse.NO_MORE_BOMB;
				this.pollBot();
			}
		}


		return (R) response;
	}

	public Game getGame() {
		return game;
	}
}
