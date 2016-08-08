
/**
 * This game is based on John Conway's Game of Life as described in Wikipedia.
 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 */

// PUT import statements here
import java.util.Scanner;

//CLASS HEADER HERE
/**
 * This class displays a menu giving a user options to execute or quit our Game
 * of Life. Once a user picks an option, that option then initializes a pattern
 * and displays a grid with alive and dead cells. It then gives the user the
 * option to continue with generations or restart at the main menu.
 *
 * <p>
 * No known bugs for this program.
 * </p>

 */
public class GameOfLife {

	// DO NOT ADD STATIC FIELDS TO THIS CLASS
	// YOU MUST SOLVE ALL PARTS BY PASSING THE
	// THE VALUES YOU NEED TO AND FROM METHODS.
	// WE WILL TEST ALL METHODS INDEPENDENT OF
	// OTHER METHODS.
	//
	// THAT CAN ONLY WORK IF YOU DEFINE EACH
	// METHOD AS DESCRIBED. YOU MAY NOT IMPLEMENT
	// YOUR OWN DESIGN EXCEPT TO ADD PRIVATE
	// HELPER METHODS AS YOU LIKE. YOU MUST
	// STILL IMPLEMENT ALL PUBLIC METHODS
	// OF THIS CLASS.

	/**
	 * Program execution starts here. Displays a menu with different options and
	 * allows the user to initialize a certain type of world and displays to the
	 * console. Then the program allows the user to decide whether they want to
	 * make more generations or return to the main menu. The main menu also
	 * allows them to quit the game.
	 * 
	 * The method doesn't return anything or take parameters, but does require
	 * user input.
	 */
	public static void main(String[] args) {
		// Initialize scanner
		Scanner scnr = new Scanner(System.in);
		// declare local variables

		// Display Welcome message
		boolean end = false;
		System.out.println("Welcome to the Game Of Life");
		while (end == false) {
			System.out.println("Select a pattern of life for the world\n"
					+ "1 - Glider\n2 - Beacon\n"
					+ "3 - Boat\n4 - R-pentomino\n" + "5 - Random\n9 - Exit\n"
					+ "Choice:");
			// create new world with Config.WORLD_ROWS amount of rows
			// and Config.WORLD_COLUMNS amount of columns
			boolean[][] a = createNewWorld(Config.WORLD_ROWS,
					Config.WORLD_COLUMNS);
			// create an empty array with same parameters as a to allow
			// copying in for the next generations.
			boolean[][] emptyArray = 
					new boolean[Config.WORLD_ROWS][Config.WORLD_COLUMNS];

			// initialize string pattername to be access in the menu options.
			String patternName = null;
			// menu choices
			// get input from user for which pattern to display

			if (scnr.hasNextInt()) {
				int userInput = scnr.nextInt();

				switch (userInput) {
				case 1:
					initializeGlider(a);
					patternName = "Glider ";
					break;
				case 2:
					initializeBeacon(a);
					patternName = "Beacon ";
					break;
				case 3:
					initializeBoat(a);
					patternName = "Boat ";
					break;
				case 4:
					initializeRpentomino(a);
					patternName = "R-pentomino ";
					break;
				case 5:
					initializeRandomWorld(a);
					patternName = "Random ";
					break;
				case 9:
					System.out.println("End of Game of Life.");
					end = true;
					System.exit(0);
					break;
				default:
					System.out.println("Invalid input");
					continue;
				}

				int GenerationNum = 0;
				// loop to print out world and prompt for next generation or
				// Exit
				boolean done = false;
				while (done != true) {
					// this allows the program to expect input
					String input = scnr.nextLine();
					// if the input is an enter
					if (input.equals("")) {
						printWorld(patternName, a, GenerationNum);
						int cellsAlive = numOfLivingCells(a);
						System.out.println(cellsAlive + " cells are alive.");
						System.out.println();
						System.out.println("Options\n(Enter): "
								+ "show next generation\n"
								+ "end(Enter): end this simulation\nChoice:");
						nextGeneration(a, emptyArray);
						for (int row = 0; row < emptyArray.length; row++) {
							for (int column = 0; 
									column < emptyArray[row].length; column++){
								a[row][column] = emptyArray[row][column];

							}
						}
						clearWorld(emptyArray);
						GenerationNum++;
					} else {
						done = true;
						// allows us to get out of the loop and reshow the menu
						break;
					}
				}

			} else {
				System.out.println("Invalid input");
				scnr.nextLine();
			}
		}

	}

