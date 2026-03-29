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
		ArrayList<Monster> arm = new ArrayList<>();
		for (int i = 0; i < allMonsters.size(); i++) {
			if(allMonsters.get(i).getRole().equals(role))
				arm.add(allMonsters.get(i));
		}
		Random r = new Random();
		return arm.get(r.nextInt(arm.size()-1));
	
	}
}
