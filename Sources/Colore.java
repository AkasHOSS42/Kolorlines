import java.awt.Color;
/* Un objet coloré dont la couleur est comparable
à celle d'autres Colore. On en profite pour définir quelques
constantes de types Color. */
abstract class Colore implements Alignable{
    protected Color couleur;

    public Colore(int r, int g, int b){
	couleur=new Color(r, g, b);
    }
    
    public Colore(Color c){
    	couleur=c;
    }

    public Color getColor(){
	return couleur;
    }

    public void setColor(Color c){
	couleur=c;
    }

    public boolean memeCouleurQue(Colore c){
    	if(c==null)
    		return false;
	if(couleur==arcEnCiel||c.couleur==arcEnCiel)
	    return true;
	return couleur.equals(c.couleur);
    }

    public boolean memeCouleurQue(Color c){
	return couleur==arcEnCiel||c==arcEnCiel||couleur.equals(c);
    }

    public static final Color noir=new Color(0, 0, 0);
    public static final Color blanc=new Color(255, 255, 255);
    public static final Color rouge=new Color(255, 0, 0);
    public static final Color vert=new Color(0, 255, 0);
    public static final Color bleu=new Color(0, 0, 255);
    public static final Color jaune=new Color(255, 255, 0);
    public static final Color marron=new Color(128, 80, 0);
    public static final Color arcEnCiel=new Color(0, 0, 0);
}
