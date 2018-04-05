package eu.erasmus.intelligentSystems.search.MazeBot;
import java.util.Random;

import robocode.Robot;

public class MazeBot extends Robot {
	private int _fieldSize = 10;
	private int _obstacleSeed = 1;
	private int _agentSeed = 2;
	private int _numObstacles = _fieldSize * _fieldSize * 3 / 10;
	private boolean[] _occupiedFields;
	private int _myRow;
	private int _myCol;
	
	
	public MazeBot(){
		Random generator = new Random(_obstacleSeed);
		_occupiedFields = new boolean[_fieldSize*_fieldSize];
		// Injecting the same picuture of map that is generated also in RoutFinder class
		for (int currentObstacle = 0; currentObstacle < _numObstacles; currentObstacle++) {
			int initialObstacleRow = generator.nextInt(_fieldSize);
			int initialObstacleCol = generator.nextInt(_fieldSize);
			while (_occupiedFields[initialObstacleRow*_fieldSize+initialObstacleCol]) {
				initialObstacleRow = generator.nextInt(_fieldSize);
				initialObstacleCol = generator.nextInt(_fieldSize);
			}
			_occupiedFields[initialObstacleRow*_fieldSize+initialObstacleCol] = true;
		}
		// Injecting agent's position
		generator = new Random(_agentSeed);
		int i = generator.nextInt(_fieldSize);
		int j = generator.nextInt(_fieldSize);
		while (_occupiedFields[i*_fieldSize+j]) {
			i = generator.nextInt(_fieldSize);
			j = generator.nextInt(_fieldSize);
		}
		_myRow = i;
		_myCol = j;
	}
	
	// A* Algorithm implementation
	private void FindRoute() {
		
	}
}


