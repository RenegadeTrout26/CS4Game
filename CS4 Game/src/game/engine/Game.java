package game.engine;

import java.util.*;
import game.engine.dataloader.DataLoader;
import game.engine.monsters.*;

import java.io.*;

public class Game { //5.22
	private Board board;
	private ArrayList<Monster> allMonsters;
	private Monster player;
	private Monster opponent;
	private Monster current;
	public Monster getCurrent() {
		return current;
	}
	public void setCurrent(Monster current) {
		this.current = current;
	}
	public Board getBoard() {
		return board;
	}
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters;
	}
	public Monster getPlayer() {
		return player;
	}
	public Monster getOpponent() {
		return opponent;
	}
	
	public Game(Role playerRole) throws IOException
	{
		
		board = new Board(DataLoader.readCards());
		allMonsters = DataLoader.readMonsters();
		if(Role.SCARER==playerRole)
		{
			player =selectRandomMonsterByRole(Role.SCARER);
			opponent =selectRandomMonsterByRole(Role.LAUGHER);
		}
		else
		{
			player =selectRandomMonsterByRole(Role.LAUGHER);
			opponent =selectRandomMonsterByRole(Role.SCARER);
		}
		
		current = player;
	}
	
	
	private Monster selectRandomMonsterByRole(Role role)
	{
		Random r = new Random();
		int x=r.nextInt(4);
		int[] Scarer= {0,2,4,6};
		int[] Laugher = {1,3,5,7};
		if(Role.SCARER==role)
		{
			return  allMonsters.get(Scarer[x]);
		}
		else
		{
			return allMonsters.get(Laugher[x]);	
		}
	}
}
