package fr.dauphine.robombastic.model;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import fr.dauphine.robombastic.*;
import fr.dauphine.robombastic.graphicalobjects.Bomb;
import fr.dauphine.robombastic.gui.GameFrame;
import fr.dauphine.robombastic.implementation.*;
import fr.dauphine.robombastic.util.FileUtils;

import javax.swing.*;

/**
 * 
 * @author Anis TLILANE
 *
 */
public class Game {
	public enum Etat{
		init, started, finished, paused
	}
	
	//Etat du jeu
	private Etat etat = Etat.init;
	
	//Les parametres du jeu...
	private GeneralInfo generalInfo = null;
	
	/**
	 * Le nom de fichier texte contenant la description de l’arêne
	 */
	private String arena 		= System.getProperty("user.dir")+"/src/arena.txt";
	
	//private int arenaWidth; //in cells
	//private int arenaHeight; //in cells
	
	/**
	 * sous-répertoire du projet quicontient la description des armées de 
	 * robots qui vont s’affronter sur l’arène
	 */
	private String robotsDir	= "robots";
	
	/**
	 * le nombre maximum de robots que peut creer une armée.
	 */
	private int armyLimit		= 7;

	private Scene	scene;
	
	private String arenaTxt;
	
	/**
	 * Ce map parmet de lier le nom du jar avec la nom de la classe (nom complets)
	 * Ceci permet de recharger le même jar deux fois, et garder la trace des jars
	 * déjà charger dans le jeu
	 */
	private Map<String, String>	mapJarsBotClasses = new HashMap<String, String>();
	
	/**
	 * Ce map permet de faire correspondre le nom de la classe (qui est le nom de l'armée)
	 * avec la liste des bots qui constituent cette armée
	 */
	private Map<String, List<Bot>> mapNamesArmies = new HashMap<String, List<Bot>>();
	
	/**
	 * Ce map permet d'avoir l'acces au botContext de d'un autre Bot afin de verifier ou
	 * de modifier son état... (????)
	 */
	private Map<Bot, BotContext> mapBotBotContext = new HashMap<Bot, BotContext>();
	
	private List<RobotThread> listThreadsOfRobots = new ArrayList<RobotThread>();
	
	private ThreadGame		  threadGame;
	
	public void extractParameters(String[] parameters){
		String error = null;
		
		int radarSize 		= 3;
		int turnDuration 	= 1000;
		int bombDuration	= 6;
		
		for (int i=0; i<parameters.length; i++){
			String nameParam = parameters[i];
			
			if (nameParam.equals("-arena")){
				arena = parameters[i+1];
				try{
					arena = parameters[i+1];	
				}
				catch (Exception e){
					error = "Parametre arena est incorrect ou non specifie ...";
				}
			}
			else if (nameParam.equals("-robotsDir")){
				try{
					robotsDir = parameters[i+1];	
				}
				catch (Exception e){
					error = "Parametre robotsDir est incorrect ou non specifie ...";
				}
			}
			else if (nameParam.equals("-radarSize")){
				try{
					radarSize = Integer.parseInt(parameters[i+1]);	
				}
				catch (Exception e){
					error = "Parametre radarSize est incorrect ou non specifie ...";
				}
			}
			else if (nameParam.equals("-armyLimit")){
				try{
					armyLimit = Integer.parseInt(parameters[i+1]);	
				}
				catch (Exception e){
					error = "Parametre armyLimit est incorrect ou non specifie ...";
				}
			}
			else if (nameParam.equals("-turnDuration")){
				try{
					turnDuration = Integer.parseInt(parameters[i+1]);	
				}
				catch (Exception e){
					error = "Parametre turnDuration est incorrect ou non specifie ...";
				}
			}
			else if (nameParam.equals("-bombDuration")){
				try{
					bombDuration = Integer.parseInt(parameters[i+1]);	
				}
				catch (Exception e){
					error = "Parametre bombDuration est incorrect ou non specifie ...";
				}
			}
			else{
				error = "Erreur, parametre non reconuu ...";
			}
		}
		
		if (error != null){
			System.err.print(error);
			System.exit(1);
		}
		
		initGeneralInfo(radarSize, turnDuration, bombDuration);
	}
	
	/**
	 * Accéder au map des noms et listes des robots
	 * @return
	 */
	public Map<String, List<Bot>> getMapNamesArmies() {
		return mapNamesArmies;
	}
	
