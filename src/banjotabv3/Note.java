package banjotabv3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Note {
	String name;
	LinkedList<Integer> stChoices= new LinkedList<Integer>();
	int prevFret;
	int prevSt;
	
	
	
	public Note(String name, int prevSt,	int prevFret){ 
		//choices is list of fret choices in order from 1rst to fifth string 
		//rankings is a list of the strings indices in order from best to worst
		//System.out.println("created note and choices are "+choices.toString()+" and rankings are " + rankings.toString());
		this.name=name;
		this.prevFret=prevFret;
		this.prevSt=prevSt;
		
		//System.out.println("created note "+ this.name+ " and real best string choices are "+ this.stChoices.toString());
	}
	public void putBestBack(int best) {
		this.stChoices.add(0,best);
	}
	public void reRank(ArrayList<Integer> rankings) {
		this.stChoices.clear();
		for(int best:rankings) {					//go through list of best to worst. put corresp frets into choices. 
			this.stChoices.add((best));
		}
	}
	public String getName() {
		return this.name;
	}
	public int getPrevSt() {
		return this.prevSt;
	}
	public int getPrevFret() {
		return this.prevFret;
	}
	public void setPrevSt(int i) {
		 this.prevSt = i;
	}
	public void setPrevFret(int i) {
		 this.prevFret= i;
	}
	public int getNextBest() {
		try{
			return this.stChoices.remove();

		}catch(NoSuchElementException e){
			System.out.println("not found anywhere");
			return -1;
		}
	}
	
	public int peekNextBest() throws NoSuchElementException{
		if (this.stChoices.peek()==null) {
			throw new NoSuchElementException();

		}else{
			return this.stChoices.peek();
		}
	}
}


/*
import banjotabv2.Main.NoteNames;

public class Note {
	private int octave;
	private NoteNames noteName;
	private String name;

	public Note(NoteNames noteGiven, int octaveGiven) {
        this.noteName = noteGiven;
        this.octave = octaveGiven;
        
        this.name = String.format("%s%d",noteName,octave);
    }
	
	public String getName() {
		return name;
	}
	public NoteNames getNoteName() {
		return noteName;
	}
	public int getOctave() {
		return octave;
	}
}
*/
