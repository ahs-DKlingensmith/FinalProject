/**
 * Dylan Klingensmith
 * Period 2
 * 5/22/19
 * Final Project - Bingo
 */


import java.io.*;

public class MainDriver
{
   public static void main (String[] args) throws IOException
   {
		Bingo game = new Bingo();
		game.write("input.txt");
		game.read("input.txt");
		int x = game.playGame();
		System.out.println("The winning number is " + x);
	}
}