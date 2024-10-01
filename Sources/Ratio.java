class Ratio{
    private int numera, denomi;
    public Ratio(int numera, int denomi){
	this.numera=numera;
	this.denomi=denomi;
    }

    public Ratio(int n){
	this(n, 1);
    }

    public static Ratio produit(Ratio a, Ratio b){
	return new Ratio(b.numera*a.numera, b.denomi*a.denomi);
    }

    public static Ratio somme(Ratio a, Ratio b){
	return new Ratio(b.numera*a.denomi+b.denomi*a.numera, b.denomi*a.denomi);
    }

    public static Ratio quotient(Ratio dividende, Ratio diviseur){
	return produit(dividende, new Ratio(diviseur.denomi, diviseur.numera));
    }

    public boolean equals(Ratio a){
	return (denomi*a.numera==numera*a.denomi);
    }

    public boolean plusGrand(Ratio a){
	return (a.denomi*numera>=a.numera*denomi);
    }

    public boolean plusGrand(int a){
	return plusGrand(new Ratio(a));
    }

    public Ratio oppose(){
	return new Ratio(-numera, denomi);
    }
    
    public String toString(){
    	return numera+"/"+denomi;
    }
}
