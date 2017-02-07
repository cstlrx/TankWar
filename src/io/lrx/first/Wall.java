package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5ÏÂÎç1:47:23
 */
// Ç½
public class Wall {
	private final int curX;
	private final int curY;

	private final String brickWallPath = System.getProperty("user.dir")
			+ "\\img\\wall\\brickwall.gif";
	private final String steelWallPath = System.getProperty("user.dir")
			+ "\\img\\wall\\steelwall.gif";

	private Image brickWall;
	private Image steelWall;

	private static final int WALL_WIDTH = 40;
	private static final int WALL_HEIGHT = 40;

	private final TankClient tc;
	private boolean live = true;
	private final Image img;
	private final boolean isSteel;

	public Wall(int curX, int curY, TankClient tc, boolean isSteel) {
		super();
		this.curX = curX;
		this.curY = curY;
		this.tc = tc;
		this.isSteel = isSteel;
		img = isSteel ? getSteelWall() : getBrickWall();// Ñ¡Ôñ×©Ç½»¹ÊÇÌúÇ½
	}

	public boolean isSteel() {
		return isSteel;
	}

	public int getCurX() {
		return curX;
	}

	public int getCurY() {
		return curY;
	}

	public void draw(Graphics g) {
		if (live) {
			g.drawImage(img, curX, curY, WALL_WIDTH, WALL_HEIGHT, null);
		}
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public static int getWallWidth() {
		return WALL_WIDTH;
	}

	public static int getWallHeight() {
		return WALL_HEIGHT;
	}

	public Image getBrickWall() {
		Image brickWall = new ImageIcon(brickWallPath).getImage();
		return brickWall;
	}

	public void setBrickWall(Image brickWall) {
		this.brickWall = brickWall;
	}

	public Image getSteelWall() {
		Image steelWall = new ImageIcon(steelWallPath).getImage();
		return steelWall;
	}

	public void setSteelWall(Image steelWall) {
		this.steelWall = steelWall;
	}

	public Rectangle getRec() {
		return new Rectangle(curX, curY, WALL_WIDTH, WALL_HEIGHT);
	}

	public Line2D getLine(Direction d) {
		switch (d) {
		case U:
			return new Line2D.Double(curX, curY, curX + WALL_WIDTH, curY);
		case D:
			return new Line2D.Double(curX, curY + WALL_HEIGHT, curX
					+ WALL_WIDTH, curY + WALL_HEIGHT);
		case L:
			return new Line2D.Double(curX, curY, curX, curY + WALL_HEIGHT);
		case R:
			return new Line2D.Double(curX + WALL_HEIGHT, curY, curX
					+ WALL_WIDTH, curY + WALL_HEIGHT);
		}
		return null;
	}
}
