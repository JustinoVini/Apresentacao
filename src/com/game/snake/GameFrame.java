package com.game.snake;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	// Cria��o do construtor.
	public GameFrame(){
		GamePanel panel = new GamePanel();		
		this.add(new GamePanel());
		// Fun��o para inserir o t�tulo do jogo.
		this.setTitle("Snake Game");		
		// Fun��o para que ao clicar no bot�o "X" o jogo feche automaticamente.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		// Fun��o Resizable, para que o usuario n�o possa redimensionar a janela do jogo.
		this.setResizable(false);
		this.pack();
		// Comando para que apare�a o frame.
		this.setVisible(true);
		// comando utilizado para setar onde a janela do frame ser� aberta, aqui setado para o centro.
		this.setLocationRelativeTo(null);
	}
}
