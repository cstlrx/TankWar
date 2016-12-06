package io.lrx.first;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-5ÏÂÎç1:47:23
 */
// Ç½
public class Wall {
	private final String brickWallPath = System.getProperty("user.dir")
			+ "\\img\\wall\\brickwall.gif";
	private final String steelWallPath = System.getProperty("user.dir")
			+ "\\img\\wall\\steelwall.gif";

	private Image brickWall;
	private Image steelWall;

	private static final int WALL_WIDTH = 40;
	private static final int WALL_HEIGHT = 40;

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

}
