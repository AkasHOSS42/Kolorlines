import java.awt.Color;
import java.util.LinkedList;
import java.util.ListIterator;
abstract class Plateau{
    protected Case[][] board;
    
    /* La liste des coordonnées de toutes les cases vides. */
    protected LinkedList<Pair> casesVides=new LinkedList<Pair>();

    protected Case trouve(Pair pos){
	int x=pos.X();
	int y=pos.Y();

	if(x<0||y<0||x>=board.length||y>=board.length)
	    return null;
	return board[x][y];
    }

    /* Retourne le tableau des cases adjacentes à trouve(pos). */
    protected Case[] voisins(Pair pos){
	Case[] ans=new Case[9];
	int rank=0;
	Pair deplacement;
	for(int dy=-1; dy<2; dy++)
	    for (int dx=-1; dx<2; dx++){
		deplacement=Pair.sum(pos, new Pair(dx, dy));
		ans[rank]=trouve(deplacement);
		rank++;
	    }
	return ans;
    }

    public boolean add(Pair pos, Color c){
	if(casesVides.contains(pos)){
	    trouve(pos).setColor(c);
	    casesVides.remove(pos);
	    return true;
	}
	return false;
    }

    public void remove(Pair pos){
    	Case c=trouve(pos);
	c.setColor(c.vide());
	casesVides.add(pos);
    }

    public boolean deplace(Pair depart, Pair arrivee){
	Case dep=trouve(depart);
	if(dep==null)
	    return false;
	if(!add(arrivee, dep.getColor()))
	    return false;
	remove(depart);
	return true;
    }
}