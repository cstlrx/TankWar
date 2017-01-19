package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5下午8:15:51
 */
//
public class Missile {
	private int curX;
	private int curY;
	private final Direction dir;

	private static final int SPEED = 10;
	private static final int R = 30;// 子弹半径

	TankClient tc;
	private boolean live = true;

	public boolean isLive() {
		return live;
	}

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
		// 子弹越界则去除子弹
		if (curX < 0 || curY < 0 || curX > tc.getWindowWidth()
				|| curY > tc.getWindowHeight()) {
			live = false;
			MainTank.getMissileList().remove(this);
		}
	}

	public Missile(int curX, int curY, Direction dir) {
		this.curX = curX;
		this.curY = curY;
		this.dir = dir;
	}

	public Missile(int curX, int curY, Direction dir, TankClient tc) {
		this(curX, curY, dir);
		this.tc = tc;
	}

	public void draw(Graphics g) {
		g.drawImage(getImage(), getCurX(), getCurY(), getR(), getR(), null);
	}

}
