package io.lrx.first;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * @author lrx
 * @time 2016-12-4����10:32:30
 */
// ����̹�˵� ����
public class Tank {
	private String name;// ����
	private boolean good;
	private int SPEED = 5;

	private static Random rand = new Random();

	protected String tankUPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankU.gif";// ����̹��ͼƬ�����ļ�·��,·��д����
	protected String tankRPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankR.gif";// ����̹��ͼƬ�����ļ�·��
	protected String tankDPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankD.gif";// ����̹��ͼƬ�����ļ�·��
	protected String tankLPath = System.getProperty("user.dir")
			+ "\\img\\tank\\tankL.gif";// ����̹��ͼƬ�����ļ�·��

	protected Image tankUImage = new ImageIcon(tankUPath).getImage();
	protected Image tankRImage = new ImageIcon(tankRPath).getImage();
	protected Image tankDImage = new ImageIcon(tankDPath).getImage();
	protected Image tankLImage = new ImageIcon(tankLPath).getImage();
	protected Image tankCurImage = getTankUImage();

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

	private Direction dir = Direction.U;

	private boolean live = true;

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc;

	public Tank() {
	}

	public Tank(int curX, int curY, TankClient tc) {
		this.tc = tc;
		curXPos = curX;
		curYPos = curY;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public int getCurX() {
		return curXPos;
	}

	public void setCurX(int curXPos) {
		this.curXPos = curXPos;
	}

	public int getCurY() {
		return curYPos;
	}

	public void setCurY(int curYPos) {
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

	public int getSPEED() {
		return SPEED;
	}

	public void setSPEED(int sPEED) {
		SPEED = sPEED;
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

	public Rectangle getRec() {
		return new Rectangle(curXPos, curYPos, TANK_WIDTH, TANK_HEIGHT);
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

	public boolean judgeWall(Direction d) {// d��ʾһ����̹�˷�����ŵ�����
		for (int i = 0; i < tc.liveWall.size(); i++)
			// ��ǽ�ཻ
			if (this.getRec().intersectsLine(tc.liveWall.get(i).getLine(d))
					|| this.getRec().intersects(tc.liveWall.get(i).getRec()))
				return false;
		return true;

	}

	// ȡ������ Ŀǰû�õ�
	private Direction reDirection(Direction d) {
		switch (d) {
		case U:
			return Direction.D;
		case D:
			return Direction.U;
		case L:
			return Direction.R;
		case R:
			return Direction.L;
		}
		return null;
	}

	private int step = rand.nextInt(12) + 3;

	// ------------------------------------------------------------
	// tank�ƶ�����
	public void move() {
		if (!isInArea(this.getDir())) {
			step = 0;
			// return;
		}

		switch (this.dir) {
		case U:
			tankCurImage = tankUImage;
			// tankMoveUp();
			if (isInArea(Direction.U))
				tankMoveUp();
			// �ж��Ƿ��ǽ�ཻ
			if (!judgeWall(Direction.D))
				tankMoveDown();
			break;
		case R:
			// tankMoveRight();
			tankCurImage = tankRImage;
			if (isInArea(Direction.R))
				tankMoveRight();
			if (!judgeWall(Direction.L))
				tankMoveLeft();
			break;
		case D:
			tankCurImage = tankDImage;
			// tankMoveDown();
			if (isInArea(Direction.D))
				tankMoveDown();
			if (!judgeWall(Direction.U))
				tankMoveUp();
			break;
		case L:
			tankCurImage = tankLImage;
			// tankMoveLeft();
			if (isInArea(Direction.L))
				tankMoveLeft();
			if (!judgeWall(Direction.R))
				tankMoveRight();
			break;
		}
		Direction[] array = Direction.values();// ��ö��ת��Ϊ����
		if (!good) {
			Direction dir = array[rand.nextInt(array.length)];
			if (step == 0) {
				this.setDir(dir);
				// tc.liveMissile.add(this.fire());
				step = rand.nextInt(100) + 3;

			} else
				step--;
			if (rand.nextInt(50) > 47)
				tc.liveMissile.add(this.fire());
		}
		// while (liveTank.size() > 1) {
		// for (int i = 1; i < liveTank.size(); i++) {
		// Tank t = liveTank.get(i);
		// // t.setDir(array[(int) (Math.random() * 4)]);
		// liveMissile.add(t.fire());
		// t.move();
		//
		// }
	}

	public void draw(Graphics g) {

		if (this.live)
			// ����̹��
			g.drawImage(tankCurImage, getCurX(), getCurY(), getTankWidth(),
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
		Missile m = new Missile(missileX, missileY, this.getDir(), tc,
				this.isGood());
		return m;
	}
}

class EnemyTank extends Tank {
	EnemyTank() {
		// ��·��
		this.setTankDPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1D.gif");
		this.setTankUPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1U.gif");
		this.setTankLPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1L.gif");
		this.setTankRPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1R.gif");
		this.setCurX(0);
		this.setCurY(30);
	}

	EnemyTank(int curX, int curY, TankClient tc) {
		super(curX, curY, tc);
		this.setTankDPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1D.gif");
		// �˴�·����������̹������ͼƬΪ��
		this.setTankUPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1U.gif");
		this.setTankLPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1L.gif");
		this.setTankRPath(System.getProperty("user.dir")
				+ "\\img\\tank\\enemy1R.gif");
		tankUImage = new ImageIcon(tankUPath).getImage();
		tankDImage = new ImageIcon(tankDPath).getImage();
		tankLImage = new ImageIcon(tankLPath).getImage();
		tankRImage = new ImageIcon(tankRPath).getImage();
		this.tankCurImage = tankUImage;
		this.setDir(Direction.D);
		this.setGood(false);

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
			if (this.getDir() == Direction.U)
				move();
			else {
				this.tankCurImage = this.tankUImage;
			}
			setDir(Direction.U);
			break;

		case (KeyEvent.VK_RIGHT):// ��ͷ�Ұ���
			if (this.getDir() == Direction.R)
				move();
			else {
				this.tankCurImage = this.tankRImage;
			}
			setDir(Direction.R);
			break;
		case (KeyEvent.VK_DOWN):// ��ͷ�°���
			if (this.getDir() == Direction.D)
				move();
			else {
				this.tankCurImage = this.tankDImage;
			}
			setDir(Direction.D);
			break;
		case (KeyEvent.VK_LEFT):// ��ͷ����

			if (this.getDir() == Direction.L)
				move();
			else {
				this.tankCurImage = this.tankLImage;
			}
			setDir(Direction.L);
			break;
		case (KeyEvent.VK_ENTER):// ���ӵ�
			// ���Լ�����
			missileList.add(fire());
			break;
		}
	}

	MainTank() {
	}

	MainTank(int curX, int curY, TankClient tc) {
		super(curX, curY, tc);
		this.setGood(true);
		super.setSPEED(5);
	}
}
