package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche; 
	

	public Village(String nom, int nbVillageoisMaximum , int nbEtalsMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtalsMarche);
	}
   public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	//TODO reprendre les bonnes pratiques de la classe interne
	private  static class Marche {	
		private Etal[]etals;
		
		
		private Marche(int nbEtals) {
			this.etals= new Etal[nbEtals];
			for( int i = 0 ; i < nbEtals ; i ++) {
				etals[i]= new Etal();
			}
		}
		
		private void utiliserEtal ( int indiceEtal, Gaulois vendeur , String produit , int nbProduit ) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtallibre() {
			for (int i= 0 ; i < etals.length ; i ++ ) {
				if ( !etals[i].isEtalOccupe()) {
					return i ; 
				}		  
			}
			return -1;
			}
		
		
		private Etal[] trouverEtals(String produit ) {
			int nbProduit = 0 ;
			for( int i = 0 ; i < etals.length ; i++ ) {
				Etal etal= etals[i];
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					nbProduit++;	
			}
		}
		Etal [] tab = new Etal[nbProduit];
		int compteur = 0 ;
		for ( Etal etal : etals) {
			if ( etal.isEtalOccupe() && etal.contientProduit(produit)){
				tab[compteur++] = etal ;
			}
		}
		return tab ;
		}
				
		
		
		private Etal trouverVendeur( Gaulois gaulois) {
			for(Etal etal : etals) {
				Gaulois vendeur = etal.getVendeur();
				if( etal.isEtalOccupe() && vendeur != null && vendeur.equals(gaulois)) {
					return etal;
				}
			}
			return null;
		}
		private  String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
		    int nbEtalVide = 0 ;  
			for ( Etal etal : etals) {
				if(etal.isEtalOccupe()) {
					chaine.append(etal.afficherEtal());	
				}else { 
					nbEtalVide++;
				} }if ( nbEtalVide > 0 ) {		
				chaine.append("Il reste " +nbEtalVide + " etals non utilisées dans le marché \n");
			}
			return chaine.toString();
		}
	}
		


	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef ==  null  ) {
			throw new  VillageSansChefException("le village doit avoir un chef \n");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef ");
			chaine.append( chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if ( etalsProduit.length == 0) {
			chaine.append("pas de produit");
		}else if ( etalsProduit.length == 1 ) {
			chaine.append("seul le vendeur" + etalsProduit[0].getVendeur().getNom() + " propose " + produit + "au marche \n");
		} else {
			chaine.append(" les vendeurs qui proposent" + produit + " soont : \n");
			for ( Etal etal : etalsProduit) {
				chaine.append("-" + etal.getVendeur().getNom() + "\n");
			}
		}
        return chaine.toString();
	}
	
	
	public String installerVendeur ( Gaulois vendeur , String produit , int nbProduit ) {
		StringBuilder chaine = new StringBuilder();
		chaine.append( vendeur.getNom() + " cherche un endroit ou vendre " +nbProduit+ "" + produit + "\n");
		int indiceEtal = marche.trouverEtallibre();
		if (indiceEtal != -1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append(" le vendeur"  + vendeur.getNom() +" vend des" + produit+ " a l etal num " + ( indiceEtal +1) + "\n");			
		}
		return chaine.toString();
	}
	
	public String partirVendeur (Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		if(etal != null) {	
			return etal.libererEtal();
		}return null;
	}
	
	
	
	// ajouté a la fin 
		public Etal rechercherEtal(Gaulois vendeur) {
		    return marche.trouverVendeur(vendeur); 
		}
		
		
	public String afficherMarche() {
		return marche.afficherMarche(); 
	}
	
	
}