	/**
	 * Accéder au map des bot avec leurs BotContext correspondant
	 * @return
	 */
	public Map<Bot, BotContext> getMapBotBotContext() {
		return mapBotBotContext;
	}
	
	/**
	 * Init the generalInfo of the game, and create the scence object which
	 * represents the arena cells matrix
	 * 
	 * @param radarSize
	 * @param turnDuration
	 * @param bombDuration
	 */
	private void initGeneralInfo(int radarSize, int turnDuration, int bombDuration){
		arenaTxt = FileUtils.readTextFromFile(this.arena);
		
		if (arenaTxt == null){
			System.err.println("Erreur du fichier de description de l'arene du jeu !!!");
			System.exit(-1);
		}
		
		String rows[] = arenaTxt.split("\\n");
		
		if (rows.length <= 3){
			System.err.println ("Une arene doit contenir au moins 10 lignes");
			System.exit(-1);
		}
		
		if (rows[0].length() <= 3){
			System.err.println ("Une arene doit contenir au moins 10 colonnes");
			System.exit(-1);
		}
		
		int arenaHeight = rows.length;
		int arenaWidth = rows[0].length();
		this.generalInfo = new ImpGeneralInfo(arenaWidth, arenaHeight, radarSize, turnDuration, bombDuration);
		
		scene = new Scene(arenaHeight, arenaWidth);
		initScene();
		scene.setDelayBomb(bombDuration);
	}
	
