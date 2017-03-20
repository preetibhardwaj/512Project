
public class TestMymodel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyModelClass m=new MyModelClass(0);
		m.setNumStates(4);
		m.addArc(0, 0);
		m.addArc(0, 1);
		m.addArc(1, 1);
		m.addArc(1, 0);
		m.addArc(1, 2);
		m.addArc(2, 1);
		m.addArc(2, 2);
		m.addArc(2, 3);
		m.addArc(3, 1);
		m.addArc(3, 2);
		m.addArc(3, 3);
	
		StateSet p=m.makeEmptySet();
		m.addState(3, p);
		StateSet q=m.makeEmptySet();
		m.addState(2, q);
		m.addState(3, q);
		StateSet s=m.makeEmptySet();
		m.elementOf(2,s);
		m.copy(q, p);
		//m.AX(p,s);
		//m.AX(q,s);
		//m.EX(q,s);
		//m.AND(p, q, s);
		//m.OR(p, q, s);
		//m.NOT(p, s);
		//m.display(s);
		//m.IMPLIES(p, q, s);
		//m.AF(p, s);
		//m.display(s);
		//m.EG(p, s);
		//m.display(s);
		m.EU(p,q,s);
		m.display(s);
		//m.EF(q,s);
		//m.display(s);
		//m.AU(p,q,s);
		//m.display(s);
	}

}
