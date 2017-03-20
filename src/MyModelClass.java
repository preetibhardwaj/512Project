import java.util.ArrayList;
import java.util.Collections;

public class MyModelClass implements Model {
	
	public static int debugLevel;
	//to check if finish is called or not
	public static boolean CheckFinish;
	//it won't let add edges if CheckFinish=false again and structure is still invalid 
	public static boolean CheckInvalid;
	public static ArrayList<Integer>[] Farray;
	public static ArrayList<Integer>[] Rarray;
	
	public MyModelClass(int debug_level){
		debugLevel=debug_level;
		//System.out.println("started");
	}
	
	public void setNumStates(int n){
		Farray=new ArrayList[n];
		Rarray=new ArrayList[n];
	}

	@Override
	//finish is called only once
	public boolean finish() {
		// TODO Auto-generated method stub
		CheckFinish=true;
		for(int i=0;i<Farray.length-1;i++){
			if(Farray[i]==null){
				CheckFinish=false;
				CheckInvalid=true;
				System.out.println("The given structure is an invalid Kripke structure");
				
				break;
			}
		}
		return CheckFinish;
	}

	@Override
	public boolean isValidState(int s) {
		// TODO Auto-generated method stub
		if(s>=0 && s<=Farray.length-1){
			return true;
		}
		//System.out.println("in isvalid state");
		return false;
	}

	@Override
	//I am creating two arrays her one for outgoing edges to a state and one for incoming edges
	public void addArc(int s1, int s2) {
		// TODO Auto-generated method stub
		if(CheckFinish==false && CheckInvalid==false){
			if(Farray[s1]==null){
				ArrayList<Integer> ll=new ArrayList<Integer>();
				ll.add(s2);
				Farray[s1]=ll;
			}
			else{
				if(!Farray[s1].contains(s2)){
					Farray[s1].add(s2);
				}
			}
			if(Rarray[s2]==null){
				ArrayList<Integer> ll=new ArrayList<Integer>();
				ll.add(s1);
				Rarray[s2]=ll;
			}
			else{
				if(!Rarray[s2].contains(s1)){
					Rarray[s2].add(s1);
				}
			}
		}
		//System.out.println("front"+s1+""+Farray[s1]);
		//System.out.println("reverse"+ s2+""+Rarray[s2]);
	}
	
	@Override
	public StateSet makeEmptySet() {
		// TODO Auto-generated method stub
		StateSet se=(StateSet) new MyStateSet();
		return se;
	}

	@Override
	public void deleteSet(StateSet sset) {
		// TODO Auto-generated method stub
		sset.setDelete();
	}

	@Override
	public void addState(int s, StateSet sset) {
		// TODO Auto-generated method stub
		sset.AddState(s);
	}

	@Override
	public void copy(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		ArrayList<Integer> a=sset.getElements();
		rset.setElements(a);
		//System.out.println("copy set r"+rset);
		//System.out.println("copy set s"+sset);
	}

	@Override
	public void NOT(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		//if(sset!=rset){
		ArrayList<Integer> a=sset.getElements();
		ArrayList<Integer> putValues=new ArrayList<Integer>();
		for(int i=0;i<Farray.length;i++){
			if(!a.contains(i)){
					putValues.add(i);
			}
		}
		rset.setElements(putValues);
		//System.out.println("in NOT state");
		//}
	}

	@Override
	public void EX(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		//if(rset!=sset){
			ArrayList<Integer> a=sset.getElements();
			ArrayList<Integer> putValues=new ArrayList<Integer>();
			for(int i=0;i<Farray.length;i++){
				for(int j:Farray[i]){
					if(a.contains(j)){
						if(!putValues.contains(i)){
						putValues.add(i);
						}
						break;
					}
				}
				rset.setElements(putValues);
			}
			//System.out.println("IN EX "+putValues);
		//}
	}

	@Override
	public void EF(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		StateSet tt=makeEmptySet();
		ArrayList<Integer> val=new ArrayList<Integer>();
		for(int i=0;i<Farray.length;i++){
			val.add(i);
			//System.out.println(i);
		}
		tt.setElements(val);
		//System.out.println("in EF"+val);
		EU(tt,sset,rset);
	}

	@Override
	public void EG(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		StateSet s=makeEmptySet();
		StateSet p=makeEmptySet();
		NOT(sset,s);
		AF(s,p);
		NOT(p,rset);
		//System.out.println("IN EG");
	}

	@Override
	public void AX(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		//if(rset!=sset){
		ArrayList<Integer> a=sset.getElements();
		ArrayList<Integer> putValues=new ArrayList<Integer>();
			for(int i=0;i<Farray.length;i++){
				for(int j:Farray[i]){
					if(a.contains(j)){
						if(!putValues.contains(i)){
						putValues.add(i);
						}
					}
					else{
						//putValues=null;
						break;
					}
				}
				
				//System.out.println("AX putValues"+putValues);
			}
			rset.setElements(putValues);
		//}
	}

