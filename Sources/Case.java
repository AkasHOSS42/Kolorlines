import java.awt.Color;
abstract class Case extends Colore implements Alignable{
	/* Les cases adjacentes à $this. */
    protected Case N, S, E, O, NE, NO, SE, SO;
    protected Pair pos;

    /* La couleur d'une case vide, qui n'est pas la même selon le jeu auquel on joue. */
    protected abstract Color vide();
    
    public Case(Pair pos){
    	super(null);
    	couleur=vide();
	this.pos=pos;
    }

    public void setVoisins(Case[] voisins){
	NO=voisins[0];
	N=voisins[1];
	NE=voisins[2];
	O=voisins[3];
	E=voisins[5];
	SO=voisins[6];
	S=voisins[7];
	SE=voisins[8];
    }

    /* Dans les méthodes qui suivent, on doit traiter certains cas particuliers
    où $couleur==Colore.$arcEnCiel, d'où la présence de certaines formules
    booléennes compliquées. Cela va sans dire, il suffit de lire aDroite, dernierADroite
    et rienGauche pour comprendre le fonctionnement de toutes les autres méthodes. */
    public Alignement aDroite(){
	if(couleur==vide()||!memeCouleurQue(E)||E.couleur==vide())
	    return null;
	Color check=null;
	if(couleur!=Colore.arcEnCiel)
		check=couleur;
	return new Alignement(pos, E.dernierADroite(check));
    }

    private Pair dernierADroite(Color check){
    	if(couleur!=Colore.arcEnCiel)
    		check=couleur;
    	else if(check==null&&E!=null&&E.couleur!=vide())
    		return E.dernierADroite(null);
    	if(E==null||!E.memeCouleurQue(check)||E.couleur==vide())
    		return pos;
    	return E.dernierADroite(check);
    }

    public Alignement enBas(){
	if(couleur==vide()||!memeCouleurQue(S)||S.couleur==vide())
	    return null;
	Color check=null;
	if(couleur!=Colore.arcEnCiel)
		check=couleur;
	return new Alignement(pos, S.dernierEnBas(check));
    }

    private Pair dernierEnBas(Color check){
    	if(couleur!=Colore.arcEnCiel)
    		check=couleur;
    	else if(check==null&&S!=null&&S.couleur!=vide())
    		return S.dernierEnBas(null);
    	if(S==null||!S.memeCouleurQue(check)||S.couleur==vide())
    		return pos;
    	return S.dernierEnBas(check);
    }
    
    public Alignement droiteBas(){
	if(couleur==vide()||!memeCouleurQue(SE)||SE.couleur==vide())
	    return null;
	Color check=null;
	if(couleur!=Colore.arcEnCiel)
		check=couleur;
	return new Alignement(pos, SE.dernierDroiteBas(check));
    }

    private Pair dernierDroiteBas(Color check){
    	if(couleur!=Colore.arcEnCiel)
    		check=couleur;
    	else if(check==null&&SE!=null&&SE.couleur!=vide())
    		return SE.dernierDroiteBas(null);
    	if(SE==null||!SE.memeCouleurQue(check)||SE.couleur==vide())
    		return pos;
    	return SE.dernierDroiteBas(check);
    }

    public Alignement droiteHaut(){
	if(couleur==vide()||!memeCouleurQue(NE)||NE.couleur==vide())
	    return null;
	Color check=null;
	if(couleur!=Colore.arcEnCiel)
		check=couleur;
	return new Alignement(pos, NE.dernierDroiteHaut(check));
    }

    private Pair dernierDroiteHaut(Color check){
    	if(couleur!=Colore.arcEnCiel)
    		check=couleur;
    	else if(check==null&&NE!=null&&NE.couleur!=vide())
    		return NE.dernierDroiteHaut(null);
    	if(NE==null||!NE.memeCouleurQue(check)||NE.couleur==vide())
    		return pos;
    	return NE.dernierDroiteHaut(check);
    }

    public boolean rienGauche(){
    	if(couleur!=Colore.arcEnCiel)
    		return !memeCouleurQue(O);
    	return O==null||!O.memeCouleurQue(E)||
    			(O.couleur!=Colore.arcEnCiel&&E!=null&&E.couleur==Colore.arcEnCiel);
    }

    public boolean rienHaut(){
    	if(couleur!=Colore.arcEnCiel)
    		return !memeCouleurQue(N);
    	return N==null||!N.memeCouleurQue(S)||
    			(N.couleur!=Colore.arcEnCiel&&S!=null&&S.couleur==Colore.arcEnCiel);
    }

    public boolean rienGaucheBas(){
    	if(couleur!=Colore.arcEnCiel)
    		return !memeCouleurQue(SO);
    	return SO==null||!SO.memeCouleurQue(NE)||
    			(SO.couleur!=Colore.arcEnCiel&&NE!=null&&NE.couleur==Colore.arcEnCiel);
    }

    public boolean rienGaucheHaut(){
    	if(couleur!=Colore.arcEnCiel)
    		return !memeCouleurQue(NO);
    	return NO==null||!NO.memeCouleurQue(SE)||
    			(NO.couleur!=Colore.arcEnCiel&&SE!=null&&SE.couleur==Colore.arcEnCiel);
    }
}

class CaseKL extends Case{
	public static final Color vide=new Color(255, 255, 255);
	
	public Color vide(){return vide;}
	
	public CaseKL(Pair pos){
		super(pos);
	}
}

class CaseGomoku extends Case{
	/* La couleur d'un goban. */
	public static final Color vide=new Color(199, 171, 104);
	
	public Color vide(){return vide;}
	
	public CaseGomoku(Pair pos){
		super(pos);
	}
}

class CaseKLPlus extends CaseKL implements Bloquable{
	public CaseKLPlus(Pair pos){
		super(pos);
	}
	
	public boolean estObstacle(){
		return couleur!=vide;
	}
	
	public Bloquable[] adjacents(){
		return new Bloquable[]{(Bloquable)N, (Bloquable)S, (Bloquable)E, (Bloquable)O};
	}
	
	public Pair getPos(){
		return pos;
	}
}