package codenegative.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class Renderer extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	
	public static final Rectangle REND_BOUNDS = new Rectangle(0, 80, 640, 300);
	public static final Dimension REND_DIMS = new Dimension(640, 300);
	public static final int REND_Y = 300;
	public static final int REND_X = 640;

	
	
	private Border b = BorderFactory.createLineBorder(Color.GRAY);
	
	public static float x = -10.0f;
	private float xVel = 0.0f;
	public static float y = -10.0f;
	public float yVel = 0.0f;
	public float gravity = 0.08f;
	public float friction = .99f;
	public float xScl = 7.0f;
	public float yScl = 7.0f;
	
	private int xStart;
	private int yStart;
	private int xFin;
	private int yFin;
	private int xNow;
	private int yNow;
	
	
	
	private boolean drawV = false;
	private boolean yBounce = true;
	private boolean isMousePressed = false;
	private boolean isInside;
	
	

	Timer tm = new Timer(5,this);
	
	public Renderer(){

		setBorder(b);
		setSize(REND_DIMS);
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		super.paintComponent(g);
		
			
			Shape cir = new Ellipse2D.Float(x,y,10.0f,10.0f);
			Shape lin = new Line2D.Float(0.0f, 0.0f, 100.0f, 100.0f);
			if(drawV){
				g2.setColor(Color.RED);
				g.drawLine(xStart+5,yStart+5,xNow+5,yNow+5);
				}
			g2.setColor(Color.BLACK);
			g2.fill(cir);
			
			
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		x += xVel; //increments x by xVelocity
		
		
		
		yVel = yDecay(yVel);
		if(yBounce){
			y = y + yVel;//add yVel increment to y value
			yVel += gravity; //accelerates y by
		}else{
			yVel = 0.0f;
		}
		
		
		float yDist = 290 - y;
		
		if(x+10 >= 640 ||x <= 0){//If ball hits left/right barrier
			
			xVel = -xVel; //Invert x direction
			xVel = xDecay(xVel);//Apply friction decay to xVel
			
		}else if(x+10+xVel>640){
			
			xVel = -xVel;
			x = 630;
			xVel = xDecay(xVel);//Apply friction decay to xVel
			System.out.println("X Collision failure");
			
		}else if(x + xVel < 0){
			
			xVel = -xVel;
			x = 0;
			xVel = xDecay(xVel);//Apply friction decay to xVel
			System.out.println("X Collision failure");	
			
		}
		
	if(yBounce){
		if(y + 10 >= 300.0f || y <= 0.0f){//If ball hits bottom barrier
			
			yVel = -yVel;//Invert y direction
			xVel = xDecay(xVel); //Apply friction decay to xVel 
			
		}else if(y + 10 + yVel > 300){ //if Y increment is greater than yDist 
			
			System.out.println("Y Collision Failure");
			yVel = -yVel;
			y = 290.0f;
			xVel = xDecay(xVel); //Apply friction decay to xVel
			
		}else if(y + yVel < 0){
			
			System.out.println("Y Collision Failure");
			yVel = -yVel;
			y = 0.0f;
			xVel = xDecay(xVel); //Apply friction decay to xVel
			
		}
	}
		if(Math.abs(yVel) <= 1.0f && Math.abs(yDist) == 1.0f){
			yBounce = false;
			yVel = 0.0f;
			xVel = xDecay(xVel);
			
		}
		
		
		if(Math.abs(yDist) <= 1.0f && Math.abs(yVel) <= 0.1f && Math.abs(xVel) <= 0.1f){//If yDist and yVel are below 2, and xVel is below 1 
			yVel = 0.0f; //stop y-axis motion
			xVel = 0.0f; //stop x-axis motion
			y = 290.0f;
			tm.stop(); //stop action loop
			System.out.println("Simulation stopped");
		}
		
		
		
		
		
		
		//System.out.println(x + "\t" + y);
		//System.out.println(xVel + "\t" + yVel);
		System.out.println(yBounce);
		repaint(); //"calls" paint method again 
	}
	public float yDecay(float yVel){ //decreases absolute y value
	if(yBounce){
		if(yVel>0){ //if falling
			return yVel; //- gravity; do nothing 	
		}else if(yVel<0){ //else if moving up
			return yVel + gravity; //slow object by adding gravity
		}else{
			return 0;
		}
	}else{
		return 0;
	}
	}
	public float xDecay(float xVal){
		if(xVal>0){
			return xVal*friction;
		}else if(xVal<0){
			return xVal*friction;
		}else{
			return 0.0f;
		}
	}
	
	public void getVelX(int x1, int x2){
		xVel = (x1-x2)/xScl;
	}
	public void getVelY(int y1, int y2){
		yVel = (y2-y1)/yScl;
	}
	public void reset(){
		x = -10.0f;
		xVel = 0.0f;
		y = -10.0f;
		yVel = 0.0f;
		yBounce = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		reset();
		
		x = e.getX();
		if(e.getY() <= 290) y = e.getY();
		else y = e.getY() + 10;
		tm.start();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		isInside = true;
		x = e.getX();
		if(e.getY() <= 290) y = e.getY();
		else y = e.getY() + 10;
	}
	@Override
	public void mouseExited(MouseEvent e) {
		isInside = false;
		if(isMousePressed) reset();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(isInside){
		isMousePressed = true;
		reset();
		tm.stop();
		xStart = e.getX();
		yStart = e.getY();
		
		}
	}
	@Override
	public void mouseReleased(MouseEvent e){
		isMousePressed = false;
		xFin = e.getX();
		yFin = e.getY();
		getVelX(xStart, xFin);
		getVelY(yFin, yStart);
		drawV = false;
		tm.start();
	}

	@Override
	public void mouseDragged(MouseEvent e){
		x = e.getX();
		y = e.getY();
		drawV = true;
		xNow = e.getX();
		yNow = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
	



}
