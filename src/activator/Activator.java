/**
 * 
 */
package activator;

import java.util.Random;

import brainSim.GreyMatrix;
import shufler.DeckDecoder;

/**
 * @author Wojciech Litwinowicz
 *
 */

public class Activator {
	
	static Long time;
	
	static DeckDecoder decoder;
	static GreyMatrix brain;
	
	public static void main(String[] args) {
		
		//deckDecoderTest();
		//biasedRandomTest();
		brainSimTest();
		
	}
	
	private static void brainSimTest() {
		int z = 500, t = 9;
		
		boolean[][] memory = new boolean[t][t];
		Random r = new Random();
		for(int y = 0; y < memory.length; y++)
			for(int x = 0; x < memory[y].length; x++) {
				memory[y][x] = r.nextBoolean();
			}
		
		startTimeCounter();
		brain = new GreyMatrix(memory);
		readTimeCounter();
		startTimeCounter();
		while(z > 0) {
			brain.update();
			z--;
			for(String line: brain.project()) {
				System.out.println(line);
			}
			System.out.println();
		}
		readTimeCounter();
		
		for(String line: brain.project()) {
			System.out.println(line);
		}
	}

	private static void biasedRandomTest() {
		int x = 26, t = x*2 + 2, c = 0, z = 100;
		
		System.out.println(x);
		for(int i = 1; i < z; i++) {
			startTimeCounter();
			
			x = (x+c) - (c=x);
			
			x ^= c;
			x += c;
			if(x < 0)
				x >>= 1;
			//x %= i;			
			//x = Math.abs(x);
			
			System.out.print(x + " = " + Math.abs(x%t) + "|");
			
			readTimeCounter();
		}
	}
	
	private static void deckDecoderTest() {
		int x = 52;
		int recap = 10000000;
		int tmp;
		
		for(int j = 0; j < 20; j++) {
			System.out.print(j + "---");
			
			decoder = new DeckDecoder(x);
			
			startTimeCounter();
			
			for(int i = 0; i < recap; i++) {
				tmp = decoder.getNewNumber();
				if(i%(recap/10) == 0)
					System.out.print("+");
			}
			
			readTimeCounter();
		}
	}
	
	private static void startTimeCounter() {
		time = System.currentTimeMillis();
	}
	
	private static void readTimeCounter() {
		time = System.currentTimeMillis() - time;
		
		System.out.println(((float)time)/1000f + "s");
	}
}
