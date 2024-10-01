import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.*;
import java.lang.RuntimeException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
abstract class Jeu{
	protected Modele modele;
	protected Vue vue;
	protected Controleur controleur;
	
	abstract class Modele extends Plateau{
		public abstract String getScore();
	    
	    public abstract boolean gameOver();
	    
	    public Colore[][] getBoard(){
	    	return board;
	    }
	    
	    public abstract Color vide();
	}
	
	class Vue extends JFrame{
		protected CaseVue[][] board;
		protected JLabel score=new JLabel();
		protected JPanel cases=new JPanel();
		
		public Vue(int n){
			/* Le conteneur principal a un GridBagLayout.
			L'objet qui contient les CaseVue a un GridLayout. */
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			board=new CaseVue[n][n];
			Container pane=getContentPane();
			Dimension d=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			JPanel top=new JPanel();
			
			/* Les ajustements sont là pour que les cases aient une forme à peu près
			carrée. Plus $n est grand, plus la forme s'allonge.*/
			JPanel ajustement1=new JPanel();
			JPanel ajustement2=new JPanel();

			top.setBorder(BorderFactory.createLineBorder(Colore.noir));
			top.add(score);
			pane.setPreferredSize(d);
			
			pane.setLayout(new GridBagLayout());
			GridBagConstraints cScore=new GridBagConstraints();
			GridBagConstraints cCases=new GridBagConstraints();
			GridBagConstraints cAjustement=new GridBagConstraints();
			
			cScore.weightx=1;
			cScore.gridy=0;
			cScore.fill=GridBagConstraints.HORIZONTAL;
			cScore.gridwidth=3;
			
			cCases.gridy=1;
			cCases.gridx=1;
			cCases.fill=GridBagConstraints.BOTH;
			cCases.weightx=1;
			cCases.weighty=1;
			
			cAjustement.weightx=0.7;
			cAjustement.weighty=1;
			cAjustement.gridy=1;
			cAjustement.gridx=0;
			cAjustement.fill=GridBagConstraints.VERTICAL;
			
			pane.add(top, cScore);
			pane.add(ajustement1, cAjustement);
			pane.add(cases, cCases);
			cAjustement.gridx=2;
			pane.add(ajustement2, cAjustement);

			cases.setLayout(new GridLayout(n, n));
			/* Dans la classe abstraite Jeu, on n'écrit pas le code
			qui remplit $cases de CaseVue, car le constructeur de ces
			CaseVue dépend du jeu auquel on joue. */
			
			pack();
			setVisible(true);
		}
		
		class CaseVue extends JButton{
			public CaseVue(Pair pos){
			setBackground(modele.vide());
			/* On doit transmettre au contrôleur les coordonnées de la case cliquée. */
			setActionCommand(pos.X()+" "+pos.Y());
			addActionListener(controleur);
			}
			
			public void setColor(Color c){
				setBackground(c);
			}
		}
		
		public void update(Colore[][] plateau, String points, boolean loss){
			score.setText(points);
			int len=plateau.length;
			for(int i=0; i<len; i++)
				for(int j=0; j<len; j++){
					board[i][j].setColor(plateau[i][j].getColor());
					if(loss)
						board[i][j].setEnabled(false);
				}
		}
	}
	
	abstract class Controleur implements ActionListener{
		protected Pair decode(String actionCommand){
			String[] cmd=actionCommand.split(" ");
    		return new Pair(Integer.parseInt(cmd[0]), Integer.parseInt(cmd[1]));
    	}
    	
    	/* Un String qui décrit le score de la partie à partir des données
    	du modèle, qui sont transmises dans un format un peu plus "brut". */
		public abstract String score();
		
		public void update(boolean loss){
			String gameOver="";
			if(loss)
				gameOver="Partie terminée!";
			vue.update(modele.getBoard(), score()+gameOver, loss);
		}
		
		public abstract void actionPerformed(ActionEvent e);
	}
}

class ArgumentTropPetitException extends RuntimeException{}
class PasDArgumentException extends RuntimeException{}