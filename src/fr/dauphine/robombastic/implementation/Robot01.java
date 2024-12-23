package fr.dauphine.robombastic.implementation;

import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import fr.dauphine.robombastic.*;
import fr.dauphine.robombastic.BotContext.Action;

public class Robot01 extends Robot {
	private String imageName = "/fr/dauphine/robombastic/imgs/robot05.jpeg";
	protected static int cpt;
	LinkedList<Action> listAction= new LinkedList<>();
	LinkedList<ImpPosition> listPosition= new LinkedList<>();

	BotContext.ActionResponse bombResponse = null;

	public Robot01() {
		super();
		cpt++;
		idBot = cpt;

		listPosition.add(new ImpPosition(0,-1));
		listPosition.add(new ImpPosition(0,+1));
		listPosition.add(new ImpPosition(-1,0));
		listPosition.add(new ImpPosition(+1,0));
	}
	
	public String getImageName() {
		return imageName;
	}
	@Override
	public void run() {
		try{
			//La boucle du jeu
			while (this.context.getCurrentBotInfo().isAlive()){
			
				listAction.add(ArenaAction.DOWN);
				listAction.add(ArenaAction.UP);
				listAction.add(ArenaAction.LEFT);
				listAction.add(ArenaAction.RIGHT);
				listAction.add(BombAction.DROP_BOMB);

				while (ImpBotContext.paused == true){
					System.out.print("");
				}

				Thread.sleep(this.context.getGeneralInfo().getTourTime());
				if (!this.context.getCurrentBotInfo().isAlive()) break;
				Map<? extends Position,ArenaItem> map = this.context.getCurrentBotInfo().radar();
				//System.err.println("aya3ziz : "+map.toString());

				for (ImpPosition p : listPosition){

					for (Map.Entry<? extends Position,ArenaItem> mp : map.entrySet()){
						if (	(mp.getKey().getX() == p.getX()	) && (mp.getKey().getY() == p.getY()	)	){
							ArenaItem aItem = mp.getValue();

							if (!aItem.equals(ArenaItem.EMPTY))
								listAction.remove(this.getAction(mp.getKey()));
							break;
						}
					}
				}

				if (bombResponse != null ){

					if (bombResponse.equals(BombAction.BombResponse.BOMB_DROPPED)){
						listAction.remove(BombAction.DROP_BOMB);
						bombResponse = null;
					}
				}

				//System.out.println(listAction.toString()+" "+idBot);

				int rndsize = listAction.size();
				Action action = null;
				if (rndsize > 0){
					action = listAction.get(Math.abs(new Random().nextInt(rndsize)));
					((ImpBotContext)this.context).addBot(this);
					if (action.equals(BombAction.DROP_BOMB))	System.err.println("JUL "+this+" MaxB = "+((ImpBotContext)this.context).getGame().getScene().getBombNotExploded());
					bombResponse = this.context.nextTurn(action);
				}

				listAction.clear();
			}
			System.err.println(this +" died");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void destroy()
	{	
		((ImpCurrentBotInfo) this.context.getCurrentBotInfo()).setAlive(false);
	}

	public Action getAction(Position p) throws Exception {
		Action action=null;

		if (p.getY() == +1) action = ArenaAction.DOWN;
		else if (p.getY() == -1) action = ArenaAction.UP;
		else if (p.getX() == -1) action = ArenaAction.LEFT;
		else if (p.getX() == +1) action = ArenaAction.RIGHT;

		if (action == null) throw new Exception("In3Al Ahtchoune yémakh !");

		return action;
	}
}
