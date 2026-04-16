public static void main(String[] args) {
    Etal etal = new Etal();
    System.out.println(etal.libererEtal());

    try {
        System.out.println(etal.acheterProduit(3, null));
    } catch (NullPointerException e) {
        e.printStackTrace();
    }
    
    try {
        Gaulois acheteur = new Gaulois("Asterix");
        System.out.println(etal.acheterProduit(-2, acheteur));
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    }

    
  
    try {
        Gaulois acheteur = new Gaulois("Asterix");
        System.out.println(etal.acheterProduit(3, acheteur));
    } catch (IllegalStateException e) {
        e.printStackTrace();
    }

    System.out.println("Fin du test");
}