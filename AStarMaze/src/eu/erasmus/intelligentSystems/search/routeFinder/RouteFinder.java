package eu.erasmus.intelligentSystems.search.routeFinder;

import java.util.Random;
import robocode.control.*;

public class RouteFinder {
	public static void main(String[] args) {	
		
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("/Users/denisdrobny/robocode")); // TODO this should be changed depending on user starting the program
		// Run from /Users/denisdrobny/robocode
		// Show the Robocode battle view
		engine.setVisible(true);
		// Create the battlefield
		int generateObstaclesSeed = 110100;
		int agentSeed = generateObstaclesSeed;
		int fieldSize = 15;
		int NumPixelRows = 64*fieldSize; 
		int NumPixelCols = 64*fieldSize;
		int NumObstacles = fieldSize*fieldSize * 3 / 10;

		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols);
		// 800x600
		// Setup battle parameters
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		/*
		 * Create obstacles and place them at random so that no pair of obstacles are at
		 * the same position
		 */
		RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck,eu.erasmus.intelligentSystems.search.MazeBot.MazeBot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles + 1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles + 1];
		
		boolean[] occupiedFields = generateObstacleMap(fieldSize,generateObstaclesSeed,NumObstacles);
		addRobotsToMap(occupiedFields,modelRobots,existingRobots,robotSetups);
		/*
		 * Create the agent and place it in a random position without obstacle
		 */
		existingRobots[NumObstacles] = modelRobots[1]; 
		placeAgent(agentSeed,fieldSize,NumObstacles,robotSetups,occupiedFields);
		
		/* Create and run the battle */
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds, inactivityTime,
				gunCoolingRate, sentryBorderSize, hideEnemyNames, existingRobots, robotSetups);
		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
	
	private static boolean[] generateObstacleMap(int fieldSize,int seed, int numObstacles){
		Random generator = new Random(seed);
		boolean [] occupiedFields = new boolean[fieldSize*fieldSize];
		for (int currentObstacle = 0; currentObstacle < numObstacles; currentObstacle++) {
			int initialObstacleRow = generator.nextInt(fieldSize);
			int initialObstacleCol = generator.nextInt(fieldSize);
			while (occupiedFields[initialObstacleRow*fieldSize+initialObstacleCol]) {
				initialObstacleRow = generator.nextInt(fieldSize);
				initialObstacleCol = generator.nextInt(fieldSize);
			}
			occupiedFields[initialObstacleRow*fieldSize+initialObstacleCol] = true;
		}
		return occupiedFields;
	}
	
	private static void placeAgent(int seed,int fieldSize,int NumObstacles,RobotSetup[] robotSetups,boolean[] occupiedFields) {
		Random generator = new Random(seed);
		int i = generator.nextInt(fieldSize);
		int j = generator.nextInt(fieldSize);
		while (occupiedFields[i*fieldSize+j]) {
			i = generator.nextInt(fieldSize);
			j = generator.nextInt(fieldSize);
		}
		double InitialAgentRow = i*64+32;
		double InitialAgentCol = j*64+32;
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentCol,InitialAgentRow, 0.0);
	}
	
	private static void addRobotsToMap(boolean[] occupiedField,RobotSpecification[] modelRobots,RobotSpecification[] existingRobots,RobotSetup[] robotSetups) {
		int fieldSize = (int)Math.sqrt(occupiedField.length);
		int numOfAdded = 0;
		for	(int i = 0; i < fieldSize; i++) {
			for	(int j = 0; j < fieldSize; j++) {
				if (occupiedField[i*fieldSize+j]) {
					existingRobots[numOfAdded] = modelRobots[0];
					robotSetups[numOfAdded] = new RobotSetup((double)j*64+32,(double)i* 64+32,0.0);
					numOfAdded++;
				}
			}
		}
	}
}
