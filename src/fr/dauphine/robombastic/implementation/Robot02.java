package fr.dauphine.robombastic.implementation;

import fr.dauphine.robombastic.*;
import fr.dauphine.robombastic.BotContext.Action;

public class Robot02 extends Robot {
	private String imageName = "/fr/dauphine/robombastic/imgs/robot06.jpg";
	
	private static final int ACTION_UP 		= 1;
	private static final int ACTION_DOWN 	= 2;
	private static final int ACTION_LEFT 	= 3;
	private static final int ACTION_RIGHT 	= 4;
	protected static int cpt;
	public Robot02() {
		super();
		cpt++;
		idBot = cpt;
	}


	public String getImageName() {
		return imageName;
	}

	public void init(BotContext context) {
		this.context = context;
	}
	
	
	@Override
	public void destroy()
	{	
		((ImpCurrentBotInfo) this.context.getCurrentBotInfo()).setAlive(false);
		
	}
	
	
	
	@Override
	public void run() {
		try{
			//La boucle du jeu
			while (this.context.getCurrentBotInfo().isAlive()){
				
				while (ImpBotContext.paused == true) {
					System.out.print("");
				}

				Thread.sleep(this.context.getGeneralInfo().getTourTime());
				if (!this.context.getCurrentBotInfo().isAlive()) break;
				//Map<? extends Position,ArenaItem> map = this.context.getCurrentBotInfo().radar();
				
				int typeAction = random.nextInt(4)+1;
				
				Action<?> action = null;
				
				switch (typeAction){
					case ACTION_UP:{
						action = ArenaAction.UP;
						break;
					}
					case ACTION_DOWN:{
						action = ArenaAction.DOWN;
						break;
					}
					case ACTION_LEFT:{
						action = ArenaAction.LEFT;
						break;
					}
					case ACTION_RIGHT:{
						action = ArenaAction.RIGHT;
						break;
					}
				}
				((ImpBotContext)this.context).addBot(this);
				this.context.nextTurn(action);
			}
			System.err.println(this +" died");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
