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
 * @time 2016-12-3����8:25:55
 */
// JFrame Ĭ����һ��contentPane��壬��ɫĬ�ϰ�ɫ
public class TankClient extends Frame {
	MainTank tank = new MainTank();// �½�̹�˶���

	Wall wall = new Wall();

	// Missile m = null;

	List<Missile> missileList = new ArrayList<Missile>();

	// Image tankCurImage = tank.getTankUImage();// ���浱ǰ̹��ͼ��
	// ����ͼƬ����Ҫ�������ݻ���ͼƬ�ϣ�һ������ʾ����
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

	// ��дpaint����
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

		// ��Ӵ��ڹرռ�����
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});
		// ����������Ϣ
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case (KeyEvent.VK_UP):// ��ͷ�ϰ���
					tank.setDir(Direction.U);
					tank.move();

					break;

				case (KeyEvent.VK_RIGHT):// ��ͷ�Ұ���
					tank.setDir(Direction.R);
					tank.move();

					break;
				case (KeyEvent.VK_DOWN):// ��ͷ�°���
					tank.setDir(Direction.D);
					tank.move();

					break;
				case (KeyEvent.VK_LEFT):// ��ͷ����
					tank.setDir(Direction.L);
					tank.move();

					break;
				case (KeyEvent.VK_ENTER):// ���ӵ�
					// ���Լ�����
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
