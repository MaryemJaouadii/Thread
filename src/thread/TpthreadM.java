package thread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TpthreadM extends JFrame implements ActionListener {

	private JButton start;
	private JButton stop;

	Monpanneau pc;
	private Animation a;
	boolean etat = false;

	

	int x = 10;
	int xinit=10;
	int xf = 10;
	int y = 300;
	int yinit=300;
	int yf = 300;
	
	
	int x2 = 550;
	int x2init=550;
	int y2init=550;
	int y2=300;
	private AnimationBack b;
	
	
	double a1;
	double a2;

	/**
	 * y=ax+b;
	 */

	public TpthreadM() {
		this.setTitle("Animation");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		start = new JButton("start");
		stop = new JButton("stop");

		JPanel pn = new JPanel();
		pn.setLayout(new FlowLayout());
		pn.setBackground(Color.DARK_GRAY);

		pn.add(start);
		pn.add(stop);

		pc = new Monpanneau();
		pc.setBackground(Color.LIGHT_GRAY);

		this.setLayout(new BorderLayout());
		this.add(pn, BorderLayout.NORTH);
		this.add(pc, BorderLayout.CENTER);

		this.setVisible(true);

		start.addActionListener(this);
		stop.addActionListener(this);
		pc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
					xf = e.getX();
					yf = e.getY();
					a1=(yf-y)/(xf-x);
					a2=(yf-y2)/(xf-x2);
			}
		});

		a = new Animation();
		b=new AnimationBack();
		a.start();
		try {
			a.join();
		} catch (InterruptedException e1) {
			System.out.println("erreur dattente");
		}
		b.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			etat = true;
		} else {
			etat = false;
		}
	}

	class Animation extends Thread {
		@Override
		public void run() {
			while ( x<xf || x>xf || y<yf || y>yf || x2<xf || x2>xf || y2<yf || y2>yf) {
				while (etat) {

					System.out.println("x= " + x+" , y= "+y+" , x2= "+x2+" , y2= "+y2 +" et xf = " +xf +", yf= "+ yf);
					System.out.println();
					
					
				
					
					if (x<xf)
						x++;
					else if (x>xf)
						x--;
						
					if (y<yf)
					y=(int) (a1*x+y);
						//y++;
					
					else if (y>yf)
						y--;						
					
					if (x2<xf)
						x2++;
					else if (x2>=xf)
						x2--;
						
					if (y2<yf)
					y2++;
					
					else if (y2>yf)
						y2--;
									
					pc.repaint();
					
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (x==xf && y==yf &&x2==xf && y2==yf) etat=false;
				}
				System.out.println("la premiere boucle est terminée");
				pc.repaint();
			}
		}
	}
	
	class AnimationBack extends Thread {
	
		@Override
		public void run() {
			etat=true;
			while (x<xinit ||x>xinit ||y>yinit|| y<yinit || x2>x2init || x2<x2init || y>y2init || y<y2init) {
				while (etat) {

					//System.out.println("i am thread 2");
					System.out.println("x= " + x+" , y= "+y+" , x2= "+x2+" , y2= "+y2 +" et xinit = " +xinit +", yinit= "+ yinit);
					if (x<xinit) 
						x++;
					else if (x>xinit)
						x--;
						
					if (y<yinit)
					y++;
					
					else if (y>yinit)
						y--;
					
					if (x2<x2init) 
						x2++;
					else if (x2>x2init)
						x2--;
						
					if (y2<yinit)
					y2++;
					
					else if (y2>yinit)
						y2--;
					
					pc.repaint();
					try {
						Thread.sleep(5); 
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (x==xinit && y==yinit &&x2==xinit && y2==yinit) etat=false;
				}
				pc.repaint();
			}
		}
	}	
			
			
			
		
	
	
	
	

	class Monpanneau extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);

			g.setColor(Color.magenta);
			g.drawRect(xf, yf, 100, 100);

			g.setColor(Color.black);
			g.fillOval(x, y, 100, 100);

			g.setColor(Color.white);
			g.fillOval(x2, y2, 100, 100);
		}
	}

	public static void main(String[] args) {
		new TpthreadM();
	}

}