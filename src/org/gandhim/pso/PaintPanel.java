package org.gandhim.pso;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class PaintPanel extends JPanel
{
	private PSOProcess process;
	private double minX, maxX, minY, maxY;
	private Font font;
	private boolean autoIterate;
	private Timer IterateTimer;

	public PaintPanel()
	{
		super();
		setBackground(Color.LIGHT_GRAY);
		process = new PSOProcess();
		executeProcess(false);
		autoIterate = false;
		
		IterateTimer = new Timer(100, new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(autoIterate)
							executeProcess(true);
					}
				}
		);
		IterateTimer.start();
		
		minX = ProblemSet.LOC_X_LOW;
		maxX = ProblemSet.LOC_X_HIGH;
		minY = ProblemSet.LOC_Y_LOW;
		maxY = ProblemSet.LOC_Y_HIGH;
		
		font = new Font("Serif", Font.PLAIN, 10);
	}
	
	public void setAutoIterate(boolean autoIterate) {
		this.autoIterate = autoIterate;
	}
	public void setIterateTimerDelay(int delay)
	{
		IterateTimer.setDelay(delay);
	}
	
	private Point formattedPoint(double x, double y)
	{
		
		double normX = (x - (minX + maxX)/2)/((maxX - minX)/2);
		double normY = (y - (minY + maxY)/2)/((maxY - minY)/2);
		
		return new Point(getWidth()/2 + (int)(getWidth()/2*normX), getHeight()/2 - ((int)(getHeight()/2*normY)));
	}
	
	public void executeProcess(boolean forward)
	{
		process.execute(forward);
		repaint();
	}
	
	public int getT()
	{
		return process.getT();
	}
	
	private void drawGrid(Graphics g)
	{
		Point point1, point2;
		
		for(int i = (int) minX; i <= maxX; i++)
		{
			point1 = formattedPoint(i, minY);
			point2 = formattedPoint(i, maxY);
			g.drawLine(point1.x, point1.y, point2.x, point2.y);
			g.drawString(String.valueOf(i), point1.x, (point1.y + point2.y)/2);
		}
		
		for(int i = (int) minY; i <= maxY; i++)
		{
			point1 = formattedPoint(minX, i);
			point2 = formattedPoint(maxX, i);
			g.drawLine(point1.x, point1.y, point2.x, point2.y);
			g.drawString(String.valueOf(i), (point1.x + point2.x)/2, point1.y);
		}
	}
	
	public void drawCircle(Graphics g, int x, int y, int size)
	{
		g.drawOval(x - size/2, y - size/2, size, size);
	}
	
	public void fillCircle(Graphics g, int x, int y, int size)
	{
		g.fillOval(x - size/2, y - size/2, size, size);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Point point;
		
		g.setColor(Color.GRAY);
		g.setFont(font);
		
		drawGrid(g);
		
		g.setColor(Color.BLUE);
		
		for(Particle particle: process.getSwarm())
		{
			point = formattedPoint(particle.getLocation().getLoc()[0], particle.getLocation().getLoc()[1]);
			fillCircle(g, point.x, point.y, 4);
		}
		
		g.setColor(Color.RED);
		
		point = formattedPoint(process.getgBestLocation().getLoc()[0], process.getgBestLocation().getLoc()[1]);
		drawCircle(g, point.x, point.y, 8);

	}
}
