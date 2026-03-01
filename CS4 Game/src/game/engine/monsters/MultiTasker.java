package game.engine.monsters;

import game.engine.Role;

public class MultiTasker extends Monster {//5.4
	private int Normalspeedturns;
	public MultiTasker(String name, String description, Role role, int energy){
		super(name, description, role,energy);
		this.Normalspeedturns=0;
	}
	public int getNormalspeedturns() {
	    return Normalspeedturns;
	}


	public void setNormalspeedturns(int Normalspeedturns) {
	    if (Normalspeedturns >= 0) {  // optional validation
	        this.Normalspeedturns = Normalspeedturns;
	    }
	}
	
		
	}

