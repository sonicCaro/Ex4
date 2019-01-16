package GUI;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import GIS.Fruit;
import GIS.Map;
import Geom.Point3D;

public class PacmanCharacter extends JPanel implements Characters {
	
	private static final long serialVersionUID = 1L;
	private Map map;
	public List<FruitCharacter> fp;
	private ImageIcon iip=new ImageIcon("GUI.img/pacman.jpg");
	private Timer timer;
	
	private Point3D ip;
	private int currX;
	private int currY;
		
	private double mb[];
	
	
	
	public PacmanCharacter(ArrayList<Fruit> f, Map m){
		map=m;
		ip=map.CoordsToPixels(f.get(0).getData().get_Orientation());
		currX=ip.ix();
		currY=ip.iy();
		f.remove(0);
		fp=new ArrayList<FruitCharacter>();
		Fruit fruit;
		Iterator<Fruit> it=f.iterator();
		while (it.hasNext()){
			fruit=it.next();
			fp.add(new FruitCharacter(m.CoordsToPixels(fruit.getData().get_Orientation())));
		}
		if(!fp.isEmpty()){
			mb=StraightLineFormula(ip.ix(),ip.iy(), fp.get(0).getx(),fp.get(0).gety());
		}
		
	}
	
	
	
	public void animate(){
		timer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!fp.isEmpty()){
					if(fp.get(0).getx()==currX && fp.get(0).gety()==currY){
						ip=new Point3D(fp.get(0).getx(),fp.get(0).gety(),0);
						fp.remove(0);
						
						if(fp.isEmpty()){
							timer.stop();
						}
						else{
							mb=StraightLineFormula(ip.ix(),ip.iy(), fp.get(0).getx(),fp.get(0).gety());
						}
					}
					
					currX+=mb[2];
					currY=(int)(mb[0]*currX+mb[1]);
					repaint();
				}
			}
	       });
	        timer.start();
	}
	
	
	public double[] StraightLineFormula(int x1, int y1, int x2, int y2){
		//returns m and b from y=m*x+b and whether x needs to go up or down
		double [] r=new double[3];
		if (x2-x1 ==0){
			  r[0]=1;
			  if(y2>y1){
				  r[1]=1;
			  }
			  else{
				  r[1]=-1;
			  }
			  r[2]=0;
			  return r;
		}
		r[0]=(y2-y1)/(x2-x1); //=m
		r[1]=y2-(r[0]*x2); //=b
		if(x2>x1){
			r[2]=1;
		}
		else{
			r[2]=-1;
		}
		return r;
	}

	
	public void paint(Graphics2D g2d) {
		super.paintComponent(g2d);
		g2d.drawImage(iip.getImage(), currX, currY, 30, 30, null);
		
        for (FruitCharacter p : fp) {
            Graphics2D g = (Graphics2D) g2d.create();
            p.paint(g);
            g.dispose();
        }

		
	}

}
