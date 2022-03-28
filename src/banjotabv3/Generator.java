package banjotabv3;


import java.util.ArrayList;


public class Generator {
	
	int prevFret;
	int prevSt;
	
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
	
	public ArrayList<Integer> rank(ArrayList<Integer> choices,int prevSt,int prevFret) {
		ArrayList<Integer> rankings= new ArrayList<Integer>();
		//int pass=1;
		for(int s=0;s<5;s++) { //as many passes as strings
			//System.out.println("pass no. "+pass);
			//pass++;
			int choice = -100;
			int iClosest = -100;
			int smallestDist = 100;
			int dist;
			
			for(int i=0;i<choices.size();i++) { //look at every string
				
				dist= choices.get(i)-prevFret;  //get the dist
				if(dist<0) {
					dist*=-1;
				}
				
				//System.out.println("index is "+i+" fret is "+choices.get(i) +" dist to prev ("+prevFret+") is " + dist);
		
				if(prevSt!=i && choices.get(i)==0) {  //if its new string and open, done
					iClosest=i;
					break;
				}
				if(prevSt!=i && choices.get(i)!=-1 && dist<smallestDist) { //get closest on new string
					smallestDist=dist;
					iClosest = i;
				}
			}
			
			if(iClosest==-100) {  //if not found anywhere, choose prev string
				iClosest=prevSt;  //it'll be a double up, or a rest "X" if it truly is not anywhere
				break;
			}
		
			choice = iClosest;
			//System.out.println("we went with "+choices.get(choice)+" on index "+ choice+ ". dist of "+smallestDist+ " bc coming from "+prevSt);
			/*
			this.prevSt = choice;
			if(choices.get(choice)!=0) {  //playing open strings doesn't require a shift
				this.prevFret = choices.get(choice);
			}
			*/
			//record best string and set it to -1. so we can get next best
			rankings.add(choice);
			choices.set(choice, -1);
			//System.out.println(rankings.toString());
		}
		return rankings;
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
