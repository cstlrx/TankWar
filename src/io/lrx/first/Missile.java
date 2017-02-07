package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5����8:15:51
 */
//
public class Missile {
	private boolean good;
	private int curX;
	private int curY;
	private final Direction dir;

	private static final int SPEED = 10;
	private static final int R = 30;// �ӵ��뾶

	TankClient tc;
	// �ӵ������
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
		// �ӵ�Խ����ȥ���ӵ�
		if (curX < 0 || curY < 0 || curX > tc.getWindowWidth()
				|| curY > tc.getWindowHeight()) {
			live = false;
			// MainTank.getMissileList().remove(this);
		}
		// �������д��̹�ˣ������ӵ��ཻ������
		for (Tank t : tc.liveTank) {
			// ֻ�е��ӵ���̹�˵ĺû���ͬʱ�Żᷢ����ײ��Ϊ
			if (t.isGood() ^ this.isGood())
				if (this.getRec().intersects(t.getRec())) {
					t.setLive(false);
					this.live = false;
					tc.liveExplode
							.add(new Explode(t.getCurX(), t.getCurY(), tc));
					return;// һ���ӵ�ֻ�ܴ���һ��̹��
				}
		}
		for (Wall w : tc.liveWall) {
			if (w.getRec().intersects(getRec())) {
				if (!w.isSteel()) {// �Ǹ���ǽ������
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
