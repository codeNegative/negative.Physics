package codenegative.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class WindowFrame extends JFrame{
	
	private static final Dimension DEFAULT_BOUNDS = new Dimension(640,480);
	
	private boolean shouldRender = false;
	private Renderer rend;
	
	
	Font fnt = new Font("Trebuchet MS", Font.BOLD, 16);
	
	JPanel panel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel posPanel = new JPanel();
	
	JButton nextButton = new JButton();
	JButton backButton = new JButton();
	JButton goButton = new JButton();
	
	JLabel label = new JLabel();
	JLabel posLabel = new JLabel();
	JTextArea textArea;
	
	JScrollPane scroll;

	public WindowFrame(Renderer rend){
		this("JFrame", DEFAULT_BOUNDS, rend);
	}
	public WindowFrame(String name, Renderer rend){
		this(name, DEFAULT_BOUNDS, rend);
	}
	
	
	public WindowFrame(String name, Dimension bounds, Renderer rend){
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(bounds);
		setResizable(false);
		setLayout(null);
		
		label.setBackground(new Color(0,200,0));
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 44));
		label.setText(" Euler's Integration Physics");
		
		nextButton.setText("Next");
		nextButton.setFont(fnt);
		nextButton.setBackground(Color.WHITE);
		
		goButton.setText("Go");
		goButton.setFont(fnt);
		goButton.setBackground(new Color(150,180,255));
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(nextButton);
		buttonPanel.add(Box.createHorizontalStrut(10));
		buttonPanel.add(goButton);
		buttonPanel.setBounds(460, 390, 206, 50);
		add(buttonPanel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.setBackground(new Color(0,200,0));
		panel.setBounds(0, 0, 640, 80);
		panel.add(label);
		add(panel);
		
		posLabel.setBackground(Color.BLACK);
		
		posPanel.setLayout(new BoxLayout(posPanel, BoxLayout.LINE_AXIS));
		posPanel.setBackground(Color.BLACK);
		posPanel.setBounds(0, 0, 100, 80);
		posPanel.add(posLabel);
		add(posPanel);
		
		this.rend = rend;
		add(rend);
		rend.setBounds(Renderer.REND_BOUNDS);
		rend.setVisible(false);
		setVisible(true);
		
		Handler h = new Handler();
		nextButton.addActionListener(h);
		goButton.addActionListener(h);
		
	}
	
	private class Handler implements ActionListener{
		private boolean status = false;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == nextButton){
				
			}else if(e.getSource() == goButton){
				if(!status){//if the simulation isn't already running, start it
					status = !status;
					shouldRender = !shouldRender;
					rend.setVisible(shouldRender);
					System.out.println("Started!");
					goButton.setText("Reset");
					goButton.setBackground(Color.PINK);
				}else{//if it's running, 
					//goButton.setText("Go");
					//goButton.setBackground(new Color(150,180,255));
					
					rend.setVisible(false);
					rend.reset();
					rend.setVisible(true);
					System.out.println("Reset!");
				}
			}
			
		}
		
	}
}
