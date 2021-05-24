package org.insa.graphs.model;

public class LabelStar extends Label{
	
	//Le cout total
	private double couttotal;
	
	private Point p1 = this.getPere().getPoints().get(0);
	private Point p2 = this.getPere().getPoints().get(1);
	
	public LabelStar(int sommet, boolean marque, double cout, Arc pere) {
		super(sommet, marque, cout, pere);
	}
	
	//Redefinir le method getTotalCost()
	public double getTotalCost() {
		return this.couttotal;
	}
	
	//Mettre a jour le cout total
	public void setCost(int cout) {
		this.cout = cout;
		this.couttotal = 
				
				 	 cout + Point.distance(p1, p2);
	}
	
}
