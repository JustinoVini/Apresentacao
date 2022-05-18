package com.game.snake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * @links https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
 * @links https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics.html
 * @links https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
 * @links https://stackoverflow.com/questions/49384321/argument-mismatch-method-cannot-be-converted-to-timertask
 * @author visil & ferchoairy
 *
 */

public class GamePanel extends JPanel implements ActionListener {

	// Criação de constantes.
	// Criação do tamanho da tela do jogo. altura e largura.
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 960;
	public static final int UNIT_SIZE = 50;
	public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	public static final int DELAY = 175;

	// Criação dos arrays.
	public final int x[] = new int[GAME_UNITS];
	public final int y[] = new int[GAME_UNITS];

	// Criação e inicialização de quantos frames o corpo da cobra irá receber ao
	// iniciar o jogo.
	int parteCorpo = 6;
	int macasComidas;
	int macaX;
	int macaY;
	char direction = 'R';
	boolean correndo = true;
	Timer timer;
	Random random;

	// Criação do construtor.
	public GamePanel() {
		// Chamada do método random para que o mesmo posicione randomicamente as maçãs.
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		// Para conseguir acessar a camada de cores do java para implementar o
		// background do frame
		// Necessita do import java.awt.*;
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		comecoJogo();
	}

	// Método para dar inicio ao jogo.
	public void comecoJogo() {
		novaMaca();
		correndo = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	// Componente de pintura do jogo.
	public void componentePintura(Graphics g) {
		super.paintComponent(g);
		desenho(g);
	}

	// Método de criação do desenho.
	public void desenho(Graphics g) {

		if (correndo) {
			/**
			for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			}**/
			
			g.setColor(Color.green);
			g.fillOval(macaX, macaY, UNIT_SIZE, UNIT_SIZE);

			for (int i = 0; i < parteCorpo; i++) {
				if (i == 0) {
					g.setColor(Color.blue);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Placar: "+macasComidas, 
			(SCREEN_WIDTH - metrics.stringWidth("Placar: "+macasComidas))/2, g.getFont().getSize());
			
		}else {
			gameOver(g);
		}
	}

	// Método de criação de nova maçã.
	public void novaMaca() {
		macaX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		macaY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	// Método de movimemtno da cobra.
	public void movimento() {
		for (int i = parteCorpo; i > 0; i++) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}

	// Método para checagem de maçã
	public void checkMaca() {

		if ((x[0] == macaX) && (y[0] == macaY)) {
			parteCorpo++;
			macasComidas++;
			novaMaca();
		}
	}

	// método para checagem de colisão.
	public void checkColisao() {
		// laço irá checar se a cabeça colide com o corpo.
		for (int i = parteCorpo; i > 0; i++) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				correndo = false;
			}
		}
		// checará se a cabeça tocara a borda esquerda.
		if (x[0] < 0) {
			correndo = false;
		}
		// checará se a cabeça tocará a borda direita.
		else if (x[0] > SCREEN_WIDTH) {
			correndo = false;
		}

		// Checagem se a cabeça toca a borda de cima.
		else if (y[0] < 0) {
			correndo = false;
		}

		else if (x[0] > SCREEN_WIDTH) {
			correndo = false;
		}

		if (!correndo) {
			timer.stop();
		}
	}

	// Método para verificar o fim do jogo.
	public void gameOver(Graphics g) {
		//Tela do placar do jogador.
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Placar: "+macasComidas, 
		(SCREEN_WIDTH - metrics.stringWidth("Placar: "+macasComidas))/2, g.getFont().getSize());
		// Fim de jogo texto.
		g.setColor(Color.red);
		g.setFont(new Font("Serif", Font.BOLD + Font.ITALIC, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Fim de Jogo", (SCREEN_WIDTH - metrics1.stringWidth("Fim de Jogo"))/2, SCREEN_HEIGHT/2);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (correndo) {
			movimento();
			checkMaca();
			checkColisao();
		}
		repaint();
	}

	// Classe criada a partir do java doc, para receber comandos do teclado.
	// Todas as classes pertencem ao pacote de evento desde o java 7.
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
