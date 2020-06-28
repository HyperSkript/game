import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Component extends Applet implements Runnable {
	public static final long serialVersionUID = 1L;
	
	public static int pixelSize = 3;
	
	public static boolean isRunning = false;
	
	public static Dimension size = new Dimension(750, 530);
	public static Dimension pixel = new Dimension((int) size.getWidth() / pixelSize, (int) size.getHeight() / pixelSize);
	
	public static Game game;
	
	public static Image screen;
	
	public Component() {
		setPreferredSize(size);
	}
	
	public void init() {
		screen = createVolatileImage((int) pixel.getWidth(), (int) pixel.getHeight());
		
		game = new Game();
	}
	
	public void start() {
		System.out.println("Started");
		isRunning = true;
		
		new Thread(this).start();
	}
	
	public void stop() {
		isRunning = false;
	}
	
	public void tick() {
		game.tick();
	}
	
	public void render() {
		Graphics g = screen.getGraphics();
		
		game.render(g);
		
		g.dispose();
		g = getGraphics();
		
		g.drawImage(screen, 0, 0, (int) size.getWidth(), (int) size.getHeight(), null);
		
		g.dispose();
	}
	
	public void run() {
		while(isRunning) {
			tick();
			render();
			
			try {
				Thread.sleep(5);
			} catch(Exception e) { }
		}
	}
	
	public static void main(String args[]) {
		System.out.println("Starting");
		Component component = new Component();
		
		JFrame frame = new JFrame();
		frame.add(component);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("TITLE");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
		
		component.init();
		component.start();
	}
	
}
