import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.*;
import java.lang.RuntimeException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
class KolorLinesPlus extends KolorLines{
	public KolorLinesPlus(int n, boolean insanity){
		modele=new ModeleKLPlus(n);
		controleur=new ControleurKLPlus();
		vue=new VueKLPlus(n);
		controleur.update(false);
		if(insanity)
			((VueKL)vue).goInsane();
	}
	
	class ModeleKLPlus extends ModeleKL{
		public ModeleKLPlus(int n){
			board=new Case[n][n];
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
				board[i][j]=new CaseKLPlus(new Pair(i, j));
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++){
				board[i][j].setVoisins(voisins(new Pair(i, j)));
				casesVides.add(new Pair(i, j));
				}
			tourOrdi();
		}
		
		public LinkedList<Pair> accessibles(Pair p){
			return Bloquable.accessibles((Bloquable)trouve(p));
		}
		
		/* Redéfinition de Plateau.deplace. */
		public boolean deplace(Pair depart, Pair arrivee){
		Case dep=trouve(depart);
		if(dep==null)
			return false;
		if(!accessibles(depart).contains(arrivee))
			return false;
		if(!add(arrivee, dep.getColor()))
			return false;
		remove(depart);
		return true;
		}
	}
	
	class VueKLPlus extends VueKL{
		public VueKLPlus(int n){
			super(n);
		}
		
		/* On veut changer la couleur du bord des cases accessibles. */
		public void setAccessibles(LinkedList<Pair> accessibles){
			boolean empty=false;
			if(accessibles==null)
				empty=true;
			for(CaseVue[]t : board)
				for(CaseVue c : t){
						if(empty)
							c.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Colore.noir));
						else
							c.setEnabled(true);
				}
			if(empty)
				return;
			int x, y;
			CaseVue c;
			for(Pair p : accessibles){
				x=p.X();
				y=p.Y();
				c=board[x][y];
				c.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, accessibleColor));
			}
		}
		
		/* Redéfinition. */
		public void goInsane(){
			boolean exit;
			Color vide=modele.vide();
			while(!quit){
				exit=false;
				for(int x=0; !exit&&x<board.length; x++){
					for(int y=0; !exit&&y<board.length; y++)
						if(board[x][y].getBackground()!=vide){
							board[x][y].doClick();
							if(deplacementVirtuel())
								exit=true;
							else
								board[x][y].doClick();
						}
				}
			}
		}
		
		/* L'ordinateur cherche une case accessible et la joue. Retourne vrai
		si et seulement si il en a trouvé une. */
		private boolean deplacementVirtuel(){
			Color c;
			for(int i=0; i<board.length; i++)
				for(int j=0; j<board.length; j++){
					c=((MatteBorder)board[i][j].getBorder()).getMatteColor();
					if(c==accessibleColor){
						board[i][j].doClick();
						return true;
					}
				}
			return false;
		}
	}
	
	private static Color accessibleColor=new Color(224, 224, 224);
	
	class ControleurKLPlus extends ControleurKL{
		/* Redéfinition. */
		public void actionPerformed(ActionEvent e){
    		Pair pos=decode(e.getActionCommand());
			if(((VueKL)vue).noSelected()){
				((VueKL)vue).setSelected(pos);
				((VueKLPlus)vue).setAccessibles(((ModeleKLPlus)modele).accessibles(pos));
			}
			else{
				Pair depart=((VueKL)vue).removeSelected();
				((VueKLPlus)vue).setAccessibles(null);
				joue(depart, pos);
				((VueKL)vue).setEnabled();
			}
    	}
    }
    
    public static void main(String[] args){
    	if(args==null||args.length<1)
	    throw new PasDArgumentException();
	int n=Integer.parseInt(args[0]);
	if(n<5)
	    throw new ArgumentTropPetitException();
	javax.swing.SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    KolorLinesPlus jeu=new KolorLinesPlus(Integer.parseInt(args[0]), false);
		}
	    });
    }
}