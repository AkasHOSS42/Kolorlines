class Alignement{
	/* On choisit de représenter un alignement par les coordonnées de ses deux
	extrémités. */
    private Pair depart, arrivee;

    public Pair getDep(){return depart;}
    public Pair getArr(){return arrivee;}

    public Alignement(Pair depart, Pair arrivee){
	this.depart=depart;
	this.arrivee=arrivee;
    }

    public int length(){
	return Math.max(Math.abs(depart.X()-arrivee.X())+1, Math.abs(depart.Y()-arrivee.Y())+1);
    }
    
    /* Retourne vrai si $this est en intersection avec $a. */
    public boolean coupe(Alignement a){
    	/* On se contente de parcourir les deux alignements pour
    	vérifier si ils possèdent un point en commun. */
    	int deltax1=arrivee.X()-depart.X();
    	int deltax2=a.arrivee.X()-a.depart.X();
    	int deltay1=arrivee.Y()-depart.Y();
    	int deltay2=a.arrivee.Y()-a.depart.Y();
    	int len1=length();
    	int len2=a.length();
    	
    	int dx1=(int)(deltax1+Math.signum(deltax1))/len1;
    	int dx2=(int)(deltax2+Math.signum(deltax2))/len2;
    	int dy1=(int)(deltay1+Math.signum(deltay1))/len1;
    	int dy2=(int)(deltay2+Math.signum(deltay2))/len2;
    	Pair deplacement1=new Pair(dx1, dy1);
    	Pair deplacement2=new Pair(dx2, dy2);
    	
    	boolean quit1=false;
    	boolean quit2=false;
    	for(Pair p1=depart; !quit1; p1=Pair.sum(p1, deplacement1)){
    		if(p1.equals(arrivee))
    			quit1=true;
    		for(Pair p2=a.depart; !quit2; p2=Pair.sum(p2, deplacement2)){
    			if(p2.equals(a.arrivee))
    				quit2=true;
    			if(p1.equals(p2))
    				return true;
    		}
    		quit2=false;
    	}
    	return false;
    }
    	
    
    /* Ci-dessous une idée d'implémentation de la méthode coupe qui n'a pas abouti.
    Le processus était le suivant : on calcule l'équation des droites qui contiennent
    les deux alignements, puis on calcule leur point d'intersection. Pour finir, on
    vérifie que ce point appartient bien aux deux alignements. Le type Ratio désigne un nombre rationnel.
    
    private Ratio coeffDirecteur(){
	return Ratio.quotient(new Ratio(depart.Y()-arrivee.Y()), new Ratio(depart.X()-arrivee.X()));
    }
    
    private Ratio ordonneeOrigine(){
	int x1=depart.X();
	int x2=arrivee.X();
	int y1=depart.Y();
	int y2=arrivee.Y();
	if(x1==0)
	    return new Ratio(y1);
	if(x2==0)
	    return new Ratio (y2);
	return Ratio.somme(new Ratio(y1),Ratio.produit(new Ratio(x1), Ratio.quotient(new Ratio(y1-y2), new Ratio(x1-x2))));
    }

    private static PairQ intersection(Alignement al1, Alignement al2){
	Ratio a1=al1.coeffDirecteur();
	Ratio b1=al1.ordonneeOrigine();
	Ratio a2=al2.coeffDirecteur();
	Ratio b2=al2.ordonneeOrigine();

	Ratio x=Ratio.quotient(Ratio.somme(b2, b1.oppose()), Ratio.somme(a1, a2.oppose()));
	Ratio y=Ratio.somme(Ratio.produit(a1, x), b1);

	return new PairQ(x, y);
    }

    static class PairQ{
	Ratio x, y;
	public PairQ(Ratio x, Ratio y){
	    this.x=x;
	    this.y=y;
	}

	public boolean appartientA(Alignement al){
	    Pair depart=al.getDep();
	    Pair arrivee=al.getArr();
	    int x1=depart.X();
	    int x2=arrivee.X();
	    int y1=depart.Y();
	    int y2=arrivee.Y();
	    return ((x.plusGrand(x1)&&!x.plusGrand(x2)) || (x.plusGrand(x2)&&!x.plusGrand(x1)))
	    && ((y.plusGrand(y1)&&!y.plusGrand(y2)) || (y.plusGrand(y2)&&!y.plusGrand(y1)));
	}
    }

    public boolean coupe(Alignement al){
	if(coeffDirecteur()==al.coeffDirecteur())
	    return false;
	PairQ intersection=intersection(this, al);
	return intersection.appartientA(this)&&intersection.appartientA(al);
    }*/
}
