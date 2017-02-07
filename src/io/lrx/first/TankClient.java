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
	MainTank tank = new MainTank(300, 400, this);// 新建坦克对象
	// 存放存活坦克的列表
	public static List<Tank> liveTank = new ArrayList<Tank>();
	// public static List<Tank> liveEnemyTank = new ArrayList<Tank>();
	public static List<Missile> liveMissile = new ArrayList<Missile>();
	public static List<Explode> liveExplode = new ArrayList<Explode>();
	public static List<Wall> liveWall = new ArrayList<Wall>();

	// Image tankCurImage = tank.getTankUImage();// 保存当前坦克图像
	// 虚拟图片，将要画的内容画到图片上，一次性显示出来
	Image virtualImage = null;
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private final List<Missile> missileList = tank.getMissileList();

	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}

	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}

	public void drawGameOver(Graphics g) {
		Image img = new ImageIcon(System.getProperty("user.dir")
				+ "\\img\\over.gif").getImage();
		g.drawImage(img, this.getWindowWidth() / 2 - img.getWidth(null),
				this.getWindowHeight() / 2 - img.getHeight(null), 100, 100,
				null);
	}

	// 重写paint方法
	@Override
	public void paint(Graphics g) {

		tank.draw(g);
		if (!tank.isLive()) {
			drawGameOver(g);
			// System.exit(0);
		}
		// eTank.draw(g);
		for (int i = 0; i < liveExplode.size(); i++) {
			liveExplode.get(i).draw(g);
			// liveExplode.remove(i);
		}
		for (int i = 0; i < liveWall.size(); i++) {
			Wall w = liveWall.get(i);
			if (!w.isLive()) {
				liveWall.remove(i);
				continue;
			}
			w.draw(g);
			// liveExplode.remove(i);
		}
		for (int i = 1; i < liveTank.size(); i++) {
			if (!liveTank.get(i).isLive()) {
				liveTank.remove(i);
				continue;
			}
			liveTank.get(i).draw(g);
		}
		// m.draw(g);
		// m.move();
		// 画出所有子弹
		if (missileList.size() > 0) {
			// 没有并发异常，用foreach有并发异常
			for (int i = 0; i < missileList.size(); i++) {
				if (!missileList.get(i).isLive()) {// 子弹死亡
					missileList.remove(i);
					continue;
				}
				missileList.get(i).draw(g);
				missileList.get(i).move();
			}
		}
		for (int i = 0; i < liveMissile.size(); i++) {
			if (!liveMissile.get(i).isLive()) {// 子弹死亡
				liveMissile.remove(i);
				continue;
			}
			liveMissile.get(i).draw(g);
			liveMissile.get(i).move();
		}
		// 画出子弹个数
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawString("Missile count:" + missileList.size(), 10, 50);
		g.drawString("Tank now:" + liveTank.size(), 10, 70);
		g.setColor(c);
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

		// 重构 ： 将按键监听加入坦克类中-》元素碰撞
		// 监听按键消息
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tank.keyPress(e);
			}
		});
		// 添加窗口关闭监听器
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});
		liveTank.add(tank);// 下标0
		// liveTank.add(eTank);
		// new Thread(new TankMoveThread()).start();

		initEnemyTank();// 下标1-size()
		initWall();
		new Thread(new PaintThread()).start();
		new Thread(new EnemyThread()).start();
	}

	public void initEnemyTank() {
		for (int i = 0; i < 10; i++) {
			liveTank.add(new EnemyTank(i * 50, 30, this));
		}
	}

	public void initWall() {
		for (int i = 0; i < 30; i++) {
			liveWall.add(new Wall(i * 40, 30 + 41, this, false));
		}
		for (int i = 0; i < 6; i++) {
			liveWall.add(new Wall(i * 40 + 100, 300, this, true));
		}
	}

	// 既然是坦克的移动规律，自然要在坦克类中定义，面向对象
	class EnemyThread implements Runnable {

		@Override
		public void run() {
			while (liveTank.size() > 1) {
				for (int i = 1; i < liveTank.size(); i++) {
					Tank t = liveTank.get(i);
					// t.setDir(array[(int) (Math.random() * 4)]);
					// liveMissile.add(t.fire());
					t.move();

				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	class PaintThread implements Runnable {
		// 每过40MS，重画界面
		@Override
		public void run() {
			while (true) {
				repaint();

				// 延迟40ms重画所有界面
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
