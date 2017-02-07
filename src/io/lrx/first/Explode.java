package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2017-2-5ÏÂÎç4:52:09
 */
// ±¬Õ¨Àà
public class Explode {

	private final static Image blast1 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast1.gif")
			.getImage();
	private final static Image blast2 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast2.gif")
			.getImage();
	private final static Image blast3 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast3.gif")
			.getImage();
	private final static Image blast4 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast4.gif")
			.getImage();
	private final static Image blast5 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast5.gif")
			.getImage();
	private final static Image blast6 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast6.gif")
			.getImage();
	private final static Image blast7 = new ImageIcon(
			System.getProperty("user.dir") + "\\img\\blast\\blast7.gif")
			.getImage();

	private int x = 10;
	private int y = 10;
	private final TankClient tc;

	Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	int step = 0;
	Image img;
	private boolean live = true;

	public void draw(Graphics g) {

		if (!live)
			return;

		switch (step) {

		case (0):
			img = blast1;
			break;
		case (1):
			img = blast2;
			break;
		case (2):
			img = blast3;
			break;
		case (3):
			img = blast4;
			break;
		case (4):
			img = blast5;
			break;
		case (6):
			img = blast6;
			break;
		case (7):
			img = blast6;
			break;
		case (8):
			step = 0;
			live = false;
			return;
		}
		g.drawImage(img, x, y, 40, 40, null);
		step++;
	}
}
