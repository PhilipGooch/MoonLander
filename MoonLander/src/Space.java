import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Space extends JPanel implements ActionListener{
	
	public static final int width = 1356;
	public static final int height = 762;
	
	public static void main(String a []){
		
		JFrame j = new JFrame();
		Space s = new Space();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(width, height);
		j.add(s);							
		j.setLocationRelativeTo(null);
		j.setResizable(false);
		j.setVisible(true);
	}
	
	Timer t;
	Ship s;
	Platform p;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	boolean left = false;
	boolean right = false;
	boolean thrust = false;
	boolean gameover = true;
	int x;
	int y;
	Font f;
	int cx = 1200;
	int cy = 600;
	
	public Space(){
		s = new Ship();
		t = new Timer(15, this);
		t.start();
		addKeyListener(new KL());
		addMouseListener(new ML());
		addMouseMotionListener(new ML());
		setFocusable(true);
		f = new Font("Sherif", Font.PLAIN, 20);
	}
	
	private class ML implements MouseListener, MouseMotionListener{
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){
			x = e.getX();
			y = e.getY();
			if(e.getButton() == MouseEvent.BUTTON1){
				if(x >= cx - 80 && x <= cx + 150 && y >= cy - 30 && y <= cy + 100){
					if(Math.sqrt((x - cx) * (x - cx) + (y - (cy + 10)) * (y - (cy + 10))) < 10){
						if(s.g > 0.1){
							s.g -= 0.5;
						}
					}
					if(Math.sqrt((x - (cx + 75)) * (x - (cx + 75)) + (y - (cy + 10)) * (y - (cy + 10))) < 10){
						if(s.g < 5){
							s.g += 0.5;
						}
					}
					if(Math.sqrt((x - cx) * (x - cx) + (y - (cy + 40)) * (y - (cy + 40))) < 10){
						if(s.t > 0.1){
							s.t -= 0.5;
						}
					}
					if(Math.sqrt((x - (cx + 75)) * (x - (cx + 75)) + (y - (cy + 40)) * (y - (cy + 40))) < 10){
						if(s.t < 9){
							s.t += 0.5;
						}
					}
					if(Math.sqrt((x - cx) * (x - cx) + (y - (cy + 70)) * (y - (cy + 70))) < 10){
						if(s.w > - 0.05){
							s.w -= 0.01;
						}
					}
					if(Math.sqrt((x - (cx + 75)) * (x - (cx + 75)) + (y - (cy + 70)) * (y - (cy + 70))) < 10){
						if(s.w < 0.05){
							s.w += 0.01;
						}
					}
				}
				else{
					p = new Platform(x - 50, y);
					platforms.add(p);
				}
			}
			if(e.getButton() == MouseEvent.BUTTON3){
				detect();
			}
		}
		public void mouseDragged(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
		public void mouseMoved(MouseEvent e){}
	}
	
	public void detect(){
		for(int i = 0; i < platforms.size(); i++){
			if(Math.sqrt((x - platforms.get(i).x) * (x - platforms.get(i).x) + (y - platforms.get(i).y) * (y - platforms.get(i).y)) < 100){
				platforms.remove(i);
			}
		}
	}
	
	private class KL extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_A){
				left = true;
			}
			if(key == KeyEvent.VK_D){
				right = true;
			}
			if(key == KeyEvent.VK_W){
				s.underground = false;
				thrust = true;
			}
		}
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_A){
				left = false;
			}
			if(key == KeyEvent.VK_D){
				right = false;
			}
			if(key == KeyEvent.VK_W){
				thrust = false;
			}
			if(key == KeyEvent.VK_SPACE && gameover && platforms.size() > 0){
				s = new Ship(platforms.get(0).x + 50, platforms.get(0).y - 44);
				gameover = false;
			}
		}
		public void keyTyped(KeyEvent e){}
	}
	
	public void actionPerformed(ActionEvent e){
		if(left){
			if(s.a >= 650 && s.a <= 790){
				s.a -= 1.75;
			}
		}
		if(right){
			if(s.a >= 650 && s.a <= 790){
				s.a += 1.75;
			}
		}
		if(thrust && !s.underground){
			s.thrust(platforms);
		}
		else if(!s.onLand(platforms) && !s.crash(platforms)){
			s.fall();
		}
		if(s.crash(platforms)){
			gameover = true;
		}
		if(!s.onLand(platforms) && !s.crash(platforms)){
			s.wind();
		}
		s.refresh();
		repaint();
	}
	
	int gltx = cx;
	int glty = cy;
	int grtx = gltx + 75;
	int grty = glty;
	int gltxp [] = {gltx - 10, gltx + 10, gltx + 10};
	int gltyp [] = {glty + 10, glty, glty + 20};
	int grtxp [] = {grtx - 10, grtx + 10, grtx - 10};
	int grtyp [] = {grty, grty + 10, grty + 20};
	
	int tltx = cx;
	int tlty = cy + 30;
	int trtx = tltx + 75;
	int trty = tlty;
	int tltxp [] = {tltx - 10, tltx + 10, tltx + 10};
	int tltyp [] = {tlty + 10, tlty, tlty + 20};
	int trtxp [] = {trtx - 10, trtx + 10, trtx - 10};
	int trtyp [] = {trty, trty + 10, trty + 20};
	
	int wltx = cx;
	int wlty = cy + 60;
	int wrtx = wltx + 75;
	int wrty = wlty;
	int wltxp [] = {wltx - 10, wltx + 10, wltx + 10};
	int wltyp [] = {wlty + 10, wlty, wlty + 20};
	int wrtxp [] = {wrtx - 10, wrtx + 10, wrtx - 10};
	int wrtyp [] = {wrty, wrty + 10, wrty + 20};
	
	public void paint(Graphics g){
		super.paint(g);
		setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.setFont(f);
		if(!gameover){
			g.drawPolygon(s.psx,s.psy,4);
			g.drawPolygon(s.pllx,s.plly,2);
			g.drawPolygon(s.prlx,s.prly,2);
			g.drawPolygon(s.plfx,s.plfy,2);
			g.drawPolygon(s.prfx,s.prfy,2);
		}
		for(int i = 0; i < platforms.size(); i++){
			g.fillRect((int) platforms.get(i).x, (int) platforms.get(i).y, 100, 5);
		}
	
		g.drawPolygon(gltxp, gltyp, 3);
		g.drawString("" + (float) s.g, gltx + 25, glty + 18);
		g.drawPolygon(grtxp, grtyp, 3);
		
		g.drawPolygon(tltxp, tltyp, 3);
		g.drawString("" + (float) s.t, tltx + 25, tlty + 18);
		g.drawPolygon(trtxp, trtyp, 3);
		
		g.drawPolygon(wltxp, wltyp, 3);
	
		if(s.w < 0){
			g.drawString("" + (float) (s.w * 10), wltx + 18, wlty + 18);
		}
		else if(s.w > - 0.01 && s.w < 0.01){
			g.drawString("0.0", wltx + 25, wlty + 18);
		}
		else{
			g.drawString("" + (float) (s.w * 10), wltx + 25, wlty + 18);
		}
		g.drawPolygon(wrtxp, wrtyp, 3);
	}
}
