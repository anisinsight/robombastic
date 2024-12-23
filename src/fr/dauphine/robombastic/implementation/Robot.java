package fr.dauphine.robombastic.implementation;

import fr.dauphine.robombastic.Bot;
import fr.dauphine.robombastic.BotContext;
import fr.dauphine.robombastic.DeadBotException;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by mac on 22/01/2017.
 */
public class Robot implements Bot, Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6149032218145753914L;
	
	protected Random random = new Random();
    protected BotContext context = null;
    protected int idBot;


    private String nameBot;

    public Robot() {
        nameBot= this.getClass().getName()+"@"+idBot;
    }
    
    public int getDefaultArmySize() throws DeadBotException {		// robots qui reste dans le jeux
        return context.getCurrentBotInfo().getAllAliveFriendlyBots().size();
    }

    public void init(BotContext context) {
        this.context = context;
    }

    public int getIdBot() {
        return idBot;
    }
    public BotContext getBotContext(){
    	return context;
    }

    @Override
    public String toString(){

        return this.getClass().getSimpleName()+"  "+ idBot;
    }

    public String getNameBot() {
        return nameBot;
    }

	@Override
	public void run() {	
	}

	@Override
	public String getImageName() {
		return null;
	}


	@Override
	public void destroy() {	
	}

}
