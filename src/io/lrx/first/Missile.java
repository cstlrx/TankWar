package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5下午8:15:51
 */
//
public class Missile {
	private boolean good;
	private int curX;
	private int curY;
	private final Direction dir;

	private static final int SPEED = 10;
	private static final int R = 30;// 子弹半径

	TankClient tc;
	// 子弹存活量
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

	public Rectangle getRec() {
		return new Rectangle(curX, curY, R, R);
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
			// MainTank.getMissileList().remove(this);
		}
		// 遍历所有存活坦克，若与子弹相交则死亡
		for (Tank t : tc.liveTank) {
			// 只有当子弹和坦克的好坏不同时才会发生碰撞行为
			if (t.isGood() ^ this.isGood())
				if (this.getRec().intersects(t.getRec())) {
					t.setLive(false);
					this.live = false;
					tc.liveExplode
							.add(new Explode(t.getCurX(), t.getCurY(), tc));
					return;// 一颗子弹只能打死一个坦克
				}
		}
		for (Wall w : tc.liveWall) {
			if (w.getRec().intersects(getRec())) {
				if (!w.isSteel()) {// 是钢铁墙不会损坏
					w.setLive(false);
				}
				this.live = false;
				tc.liveExplode.add(new Explode(w.getCurX(), w.getCurY(), tc));
				break;
			}
		}

	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Missile(int curX, int curY, Direction dir) {
		this.curX = curX;
		this.curY = curY;
		this.dir = dir;
	}

	public Missile(int curX, int curY, Direction dir, TankClient tc,
			boolean good) {
		this(curX, curY, dir);
		this.tc = tc;
		this.good = good;
	}

	public void draw(Graphics g) {
		g.drawImage(getImage(), getCurX(), getCurY(), getR(), getR(), null);
	}

}