	private void initScene(){
		String rows[] = arenaTxt.split("\\n");
		
		for (int i=0; i<this.generalInfo.getArenaHeight(); i++){
			String row = rows[i];
			for (int j=0; j<this.generalInfo.getArenaWidth(); j++){
				char cell = row.charAt(j);
				
				if (cell == 'w'){
					scene.put(i, j, ArenaItem.WALL);
				}
				else if (cell == ' '){
					scene.put(i, j, ArenaItem.EMPTY);
				}
				else{
					System.err.println ("Caractère non pris en charge : "+cell);
					System.exit(-1);
				}
			}
		}
	}
	
	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}
	
	/**
	 * Load the robots from the direcotry robotsDir
	 * 
	 * Permet de charger les jars robots et de réaliser l'instanciation des robots
	 * et les stocker dans les listes correspondantes
	 */
	private void initRobots(){
		mapNamesArmies.clear();
		mapBotBotContext.clear();
		
		//Faire la boucle sur le nombre de bots dans une armée
		String an = "Robot01";
		mapNamesArmies.put(an, new ArrayList<Bot>());
		for (int i=0; i<armyLimit; i++){
			Bot bot = new Robot01();
			
			BotContext botContext = new ImpBotContext(this, "Robot01");
			bot.init(botContext);
			((ImpCurrentBotInfo)botContext.getCurrentBotInfo()).setBot(bot);
			mapNamesArmies.get(an).add(bot);
			mapBotBotContext.put(bot, botContext);
			this.getScene().addBot(bot, i+1);
		}
		
		an = "Robot02";
		mapNamesArmies.put(an, new ArrayList<Bot>());
		this.scene.setEnemy(Robot02.class.getClass().getSimpleName());
		for (int i=0; i<armyLimit; i++){
			Bot bot = new Robot02();
			
			BotContext botContext = new ImpBotContext(this, "Robot02");
			bot.init(botContext);
			((ImpCurrentBotInfo)botContext.getCurrentBotInfo()).setBot(bot);
			mapNamesArmies.get(an).add(bot);
			mapBotBotContext.put(bot, botContext);
			this.getScene().addBot(bot, i+1);
		}
		
		scene.updateCellSizes();
		
		if (true){
			return;
		}
		
		/**
		 * Verifier le dossier de l'implémentation des robots (dossier contenant des jars)
		 */
		File file = new File(robotsDir);
		if (!file.exists()){
			System.err.println ("le dossier des robots 'robotsDir' "+robotsDir+" est introuvable ...");
			System.exit(-1);
		}
		
		/**
		 * Récupérer les jars du dossier
		 * chaque jar contient une classe qui implémente les robots
		 */
		File[] jars = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				if (file.isFile() && file.getName().endsWith(".jar")){
					return true;
				}
				return false;
			}
		});
		
		/**
		 * Il faut avoir au moins deux jars différents (deux armée différentes)
		 */
		if (jars.length < 2){
			System.err.println ("Erreur, Nous devons avoir au moins deux fichiers JARs dans le repetoir : "+robotsDir);
			System.exit(-1);
		}
		
		try{
			mapNamesArmies.clear();
			mapBotBotContext.clear();

			//Pour chaque jar...
			for (File jarFile : jars){
				//Lire le manifest du jar
				Manifest mf = new JarFile(jarFile.getAbsolutePath()).getManifest();
				
				//récupérer le nom de la classe qui implémente l'interface Bot ...
				String botClass = mf.getMainAttributes().getValue("Bot-Class");
				
				String armyName = jarFile.getName();
				
				//Correspondre le nom du jar avec le nom de la classe ...
				mapJarsBotClasses.put(armyName, botClass);
				
				//Correspondre le nom du jar avec la liste des bots de la même armée 
				mapNamesArmies.put(armyName, new ArrayList<Bot>());
				
				/**
				 * Dans cette parti, on charge la classe qui implémente le robot afin de l'instancier
				 */
				Class<?> clazz = loadClass(jarFile, botClass);
				
				//Obtenir le constructeur (PAR DEFAUT)
				Constructor<?> consturctor = clazz.getConstructor();
				
				//Faire la boucle sur le nombre de bots dans une armée
				for (int i=0; i<armyLimit; i++){
					/**
					 *  Instancier un bot à travers un constructeur par défaut (IL FAUT PAS UTILISER UN CONSTRUCTEUR AVEC PARAMETRE)
					 */
					Bot bot = (Bot)consturctor.newInstance(); 
					
					/**
					 * Créer un objet de botContext
					 */
					BotContext botContext = new ImpBotContext(this, botClass);
					
					/**
					 * Initialiser le botContext d'un Bot
					 */
					bot.init(botContext);
					
					/**
					 * Indiquer au botContext qu'elle bot qui lui correspond
					 */
					((ImpCurrentBotInfo)botContext.getCurrentBotInfo()).setBot(bot);
					
					/**
					 * Ajouter le bot à la liste de son armée identifier par armyName (le nom du fichier jar)
					 */
					mapNamesArmies.get(armyName).add(bot);
					
					/**
					 * Faire correspondre le bot avec son botContext
					 */
					mapBotBotContext.put(bot, botContext);
					
					/**
					 * Ajout le bot à la scène (l'arène)
					 */
					this.getScene().addBot(bot, i+1);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Méthode utilitaire qui permt de charger une classe dynamiquement à partir d'un fichier jar
	 * 
	 * @param jarFile
	 * @param className
	 * @return
	 */
	private static Class<?> loadClass(File jarFile, String className){
		Class<?> clazz = null;
		
		try{
			URL url = jarFile.toURI().toURL();
			URL[] urls = new URL[]{url};
	
			ClassLoader cl = new URLClassLoader(urls);
			clazz = cl.loadClass(className);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return clazz;
	}
	
	/**
	 * Avoir l'accès à la scene
	 * @return
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Mettre à jour les dimensions des cellules
	 * @param width
	 * @param height
	 */
	public void updateSize(int width, int height){
		int cellWidth = width / this.generalInfo.getArenaWidth();
		int cellHeight = height / this.generalInfo.getArenaHeight();
		
		scene.setCellSize(cellWidth, cellHeight);
	}
	
	/**
	 * Avoir l'acces à l'état du jeu
	 * @return
	 */
	public Etat getEtat() {
		return etat;
	}
	
	/**
	 * Si l'état du jeu est terminée
	 * @return
	 */
	public boolean isFinished(){
		return this.etat.equals(Etat.finished);
	}
	
	/**
	 * Si l'état du jeu est à l'initialisation (au début)
	 * @return
	 */
	public boolean isInit(){
		return this.etat.equals(Etat.init);
	}
	
	/**
	 * Si le jeu est suspendu (arrêt temporraire)
	 * @return
	 */
	public boolean isPaused(){
		return this.etat.equals(Etat.paused);
	}
	
	/**
	 * Si l'état du jeu est démarré (en cours du jeu)
	 * @return
	 */
	public boolean isStarted(){
		return this.etat.equals(Etat.started);
	}
	
	/**
	 * Obtenir les informations sur le jeu sous forme de chaîne de caractère
	 * @return
	 */
	public String getGameInfo(){
		String info = "";
		if (this.isFinished()){
			info = "Jeu fini";
		}
		else if (this.isInit()){
			info = "Jeu non-démarré";
		}
		else if (this.isPaused()){
			info = "Jeu Suspendu";
		}
		else if (this.isStarted()){
			info = "Jeu en cours";
		}
		
		int i = 1;
		for (String key : this.mapNamesArmies.keySet()){
			info += "  -  Armi N° "+i+ " ("+key+") ";
			info += " : "+this.mapNamesArmies.get(key).size()+" robots";
			
			info += "    ";
			
			i++;
		}
		
		return info.trim();
	}
	
	/**
	 * Pour demarrer le jeu ...
	 */
	public void start(){
		if (this.isPaused()){
			this.etat = Etat.started;
		}
		else{
			this.etat = Etat.started;
			
			this.initScene();
			this.initRobots();
			this.createRobotThreads();
			
			threadGame = new ThreadGame(this);
			threadGame.start();
		}
	}
	
	/**
	 * Pour suspendre le jeu
	 */
	public void pause(){
		if (!ImpBotContext.paused){
			this.etat = Etat.paused;
			ImpBotContext.paused = true;
		} else{
			this.etat = Etat.started;
			ImpBotContext.paused = false;
		}

	}
	
	/**
	 * Pour arrêter le jeu
	 */
	public void stop(){
		this.etat = Etat.finished;
	}
	
	/**
	 * Pour créer les threads des robots (pour chaque robot, on lui crée un thread)
	 */
	private void createRobotThreads(){
		this.listThreadsOfRobots.clear();
		for (String key : this.mapNamesArmies.keySet()){
			List<Bot> robots = this.mapNamesArmies.get(key);
			for (Bot robot : robots){
				this.listThreadsOfRobots.add(new RobotThread(robot));
			}
		}
	}
	
	/**
	 * Cette class nous permet de creer un thread pour un robot 
	 * @author Anis TLILANE
	 *
	 */
	private static class RobotThread extends Thread{
		private Bot robot = null;


		public RobotThread(Bot robot){
			this.robot = robot;
		}

		@Override
		public void run() {
			robot.run();
		}
		
	}
	
	/**
	 * Pour le thread du jeu, oui il y a la boucle du jeu...
	 * @author Anis TLILANE
	 */
	private static class ThreadGame extends Thread{
		private Game game;
		
		public ThreadGame(Game game) {
			this.game = game;
		}
		
		public void run(){
			try {
				for (RobotThread thread : this.game.listThreadsOfRobots){
					thread.start();
				}
				
				int tour = 1;
				while (Thread.currentThread().isAlive()){//La boucle du jeu
					//Si le jeu est en cours ...
					if (game.isStarted()){//Le cas du Démarrage
						
						int time =(int) System.currentTimeMillis();
						while (time + game.generalInfo.getTourTime() >(int) System.currentTimeMillis());
						//sleep(game.generalInfo.getTourTime());

						bombsController();

						endGame();

						System.out.println("------------------------ Boucle du jeu ... "+tour++);
						
						GameFrame.getInstanceWithoutCreation().updateDrawing();
					
					}
					
					//Si le jeu est suspendu
					else if (game.isPaused()){//Le cas du Pause
						while (!game.isStarted()){
							sleep(500);
						}
					}
					
					//Si le jeu est achevé
					else{//Le cas du jeu est fini
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void bombsController(){
			Map<Bomb, Integer> mapBombs = game.scene.getMapBombs();
			for (Map.Entry<Bomb, Integer> mp :mapBombs.entrySet()) {

				if (!mp.getKey().isExploded()) {
					if (mp.getValue() == 1) {
						mp.setValue(0);
						this.game.scene.ex(mp.getKey());
					} else {
						mp.setValue(mp.getValue() - 1);
					}
				}
			}
		}

		public void endGame() throws DeadBotException {

			int nbBotAliveR1=0;
			for (Bot bot: game.mapNamesArmies.get("Robot01")){
				if (	((Robot)bot).getBotContext().getCurrentBotInfo().isAlive()	){
					nbBotAliveR1++;
				}
			}

			if (nbBotAliveR1==0){
				game.pause();
				JOptionPane.showMessageDialog(GameFrame.getInstance(game).getpInfo(), "Vainqueur : Armées Robot02", "Information", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} else {
				int nbBotAliveR2 =0;
				for (Bot bot: game.mapNamesArmies.get("Robot02")){
					if (	((Robot)bot).getBotContext().getCurrentBotInfo().isAlive()	){
						nbBotAliveR2++;
					}
				}
				if (nbBotAliveR2==0){
					game.pause();
					JOptionPane.showMessageDialog(GameFrame.getInstance(game).getpInfo(), "Vainqueur : Armées Robot01", "Information", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
}