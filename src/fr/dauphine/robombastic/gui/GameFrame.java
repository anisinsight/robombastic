package fr.dauphine.robombastic.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.dauphine.robombastic.gui.drawing.DrawingZone;
import fr.dauphine.robombastic.model.Game;

/**
 * 
 * @author Anis TLILANE
 *
 *	La fenetre du jeu
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Une instance d'une partie du jeu ...
	 */
	private Game 		game = null;
	
	/**
	 * La zone de dessin qui fait le rendu de la scence et du jeu
	 */
	private DrawingZone	drawingZone;
	
	private JButton		bDemarrerJeu, bPauseJeu, bStopJeu;
	
	private JPanel		pInfo;
	private JLabel		lInfo;
	
	private static GameFrame instance = null;
	
	public static GameFrame getInstance(Game game) {
		if (instance == null){
			instance = new GameFrame(game);
		}
		
		return instance;
	}
	
	public static GameFrame getInstanceWithoutCreation(){
		return instance;
	}
	
	private GameFrame(Game game){
		this.game = game;
		
		createGUI();
		layoutGUI();
		eventsGUI();
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		updateDrawing();
	}
	
	private void createGUI(){
		this.setTitle("Projet Robombastic - 2016/2017");
		drawingZone = new DrawingZone(this.game);
		
		bDemarrerJeu = new JButton("Démarrer");
		
		bPauseJeu = new JButton("Pause");
		
		bStopJeu = new JButton("Stop");
				
		pInfo = new JPanel();
		lInfo = new JLabel();
		
		lInfo.setText(game.getGameInfo());
		
		pInfo.setBackground(new Color(0xd4cece));
	}
	
	private void layoutGUI(){
		this.setSize(new Dimension(1000, 600));
		this.setMinimumSize(this.getSize());
		this.setLocationRelativeTo(null);
		
		pInfo.setLayout(new FlowLayout());
		pInfo.add(lInfo);
		
		Container container = this.getContentPane();
		
		GroupLayout layout = new GroupLayout(container);
		container.setLayout(layout);
		
		this.updateLayout();
		
		updateLayout();
	}
	
	private void updateLayout(){
		Dimension size = this.getContentPane().getSize();
		
		int width = size.width-6;//3+3
		int height = size.height-34;//3+3+3+25
		
		if (width < 0 || height<0){
			return;
		}
		
		GroupLayout layout = (GroupLayout)this.getContentPane().getLayout();
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addComponent(pInfo, 100, 100, Short.MAX_VALUE)
								.addGap(3)
								.addComponent(bDemarrerJeu)
								.addComponent(bPauseJeu)
								.addComponent(bStopJeu)
						)
						.addComponent(drawingZone, width, width, width)
				)
				.addGap(3)
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(3)
				.addGroup(layout.createParallelGroup()
						.addComponent(pInfo, 25, 25, 25)
						.addComponent(bDemarrerJeu, 25, 25, 25)
						.addComponent(bPauseJeu, 25, 25, 25)
						.addComponent(bStopJeu, 25, 25, 25)
				)
				.addGap(3)
				.addComponent(drawingZone, height, height, height)
				.addGap(3)
		);
		
		this.game.updateSize(width, height);
		this.drawingZone.updateSize(width, height);
	}
	
	private void eventsGUI(){
		this.getContentPane().addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				thisComponentResized(e);
			}
		});
		
		bDemarrerJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bDemarrerJeuActionPerformed(e);
			}
		});
		
		bPauseJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bPauseJeuActionPerformed(e);
			}
		});
		
		bStopJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bStopJeuActionPerformed(e);
			}
		});
	}
	
	private void thisComponentResized(ComponentEvent e){
		this.updateLayout();
	}
	
	private void bDemarrerJeuActionPerformed(ActionEvent e){
		this.game.start();
		
		this.lInfo.setText(this.game.getGameInfo());
		
		bDemarrerJeu.setEnabled(false);
		
		this.drawingZone.refresh();
	}
	
	private void bPauseJeuActionPerformed(ActionEvent e){
		if (!this.game.isPaused()){
			bStopJeu.setEnabled(false);
		}else{
			bStopJeu.setEnabled(true);
		}
		this.game.pause();
		
		this.lInfo.setText(this.game.getGameInfo());
		
		bDemarrerJeu.setEnabled(false);
		this.drawingZone.refresh();
	}
	
	private void bStopJeuActionPerformed(ActionEvent e){
		this.game.stop();
		
		this.lInfo.setText(this.game.getGameInfo());
		
		bDemarrerJeu.setEnabled(false);
		bPauseJeu.setEnabled(false);
		this.drawingZone.refresh();
	}
		
	public void updateDrawing(){
		this.drawingZone.refresh();
	}
	
	public static void showGUI(Game game){
		GameFrame frame = getInstance(game);
		frame.setVisible(true);
	}

	public JPanel getpInfo() {
		return pInfo;
	}
}
