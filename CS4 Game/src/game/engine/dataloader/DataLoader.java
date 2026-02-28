package game.engine.dataloader;

import game.engine.Role;
import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;

import java.util.*;
import java.io.*;


public class DataLoader {
	private static String CARDS_FILE_NAME = "cards.csv";
	private static String CELLS_FILE_NAME = "cells.csv";
	private static String MONSTERS_FILE_NAME = "monsters.csv";
	
	public static ArrayList<Card> readCards() throws IOException
	{
		ArrayList<Card> arc= new ArrayList<Card>();
		try(BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE_NAME)))
		{
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				switch(values[0])
				{
				case "SwapperCard": arc.add(new SwapperCard(values[1],values[2],Integer.parseInt(values[3])));break;
				case "ShieldCard": arc.add(new ShieldCard(values[1],values[2],Integer.parseInt(values[3])));break;
				case "EnergyStealCard": arc.add(new EnergyStealCard(values[1],values[2],Integer.parseInt(values[3]),Integer.parseInt(values[4])));break;
				case "StartOverCard": arc.add(new StartOverCard(values[1],values[2],Integer.parseInt(values[3]),Boolean.getBoolean(values[4])));break;
				case "ConfusionCard": arc.add(new ConfusionCard(values[1],values[2],Integer.parseInt(values[3]),Integer.parseInt(values[4])));break; 
				}
				
			}
			return arc;
		}
		catch(IOException e){
		e.printStackTrace();
		return null;}
		
	}
	
	public static ArrayList<Cell> readCells() throws IOException
	{
		ArrayList<Cell> arc= new ArrayList<Cell>();
		try(BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME)))
		{
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				switch(values.length)
				{
				case 2: arc.add((Integer.parseInt(values[1])>0)?new ConveyorBelt(values[0], Integer.parseInt(values[1])):new ContaminationSock(values[0], Integer.parseInt(values[1])));break;
				case 3: arc.add(new DoorCell(values[0], Role.valueOf(values[1]), Integer.parseInt(values[2])));break;
				}
				
			}
			return arc;
		}
		catch(IOException e){
		e.printStackTrace();
		return null;}
		
	}
	
	public static ArrayList<Monster> readMonsters() throws IOException
	{
		ArrayList<Monster> arc= new ArrayList<Monster>();
		try(BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME)))
		{
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				switch(values[0])
				{
				case "Dasher": arc.add(new Dasher());break; //incomplete syntax
				case "Dynamo": arc.add(new Dynamo());break;
				case "Schemer": arc.add(new Schemer());break;
				case "MultiTasker": arc.add(new MultiTasker());break;
				}
				
			}
			return arc;
		}
		catch(IOException e){
		e.printStackTrace();
		return null;}
		
	}
}
