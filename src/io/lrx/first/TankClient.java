package io.lrx.first;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-3����8:25:55
 */
// JFrame Ĭ����һ��contentPane��壬��ɫĬ�ϰ�ɫ
public class TankClient extends Frame {
	MainTank tank = new MainTank(this);// �½�̹�˶���
	Wall wall = new Wall();
	// Missile m = null;

	// Image tankCurImage = tank.getTankUImage();// ���浱ǰ̹��ͼ��
	// ����ͼƬ����Ҫ�������ݻ���ͼƬ�ϣ�һ������ʾ����
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

	public void drawWall(Graphics g) {
		for (int i = 0; i < WINDOW_WIDTH; i += 40) {
			for (int j = 0; j < WINDOW_HEIGHT; j += 100)
				g.drawImage(wall.getSteelWall(), i, j, Wall.getWallWidth(),
						Wall.getWallHeight(), null);
		}
	}

	// ��дpaint����
	@Override
	public void paint(Graphics g) {

		tank.draw(g);
		// ���������ӵ�
		if (missileList.size() > 0) {
			// û�в����쳣����foreach�в����쳣
			for (int i = 0; i < missileList.size(); i++) {
				missileList.get(i).draw(g);
				missileList.get(i).move();
			}
		}
		// �����ӵ�����
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawString("Missile count:" + missileList.size(), 10, 50);
		g.setColor(c);
		// drawWall(g);
	}

	// ˫���壬�����˸,ԭ��
	@Override
	public void update(Graphics g) {
		if (virtualImage == null) {
			virtualImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);// һ�δ�������
		}
		Graphics virtualG = virtualImage.getGraphics();// ���ͼƬ�Ļ���
		Color c = virtualG.getColor();

		virtualG.setColor(this.getBackground());
		virtualG.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		virtualG.setColor(c);
		paint(virtualG);

		g.drawImage(virtualImage, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
	}

	public void getFrame() {

		// **********************************************
		// ���ô���ͼ�꼰����
		// �õ�·��
		String iconPath = System.getProperty("user.dir")
				+ "\\img\\tank\\tank.jpg";
		ImageIcon image = new ImageIcon(iconPath);
		// "E:\\JavaWebStudy\\WebProject\\TankWar1.0\\img\\tank\\tank.jpg"
		this.setIconImage(image.getImage());// ����ͼ��
		this.setTitle("TankWar");
		// **********************************************
		// ����ǽ

		// ----------------------------------------------
		// ��Ч
		// JPanel p = new JPanel();
		// p.setBackground(Color.GREEN);
		// this.add(p);
		// this.setContentPane(p);

		// this.getContentPane().setBackground(Color.BLACK);//
		// setVisible(false);
		// ----------------------------------------------
		this.setBackground(Color.black);
		this.setBounds(100, 100, WINDOW_WIDTH, WINDOW_HEIGHT);// ���ô�С
		this.setResizable(false);// ���ɸı��С
		this.setVisible(true);

		// �ع� �� ��������������̹������-��Ԫ����ײ
		// ����������Ϣ
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tank.keyPress(e);
			}
		});
		// ��Ӵ��ڹرռ�����
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});
		// new Thread(new TankMoveThread()).start();
		new Thread(new PaintThread()).start();
	}

	class PaintThread implements Runnable {
		// ÿ��40MS���ػ�����
		@Override
		public void run() {
			while (true) {
				repaint();
				// �ӳ�40ms�ػ����н���
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// ��̹���ƶ��߳�
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
