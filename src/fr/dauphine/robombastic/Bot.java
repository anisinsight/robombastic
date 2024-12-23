package fr.dauphine.robombastic;

/** Represents one bot for the game.
 * 
 *  All classes that implement a Bot :
 *  <ol>
 *   <li>required a public default constructor.
 *  </ol>
 *  
 *  All the methods of the Bot interface are called
 *  by the game during the lifecycle of a bot :
 *  <ol>
 *   <li>{@link #init(BotContext) init} called with a context
 *       during the initialisation of the bot.
 *   <li>{@link #run() run} called once by the game on the thread
 *       associated to the bot.
 *   <li>{@link #destroy() destroy} called by the game
 *       when the bot die. This call may occured when the bot
 *       {@link #run() run}.
 *  </ol>
 *  
 *  The bot {@link fr.dauphine.robombastic.BotContext context} is a facade
 *  of the game that the bot used to ask some informations or
 *  to act on the arena.
 *  
 * @author remi, florian, benjamin 
 * @version 1.0
 */
public interface Bot {
  /** Called by the game to initialise a bot with
   *  the context.
   *  When this method is called all bots of a familly
   *  are already created.
   *  
   * @param context facade object that permit interaction between
   *  the current bot and the game.
   */
  public void init(BotContext context);
  
  /** Called by the game in a bot specific thread.
   *  This method is called once.
   */
  public void run();
  
  /** Called by the game to indicated that the
   *  current bot is dead.
   */
  public void destroy();
  
  /** Returns the filename, relative to the bot class,
   *  of the image representing the bot in the arena.
   * @return the filename of the bot image  
   */
  public String getImageName(); 	  
  
  
  /** Returns the preferred number of bots.
   * @return the preferred number of bot of an army.
   */
  int getDefaultArmySize() throws DeadBotException;

}
