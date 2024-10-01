import javax.swing.*;
/* La classe pour faire jouer le robot stupide. */
class Insanity{
	public static void main(String[] args){
	if(args==null||args.length<2)
	    throw new PasDArgumentException();
	int n=Integer.parseInt(args[0]);
	if(n<5)
	    throw new ArgumentTropPetitException();
	javax.swing.SwingUtilities.invokeLater(new Runnable(){
		public void run(){
			Jeu jeu;
			switch(Integer.parseInt(args[1])){
			case 0 :
				jeu=new Gomoku(Integer.parseInt(args[0]), true);
				break;
			case 1 :
				jeu=new KolorLines(Integer.parseInt(args[0]), true);
				break;
			case 2 :
				jeu=new KolorLinesPlus(Integer.parseInt(args[0]), true);
				break;
			default :
				throw new PasDArgumentException();
			}
		}
	    });
    }
}