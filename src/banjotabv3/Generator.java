package banjotabv3;


import java.util.ArrayList;


public class Generator {
	
	int prevFret;
	int prevSt;
	ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
	
	
	public enum NoteNames{
		C,Db,D,Eb,E,F,Gb,G,Ab,A,Bb,B
	}
	
	public Generator() {
		prevSt=-100;
		prevFret=0;
	}
	
	/*
		for(Integer fret: getChoices(sts,"E5")) {
			System.out.println(String.valueOf(fret));
		}
	*/
		
	
	public void printSts(ArrayList<St> sts) {
		for(St s:sts) {
			s.printSt();
		}
		System.out.println("\n");
	}
	public void printTab(ArrayList<St> sts) {
		for(St s:sts) {
			s.printStTab();
		}
	}
	public void addBarline(ArrayList<St> sts) {
		for(St s:sts) {
			s.barline();
		}
	}
	
	public ArrayList<Integer> getChoices(ArrayList<St> sts,String note) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(St s:sts) {
			arr.add(s.getFret(note));
		}
		return arr;
	}
	
	
	
	public int getPrevSt() {
		return this.prevSt;
	}
	public void setPrevSt(int choice) {
		this.prevSt = choice;
		
	}
	public int getPrevFret() {
		return this.prevFret;
	}
	public void setPrevFret(int choice) {
		this.prevFret = choice;
		
	}
	public void rest(ArrayList<St> sts) {
		
		for(St s:sts) {
			s.rest();
		}
	}
	public void delete(ArrayList<St> sts) {
		for(St s:sts) {
			s.delete();
		}
	}
	
	public void makeChoice(ArrayList<St> sts, ArrayList<Integer> choices, int strChoice) {
		int i = 0;
		for(St s:sts) {
			//*****************************
			
			if(i==strChoice) {
				s.choose(choices.get(i));
			}else {
				s.notChoose();
			}
			
			//*****************************
			/*
			if(choices.get(i)!=-1) {
				s.choose(choices.get(i));
			}else {
				s.notChoose();
			}
			*/
			//******************************
			i++;
		}
	}

}
