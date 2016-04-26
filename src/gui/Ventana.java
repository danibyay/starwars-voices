package gui;

import java.awt.Dimension;


import javax.swing.JFrame;

import javax.swing.JPanel;

public class Ventana extends JFrame{

	JPanel panelvoces;
	
	public Ventana(){
		super("Transforma tu voz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.setSize(new Dimension(800, 700)); 
		
		this.panelvoces = new PanelVoces();
		this.add(panelvoces);
		
		this.setVisible(true);	
	}
	
	
	public static void main(String[] args) {
		Ventana v = new Ventana();
	}


}
