//Ben Boggs
import java.lang.Math;
import java.util.Scanner;

public class Minesweeper
{
	private boolean[][] minemap; //true if mine
	private int[][] board; //number of mines nearby; -num if mine
	private int mines, rows, cols, spacesLeft;
	private String[][] state; //visual board

	public static void main(String args[])
	{
		Scanner settings = new Scanner(System.in);
		System.out.print("rows: ");
		int r = settings.nextInt();
		System.out.print("cols: ");
		int c = settings.nextInt();
		System.out.print("mines: ");
		int m = settings.nextInt();
		Minesweeper x = new Minesweeper(r, c, m);
	}

	public Minesweeper(int initR, int initC, int initM)
	{
		mines = initM;
		rows = initR;
		cols = initC;
		spacesLeft = rows*cols - mines;
		if (mines > rows*cols)
		{
			System.out.println("Error: too many mines");
		}
		else
		{
			minemap = new boolean[rows][cols];
			board = new int[rows][cols];
			state = new String[rows][cols];
			initBoard();
			play();
		}
	}

	private void play()
	{
		
		int r, c, guesses;
		boolean alive = true;
		guesses = 0;
		while (alive && guesses < rows*cols-mines)
		{
			printBoard();
			alive = guess(getRow(), getCol());
			guesses++;
			spacesLeft--;
		}
		if (alive)
			winner();
		else
			loser();
	}

	private void initBoard()
	{
		int m = mines;
		while (m > 0)
		{
			int r = (int)(rows*Math.random());
			int c = (int)(cols*Math.random());
			if (!minemap[r][c])
			{
				minemap[r][c] = true;
				m--;
			}			
		}

		for (int i=0; i<rows; i++)
		{
			for (int j=0; j<cols; j++)
			{
				if (minemap[i][j])
				{
					for (int y=i-1; y<=i+1; y++)
						for (int x=j-1; x<=j+1; x++)
							if (x>=0 && y>=0 && x<cols && y<rows)
								board[y][x] += 1;
					board[i][j] = -rows*cols;
				}
				state[i][j] = "[]";
			}
		}
	}

	private void printBoard()
	{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\nSpaces Left: " + spacesLeft + "\n");
		for (int r=0; r<rows; r++)
		{
			for (int c=0; c<cols; c++)
				System.out.print(state[r][c] + " ");
			System.out.println("");
		}
		System.out.println("");
	}

	private boolean guess(int r, int c)
	{
		if (minemap[r][c])
			return false;

		/*
		else if (board[r][c] == 0)
		{
			findZeros(r, c);       //clear all adjacent zeros
			return true;
		}
		*/

		else
		{
			state[r][c] = board[r][c] + " ";
			return true;
		}
	}

/*	private void findZeros(int r, int c)
	{
		state[r][c] = "0 ";
		for (int i=r-1; i<=r+1; i++)
		{
			for (int j=c-1; j<=c+1; j++)
			{
				if (j>=0 && i>=0 && i<rows && j<cols)
				{
					if (board[i][j] == 0 && (i!=r || j!=c))
					{
						findZeros(i, j);
					}
				}
				
			}	
		}
	} */

	private int getRow()
	{
		Scanner reader = new Scanner(System.in);
		System.out.print("row: ");
		int r = reader.nextInt();
		while (!inBounds(r, rows))
		{
			System.out.println("Input row out of bounds.");
			System.out.print("row: ");
			r = reader.nextInt();
		}
		return r-1;
	}

	private int getCol()
	{
		Scanner reader = new Scanner(System.in);
		System.out.print("col: ");
		int c = reader.nextInt();
		while (!inBounds(c, cols))
		{
			System.out.println("Input col out of bounds.");
			System.out.print("col: ");
			c = reader.nextInt();
		}
		return c-1;
	}

	private boolean inBounds(int num, int bound)
	{
		return num >= 1 && num <= bound;
	}

	private void winner()
	{
		for (int r=0; r<rows; r++)
		{
			for (int c=0; c<cols; c++)
			{
				if (minemap[r][c])
					System.out.print("* ");
				else
					System.out.print("- ");
			}
			System.out.println("");
		}
		System.out.println("You Win!");
	}

	private void loser()
	{
		for (int r=0; r<rows; r++)
		{
			for (int c=0; c<cols; c++)
			{
				if (minemap[r][c])
					System.out.print("*  ");
				else
					System.out.print(state[r][c] + " ");
			}
			System.out.println("");
		}
		System.out.println("You Lose!");
	}
}
