import java.util.ArrayList;

public class MyStateSet implements StateSet{
	
	public ArrayList<Integer> val;
	public MyStateSet(){
		val=new ArrayList<Integer>();
	}
	@Override
	public void AddState(int i) {
		// TODO Auto-generated method stub
		val.add(i);
		//System.out.println(val);
	}
	@Override
	public ArrayList<Integer> getElements() {
		// TODO Auto-generated method stub
		return val;
	}
	@Override
	public void setElements(ArrayList<Integer> a) {
		// TODO Auto-generated method stub
		val=a;
		//System.out.println("value"+val);
	}
	@Override
	public void setDelete() {
		// TODO Auto-generated method stub
		val=null;
		//System.out.println("delete"+val);
	}
}
