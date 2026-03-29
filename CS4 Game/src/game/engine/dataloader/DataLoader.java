package game.engine.dataloader;

import game.engine.*;
import game.engine.exceptions.*;
import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;

import java.util.*;
import java.io.*;


public class DataLoader {
	private static final String CARDS_FILE_NAME = "cards.csv";
	private static final String CELLS_FILE_NAME = "cells.csv";
	private static final String MONSTERS_FILE_NAME = "monsters.csv";
	
	public static ArrayList<Card> readCards() throws IOException
	{
		ArrayList<Card> arc= new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE_NAME));
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				switch(values[0])
				{
				case "SWAPPER": arc.add(new SwapperCard(values[1],values[2],Integer.parseInt(values[3])));break;
				case "SHIELD": arc.add(new ShieldCard(values[1],values[2],Integer.parseInt(values[3])));break;
				case "ENERGYSTEAL": arc.add(new EnergyStealCard(values[1],values[2],Integer.parseInt(values[3]),Integer.parseInt(values[4])));break;
				case "STARTOVER": arc.add(new StartOverCard(values[1],values[2],Integer.parseInt(values[3]),Boolean.parseBoolean(values[4])));break;
				case "CONFUSION": arc.add(new ConfusionCard(values[1],values[2],Integer.parseInt(values[3]),Integer.parseInt(values[4])));break;
				default: throw new InvalidCSVFormat("Invalid card type: "+line,line); 
				}
			
				
			
			
		}
		
		return arc;
		
	}
	
	public static ArrayList<Cell> readCells() throws IOException
	{
		ArrayList<Cell> arc= new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME));
		
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				
				switch(values.length)
				{
				case 2: arc.add((Integer.parseInt(values[1])>0)?new ConveyorBelt(values[0], Integer.parseInt(values[1])):new ContaminationSock(values[0], Integer.parseInt(values[1])));break;
				case 3: arc.add(new DoorCell(values[0], Role.valueOf(values[1]), Integer.parseInt(values[2])));break;
			
				default: throw new InvalidCSVFormat("Invalid Cell type: "+line,line); 
				}
				
			
			}
			
		
		
		
		return arc;
	}
	
	public static ArrayList<Monster> readMonsters() throws IOException
	{
		ArrayList<Monster> arc= new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(MONSTERS_FILE_NAME));
		
			String line;
			while((line =br.readLine())!= null)
			{
				String[] values = line.split(",");
				
				switch(values[0])
				{
				case "DASHER": arc.add(new Dasher(values[1],values[2],Role.valueOf(values[3]),Integer.parseInt(values[4])));break; 
				case "DYNAMO": arc.add(new Dynamo(values[1],values[2],Role.valueOf(values[3]),Integer.parseInt(values[4])));break;
				case "SCHEMER": arc.add(new Schemer(values[1],values[2],Role.valueOf(values[3]),Integer.parseInt(values[4])));break;
				case "MULTITASKER": arc.add(new MultiTasker(values[1],values[2],Role.valueOf(values[3]),Integer.parseInt(values[4])));break;
				default: throw new InvalidCSVFormat("Invalid Monster type: "+line,line);
				}
				
			
			
			
		}
		
		return arc;
		
	}
}
