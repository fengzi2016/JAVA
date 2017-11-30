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
 * @Description:游戏界面
 * @author guan
 * @date 2017/11/30
 *
 */
public class PlaneFrame extends JFrame {
	//获取到资源路径
	public static String path=System.getProperty("user.dir")+"\\Rescouce\\resource";
	//准备一个容器将这些图片保存起来
	public static HashMap<String,BufferedImage> maps= new HashMap<>();
	//静态语句块，最先加载的东西 大
	static {
		File[] file= new File[path].listFiles();
		for(int i=0 ;i<file.length;i++) {
			
			//前面存名称，后面存图片
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
	  //将小面板添加到大面板
	  jp=new PlanePanle();
	  this.setContentPane(jp);
	  //点击插号退出程序
	  this.setDefaultCloseOperation(3);
	  //添加适配器
	  this.addKeyListener(new MykeyListerner());
	  setVisible(true);
	  
    }
	//添加一个适配器
	class MykeyListerner extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			jp.KeyPressed(e);
		}
		
	}
	class PlanePanle extends JPanel{
	// 初始化背景坐标
	Point bgPoint =new Point(0,-830);
	// 初始化飞机坐标
		Point planePoint =new Point(280,500);
	public PlanePanle() {
		//启动线程
		new Thread(new BgThread()).start();
	}
	// 存储子弹坐标
	List<Point> list = new ArrayList<>();
	public void paint(Graphics g) {
		//调用父类的方法，如果不掉用会产生重叠
		super.paint(g);
		//创建一画板
		BufferedImage image = new BufferedImage(640,700,BufferedImage.TYPE_INT_RGB);
		//创建一个画笔
		Graphics gs = image.createGraphics();
		// 画背景
		gs.drawImage(PlaneFrame.maps.get("background1.bmp"), bgPoint.x, bgPoint.y, this);
		gs.drawImage(PlaneFrame.maps.get("background1.bmp"), bgPoint.x, bgPoint.y-1530, this);
		gs.drawImage(PlaneFrame.maps.get("plane.png"), planePoint.x,planePoint.y, this);
		gs.drawImage(PlaneFrame.maps.get("m3.png"), 100,100, this);
		//将画板添加在jp里
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
				// 线程休眠
				try {
					Thread.sleep(30);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				//更新
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
	
	
	//键盘按下去
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

