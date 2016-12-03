package io.lrx.first;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author lrx
 * @time 2016-12-3����8:25:55
 */
public class TankClient extends JFrame {

	int x = 100;
	int y = 500;

	// �õ�·��
	String tankUPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankU.gif";
	String tankDPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankD.gif";
	String tankLPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankL.gif";
	String tankRPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankR.gif";

	Image tankU = new ImageIcon(tankUPath).getImage();

	// ��дpaint����
	@Override
	public void paint(Graphics g) {
		// /System.out.println("paint");
		g.drawImage(tankU, x, y, 40, 40, getBackground(), null);

		y += 10;
	}

	public void getFrame() {

		// ��Ӵ��ڹرռ�����
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}

		});

		// �õ�·��
		String iconPath = System.getProperty("user.dir")
				+ "\\img\\tank\\tank.jpg";
		ImageIcon image = new ImageIcon(iconPath);
		// "E:\\JavaWebStudy\\WebProject\\TankWar1.0\\img\\tank\\tank.jpg"
		this.setIconImage(image.getImage());// ����ͼ��
		this.setTitle("TankWar");
		this.setBounds(100, 100, 800, 600);

		this.setBackground(Color.black);

		this.setResizable(false);// ���ɸı��С

		this.setVisible(true);

		new Thread(new TankMoveThread()).start();
	}

	// �ػ��߳�
	class TankMoveThread implements Runnable {
		@Override
		public void run() {
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		TankClient t = new TankClient();

		t.getFrame();
	}
}