	@Override
	public void AF(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		ArrayList<Integer> myset=sset.getElements();
		ArrayList<Integer> a;
		boolean change=true;
		boolean AllNotpresent=false;
		while(change==true){
		a=new ArrayList<Integer>(myset);
		change=false;
		for(int i:a){
			if(Rarray[i]!=null){
			for(int j: Rarray[i]){
				if(j!=i){
					for(int k:Farray[j]){
						if(a.contains(k)){
							AllNotpresent=false;
						}
						else{
							AllNotpresent=true;
							break;
						}
					}
					if(AllNotpresent==false){
						if(!myset.contains(j)){
						myset.add(j);
						change=true;
						}
					}	
				}
			}
			}
		}
	}
		rset.setElements(myset);
		//System.out.println("IN AF"+myset);
}

	@Override
	public void AG(StateSet sset, StateSet rset) {
		// TODO Auto-generated method stub
		StateSet s1=(StateSet) new MyStateSet();
		StateSet s2=(StateSet) new MyStateSet();
		NOT(sset,s1);
		EF(s1,s2);
		NOT(s2,rset);
		//System.out.println("IN AG");
	}

	@Override
	public void AND(StateSet sset1, StateSet sset2, StateSet rset) {
		// TODO Auto-generated method stub
		if(sset1!=sset2){
			ArrayList<Integer> first=sset1.getElements();
			ArrayList<Integer> second=sset2.getElements();
			ArrayList<Integer> putValues=new ArrayList<Integer>();
		for(int i=0;i<Farray.length;i++){
				if(first.contains(i)&& second.contains(i)){
					if(!putValues.contains(i)){
						putValues.add(i);
						}
				}
			}
		rset.setElements(putValues);
		//System.out.println("AND putValues"+putValues);
		}
		else{
			if(rset!=sset1)
			rset=sset1;
		}
	}

	@Override
	public void OR(StateSet sset1, StateSet sset2, StateSet rset) {
		// TODO Auto-generated method stub
		if(sset1!=sset2){
			ArrayList<Integer> first=sset1.getElements();
			ArrayList<Integer> second=sset2.getElements();
			ArrayList<Integer> putValues=new ArrayList<Integer>();
		for(int i=0;i<Farray.length;i++){
				if(first.contains(i) || second.contains(i)){
					if(!putValues.contains(i)){
						putValues.add(i);
						}
				}
			}
		rset.setElements(putValues);
		//System.out.println("OR putValues"+putValues);
		}
		else{
			if(rset!=sset1)
			rset=sset1;
		}
	}

	@Override
	public void IMPLIES(StateSet sset1, StateSet sset2, StateSet rset) {
		// TODO Auto-generated method stub
		StateSet s=makeEmptySet();
		NOT(sset1,s);
		OR(s,sset2,rset);
		//System.out.println(rset);
	}

	@Override
	public void EU(StateSet sset1, StateSet sset2, StateSet rset) {
		// TODO Auto-generated method stub
		ArrayList<Integer> val1=sset1.getElements();
		ArrayList<Integer> val2=sset2.getElements();
		ArrayList<Integer> putValues=new ArrayList<Integer>(val2);
		ArrayList<Integer> a;
		ArrayList<Integer> c=new ArrayList<Integer>(putValues);;
		
		while(c.size() > 0){
			//System.out.println("In EU 1"+putValues);
			a=new ArrayList<Integer>(c);
			c.clear();
			//System.out.println("A Size:"+a.size());
			for(Integer i:a){
				//System.out.println("In EU 2"+putValues);
				if(Rarray[i]!=null){
				for(Integer k: Rarray[i]){
					if(k!=i){
					if(val1.contains(k) && !putValues.contains(k)){
						//System.out.println("In EU 3"+putValues);
								putValues.add(k);
								c.add(k);
							
					}
					}
				}
				}
			}
			//System.out.println("Elements added :"+c.size());
		}
		rset.setElements(putValues);
		//System.out.println("In EU"+putValues);
	}


	@Override
	public void AU(StateSet sset1, StateSet sset2, StateSet rset) {
		// TODO Auto-generated method stub
		StateSet s=makeEmptySet();
		StateSet p=makeEmptySet();
		StateSet q=makeEmptySet();
		StateSet r=makeEmptySet();
		StateSet a=makeEmptySet();
		StateSet b=makeEmptySet();
		StateSet m=makeEmptySet();
		NOT(sset2,s);
		AND(sset1,s,p);
		NOT(sset1,q);
		AND(q,s,r);
		EU(p,r,a);
		EG(s,b);
		OR(a,b,m);
		NOT(m,rset);
		//System.out.println("AU");
	}

	@Override
	public boolean elementOf(int s, StateSet sset) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> values=sset.getElements();
		if(values!=null){
		if(values.contains(s)){
			return true;
		}
		}
		//System.out.println("ELEMENT OF return statement");
		return false;
	}

	@Override
	public void display(StateSet sset) {
		// TODO Auto-generated method stub
		ArrayList<Integer> values=sset.getElements();
		if(values.isEmpty()){
			System.out.println("{}");
		}
		else{
		Collections.sort(values);
		System.out.print("{");
		   for(int i:values){
			   if(values.indexOf(i)==0){
				   System.out.print("S"+i);
			   }
			   else{
				   System.out.print(", S"+i);
			   }
			}
		   System.out.println("}");
	}
	}
}
