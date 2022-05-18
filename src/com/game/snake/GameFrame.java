package com.game.snake;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	// Criação do construtor.
	public GameFrame(){
		GamePanel panel = new GamePanel();		
		this.add(new GamePanel());
		// Função para inserir o título do jogo.
		this.setTitle("Snake Game");		
		// Função para que ao clicar no botão "X" o jogo feche automaticamente.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		// Função Resizable, para que o usuario não possa redimensionar a janela do jogo.
		this.setResizable(false);
		this.pack();
		// Comando para que apareça o frame.
		this.setVisible(true);
		// comando utilizado para setar onde a janela do frame será aberta, aqui setado para o centro.
		this.setLocationRelativeTo(null);
	}
}
