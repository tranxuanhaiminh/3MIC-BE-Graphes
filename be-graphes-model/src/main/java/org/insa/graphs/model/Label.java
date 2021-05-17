package org.insa.graphs.model;

public final class Label implements Comparable<Label> {
	
	//Sommet associé à ce label (sommet ou numéro de sommet).
	private int sommet;
	
	//Booléen, vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	private boolean marque;
	
	//Valeur courante du plus court chemin depuis l'origine vers le sommet
	private double cout;
	
	//Correspond au sommet précédent sur le chemin correspondant au plus court chemin courant
	private Arc pere;
	
	//Creer un Label et l'associer a un noeud
	public Label(int sommet, boolean marque, double cout, Arc pere) {
		this.sommet = sommet;
		this.marque = marque;
		this.cout = cout;
		this.pere = pere;
	}
	
	//Renvoie le sommet
	public int getSommet() {
		return this.sommet;
	}
	
	//Renvoie le coût de ce label
	public double getCost() {
		return this.cout;
	}
	
	//Renvoie le marque de ce label
	public boolean getMarque() {
		return this.marque;
	}
	
	//Renvoie le node precedent sur le chemin correspondant au plus court chemin courant
	public Arc getPere() {
		return this.pere;
	}
	
	//Remarquer le marque de ce label
	public void setMarque(boolean marque) {
		this.marque = marque;
	}
	
	//Mettre a jour le cout du node associe a ce label
	public void setCost(double cost) {
		this.cout = cost;
	}
	
	//Mettre a jour le sommet precedent sur le chemin correspondant au plus court chemin courant
	public void setPere(Arc pere) {
		this.pere = pere;
	}
	
	//Method pour comparer les labels
	public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other instanceof Label) {
            return getCost() == ((Label) other).getCost();
        }
        return false;
    }
	
	public int compareTo(Label other) {
        return Double.compare(getCost(), other.getCost());
    }
}
