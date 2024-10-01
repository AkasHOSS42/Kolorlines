import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.*;
import java.lang.RuntimeException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
class KolorLines extends Jeu{
	/* $insanity indique si c'est le robot stupide qui joue. */
	public KolorLines(int n, boolean insanity){
		modele=new ModeleKL(n);
		controleur=new ControleurKL();
		vue=new VueKL(n);
		controleur.update(false);
		if(insanity)
			((VueKL)vue).goInsane();
	}
	
	public KolorLines(){}
	
	class ModeleKL extends Modele{
		protected int score=0;
		
		public ModeleKL(int n){
			board=new Case[n][n];
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
				board[i][j]=new CaseKL(new Pair(i, j));
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++){
				board[i][j].setVoisins(voisins(new Pair(i, j)));
				casesVides.add(new Pair(i, j));
				}
			tourOrdi();
		}
		
		public ModeleKL(){}
		
		public void remove(Alignement al){
		int len=al.length();
		Pair dep=al.getDep();
		Pair arr=al.getArr();
		int deltaX=arr.X()-dep.X();
		int deltaY=arr.Y()-dep.Y();
		int dx=(deltaX+(int)Math.signum(deltaX))/len;
		int dy=(deltaY+(int)Math.signum(deltaY))/len;
		Pair deplacement=new Pair(dx, dy);
		for(Pair p=dep; !p.equals(arr); p=Pair.sum(p, deplacement))
			remove(p);
		remove(arr);
		}
		
		public String getScore(){
			return ""+score;
		}
		
		public boolean gameOver(){
			return casesVides.size()<4;
		}
		
		public Color vide(){
			return CaseKL.vide;
		}
		
		/* Le sujet stipule que deux alignements intersectés doivent rapporter plus de
		points que deux alignements séparés. On doit donc pouvoir compter les intersections. */
		protected int nbIntersections(LinkedList<Alignement> als){
			if(als.size()<2)
			return 0;
			Alignement a1, a2;
			ListIterator l1, l2;
			int ans=0;
			int len=als.size();
			l1=als.listIterator(0);
			for(int i=0; i<len-1; i++){
			a1=(Alignement)l1.next();
			l2=als.listIterator(i+1);
			while(l2.hasNext()){
				a2=(Alignement)l2.next();
				if(a1.coupe(a2))
				ans++;
			}
			}
			return ans;
		}

		public void addScore(LinkedList<Alignement> als){
			int bonus=nbIntersections(als)*3;
			for(Alignement al : als)
				bonus+=2*al.length()-5;
			score+=bonus;
		}
	
		protected Color couleurRandom(){
			int n=(int)(Math.random()*6);
			switch(n){
			case 0:
			return Colore.rouge;
			case 1:
			return Colore.vert;
			case 2:
			return Colore.bleu;
			case 3:
			return Colore.jaune;
			case 4:
			return Colore.marron;
			default:
			return Colore.arcEnCiel;
			}
		}
	
		protected void joueRandom(){
			Color c=couleurRandom();
			add(casesVides.get((int)(Math.random()*casesVides.size())), c);
		}
	
		public void tourOrdi(){
			for(int i=0; i<3; i++)
			joueRandom();
		}
		
		public void removeAlignements(LinkedList<Alignement> list){
			for(Alignement al : list)
			remove(al);
		}
	
