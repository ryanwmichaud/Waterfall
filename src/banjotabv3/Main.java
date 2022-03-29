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
		
		try {
			Scanner scan = new Scanner(new File(fName));
			
			while (scan.hasNext()) {
				String currentNoteName=scan.next();									//next note

				Note currentNote = new Note(currentNoteName, gen.prevSt,gen.prevFret);				//make+save note object w the choices sorted
				noteIt.add(currentNote); 
				
				
			}
			
			gen.printTab(sts);
			System.out.println("\n");
			
			
			
		}
			catch(FileNotFoundException e){
				System.out.println("File Not Found");
			}
		
	
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
	