package eu.erasmus.intelligentSystems.search.routeFinder;

import robocode.control.*;

public class RouteFinder {
	public static void main(String[] args) {
		// Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("/Users/denisdrobny/robocode"));
		// Run from C:/Robocode
		// Show the Robocode battle view
		engine.setVisible(true);
		// Create the battlefield

		int NumPixelRows = 640; // TODO
		int NumPixelCols = 640;
		int NumObstacles = 0;

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
		RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck,searchpractice.RouteBot*");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles + 1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles + 1];
		for (int NdxObstacle = 0; NdxObstacle < NumObstacles; NdxObstacle++) {
			double InitialObstacleRow = 0;
			double InitialObstacleCol = 0;
			existingRobots[NdxObstacle] = modelRobots[0];
			robotSetups[NdxObstacle] = new RobotSetup(InitialObstacleRow, InitialObstacleCol, 0.0);
		}
		/*
		 * Create the agent and place it in a random position without obstacle
		 */
		existingRobots[NumObstacles] = modelRobots[NumObstacles];
		double InitialAgentRow = 0;
		double InitialAgentCol = 0;
		robotSetups[NumObstacles] = new RobotSetup(InitialAgentRow, InitialAgentCol, 0.0);
		/* Create and run the battle */
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numberOfRounds, inactivityTime,
				gunCoolingRate, sentryBorderSize, hideEnemyNames, existingRobots, robotSetups);
		// Run our specified battle and let it run till it is over
		// engine.runBattle(battleSpec, true); // waits till the battle finishes
		// Cleanup our RobocodeEngine
		engine.close();
		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}