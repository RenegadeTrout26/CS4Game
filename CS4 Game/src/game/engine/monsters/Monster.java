package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public abstract class Monster implements  Comparable<Monster> {//5.1 Parent Class of all monsters
	  private  String name;
		private String description;
		 private int energy;
		 private int position;
		 private boolean frozen;
		 private boolean shielded;
		private  int confusionTurns;
		 private Role role;                 
		 private  Role originalRole;   

		//constructor
		    public Monster(String name,String description,Role originalRole,int energy) {

		        this.name = name;
		        this.description = description;
		        this.originalRole = originalRole;

		        this.role = originalRole;   

		        
		        if (energy >= Constants.MIN_ENERGY) {
		            this.energy = energy;
		        } 
		        else {
		            this.energy = Constants.MIN_ENERGY;
		        }

		        this.position = 0;          
		        this.confusionTurns = 0;    
		        this.frozen = false;
		        this.shielded = false;
		    }

		    
		    //Getters
		    public String getName() {
		        return name;
		    }

		    public String getDescription() {
		        return description;
		    }

		    public  Role getOriginalRole() {
		        return originalRole;
		    }

		    public Role getRole() {
		        return role;
		    }

		    public int getEnergy() {
		        return energy;
		    }

		    public int getPosition() {
		        return position;
		    }

		    public boolean isFrozen() {
		        return frozen;
		    }

		    public boolean isShielded() {
		        return shielded;
		    }

		    public int getConfusionTurns() {
		        return confusionTurns;
		    }

		    
		    //setters
		    public void setRole(Role role) {
		        this.role = role;
		    }

			public void setEnergy(int energy) {
				if(energy>=Constants.MIN_ENERGY)
					this.energy = energy;
				else
					this.energy=Constants.MIN_ENERGY;
			}

			public void setPosition(int position) {
				if(position>=0 && position<=99)
				this.position = position;
				else
					if(position>99)
						this.position=position%Constants.BOARD_SIZE;
			}

		    public void setFrozen(boolean frozen) {
		        this.frozen = frozen;
		    }

		    public void setShielded(boolean shielded) {
		        this.shielded = shielded;
		    }

		    public void setConfusionTurns(int confusionTurns) {
		        if (confusionTurns >= 0) {
		            this.confusionTurns = confusionTurns;
		        }
		    }
		    
		    
		    //CompareTo
		    public int compareTo(Monster o) {
		             
		        	return this.position-o.position;
		        }
		        
		    }
	
	
	
	
	
	
	
	
	
	
	


