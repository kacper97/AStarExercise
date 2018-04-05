package eu.erasmus.intelligentSystems.search.MazeBot;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import robocode.Robot;

public class MazeBot extends Robot {
	private int _fieldSize = 10;
	private int _obstacleSeed = 1;
	private int _agentSeed = 2;
	private int _numObstacles = _fieldSize * _fieldSize * 3 / 10;
	private boolean[] _closedCells;
	private Cell _currentCell;
	private Cell _goalCell;
	
	
	public MazeBot(){
		Random generator = new Random(_obstacleSeed);
		_closedCells = new boolean[_fieldSize*_fieldSize];
		// Injecting the same picuture of map that is generated also in RoutFinder class
		for (int currentObstacle = 0; currentObstacle < _numObstacles; currentObstacle++) {
			int initialObstacleRow = generator.nextInt(_fieldSize);
			int initialObstacleCol = generator.nextInt(_fieldSize);
			while (_closedCells[initialObstacleRow*_fieldSize+initialObstacleCol]) {
				initialObstacleRow = generator.nextInt(_fieldSize);
				initialObstacleCol = generator.nextInt(_fieldSize);
			}
			_closedCells[initialObstacleRow*_fieldSize+initialObstacleCol] = true;
		}
		// Injecting agent's position
		generator = new Random(_agentSeed);
		int i = generator.nextInt(_fieldSize);
		int j = generator.nextInt(_fieldSize);
		while (_closedCells[i*_fieldSize+j]) {
			i = generator.nextInt(_fieldSize);
			j = generator.nextInt(_fieldSize);
		}
		_currentCell = new Cell(i,j);
		// Generate the goal state. Doesn't need to be synced with RouteFinder Class
		i = generator.nextInt(_fieldSize);
		j = generator.nextInt(_fieldSize);
		while (_closedCells[i*_fieldSize+j]) {
			i = generator.nextInt(_fieldSize);
			j = generator.nextInt(_fieldSize);
		}
		_goalCell = new Cell(i,j);
	}
	
	// A* Algorithm implementation
	private void FindRoute() {
		int[] estimatedCells = createEstimation();
		PriorityQueue<EvaluatedCell> queue = new PriorityQueue<EvaluatedCell>();
		
		EvaluatedCell chosen = queue.poll();
		while(chosen != null && chosen.get_cell().compareTo(_goalCell) != 0) {
			
			
			chosen = queue.poll();
			_closedCells[chosen.get_row()*_fieldSize+chosen.get_col()] = true; // After choosing we close the cell
		}
	}
	
	// Heuristic function result written into every cell, manhattan distance from the goal state
	private int[] createEstimation() {
		int[] evaluated = new int[_fieldSize*_fieldSize];
		for(int i = 0; i < _fieldSize; i++) {
			for(int j = 0; j < _fieldSize; j++) {
				evaluated[i*_fieldSize + j] = Math.abs(i - _goalCell.get_row()) + Math.abs(j - _goalCell.get_col()); // Manhattan distance 
			}
		}
		return evaluated;
	}
	
	private List<EvaluatedCell> get_neighbours(EvaluatedCell middle,PriorityQueue<EvaluatedCell> queue,int[] estimatedCells){
		List<EvaluatedCell> result = new ArrayList<>();
		Cell c1 = new Cell(middle.get_row(),middle.get_col() + 1);
		Cell c2 = new Cell(middle.get_row(),middle.get_col() - 1);
		Cell c3 = new Cell(middle.get_row() - 1, middle.get_col());
		Cell c4 = new Cell(middle.get_row() + 1, middle.get_col());
		addCellToQueue(queue,c1,middle,estimatedCells);
		addCellToQueue(queue,c2,middle,estimatedCells);
		addCellToQueue(queue,c3,middle,estimatedCells);
		addCellToQueue(queue,c4,middle,estimatedCells);
		return result;
	}
	
	private void addCellToQueue(PriorityQueue<EvaluatedCell> queue, Cell c1, EvaluatedCell middle,int[] estimatedCells) {
		int cost;
		int estimation;
		if(c1.get_col() < _fieldSize && !_closedCells[c1.get_row()*_fieldSize+c1.get_col()] ) { 
			cost = middle.get_costFromStart();
			estimation = estimatedCells[c1.get_row()*_fieldSize+c1.get_col()];
			List<Cell> path = new ArrayList<Cell>(middle.get_pathFromStart());
			queue.add(new EvaluatedCell(c1, estimation, cost, path)); 
			}
	}
}