	/**
	 * Create a new world
	 * 
	 * @param numRows
	 *            The number of rows to be in the created world
	 * @param numColumns
	 *            The number of columns to be in the created world
	 * @return A double dimension array of boolean that is numRows by numColumns
	 *         in size and initialized to all false values.
	 */
	public static boolean[][] createNewWorld(int numRows, int numColumns) {
		// Use numRows and numColumns to initialize world
		// create the world of the proper size
		boolean[][] newWorld = new boolean[numRows][numColumns];
		clearWorld(newWorld);
		return newWorld;

	}

	/**
	 * Sets all the cells in the world to not alive (false).
	 * 
	 * @param world
	 *            , a two dimensional array of boolean
	 */
	public static void clearWorld(boolean[][] world) {

		for (int row = 0; row < world.length; row++) {
			for (int column = 0; column < world[row].length; column++) {
				world[row][column] = false;

			}
		}

	}

	/**
	 * Initializes the world to the Glider pattern.
	 * 
	 * <pre>
	 * ..........
	 * .@........
	 * ..@@......
	 * .@@.......
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * </pre>
	 * 
	 * @param world
	 *            the existing double dimension array that will be reinitialized
	 *            to the Glider pattern.
	 */
	public static void initializeGlider(boolean[][] world) {
		// initialize to all false values
		clearWorld(world);
		// in the world set specific cells to true that are alive for the
		// glider pattern
		world[1][1] = true;
		world[2][2] = true;
		world[2][3] = true;
		world[3][1] = true;
		world[3][2] = true;

	}

	/**
	 * Initializes the world to the Beacon pattern.
	 * 
	 * <pre>
	 * ..........
	 * .@@.......
	 * .@........
	 * ....@.....
	 * ...@@.....
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * </pre>
	 * 
	 * @param world
	 *            the existing double dimension array that will be reinitialized
	 *            to the Beacon pattern.
	 */
	public static void initializeBeacon(boolean[][] world) {
		// initialize to all false values
		clearWorld(world);
		// in the world set the cells to true that are alive for the
		// Beacon pattern
		world[1][1] = true;
		world[1][2] = true;
		world[2][1] = true;
		world[3][4] = true;
		world[4][3] = true;
		world[4][4] = true;

	}

	/**
	 * Initializes the world to the Boat pattern.
	 * 
	 * <pre>
	 * ..........
	 * .@@.......
	 * .@.@......
	 * ..@.......
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * </pre>
	 * 
	 * @param world
	 *            the existing double dimension array that will be reinitialized
	 *            to the Boat pattern.
	 */
	public static void initializeBoat(boolean[][] world) {
		// initialize to all false values
		clearWorld(world);
		// in the world set the cells to true that are alive for the
		// Boat pattern.

		world[1][1] = true;
		world[1][2] = true;
		world[2][1] = true;
		world[2][3] = true;
		world[3][2] = true;

	}

	/**
	 * Initializes the world to the R-pentomino pattern.
	 * 
	 * <pre>
	 * ..........
	 * ..@@......
	 * .@@.......
	 * ..@.......
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * </pre>
	 * 
	 * @param world
	 *            the existing double dimension array that will be reinitialized
	 *            to the R-pentomino pattern.
	 */
	public static void initializeRpentomino(boolean[][] world) {
		// initialize to all false values
		clearWorld(world);
		// in the world set the cells to true that are alive for the
		// R-pentomino pattern.
		world[1][2] = true;
		world[1][3] = true;
		world[2][1] = true;
		world[2][2] = true;
		world[3][2] = true;

	}