		public boolean tourJoueur(Pair depart, Pair arrivee){
			if(!deplace(depart, arrivee))
			return false;
			LinkedList<Alignement> list=Alignable.alignements(board, 5);
			removeAlignements(list);
			addScore(list);
			return true;
		}
	}
	
	class VueKL extends Vue{
		/* Les coordonnées de la case sélectionnée. On rappelle que dans
		KolorLines un coup se fait en deux temps : on sélectionne la case à deplacer,
		puis on sélectionne sa destination. On gère ces deux étapes en manipulant selected. */
		protected Pair selected=null;
		
		/* Vrai si la partie est terminée. */
		protected boolean quit=false;
		
		public VueKL(int n){
			super(n);
			CaseVueKL c;
			for(int i=0; i<n; i++)
			for(int j=0; j<n; j++){
				c=new CaseVueKL(new Pair(i, j));
				board[i][j]=c;
				cases.add(c);
			}
		}
		
		class CaseVueKL extends CaseVue{
			/* Lorsque l'utilisateur sélectionne la case qu'il veut déplacer,
			ses bordures doivent changer de couleur. On choisit donc de donner à chaque case
			des bordures relativement épaisses (4px), pour que le changement de couleur
			soit visible. Remarque : si la taille du plateau est trop grande, les cases sont
			trop petites par rapport à leurs bords et on ne voit plus rien. La vue est donc codée
			dans l'hypothèse où l'utilisateur joue sur un plateau de taille "normale", pas
			ridiculement grande. */
			public CaseVueKL(Pair pos){
				super(pos);
				setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Colore.noir));
			}
			
			public void setColor(Color c){
				super.setColor(c);
				if(c==modele.vide())
					setEnabled(false);
				else
					setEnabled(true);
			}
		}
		
		public boolean noSelected(){
			return selected==null;
		}
		
		public void setSelected(Pair pos){
			selected=pos;
			board[pos.X()][pos.Y()].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, selectedColor));
		}
		
		public Pair removeSelected(){
			Pair ans=selected;
			board[ans.X()][ans.Y()].setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Colore.noir));
			selected=null;
			return ans;
		}
		
		/* Rends cliquables les cases selon la situation. */
		public void setEnabled(){
			boolean b=selected==null;
		for(CaseVue[] t : board)
				for(CaseVue c : t)
					/* Si aucune case n'est sélectionnée, les cases vides ne sont pas
					cliquables. Sinon, les cases non vides sont quand même cliquables,
					comme ça l'utilisateur peut annuler sa sélection en cliquant sur une
					case non vide. */
					c.setEnabled(!b||(c.getBackground()!=modele.vide()));
		}
		
		/* Le robot stupide joue une partie entière, en cliquant virtuellement
		sur les boutons. */
		public void goInsane(){
			boolean exit;
			int x=0;
			int y=0;
			Color vide=modele.vide();
			while(!quit){
				exit=false;
				for(x=0; !exit&&x<board.length; x++){
					for(y=0; !exit&&y<board.length; y++)
						if(board[x][y].getBackground()!=vide){
							exit=true;
						}
				}
				x--;
				y--;
				board[x][y].doClick();
				exit=false;
				for(x=0; !exit&&x<board.length; x++){
					for(y=0; !exit&&y<board.length; y++)
						if(board[x][y].getBackground()==vide){
							exit=true;
						}
				}
				x--;
				y--;
				board[x][y].doClick();
			}
		}
		
		public void update(Colore[][] plateau, String points, boolean loss){
			super.update(plateau, points, loss);
			if(loss)
				quit=true;
		}
    }
    
    class ControleurKL extends Controleur{
    	public String score(){
    		return "Score : "+modele.getScore()+".";
    	}
    	
    	public boolean joue(Pair depart, Pair arrivee){
    		if(!((ModeleKL)modele).tourJoueur(depart, arrivee))
    			return false;
    		/* On considère que le jeu est fini quand, après que
    		l'utilisateur ait joué un coup, il reste 3 cases vides ou moins.
    		Remarque : si l'ordinateur réalise un alignement de 5 cases ou plus,
    		celui-ci n'est pas retiré du plateau immédiatement 
    		(il le sera si le joueur joue son prochain coup sans y toucher). */
    		boolean loss=true;
    		if(!modele.gameOver()){
    			loss=false;
    			((ModeleKL)modele).tourOrdi();
    			/* On voit ici la raison pour laquelle Controleur.update prends
    			un booléen en argument : on veut appeler $modele.gameOver
    			AVANT de que l'ordi joue et non APRES. */
    		}
    		update(loss);
    		return loss;
    	}
    	
    	public void actionPerformed(ActionEvent e){
    		Pair pos=decode(e.getActionCommand());
			if(((VueKL)vue).noSelected())
				((VueKL)vue).setSelected(pos);
			else{
				Pair depart=((VueKL)vue).removeSelected();
				if(joue(depart, pos))
					return;
			}
			((VueKL)vue).setEnabled();
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
		    KolorLines jeu=new KolorLines(Integer.parseInt(args[0]), false);
		}
	    });
    }
    
    protected static Color selectedColor=new Color(64, 64, 64);
}