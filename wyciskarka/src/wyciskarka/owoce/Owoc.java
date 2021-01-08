
package wyciskarka.owoce;


public class Owoc {
   public  double iloscSoku;
   public  double gramatura;
    
    
    
    public Owoc()
{
    System.out.println("Domyslny konstruktor z klasy Owoc");
}
    public Owoc(double iloscSoku, double gramatura)
{
    this.iloscSoku = iloscSoku;
    this.gramatura = gramatura;
    System.out.println("Niedomyslny konstruktor klasa Owoc ");
}

    
    
}