	/**
	 * Initialize the GameOfLife world with a random selection of cells alive.
	 * 
	 * @param world
	 *            the existing double dimension array that will be reinitialized
	 *            to a Random pattern.
	 */
	public static void initializeRandomWorld(boolean[][] world) {
		// initialize to all false values
		clearWorld(world);
		// for the cell in the specific row and column, give it a
		// true value 'Config.CHANCE_ALIVE' percent of the time.
		for (int row = 0; row < world.length; row++) {
			for (int column = 0; column < world[row].length; column++) {
				if (Config.RNG.nextDouble() < Config.CHANCE_ALIVE) {
					world[row][column] = true;
				} else {
					world[row][column] = false;
				}
			}
		}
	}

	/**
	 * Whether a cell is living in the next generation of the game.
	 * 
	 * The rules of the game are as follows: 1) Any live cell with fewer than
	 * two live neighbors dies, as if caused by under-population. 2) Any live
	 * cell with two or three live neighbors lives on to the next generation. 3)
	 * Any live cell with more than three live neighbors dies, as if by
	 * overcrowding. 4) Any dead cell with exactly three live neighbors becomes
	 * a live cell, as if by reproduction.
	 * 
	 * @param numLivingNeighbors
	 *            The number of neighbors that are currently living.
	 * @param cellCurrentlyLiving
	 *            Whether the cell is currently living.
	 * 
	 * @return True if this cell is living in the next generation and false if
	 *         it is not.
	 */

