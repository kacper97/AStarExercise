package eu.erasmus.intelligentSystems.search.MazeBot;
import java.util.Random;

import robocode.Robot;

public class MazeBot extends Robot {
	private int _fieldSize = 10;
	private int _seed = 1;
	private int _numObstacles = _fieldSize * _fieldSize * 3 / 10;
	private boolean[] _occupiedFields;
	
	public MazeBot(){
		Random generator = new Random(_seed);
		_occupiedFields = new boolean[_fieldSize*_fieldSize];
		// Injecting the same picuture of map that is generated also in RoutFinder class
		for (int currentObstacle = 0; currentObstacle < _numObstacles; currentObstacle++) {
			int initialObstacleRow = generator.nextInt(_fieldSize);
			int initialObstacleCol = generator.nextInt(_fieldSize);
			while (_occupiedFields[initialObstacleRow*initialObstacleCol]) {
				initialObstacleRow = generator.nextInt(_fieldSize);
				initialObstacleCol = generator.nextInt(_fieldSize);
			}
			_occupiedFields[initialObstacleRow*initialObstacleCol] = true;
		}
	}
}


