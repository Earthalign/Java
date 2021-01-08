package wyciskarka;

import wyciskarka.owoce.Ananas;
import wyciskarka.owoce.Jablko;
import wyciskarka.owoce.Owoc;

public class Wyciskarka {

    
    public static void main(String[] args) {
        
        Owoc o = new Owoc(10, 20);
        System.out.println(o.iloscSoku);
        Ananas a = new Ananas(30, 60);
        System.out.println(a.iloscSoku);
        Jablko j = new Jablko();
        
        
        var ok = "Bardzo dobrze";
        System.out.println(ok);
        
        
    }
    
}
