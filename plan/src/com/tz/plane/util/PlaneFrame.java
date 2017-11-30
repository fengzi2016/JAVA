package com.tz.plane.util;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import  javax.swing.JPanel;
/**
 * @ClassName:PlaneFrame
 * @Description:��Ϸ����
 * @author guan
 * @date 2017/11/30
 *
 */
public class PlaneFrame extends JFrame {
	//��ȡ����Դ·��
	public static String path=System.getProperty("user.dir")+"\\Rescouce\\resource";
	//׼��һ����������ЩͼƬ��������
	public static HashMap<String,BufferedImage> maps= new HashMap<>();
	//��̬���飬���ȼ��صĶ��� ��
	static {
		File[] file= new File[path].listFiles();
		for(int i=0 ;i<file.length;i++) {
			
			//ǰ������ƣ������ͼƬ
			try {maps.put(file[i].getName(),ImageIO.read(file[i]));
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private static final long serialVersionUID = 1L;
	PlanePanle jp;
	public PlaneFrame() {
	  this.setSize(640,700);
	  this.setTitle("xxx");
	  this.setLocationRelativeTo(null);
	  this.setResizable(false);
	  //��С�����ӵ������
	  jp=new PlanePanle();
	  this.setContentPane(jp);
	  //�������˳�����
	  this.setDefaultCloseOperation(3);
	  //���������
	  this.addKeyListener(new MykeyListerner());
	  setVisible(true);
	  
    }
	//���һ��������
	class MykeyListerner extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			jp.KeyPressed(e);
		}
		
	}
	class PlanePanle extends JPanel{
	// ��ʼ����������
	Point bgPoint =new Point(0,-830);
	// ��ʼ���ɻ�����
		Point planePoint =new Point(280,500);
	public PlanePanle() {
		//�����߳�
		new Thread(new BgThread()).start();
	}
	// �洢�ӵ�����
	List<Point> list = new ArrayList<>();
	public void paint(Graphics g) {
		//���ø���ķ�������������û�����ص�
		super.paint(g);
		//����һ����
		BufferedImage image = new BufferedImage(640,700,BufferedImage.TYPE_INT_RGB);
		//����һ������
		Graphics gs = image.createGraphics();
		// ������
		gs.drawImage(PlaneFrame.maps.get("background1.bmp"), bgPoint.x, bgPoint.y, this);
		gs.drawImage(PlaneFrame.maps.get("background1.bmp"), bgPoint.x, bgPoint.y-1530, this);
		gs.drawImage(PlaneFrame.maps.get("plane.png"), planePoint.x,planePoint.y, this);
		gs.drawImage(PlaneFrame.maps.get("m3.png"), 100,100, this);
		//�����������jp��
		for(int i=0;i<list.size();i++) {
			gs.drawImage(PlaneFrame.maps.get("m3.png"), list.get(i).x,list.get(i).y,this);
		}
		gs.drawImage(image,0,0,this);

		}
	class BgThread implements Runnable{
		public void run() {
			while(true) {
				if(bgPoint.y==700) {
					bgPoint.y=-830;
				}
				bgPoint.y+=1;
				// �߳�����
				try {
					Thread.sleep(30);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				//����
				repaint();
			}
			public void fire() {
				if(buff==0) {
					list.add(new Point(planePoint.x+30,planePoint.y-80));
					buff = 1;
				}else {
					
				} 
			}
			
		}
	
	}
	
	
	//���̰���ȥ
	public void KeyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE) {
		  fire();
		}
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			planePoint.y-=10;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			planePoint.y+=10;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			planePoint.x-=10;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			planePoint.x+=10;
		}
	}
	
}

public  static void main(String[] args) {
	new PlaneFrame();
}
}

