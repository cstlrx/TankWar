package io.lrx.first;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5ÏÂÎç8:15:51
 */
//
public class Missile {
	private int curX;
	private int curY;
	private final Direction dir;

	private static final int SPEED = 10;
	private static final int R = 50;// ×Óµ¯°ë¾¶

	public static int getR() {
		return R;
	}

	private final String missilePath = System.getProperty("user.dir")
			+ "\\img\\tankmissile.gif";
	private final Image image = new ImageIcon(missilePath).getImage();

	public Image getImage() {
		return image;
	}

	public int getCurX() {
		return curX;
	}

	public void setCurX(int curX) {
		this.curX = curX;
	}

	public int getCurY() {
		return curY;
	}

	public void setCurY(int curY) {
		this.curY = curY;
	}

	public Direction getDir() {
		return dir;
	}

	public void move() {
		switch (dir) {
		case U:
			curY -= SPEED;
			break;
		case R:
			curX += SPEED;
			break;
		case D:
			curY += SPEED;
			break;
		case L:
			curX -= SPEED;
			break;
		}
	}

	public Missile(int curX, int curY, Direction dir) {
		this.curX = curX;
		this.curY = curY;
		this.dir = dir;
	}

}
