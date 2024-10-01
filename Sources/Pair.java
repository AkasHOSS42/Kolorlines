class Pair{
    /* On modélise les positions et les déplacements par des vecteurs */
    private int x, y;

    public Pair(int x, int y){
	this.x=x;
	this.y=y;
    }

    public int X(){return x;}

    public int Y(){return y;}

    public static Pair sum(Pair p1, Pair p2){
	return new Pair(p1.x+p2.x, p1.y+p2.y);
    }

    public boolean equals(Object o){
	if(o==null||!(o instanceof Pair))
	    return false;
	Pair p=(Pair)o;
	return x==p.x&&y==p.y;
    }
}
