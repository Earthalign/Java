/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wyciskarka.owoce;

/**
 *
 * @author Earthalign
 */
public class Ananas extends Owoc {
    
    public Ananas()
    {
        System.out.println("Domyślny konstruktor z klasy ANANAS ");
    }
    public Ananas(double iloscSoku, double gramatura)
    {
        super(iloscSoku, gramatura);
    }
    
}
