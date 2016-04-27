package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Driver4;


public class PanelVoces extends JPanel implements ActionListener, Runnable{
	private JButton grabar;
	private JButton b1,b2,b3,b4,b5;
	private JLabel labelMensaje;
	private String mensaje;
	private Driver4 driver;
	private boolean clickgrabar;
	private Thread h;
	private int segundos;
	
	public PanelVoces(){
		driver = new Driver4();
		h = new Thread();
		
		b1 = new JButton(new ImageIcon("darth.png"));
		b2 = new JButton(new ImageIcon("storm.png"));
		b3 = new JButton(new ImageIcon("cepo.png"));
		b4 = new JButton(new ImageIcon("artu.png"));
		b5 = new JButton(new ImageIcon("yoda.png"));
		
		this.segundos = 5;
		this.labelMensaje = new JLabel("    dfs               ");
		this.mensaje = "" + this.segundos;
		
		this.grabar = new JButton(new ImageIcon("recordd.png"));
		
		this.grabar.addActionListener(this);
		this.b1.addActionListener(this);
		this.b2.addActionListener(this);
		this.b3.addActionListener(this);
		this.b4.addActionListener(this);
		this.b5.addActionListener(this);
		
		buildLayout();
	}
	
	public void buildLayout(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth=1; 
		c.gridy = 0; c.gridx = 0; this.add(b1, c);
		c.gridx = 1; this.add(grabar,c);
		c.gridx = 2; this.add(b2, c);
		c.gridy = 1; c.gridx = 0; this.add(b3, c);
		c.gridx = 1; this.add(b4, c);
		c.gridx = 2; this.add(b5, c);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==grabar){
			driver.grabarAudio();
			this.clickgrabar = true;
		}
		else{
			if(e.getSource()==b1){
				driver.setSel(1);
			}
			else if(e.getSource()==b2){
				driver.setSel(2);
			}
			else if(e.getSource()==b3){
				driver.setSel(3);
			}
			else if(e.getSource()==b4){
				driver.setSel(4);
			}
			else if(e.getSource()==b5){
				driver.setSel(5);
			}
			driver.reproducirFourier();
		}
		

	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.white);
		//g.drawString(this.segundos+"", this.getWidth()/2-40, 40);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(this.clickgrabar){
			h.start();
			try {
				h.sleep(1000);
				this.segundos--;
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}
