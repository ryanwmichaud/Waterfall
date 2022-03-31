package banjotabv3;
//idea - interface where you can manually change jumps you don't like. click on a note and it'll give you the next "best" choice and recompute the following notes in real time. click to cycle through including same string
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import banjotabv3.Generator.NoteNames;


//working pretty well! I think that after an open string, we should have lastFret still be saved as last fret. 
//also need to translate choices into n string tab! already have a lot of the functions to do it I think. 
//maybe if lastOPen is true, we keep the same last fret but increas the max jump distance

//consider saving one string on multi-line tab that we translate to multiple strings... so like storing 1a2a3a4a5a1b2b3b4b5b1c2c3c4c5c rather than 1a1b1c and 2a2b2c and 3a3b3c and so on. might actually be more work and less efficient... 


public class Main {

	
	public static ArrayList<Integer> backtrack(Generator gen, ArrayList<String> noteList, ArrayList<St> sts, int curr, ArrayList<Integer> sofar, boolean lastOpen,int lastFret) { //notetoget is like colum - its the length of sofar!!!
		
		if(sofar.size()==noteList.size()) { //if picked for all notes to pick, 
			System.out.println(sofar + " we should be done here");
			//int x  = 4/0; //force a stop
			gen.solutions.add(sofar);
			return sofar;
		} 
		
		System.out.print("\n"+sofar+"checking string "+curr+", last open is "+lastOpen+",   ");
		
		
		
		if(curr==sts.size()){ //if for the current note, we've checked all the strings. so 0-4 strings and were at 5
			return null; //then fail so we can give up on this dead end
		}
		

		int jump=0;    //fig out the jump if not coming from open 
		try {
			int potFret = (sts.get(curr).getFret(noteList.get(sofar.size())));
			if(!lastOpen && (potFret!=0))  { //jump stays zero if last is 0
				
				
				jump = potFret-lastFret;	//abs value of the difference
				if(jump<0) {
					jump*=-1;
				}
				System.out.print(potFret+" from "+lastFret+" jump is "+jump);
				
				
			}
		}catch(IndexOutOfBoundsException e) {
			System.out.print("either first or out of strings. leaving jump as 0");
		}
		
		
		if((sts.get(curr).getFret(noteList.get(sofar.size()))!=-1) 						//if note can be found on that string,
				&&(jump<3) 																//and not too big jump, 
				&&(((sofar.size())==0) || (curr!=sofar.get(sofar.size()-1)) )    ) {	//and not same string as previous. (or no previous yet)
			
			
			ArrayList<Integer>sofarPlusCurr = new ArrayList<Integer>(sofar);
			sofarPlusCurr.add(curr);
			int potFret = (sts.get(curr).getFret(noteList.get(sofar.size()))) ;
			boolean thisOpen = false;
			
			if((potFret==0) || (sofar.size()==0)) { 	//consider it open if its open or the first choice
				thisOpen=true;	
			}
			
			
			
			
			
			if(backtrack( gen, noteList, sts, 0,sofarPlusCurr,thisOpen ,potFret ) != null ) { //if calling it on current doesnt fail
				
				return backtrack( gen, noteList, sts, curr,sofarPlusCurr,thisOpen,potFret );//return the call! that's the one!
			}else {
				backtrack( gen, noteList, sts, curr+1,sofar,lastOpen,lastFret  );	//if it failed, go to next curr
			}
		}
		
		return backtrack( gen, noteList, sts, curr+1,sofar,lastOpen,lastFret  );	//if it failed, go to next curr
		
	}
	

	public static void main(String args[]) {
		ArrayList<String> noteList = new ArrayList<String>();
		//ListIterator<Note> noteIt = noteList.listIterator();

		Generator gen =new Generator();
		String fName=args[0];
		
		ArrayList<St> sts= new ArrayList<St>();  //make the strings 
		
		St firstSt = new St(NoteNames.D,5);
		St secondSt = new St(NoteNames.B,4);
		St thirdSt = new St(NoteNames.G,4);
		St fourthSt = new St(NoteNames.D,4);
		St fifthSt = new St(NoteNames.G,5,5);
		
		/*
		St firstSt = new St(NoteNames.A,5);
		St secondSt = new St(NoteNames.D,5);
		St thirdSt = new St(NoteNames.G,4);
		St fourthSt = new St(NoteNames.C,4);
		*/
		sts.add(firstSt);
		sts.add(secondSt);
		sts.add(thirdSt);
		sts.add(fourthSt);	
		sts.add(fifthSt);
		
		gen.printSts(sts);
		
		try { //make list of notes 
			Scanner scan = new Scanner(new File(fName));
			
			while (scan.hasNext()) {
				String currentNoteName=scan.next();									//next note

				//Note currentNote = new Note(currentNoteName, gen.prevSt,gen.prevFret);				//make+save note object w the choices sorted
				noteList.add(currentNoteName);
				//noteIt.add(currentNote); 			
			}
			System.out.println(noteList);
			//gen.printTab(sts);
			//System.out.println("\n");
		}
			catch(FileNotFoundException e){
				System.out.println("File Not Found");
			}
		ArrayList<Integer> choices = new ArrayList<Integer>();
		System.out.println(backtrack(gen, noteList, sts, 0, choices,true,0));
		System.out.println("There are "+gen.solutions.size()+" possible ways with your constraints");
		System.out.println(gen.solutions);
	}
}


/*
 * formatting code
int count=0;
count++;

if(count%(8)==0) {
	gen.addBarline(sts);
}

if(count%30==0) {
	gen.printTab(sts);
	System.out.println("\n");
}

*/
	