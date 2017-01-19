package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-4����10:32:30
 */
// ����̹�˵� ����
public class Tank {
	private String name;// ����

	private String tankUPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankU.gif";// ����̹��ͼƬ�����ļ�·��,·��д����
	private String tankRPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankR.gif";// ����̹��ͼƬ�����ļ�·��
	private String tankDPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankD.gif";// ����̹��ͼƬ�����ļ�·��
	private String tankLPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankL.gif";// ����̹��ͼƬ�����ļ�·��

	private final Image tankUImage = new ImageIcon(tankUPath).getImage();
	private final Image tankRImage = new ImageIcon(tankRPath).getImage();
	private final Image tankDImage = new ImageIcon(tankDPath).getImage();
	private final Image tankLImage = new ImageIcon(tankLPath).getImage();
	private Image tankCurImage = getTankUImage();

	private int initXPos;// ̹�˳�ʼ����
	private final int initYPos = 30;// ���������
	private int curXPos; // ̹�˵�ǰ����
	private int curYPos;

	private final int minX = 0;
	private final int minY = 30;// ���������
	private int maxX;
	private int maxY;

	private static final int TANK_WIDTH = 40;
	private static final int TANK_HEIGHT = 40;

	private static final int SPEED = 5;
	private Direction dir = Direction.U;

	TankClient tc;

	public Tank() {
	}

	public Tank(TankClient tc) {
		this.tc = tc;
		curXPos = initXPos;
		curYPos = initYPos;
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

	// ̹���ƶ�
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

	// �ж�̹���Ƿ��ڱ߽�λ��
	public boolean isInArea(Direction d) {
		switch (d) {
		case L:
			if (curXPos - SPEED < 0)
				return false;
			break;
		case R:
			if (curXPos + SPEED + TANK_WIDTH > tc.getWindowWidth())
				return false;
			break;
		case U:
			if (curYPos - SPEED < 30)
				return false;
			break;
		case D:
			if (curYPos + SPEED + TANK_HEIGHT > tc.getWindowHeight())
				return false;
			break;
		}
		// if (curXPos - SPEED < 0 || curXPos + SPEED > tc.getWindowWidth()
		// || curYPos - SPEED < 0
		// || curYPos + SPEED > tc.getWindowHeight()) {
		// return false;
		// }
		return true;
	}

	// ------------------------------------------------------------
	// tank�ƶ�����
	public void move() {
		switch (this.dir) {
		case U:
			tankCurImage = tankUImage;
			// tankMoveUp();
			if (isInArea(Direction.U))
				tankMoveUp();
			break;
		case R:
			// tankMoveRight();
			tankCurImage = tankRImage;
			if (isInArea(Direction.R))
				tankMoveRight();
			break;
		case D:
			tankCurImage = tankDImage;
			// tankMoveDown();
			if (isInArea(Direction.D))
				tankMoveDown();
			break;
		case L:
			tankCurImage = tankLImage;
			// tankMoveLeft();
			if (isInArea(Direction.L))
				tankMoveLeft();
			break;
		}
	}

	public void draw(Graphics g) {
		// ����̹��
		g.drawImage(tankCurImage, getCurXPos(), getCurYPos(), getTankWidth(),
				getTankHeight(), tc.getBackground(), null);
	}

	public Missile fire() {

		int missileX = this.curXPos;
		int missileY = this.curYPos;

		// ����ӵ����Ͻ�����
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
		// new �ӵ�������̹�˵�ǰλ�ü�����
		Missile m = new Missile(missileX, missileY, this.getDir());
		return m;
	}
}

class MainTank extends Tank {
	// �ӵ�����
	private static final List<Missile> missileList = new ArrayList<Missile>();

	public static List<Missile> getMissileList() {
		return missileList;
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}

	// ����̹�˵Ĳ���
	public void keyPress(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_UP):// ��ͷ�ϰ���
			setDir(Direction.U);
			move();
			break;

		case (KeyEvent.VK_RIGHT):// ��ͷ�Ұ���
			setDir(Direction.R);
			move();
			break;
		case (KeyEvent.VK_DOWN):// ��ͷ�°���
			setDir(Direction.D);
			move();
			break;
		case (KeyEvent.VK_LEFT):// ��ͷ����
			setDir(Direction.L);
			move();
			break;
		case (KeyEvent.VK_ENTER):// ���ӵ�
			// ���Լ�����
			missileList.add(fire());
			break;
		}
	}

	MainTank() {
	}

	MainTank(TankClient tc) {
		super(tc);
	}
}
