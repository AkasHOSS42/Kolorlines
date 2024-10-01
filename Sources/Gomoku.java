import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.*;
import java.lang.RuntimeException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
class Gomoku extends Jeu{
	public Gomoku(int n, boolean insanity){
		modele=new ModeleGomoku(n, insanity);
		controleur=new ControleurGomoku();
		vue=new VueGomoku(n);
		controleur.update(false);
	}
	
	class ModeleGomoku extends Modele{
		class Joueur{
			private int score=0;
			private Color couleur;
			private String nom;
			
			/* Vrai si le joueur est un robot stupide. */
			private boolean cpu;
			
			/* Le programme risque de mal fonctionner si on 
			rentre un nom de joueur contenant des caractères ' ' ou '-'. */
			public Joueur(Color c, String s, boolean b){
				nom=s;
				cpu=b;
				couleur=c;
			}
			
			public void setScore(int n){score=n;}
			public int getScore(){return score;}
			public Color getColor(){return couleur;}
			public boolean getCPU(){return cpu;}
			public String getNom(){return nom;}
			/* Avec une telle implémentation de Joueur, on peut
			facilement créer des variantes du Gomoku avec un nom, une couleur
			ou un nombre de joueurs différents (humains ou non). Il suffira de changer le code du
			constructeur. */
		}
		
		/* On pourrait décider que $joueurs soit une liste circulaire.
		Si cela avait été le cas, cela aurait posé un problème au niveau de l'affichage
		du score. Chaque tour, l'ordre des joueurs dont on affiche le score
		changerait, ce qui peut être un peu perturbant pour les utilisateurs.
		Je préfère donc me servir d'un tableau. */
		private Joueur[] joueurs;
		
		/* Le joueur dont c'est le tour. */
		private int index=0;
		
		public ModeleGomoku(int n, boolean insanity){
			joueurs=new Joueur[]{new Joueur(Colore.noir, "Noir", false), new Joueur(Colore.blanc, "Blanc", insanity)};
			board=new Case[n][n];
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++)
				board[i][j]=new CaseGomoku(new Pair(i, j));
			for(int i=0; i<n; i++)
				for(int j=0; j<n; j++){
				board[i][j].setVoisins(voisins(new Pair(i, j)));
				casesVides.add(new Pair(i, j));
				}
		}
		
		/* Retourne une chaîne de caractères contenant
		les scores de tous les joueurs. */
		public String getScore(){
			String ans1="";
			String ans2="";
			for(int i=0; i<joueurs.length-1; i++){
				ans1+=joueurs[i].getNom()+" ";
				ans2+=joueurs[i].getScore()+" ";
			}
			return ans1+joueurs[joueurs.length-1].getNom()+"-"+ans2+joueurs[joueurs.length-1].getScore();
		}
		
		public boolean gameOver(){
			return casesVides.size()==0;
		}
		
		public Color vide(){
			return CaseGomoku.vide;
		}
		
		public boolean joue(Pair p){
			Color c=joueurs[index].getColor();
			if(add(p, c)){
				updateScore();
				index=(index+1)%joueurs.length;
				if(!gameOver())
					goInsane();
				return true;
			}
			return false;
		}
		
		/* Le robot stupide joue un tour. */
		public void goInsane(){
			if(joueurs[index].getCPU()==false)
				return;
			Color vide=vide();
			Color c=joueurs[index].getColor();
			boolean exit=false;
			Pair p=null;
			for(int i=0; !exit&&i<board.length; i++)
				for(int j=0; !exit&&j<board.length; j++)
					if(board[i][j].getColor()==vide){
						p=new Pair(i, j);
						exit=true;
					}
			add(p, c);
			updateScore();
			index=(index+1)%joueurs.length;
			if(!gameOver())
				goInsane();
		}
		
		/* On se contente de modifier le score du joueur qui vient de jouer. */
		private void updateScore(){
			Joueur j=joueurs[index];
			LinkedList<Alignement> list=Alignable.alignements(board, 5);
			Color c=j.getColor();
			int newScore=0;
			for(Alignement a : list)
				if(c==couleurDe(a))
					newScore+=a.length()/5;
			j.setScore(newScore);
		}
		
		private Color couleurDe(Alignement a){
			return trouve(a.getDep()).getColor();
		}
	}
	
	class VueGomoku extends Vue{
		public VueGomoku(int n){
			super(n);
			CaseVueGomoku c;
			for(int i=0; i<n; i++)
			for(int j=0; j<n; j++){
				c=new CaseVueGomoku(new Pair(i, j));
				board[i][j]=c;
				cases.add(c);
			}
		}
		
		class CaseVueGomoku extends CaseVue{
			public CaseVueGomoku(Pair pos){
				super(pos);
			}
			
			/* Contrairement à KolorLines, comme les coups se font en 1 et non
			2 étapes, on n'a pas besoin d'écrire des méthodes setEnabled,
			setSelected, etc... */
			public void setColor(Color c){
				super.setColor(c);
				if(c==modele.vide())
					setEnabled(true);
				else
					setEnabled(false);
			}
		}
	}
	
	class ControleurGomoku extends Controleur{
		public String score(){
			String[] t =modele.getScore().split("-");
			String[] noms=t[0].split(" ");
			String[] points=t[1].split(" ");
			String ans="";
			for(int i=0; i<noms.length; i++)
				ans+=noms[i]+" : "+points[i]+" points. ";
			return ans;
		}
		
		public void actionPerformed(ActionEvent e){
			Pair pos=decode(e.getActionCommand());
			if(((ModeleGomoku)modele).joue(pos))
				update(modele.gameOver());
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
		    Gomoku jeu=new Gomoku(Integer.parseInt(args[0]), false);
		}
	    });
    }
}