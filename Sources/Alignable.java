import java.util.LinkedList;
interface Alignable{
    Alignement aDroite();
    Alignement enBas();
    Alignement droiteBas();
    Alignement droiteHaut();
    boolean rienGauche();
    boolean rienHaut();
    boolean rienGaucheBas();
    boolean rienGaucheHaut();

    public static Alignement alignementHorizontal(Alignable a){
    	/* L'id�e est que si $a est l'extr�mit� gauche d'un alignement horizontal,
    	on r�cup�re l'extr�mit� droite et on retourne l'alignement ainsi trouv�.
    	On fait bien-s�r la m�me chose avec les trois autres inclinaisons. */
	if(a.rienGauche())
	    return a.aDroite();
	return null;
    }

    public static Alignement alignementVertical(Alignable a){
	if(a.rienHaut())
	    return a.enBas();
	return null;
    }

    /* Un alignement du sud-ouest au nord-est (pente croissante) */
    public static Alignement alignementCroissant(Alignable a){
	if(a.rienGaucheBas())
	    return a.droiteHaut();
	return null;
    }

    public static Alignement alignementDecroissant(Alignable a){
	if(a.rienGaucheHaut())
	    return a.droiteBas();
	return null;
    }
    
    /* Retourne une liste de tous les alignements de $tab dont la longueur est sup�rieure
    o� �gale � $minimum. */
    public static LinkedList<Alignement> alignements(Alignable[][] tab, int minimum){
    	/* On travaille dans ce projet sur un tableau � deux dimensions,
    	mais cette m�thode peut avoir des �quivalents pour d'autres
    	structure de donn�es, comme une collection d'alignables. */
	LinkedList<Alignement> ans=new LinkedList<Alignement>();
	Alignement a1, a2, a3, a4;
	Alignement [] checkAlignements;
	for(Alignable[] t : tab)
	    for(Alignable a : t){
		a1=Alignable.alignementHorizontal(a);
		a2=Alignable.alignementVertical(a);
		a3=Alignable.alignementCroissant(a);
		a4=Alignable.alignementDecroissant(a);
		checkAlignements=new Alignement[]{a1, a2, a3, a4};
		for(Alignement i : checkAlignements)
		    if(i!=null&&i.length()>=minimum)
			ans.add(i);
	    }
	return ans;
    }
}
