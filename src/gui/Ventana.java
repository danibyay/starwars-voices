package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import javax.swing.JPanel;

public class Ventana extends JFrame{

	JPanel panelvoces;
	JPanel panelorig;
	JPanel paneltransf;
	
	public Ventana(){
		super("Transforma tu voz");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		
		this.setSize(new Dimension(1000, 800)); 
		
		this.panelvoces = new PanelVoces();
		this.panelvoces.setSize(new Dimension(400,400));
		this.panelorig = new JPanel();
		this.panelorig.setSize(new Dimension(512,350));
		this.paneltransf = new JPanel();
		this.paneltransf.setSize(new Dimension(512,350));
		
		buildLayout();
		
		this.setVisible(true);	
	}
	
	
	public void buildLayout(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridheight=2; 
		c.gridy = 0; c.gridx = 0; 
		this.add(panelvoces, c);
		c.gridheight = 1;
		c.gridx = 1;
		this.add(panelorig, c);
		c.gridy = 1;
		this.add(paneltransf, c);
		
	}
	
	
	public JPanel getPanelorig() {
		return panelorig;
	}


	public void setPanelorig(JPanel panelorig) {
		this.panelorig = panelorig;
	}


	public JPanel getPaneltransf() {
		return paneltransf;
	}


	public void setPaneltransf(JPanel paneltransf) {
		this.paneltransf = paneltransf;
	}


	public static void main(String[] args) {
		Ventana v = new Ventana();
	}


}
