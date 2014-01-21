import java.util.ArrayList;

public class Ship {
	
	double x;
	double y;
	double vx;
	double vy;
	double a = 720;
	double g = 2;
	double t = 3;		//thrust
	double w = 0;
	double td = 0.2;
	double psxx [] = new double [4];	//square points
	double psyy [] = new double [4];
	int psx [] = new int [4];
	int psy [] = new int [4];
	double pllxx [] = new double [2];	//left leg points
	double pllyy [] = new double [2];
	int pllx [] = new int [2];
	int plly [] = new int [2];
	double prlxx [] = new double [2];	//right leg points
	double prlyy [] = new double [2];
	int prlx [] = new int [2];
	int prly [] = new int [2];
	double plfxx [] = new double [2];	//left foot points
	double plfyy [] = new double [2];
	int plfx [] = new int [2];
	int plfy [] = new int [2];
	double prfxx [] = new double [2];	//right foot points
	double prfyy [] = new double [2];
	int prfx [] = new int [2];
	int prfy [] = new int [2];
	double xx;
	double yy;
	boolean underground = false;
	
	public Ship(double xxx, double yyy){
		x = xxx;
		y = yyy;
		refresh();
	}
	
	public Ship(){
		refresh();
	}
	
	public void fall(){
		vy += g * td;
		y += vy * td + 0.5 * g * td * td;
		x += vx;
	}
	
	public void thrust(ArrayList<Platform> p){
		underground = false;
		for(int i = 0; i < p.size(); i++){
			if(((x >= p.get(i).x + 20 && x <= p.get(i).x + 80) && (y > p.get(i).y - 44) && (y <= p.get(i).y + 34))){
				underground = true;
			}
		}
		vx += (xx / 10) * t;
		x += vx;
		vy -= t * td;
		y += vy * td + 0.5 * g * td * td;
	}
	
	public void wind(){
		vx += w;
	}
	
	boolean land = false;
	public boolean onLand(ArrayList<Platform> p){
		land = false;
		for(int i = 0; i < p.size(); i++){
			if(((x >= p.get(i).x + 20 && x <= p.get(i).x + 80) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34)) && (a >= 710 && a <= 730)){
				y = p.get(i).y - 44;
				vy = 0;
				vx = 0;
				a = 720;
				land = true;
			}
		}
		if(land){
			return true;
		}
		else{
			return false;
		}
	}
	
	boolean boom = false;
	public boolean crash(ArrayList<Platform> p){
		boom = false;
		for(int i = 0; i < p.size(); i++){
			if((((x >= p.get(i).x - 40 && x <= p.get(i).x + 140) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34)) && (a < 710 || a > 730))
				|| ((x >= p.get(i).x - 40 && x < p.get(i).x + 20) && (y >= p.get(i).y - 44) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34)) 
				|| ((x > p.get(i).x + 80 && x <= p.get(i).x + 140) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34))
				|| (((x >= p.get(i).x + 20 && x <= p.get(i).x + 80) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34)) && (vx < - 3 || vx > 3))
				|| (((x >= p.get(i).x + 20 && x <= p.get(i).x + 80) && (y >= p.get(i).y - 44) && (y <= p.get(i).y + 34)) && vy > 15)
				|| y > 700){
				vy = 0;
				vx = 0;
				boom = true;
			}
		}
		if(boom){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void refresh(){		
		if(a < 650){
			a = 650;
		}
		if(a > 790){
			a = 790;
		}
		
		yy = Math.sin(Math.toRadians(a - 90));
		xx = yy / Math.tan(Math.toRadians(a - 90));
		xx = xx / Math.sqrt(xx * xx + yy * yy);
		
		psyy[0] = Math.sin(Math.toRadians(a - 150)) * 40;
		psxx[0] = psyy[0] / Math.tan(Math.toRadians(a - 150));
		psx[0] = (int) (x +  psxx[0]);
		psy[0] = (int) (y + psyy[0]);
		
		psyy[1] = Math.sin(Math.toRadians(a - 30)) * 40;
		psxx[1] = psyy[1] / Math.tan(Math.toRadians(a - 30));
		psx[1] =(int) (x + psxx[1]);
		psy[1] = (int) (y + psyy[1]);
		
		psyy[2] = Math.sin(Math.toRadians(a + 30)) * 40;
		psxx[2] = psyy[2] / Math.tan(Math.toRadians(a + 30));
		psx[2] = (int) (x + psxx[2]);
		psy[2] = (int) (y + psyy[2]);
		
		psyy[3] = Math.sin(Math.toRadians(a + 150)) * 40;
		psxx[3] = psyy[3] / Math.tan(Math.toRadians(a + 150));
		psx[3] = (int) (x + psxx[3]);
		psy[3] = (int) (y + psyy[3]);
		
		pllyy[0] =  Math.sin(Math.toRadians(a + 120)) * 24;
		pllxx[0] = pllyy[0] / Math.tan(Math.toRadians(a + 120));
		pllx[0] = (int) (x + pllxx[0]);
		plly[0] = (int) (y + pllyy[0]);
		
		pllyy[1] =  Math.sin(Math.toRadians(a + 120)) * 50;
		pllxx[1] = pllyy[1] / Math.tan(Math.toRadians(a + 120));
		pllx[1] = (int) (x + pllxx[1]);
		plly[1] = (int) (y + pllyy[1]);
		
		prlyy[0] =  Math.sin(Math.toRadians(a + 60)) * 24;
		prlxx[0] = prlyy[0] / Math.tan(Math.toRadians(a + 60));
		prlx[0] = (int) (x + prlxx[0]);
		prly[0] = (int) (y + prlyy[0]);
		
		prlyy[1] =  Math.sin(Math.toRadians(a + 60)) * 50;
		prlxx[1] = prlyy[1] / Math.tan(Math.toRadians(a + 60));
		prlx[1] = (int) (x + prlxx[1]);
		prly[1] = (int) (y + prlyy[1]);
		
		plfyy[0] =  Math.sin(Math.toRadians(a + 130)) * 57;
		plfxx[0] = plfyy[0] / Math.tan(Math.toRadians(a + 130));
		plfx[0] = (int) (x + plfxx[0]);
		plfy[0] = (int) (y + plfyy[0]);
		
		plfyy[1] =  Math.sin(Math.toRadians(a + 110)) * 46;
		plfxx[1] = plfyy[1] / Math.tan(Math.toRadians(a + 110));
		plfx[1] = (int) (x + plfxx[1]);
		plfy[1] = (int) (y + plfyy[1]);
		
		prfyy[0] =  Math.sin(Math.toRadians(a + 50)) * 57;
		prfxx[0] = prfyy[0] / Math.tan(Math.toRadians(a + 50));
		prfx[0] = (int) (x + prfxx[0]);
		prfy[0] = (int) (y + prfyy[0]);
		
		prfyy[1] =  Math.sin(Math.toRadians(a + 70)) * 46;
		prfxx[1] = prfyy[1] / Math.tan(Math.toRadians(a + 70));
		prfx[1] = (int) (x + prfxx[1]);
		prfy[1] = (int) (y + prfyy[1]);
	}
}
