package froggerGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class FroggerPanel extends JPanel implements KeyListener{
	Timer gameLoopTimer;
	Timer countdownTimer;
	private int score = 0;
	private int timeRemaining = 60;
	private JProgressBar progressBar;
	private ArrayList<MovingObject> cars;
	private ArrayList<MovingObject> logs;
	private boolean isMoving = false;
	Frog frog;
	final int SCORE_BAR_HEIGHT = 120;
	final int SAFE_ZONE_HEIGHT = 45;
	final int ROAD_HEIGHT = 195;
	final int WATER_HEIGHT = 195;
	final int HOME_ZONE_HEIGHT = 75;
	final int BOARD_WIDTH;
	final int BOARD_HEIGHT;
	final int LANE_HEIGHT;
	
	FroggerPanel(){
		this.setLayout(null);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		BOARD_WIDTH = (int) screenSize.getWidth();
		BOARD_HEIGHT = (int) screenSize.getHeight();
		LANE_HEIGHT = BOARD_HEIGHT / 6;
		
		progressBar = new JProgressBar(0, timeRemaining);
		progressBar.setValue(timeRemaining);
		progressBar.setStringPainted(true);
		progressBar.setBounds(BOARD_WIDTH - 250, BOARD_HEIGHT - 100, 200, 20);
		progressBar.setForeground(Color.GREEN);
		progressBar.setStringPainted(false);
		this.add(progressBar);
		
		int frogStartY = BOARD_HEIGHT - SCORE_BAR_HEIGHT;
		frog = new Frog(BOARD_WIDTH/2, frogStartY, 25);//It was 560
		cars = new ArrayList<MovingObject>();
		
		int roadStartY = HOME_ZONE_HEIGHT + WATER_HEIGHT + SAFE_ZONE_HEIGHT + ROAD_HEIGHT - 50;
		int laneSpacing = ROAD_HEIGHT / 4;
		
		int lane1Y = roadStartY + (laneSpacing * 1);
		int carWidth1 = 40;
		int carSpeed1 = 1;
		cars.add(new MovingObject(BOARD_WIDTH, lane1Y-35, carWidth1, 30, carSpeed1, false));
		cars.add(new MovingObject(BOARD_WIDTH - 250, lane1Y-35, carWidth1, 30, carSpeed1, false));
		cars.add(new MovingObject(BOARD_WIDTH - 500, lane1Y-35, carWidth1, 30, carSpeed1, false));
		
		int lane2Y = roadStartY + (laneSpacing * 0);
		int carWidth2 = 40;
		int carSpeed2 = 1;
		cars.add(new MovingObject(0, lane2Y - 40, carWidth2, 30, carSpeed2, true));
		cars.add(new MovingObject(200, lane2Y - 40, carWidth2, 30, carSpeed2, true));
		cars.add(new MovingObject(400, lane2Y - 40, carWidth2, 30, carSpeed2, true));
		cars.add(new MovingObject(600, lane2Y - 40, carWidth2, 30, carSpeed2, true));
		
		int lane3Y = roadStartY + (laneSpacing * -1);
		int carWidth3 = 40;
		int carSpeed3 = 5;
		cars.add(new MovingObject(BOARD_WIDTH, lane3Y-45, carWidth3, 30, carSpeed3, false));
		cars.add(new MovingObject(BOARD_WIDTH - 250, lane3Y-45, carWidth3, 30, carSpeed3, false));
		
		int lane4Y = roadStartY + (laneSpacing * -2);
		int carWidth4 = 80;
		int carSpeed4 = 3;
		cars.add(new MovingObject(BOARD_WIDTH, lane4Y-50, carWidth4, 30, carSpeed4, true));
		cars.add(new MovingObject(BOARD_WIDTH - 250, lane4Y-50, carWidth4, 30, carSpeed4, true));
		
		logs = new ArrayList<MovingObject>();
		
		int waterStartY = HOME_ZONE_HEIGHT;
		int waterLaneSpacing = WATER_HEIGHT/5;
		
		int logSpeed1 = 2;
		int logWidth1 = 3 * 30;
		int waterLane1Y = waterStartY + (waterLaneSpacing * 0);
		logs.add(new MovingObject(BOARD_WIDTH, waterLane1Y + 5, logWidth1, 30, logSpeed1, false));
		logs.add(new MovingObject(BOARD_WIDTH - 300, waterLane1Y + 5, logWidth1, 30, logSpeed1, false));
		
		int logSpeed2 = 2;
		int logWidth2 = 3 * 30;
		int waterLane2Y = waterStartY + (waterLaneSpacing * 1);
		logs.add(new MovingObject(0, waterLane2Y + 5, logWidth2, 30, logSpeed2, true));
		logs.add(new MovingObject(200, waterLane2Y + 5, logWidth2, 30, logSpeed2, true));
		
		int logSpeed3 = 1;
		int logWidth3 = 6 * 30;
		int waterLane3Y = waterStartY + (waterLaneSpacing * 2);
		logs.add(new MovingObject(BOARD_WIDTH, waterLane3Y + 5, logWidth3, 30, logSpeed3, false));
		logs.add(new MovingObject(BOARD_WIDTH - 400, waterLane3Y + 5, logWidth3, 30, logSpeed3, false));
		
		int logSpeed4 = 3;
		int logWidth4 = 2 * 30;
		int waterLane4Y = waterStartY + (waterLaneSpacing * 3);
		logs.add(new MovingObject(0, waterLane4Y + 5, logWidth4, 30, logSpeed4, true));
		logs.add(new MovingObject(200, waterLane4Y + 5, logWidth4, 30, logSpeed4, true));
		logs.add(new MovingObject(400, waterLane4Y + 5, logWidth4, 30, logSpeed4, true));
		
		int logSpeed5 = 2;
		int logWidth5 = 4 * 30;
		int waterLane5Y = waterStartY + (waterLaneSpacing * 4);
		logs.add(new MovingObject(BOARD_WIDTH, waterLane5Y + 5, logWidth5, 30, logSpeed5, false));
		logs.add(new MovingObject(BOARD_WIDTH - 300, waterLane5Y + 5, logWidth5, 30, logSpeed5, false));
		
		gameLoopTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (MovingObject car : cars) {
					car.move(BOARD_WIDTH);
				}
				for (MovingObject log : logs) {
					log.move(BOARD_WIDTH);
				}
				for (MovingObject car : cars) {
					if (frog.getBounds().intersects(car.getBounds())) {
						frog.lives--;
						if(frog.lives == 0) {
							((Timer)e.getSource()).stop();
							countdownTimer.stop();
							JOptionPane.showMessageDialog(null, "You LOSE!");
						}
						frog.y = BOARD_HEIGHT - SCORE_BAR_HEIGHT;
					}
				}
				int waterStartY = HOME_ZONE_HEIGHT;
				int waterEndY = HOME_ZONE_HEIGHT + WATER_HEIGHT;
				
				if (frog.y > waterStartY && frog.y < waterEndY) {
					boolean isOnLog = false;
					for (MovingObject log : logs) {
						if (frog.getBounds().intersects(log.getBounds())) {
							isOnLog = true;
							int newFrogX = frog.x + log.speed * (log.isMovingRight ? 1 : -1);
							if (newFrogX >= 0 && newFrogX + frog.diameter < BOARD_WIDTH) {
								frog.x = newFrogX;
							}else {
								if (newFrogX < 0) {
									frog.x = 0;
								}
								else {
									frog.x = BOARD_WIDTH - frog.diameter;
								}
							}
							break;
						}
					}
					if (!isOnLog) {
						frog.lives--;
						if(frog.lives == 0) {
							((Timer)e.getSource()).stop();
							countdownTimer.stop();
							JOptionPane.showMessageDialog(null, "You LOSE!");
						}
						frog.y = BOARD_HEIGHT - SCORE_BAR_HEIGHT;
					}
				}
				
				if (frog.y < HOME_ZONE_HEIGHT) {
					((Timer)e.getSource()).stop();
					countdownTimer.stop();
					score += 1050;
					score += timeRemaining * 10;
					JOptionPane.showMessageDialog(null, "You WIN!");
				}

				repaint();
			}
		});
		gameLoopTimer.start();
		

		countdownTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timeRemaining--;
				progressBar.setValue(timeRemaining);
				
				if (timeRemaining <= 0) {
					((Timer)e.getSource()).stop();
					gameLoopTimer.stop();
					JOptionPane.showMessageDialog(null, "You LOSE!");
				}
			}
			
		});
		countdownTimer.start();
		
		this.addKeyListener(this);
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int y = 0;
		
		g.setColor(Color.GREEN);
		g.fillRect(0, y, BOARD_WIDTH, HOME_ZONE_HEIGHT);
		y += HOME_ZONE_HEIGHT;
		
		g.setColor(Color.BLUE);
		g.fillRect(0, y, BOARD_WIDTH, WATER_HEIGHT);
		y += WATER_HEIGHT;
		
		g.setColor(new Color(128, 0, 128));
		g.fillRect(0, y, BOARD_WIDTH, SAFE_ZONE_HEIGHT);
		y += SAFE_ZONE_HEIGHT;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, y, BOARD_WIDTH, ROAD_HEIGHT);
		y += ROAD_HEIGHT;
		
		g.setColor(new Color(128, 0, 128));
		g.fillRect(0, y, BOARD_WIDTH, SAFE_ZONE_HEIGHT);
		y += SAFE_ZONE_HEIGHT;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, y, BOARD_WIDTH, SCORE_BAR_HEIGHT);
		y += SCORE_BAR_HEIGHT;
		
		g.setColor(Color.RED);
		for (MovingObject car : cars) {
			g.fillRect(car.x, car.y, car.width, car.height);
		}
		
		g.setColor(new Color(139, 69, 19));
		for (MovingObject log : logs) {
			g.fillRect(log.x, log.y, log.width, log.height);
		}
		
		g.setColor(Color.GREEN);
		g.fillOval(frog.x, frog.y, frog.diameter, frog.diameter);
		
		for (int i = 0; i < frog.lives; i++) {
			g.fillOval(i+i*40, 600, 25, 25);
		}
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		String scoreText = "SCORE "+score;
		g.drawString(scoreText, 500, BOARD_HEIGHT - SCORE_BAR_HEIGHT + 35);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (!isMoving) {
			isMoving = true;
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (frog.y - 40 >= 0) {
					frog.y -= 40;//Try
					score += 10;
				} 
			}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (frog.y + 40 + frog.diameter <= BOARD_HEIGHT - SCORE_BAR_HEIGHT) {
					frog.y += 40;//Try
				}
			}else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (frog.x + frog.diameter + 15 <= BOARD_WIDTH) {
					frog.x += 20;//Try
				}
			}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (frog.x - 20 >= 0) {
					frog.x -= 20;//Try
				}
			}
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		isMoving = false;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public ArrayList<MovingObject> getCars() {
		return cars;
	}

	public void setCars(ArrayList<MovingObject> cars) {
		this.cars = cars;
	}

	public ArrayList<MovingObject> getLogs() {
		return logs;
	}

	public void setLogs(ArrayList<MovingObject> logs) {
		this.logs = logs;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