	public static boolean isCellLivingInNextGeneration(int numLivingNeighbors,
			boolean cellCurrentlyLiving) {
		// if the cell is currently living, check to see how many neighbors
		// and determine if it will live on to the next generation.
		if (cellCurrentlyLiving == true) {
			if (numLivingNeighbors == 2 || numLivingNeighbors == 3) {
				return true;
			} else {
				return false;
			}
			// If the cell is dead, check to see how many neighbors
			// and determine if it will be reborn to the next generation.
		} else if (cellCurrentlyLiving == false && numLivingNeighbors == 3) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks to see whether a cell with the Row and Column coordinates passed
	 * into the method is currently living in this generation.
	 * 
	 * @param world
	 *            an existing two dimensional boolean array
	 * @param cellRow
	 * @param cellColumn
	 * @return True if the cell is living in the current generation and false if
	 *         not.
	 */
	private static boolean cellCurrentlyLiving(boolean[][] world, int cellRow,
			int cellColumn) {
		// if the cell coordinates are set to true, the cell is living,
		// otherwise it is not.
		if (world[cellRow][cellColumn] == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method iterates through the existing world and counts how many cells
	 * are alive.
	 * 
	 * @param world
	 *            an existing two dimensional boolean array
	 * @return numLiving the number of cells living in this generation.
	 */
	private static int numOfLivingCells(boolean[][] world) {
		// create a counter to keep track of alive cells and initialize it to 0.
		int numLiving = 0;
		// Iterate through the world and if a cell is alive,
		// increase the counter by one.
		for (int row = 0; row < world.length; row++) {
			for (int column = 0; column < world[row].length; column++) {
				if (world[row][column] == true) {
					numLiving++;
				}
			}
		}
		return numLiving;
	}

	/**
	 * Whether a specific neighbor is alive.
	 *
	 * Check whether the specific cell is alive or dead.
	 * 
	 * If the cellRow is less than 0 or greater than the number of rows -1 then
	 * the cell is outside the world and returns false. If the cellColumn is
	 * less than 0 or is greater than the number of columns -1 then the cell is
	 * outside the world and returns false.
	 * 
	 * @param world
	 *            double dimension boolean array that represents the current
	 *            world.
	 * @param neighborCellRow
	 *            The row of the cell which we are wanting to know is alive.
	 * @param neighborCellColumn
	 *            The column of the cell for which we are wanting to know is
	 *            alive.
	 * 
	 * @return Whether the cell is alive.(True or false)
	 */
	public static boolean isNeighborAlive(boolean[][] world,
			int neighborCellRow, int neighborCellColumn) {
		// checks for valid row index
		if (neighborCellRow < 0 || neighborCellRow > world.length - 1) {
			return false;

			// checks for valid column index
		} else if (neighborCellColumn < 0
				|| neighborCellColumn > world[world.length - 1].length - 1) {
			return false;

		} else {
			// Checking if specified neighbor is alive or dead.
			if (world[neighborCellRow][neighborCellColumn] == true) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Counts the number of neighbors that are currently living around the
	 * specified cell.
	 *
	 * A cell has eight neighbors. The neighbors are the cells that are
	 * horizontally, vertically and diagonally adjacent.
	 * 
	 * @param world
	 *            The current world.
	 * @param cellRow
	 *            The row of the cell for which we are looking for neighbors.
	 * @param cellColumn
	 *            The column of the cell for which we are looking for neighbors.
	 * 
	 * @return The number of neighbor cells that are currently living.
	 */
	public static int numNeighborsAlive(boolean[][] world, int cellRow,
			int cellColumn) {
		// create a counter to keep track of alive neighbors
		// and initialize it to 0.
		int counter = 0;
		// checks if neighbors in the row above are alive, if they are,
		// increase the counter by one.
		boolean above = isNeighborAlive(world, cellRow - 1, cellColumn);
		if (above) {
			counter++;
		}
		boolean aboveRight = 
				isNeighborAlive(world, cellRow - 1, cellColumn + 1);
		if (aboveRight) {
			counter++;
		}
		boolean aboveLeft = isNeighborAlive(world, cellRow - 1, cellColumn - 1);
		if (aboveLeft) {
			counter++;
		}

		// checks if neighbors in the row below are alive, if they are,
		// increase the counter by one.
		boolean below = isNeighborAlive(world, cellRow + 1, cellColumn);
		if (below) {
			counter++;
		}
		boolean belowRight = 
				isNeighborAlive(world, cellRow + 1, cellColumn + 1);
		if (belowRight) {
			counter++;
		}
		boolean belowLeft = isNeighborAlive(world, cellRow + 1, cellColumn - 1);
		if (belowLeft) {
			counter++;
		}
		// checks if neighbor on the left is alive, if it is,
		// increase the counter by one.
		boolean left = isNeighborAlive(world, cellRow, cellColumn - 1);
		if (left) {
			counter++;
		}
		// checks if neighbor on the right is alive, it if is, increase the
		// counter by one.
		boolean right = isNeighborAlive(world, cellRow, cellColumn + 1);
		if (right) {
			counter++;
		}
		return counter;
	}

	/**
	 * Determines the next generation of the world.
	 * 
	 * @param currentWorld
	 *            The world currently shown.
	 * @param newWorld
	 *            The new world to determine by looking at each cells neighbors
	 *            in the current world. The new world is a empty array to start.
	 */
	public static void nextGeneration(boolean[][] currentWorld,
			boolean[][] newWorld) {
		// Iterates through the current world and checks the new state of each
		// cell
		for (int row = 0; row < currentWorld.length; row++) {
			for (int column = 0; column < currentWorld[row].length; column++) {
				// returns whether the specified cell is
				// living in the next generation
				boolean newStateOfCell = isCellLivingInNextGeneration(
						numNeighborsAlive(currentWorld, row, column),
						cellCurrentlyLiving(currentWorld, row, column));

				if (newStateOfCell) {
					newWorld[row][column] = true;
				} else {
					newWorld[row][column] = false;
				}
			}
		}

	}

	/**
	 * Prints out the world showing each cell as alive or dead.
	 * 
	 * Loops through every cell of the world. If a cell is alive, print out the
	 * Config.ALIVE character, otherwise the Config.DEAD character.
	 * 
	 * Tracks how many cells are alive and prints out the number of cells alive
	 * or that no cells are alive.
	 * 
	 * @param patternName
	 *            The name of the pattern chosen by the user.
	 * @param world
	 *            The array representation of the current world.
	 * @param generationNum
	 *            The number of the current generation.
	 */
	public static void printWorld(String patternName, boolean[][] world,
			int generationNum) {
		// declare and initialize local variables
		// print out pattern and generation
		System.out.print(patternName + "generation: ");
		System.out.print(generationNum);
		System.out.println();
		// for each row in the world
		for (int row = 0; row < world.length; row++) {
			// for each column in the world
			for (int column = 0; column < world[row].length; column++) {
				// if the cell is alive
				if (world[row][column] == true)
					System.out.print(Config.ALIVE);
				// otherwise if the cell is dead.
				else
					System.out.print(Config.DEAD);
				// print out the number of cells alive.
			}
			System.out.println();
		}
	}
}
	