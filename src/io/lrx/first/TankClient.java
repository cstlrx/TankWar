package io.lrx.first;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-3下午8:25:55
 */
// JFrame 默认有一个contentPane面板，颜色默认白色
public class TankClient extends Frame {
	MainTank tank = new MainTank();// 新建坦克对象

	Wall wall = new Wall();

	// Missile m = null;

	List<Missile> missileList = new ArrayList<Missile>();

	// Image tankCurImage = tank.getTankUImage();// 保存当前坦克图像
	// 虚拟图片，将要画的内容画到图片上，一次性显示出来
	Image virtualImage = null;
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	public void drawWall(Graphics g) {
		for (int i = 0; i < WINDOW_WIDTH; i += 40) {
			for (int j = 0; j < WINDOW_HEIGHT; j += 100)
				g.drawImage(wall.getSteelWall(), i, j, Wall.getWallWidth(),
						Wall.getWallHeight(), null);
		}
	}

	// 重写paint方法
	@Override
	public void paint(Graphics g) {

		g.drawImage(tank.getTankCurImage(), tank.getCurXPos(),
				tank.getCurYPos(), Tank.getTankWidth(), Tank.getTankHeight(),
				getBackground(), null);
		if (missileList.size() > 0) {
			for (Missile m : missileList) {
				g.drawImage(m.getImage(), m.getCurX(), m.getCurY(), m.getR(),
						m.getR(), null);
				m.move();
			}
		}
		// if (m != null) {
		// g.drawImage(m.getImage(), m.getCurX(), m.getCurY(), m.getR(),
		// m.getR(), null);
		// m.move();
		// }
		// drawWall(g);
	}

	// 双缓冲，解决闪烁,原理
	@Override
	public void update(Graphics g) {
		if (virtualImage == null) {
			virtualImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);// 一次创建即可
		}
		Graphics virtualG = virtualImage.getGraphics();// 获得图片的画笔
		Color c = virtualG.getColor();
		virtualG.setColor(this.getBackground());
		virtualG.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		virtualG.setColor(c);
		paint(virtualG);

		g.drawImage(virtualImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);

	}

	public void getFrame() {

		// **********************************************
		// 设置窗口图标及标题
		// 得到路径
		String iconPath = System.getProperty("user.dir")
				+ "\\img\\tank\\tank.jpg";
		ImageIcon image = new ImageIcon(iconPath);
		// "E:\\JavaWebStudy\\WebProject\\TankWar1.0\\img\\tank\\tank.jpg"
		this.setIconImage(image.getImage());// 设置图标
		this.setTitle("TankWar");
		// **********************************************

		// 设置墙

		// ----------------------------------------------
		// 无效
		// JPanel p = new JPanel();
		// p.setBackground(Color.GREEN);
		// this.add(p);
		// this.setContentPane(p);

		// this.getContentPane().setBackground(Color.BLACK);//
		// setVisible(false);
		// ----------------------------------------------
		this.setBackground(Color.black);
		this.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);// 设置大小
		this.setResizable(false);// 不可改变大小
		this.setVisible(true);

		// 添加窗口关闭监听器
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});
		// 监听按键消息
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case (KeyEvent.VK_UP):// 箭头上按下
					tank.setDir(Direction.U);
					tank.move();

					break;

				case (KeyEvent.VK_RIGHT):// 箭头右按下
					tank.setDir(Direction.R);
					tank.move();

					break;
				case (KeyEvent.VK_DOWN):// 箭头下按下
					tank.setDir(Direction.D);
					tank.move();

					break;
				case (KeyEvent.VK_LEFT):// 箭头左按下
					tank.setDir(Direction.L);
					tank.move();

					break;
				case (KeyEvent.VK_ENTER):// 发子弹
					// 可以加声音
					missileList.add(tank.fire());
					break;
				}
			}

		});

		// new Thread(new TankMoveThread()).start();
		new Thread(new PaintThread()).start();
	}

	class PaintThread implements Runnable {

		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 主坦克移动线程
	class TankMoveThread implements Runnable {
		@Override
		public void run() {

		}
	}

	public static void main(String[] args) {
		TankClient t = new TankClient();
		t.getFrame();
	}
}
