package io.lrx.first;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-4上午10:32:30
 */
// 所有坦克的 父类
public class Tank {
	private String name;// 名字

	private String tankUPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankU.gif";// 向上坦克图片所在文件路径,路径写错，哼
	private String tankRPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankR.gif";// 向右坦克图片所在文件路径
	private String tankDPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankD.gif";// 向下坦克图片所在文件路径
	private String tankLPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankL.gif";// 向左坦克图片所在文件路径

	private final Image tankUImage = new ImageIcon(tankUPath).getImage();
	private final Image tankRImage = new ImageIcon(tankRPath).getImage();
	private final Image tankDImage = new ImageIcon(tankDPath).getImage();
	private final Image tankLImage = new ImageIcon(tankLPath).getImage();
	private Image tankCurImage = getTankUImage();

	private int initXPos;// 坦克初始坐标
	private int initYPos;
	private int curXPos; // 坦克当前坐标
	private int curYPos;

	private final int minX = 0;
	private final int minY = 0;
	private int maxX;
	private int maxY;

	private static final int TANK_WIDTH = 40;
	private static final int TANK_HEIGHT = 40;

	private static final int SPEED = 5;
	private Direction dir = Direction.U;

	public Tank() {
	}

	public int getCurXPos() {
		return curXPos;
	}

	public void setCurXPos(int curXPos) {
		this.curXPos = curXPos;
	}

	public int getCurYPos() {
		return curYPos;
	}

	public void setCurYPos(int curYPos) {
		this.curYPos = curYPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// ------------------------------------------------------------
	public Image getTankUImage() {
		return tankUImage;
	}

	public Image getTankRImage() {
		return tankRImage;
	}

	public Image getTankDImage() {
		return tankDImage;
	}

	public Image getTankLImage() {
		return tankLImage;
	}

	// ------------------------------------------------------------
	public String getTankUPath() {
		return tankUPath;
	}

	public void setTankUPath(String tankUPath) {
		this.tankUPath = tankUPath;
	}

	public String getTankRPath() {
		return tankRPath;
	}

	public void setTankRPath(String tankRPath) {
		this.tankRPath = tankRPath;
	}

	public String getTankDPath() {
		return tankDPath;
	}

	public void setTankDPath(String tankDPath) {
		this.tankDPath = tankDPath;
	}

	public String getTankLPath() {
		return tankLPath;
	}

	public void setTankLPath(String tankLPath) {
		this.tankLPath = tankLPath;
	}

	public static int getTankWidth() {
		return TANK_WIDTH;
	}

	public static int getTankHeight() {
		return TANK_HEIGHT;
	}

	// ------------------------------------------------------------

	public void move() {
		switch (this.dir) {
		case U:
			tankCurImage = tankUImage;
			tankMoveUp();
			break;
		case R:
			tankMoveRight();
			tankCurImage = tankRImage;
			break;
		case D:
			tankCurImage = tankDImage;
			tankMoveDown();
			break;
		case L:
			tankCurImage = tankLImage;
			tankMoveLeft();
			break;
		}
	}

	public Image getTankCurImage() {
		return tankCurImage;
	}

	public void setTankCurImage(Image tankCurImage) {
		this.tankCurImage = tankCurImage;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	// 坦克移动
	public void tankMoveUp() {
		curYPos -= SPEED;
	}

	public void tankMoveRight() {
		curXPos += SPEED;
	}

	public void tankMoveDown() {
		curYPos += SPEED;
	}

	public void tankMoveLeft() {
		curXPos -= SPEED;
	}

	public Missile fire() {

		int missileX = this.curXPos;
		int missileY = this.curYPos;

		// 算出子弹左上角坐标
		switch (this.dir) {
		case U:
			missileX = this.curXPos + TANK_WIDTH / 2 - Missile.getR() / 2;
			missileY = this.curYPos - Missile.getR();
			break;
		case R:
			missileX = this.curXPos + TANK_WIDTH;
			missileY = this.curYPos + TANK_HEIGHT / 2 - Missile.getR() / 2;
			break;
		case D:
			missileX = this.curXPos + TANK_WIDTH / 2 - Missile.getR() / 2;
			missileY = this.curYPos + TANK_HEIGHT;
			break;
		case L:
			missileX = this.curXPos - Missile.getR();
			missileY = this.curYPos + TANK_HEIGHT / 2 - Missile.getR() / 2;
			break;
		}
		// new 子弹，传入坦克当前位置及方向
		Missile m = new Missile(missileX, missileY, this.getDir());
		return m;
	}
}

class MainTank extends Tank {

	MainTank() {

	}
}
