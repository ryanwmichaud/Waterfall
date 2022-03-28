package banjotabv3;


import java.util.HashMap;

import banjotabv3.Generator.NoteNames;


public class St {
	
	protected HashMap<String,Integer> map = new HashMap<String,Integer>();  
	String stringRep="";
	int octave;
	NoteNames open;
	String tab = "";
	int capo=-1;

		
	public St(NoteNames d, int octaveGiven) {
		map.put(d+String.valueOf(octaveGiven), 0);
		stringRep= stringRep.concat(d.toString()+octaveGiven+" ");
		this.octave=octaveGiven;
		this.open = d;
		this.tab = this.tab.concat(this.open.toString()+" |");
		buildFretBoard();
		
	}
	
	public St(NoteNames d, int octaveGiven, int capo) {
		map.put(d+String.valueOf(octaveGiven), 0);
		stringRep= stringRep.concat(d.toString()+octaveGiven+" ");
		this.octave=octaveGiven;
		this.open = d;
		this.tab = this.tab.concat(this.open.toString()+" |");
		this.capo=capo;
		buildFretBoard();
	}
	
	private void buildFretBoard() {
		//build the rest of the fret board  
				int fret = 1;
				int currentOctave = this.octave;
				int currentNoteNum = this.open.ordinal() + 1;   //change? to enum hashmap thing? 
				
				if(capo!=-1) {
					for(int i =0;i<capo-1;i++) { 	//we want to leave orig string name at bottom if capo,dif from retuning. so put new "open" up the neck
						if (i%2==0) {
							stringRep= stringRep.concat("   ");
						}else {
							stringRep= stringRep.concat("    ");

						}
					}
					currentNoteNum = this.open.ordinal()  + capo-5; 	//change starting note and fret
					if (currentNoteNum>=12) {
						currentNoteNum%=12;
					}
										
					fret=capo;
					
					
				}
				
				
				
				while(fret<22) {
					if(currentNoteNum==12) {
						currentNoteNum=0;
						currentOctave++;
					}
					//Note current = new Note(NoteNames.values()[currentNoteNum],currentOctave);
					
					stringRep= stringRep.concat(NoteNames.values()[currentNoteNum]+String.valueOf(currentOctave)+" ");
					
					if(fret==capo) {
						map.remove(this.open+String.valueOf(this.octave));
						map.put(NoteNames.values()[currentNoteNum]+String.valueOf(currentOctave), 0);					
					}else {
						map.put(NoteNames.values()[currentNoteNum]+String.valueOf(currentOctave), fret);
					}
					fret++;
					currentNoteNum++;
				}
			System.out.println(map.toString());	
				
	}
	
	/*
private void buildFretBoardCapo()  {
		
		//build the rest of the fret board  
		int fret = 1;
		int currentNoteNum = this.open.ordinal()+1 ;   //change? to enum hashmap thing?
		int currentOctave = this.octave;
		
		while(fret<=5) {	
			fret++;
		}
		
		while(fret<22) {
			if(currentNoteNum==12) {
				currentNoteNum=0;
				currentOctave++;
			}
			//Note current = new Note(NoteNames.values()[currentNoteNum],currentOctave);
			
			stringRep= stringRep.concat(NoteNames.values()[currentNoteNum]+String.valueOf(currentOctave)+" ");
			
			map.put(NoteNames.values()[currentNoteNum]+String.valueOf(currentOctave), fret);
			fret++;
			currentNoteNum++;
		}
	}
	*/
	public void printSt(){
		/*for(Note current:map.keySet()) {
			System.out.printf("%d for %s\n",map.get(current), current.getName());
		}*/
		System.out.println(this.stringRep);
	}
	public void printStTab(){
		System.out.println(this.tab);
		this.tab=this.open.toString()+" |";
	}
	
	public int getFret(String n) throws NullPointerException {
		
		int ret = -1;
		try{
			ret = map.get(n);
		}catch(NullPointerException e) {
			//System.out.printf("%s was not found on this string\n",n);
		}
		return ret;
	}
	public void rest() {
		this.tab=this.tab.concat("X--");
	}
	public void delete() {
		this.tab=this.tab.substring(0,this.tab.length()-3);
	}
	
	public void choose(int fret) {
		if(fret>9) { //check if single digit
			this.tab=this.tab.concat(String.valueOf(fret)+"-");
		}else if(fret==-1){
			this.tab=this.tab.concat("X--");
		}else {
			this.tab=this.tab.concat(String.valueOf(fret)+"--");
		}
	}
	public void barline() {
		this.tab=this.tab.concat("|");
	}
	
	public void notChoose() {
		this.tab=this.tab.concat("---");
	}
	

}