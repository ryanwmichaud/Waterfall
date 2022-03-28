package banjotabv3;
//idea - interface where you can manually change jumps you don't like. click on a note and it'll give you the next "best" choice and recompute the following notes in real time. click to cycle through including same string
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import banjotabv3.Generator.NoteNames;

public class Main {

	public static void main(String args[]) {
		ArrayList<Note> noteList = new ArrayList<Note>();
		ListIterator<Note> noteIt = noteList.listIterator();

		Generator gen =new Generator();
		String fName=args[0];
		
		ArrayList<St> sts= new ArrayList<St>();  //make the strings 
		/*
		St firstSt = new St(NoteNames.D,5);
		St secondSt = new St(NoteNames.B,4);
		St thirdSt = new St(NoteNames.G,4);
		St fourthSt = new St(NoteNames.D,4);
		St fifthSt = new St(NoteNames.G,5,7);
		*/
		St firstSt = new St(NoteNames.A,5);
		St secondSt = new St(NoteNames.D,5);
		St thirdSt = new St(NoteNames.G,4);
		St fourthSt = new St(NoteNames.C,4);
		sts.add(firstSt);
		sts.add(secondSt);
		sts.add(thirdSt);
		sts.add(fourthSt);	
		//sts.add(fifthSt);
		
		gen.printSts(sts);
		
		try {
			Scanner scan = new Scanner(new File(fName));
			int count=0;
			while (scan.hasNext()) {
				String currentNoteName=scan.next();									//next note
				ArrayList<Integer> choices = gen.getChoices(sts,currentNoteName);	//get choices
				ArrayList<Integer> rankings = gen.rank(choices,gen.prevSt,gen.prevFret);	//sort choices best to worst
				choices = gen.getChoices(sts,currentNoteName); 								//restore once got rankings
				Note currentNote = new Note(currentNoteName,choices,rankings,gen.prevSt,gen.prevFret);				//make+save note object w the choices sorted
				noteIt.add(currentNote); 
				
				try {
					int stChoice = currentNote.peekNextBest();
					int jump = sts.get(stChoice).getFret(currentNoteName) - gen.prevFret;
					if(jump<0) {
						jump*=-1;
					}
					if(jump<5||sts.get(stChoice).getFret(currentNoteName)==0||gen.prevFret==0||gen.prevFret==-1) {
			
						doNextBest( gen, currentNote, sts, choices);  //use what we've decided was best in the note
					}else {
						Note n = noteIt.previous();
						System.out.println(n.getName()+" is impossible bc choices are "+choices+" from "+gen.prevFret);
						n = noteIt.previous();
						System.out.println(n.getName()+" is the problem. were gonna change it");

						gen.delete(sts);
						

						ArrayList<Integer>nChoices = gen.getChoices(sts,n.getName()); 	//get all our choices back, use our old rankings
						doNextBest(gen,n,sts,nChoices);    		      //use what we've decided was next best in the note
						
						
						System.out.println("f "+gen.prevFret+" s "+gen.prevSt);

						n=noteIt.next();
						n=noteIt.next();
						/*n.setPrevFret(gen.p);
						n.setPrevSt();
						gen.setPrevFret(n.getPrevFret());
						gen.setPrevSt(n.getPrevSt());
						*/
						nChoices = gen.getChoices(sts,n.getName());              //we changed the last note. now we need to re decide new best.
						rankings = gen.rank(choices,gen.prevSt,gen.prevFret);
						n.reRank(rankings);
						System.out.println("f "+gen.prevFret+" s "+gen.prevSt);
						
						doNewBest(gen,n,sts,nChoices);
					}
				}catch(NoSuchElementException e){
					
					gen.makeChoice(sts, choices, gen.prevSt);
					count++;
					gen.prevFret=-1;
					gen.prevSt=-1;
					continue;
				}
				

				count++;
				
				if(count%(8)==0) {
					gen.addBarline(sts);
				}
				
				if(count%30==0) {
					gen.printTab(sts);
					System.out.println("\n");
				}
				
			}
			gen.printTab(sts);
			System.out.println("\n");
			
			
			
		}
			catch(FileNotFoundException e){
				System.out.println("File Not Found");
			}
		
	
	}
	//this one just makes next best choice given the info about prev fret/string in gen. assumes nothing changed before
	public static void doNextBest(Generator gen,Note currentNote,ArrayList<St> sts,ArrayList<Integer> choices) {
		int stChoice=currentNote.getNextBest();
		
		if (stChoice==-1) {
			stChoice=gen.prevSt; //or jsut go back to before bc its not an improvemnt? or go back further recursively?
		}else {
			if(sts.get(stChoice).getFret(currentNote.getName())>0) {//don't shift for open or rest
				gen.setPrevFret(sts.get(stChoice).getFret(currentNote.getName()));
			}
		}
		
		gen.makeChoice( sts, choices, stChoice);		//make choice
		gen.setPrevSt(stChoice);
		
	}
	public static void doNewBest(Generator gen,Note currentNote,ArrayList<St> sts,ArrayList<Integer> choices) {
		int stChoice=currentNote.getNextBest();
		gen.makeChoice( sts, choices, stChoice);		//make choice
		gen.setPrevSt(stChoice);
		if(sts.get(stChoice).getFret(currentNote.getName())>0) {//don't shift for open or rest
			gen.setPrevFret(sts.get(stChoice).getFret(currentNote.getName()));
		}
		
	}
}

