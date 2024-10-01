import java.util.LinkedList;
/* Une interface qui sert pour le KolorLines am�lior�.
on propose de calculer l'ensemble des cases accessibles
par une autre case � l'aide d'une m�thode de r�cursion dynamique. */
interface Bloquable{
	boolean estObstacle();
	Bloquable[] adjacents();
	Pair getPos();
	
	public static LinkedList<Pair> accessibles(Bloquable b){
		if(b==null)
			return null;
		LinkedList<Pair> ans=new LinkedList<Pair>();
		for(Bloquable x : b.adjacents())
			if(x!=null&&!x.estObstacle())
				addAccessibles(ans, x);
		ans.remove(b.getPos());
		return ans;
	}
	
	public static void addAccessibles(LinkedList<Pair> list, Bloquable b){
		if(b==null||list==null)
			return;
		list.add(b.getPos());
		for(Bloquable x : b.adjacents())
			if(x!=null&&!x.estObstacle()&&!list.contains(x.getPos()))
				addAccessibles(list, x);
	}
}