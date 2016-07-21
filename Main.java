package codenegative.engine;

import javax.swing.*;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Main implements Runnable{
	
	int x = 0;
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Main());
		thread1.start();
				
		
		//Enable Anti Aliasing
		System.setProperty("awt.useSystemAAFontSettings","on");
		System.setProperty("swing.aatext", "true");
		}
	
	public Main(){
		
	}

	public void run(){
		
		try{
			
			Renderer rend = new Renderer();
			WindowFrame fr = new WindowFrame( "NegativePhysics 0.0.4", rend);
			JOptionPane p = new JOptionPane();
			
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Trebuchet MS.ttf")));
			
		}catch(Exception e){
			
		}
		
	}
}
