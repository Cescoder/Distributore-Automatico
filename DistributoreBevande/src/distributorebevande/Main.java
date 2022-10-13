//Francesco Scuriatti

package distributorebevande;

import java.util.Scanner;

public class Main {
    
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String password;
        
        //Impostazione della password
        do{
            print("Imposta la password del distributore: ");
            password = input.next();
        }while(password.equals(null));
        
        //Creazione dell'oggetto
        Distributore d = new Distributore(password);
        
        int choice;
        
        //Menu di scelta
        do{
            choice = d.menu();
            
            if(choice <d.getDrinksCount()+1){
                d.getDrink()[choice].toString();
                println(d.getDrink()[choice].getName()+" selezionato!");
                d.product(d.getDrink()[choice]);
                d.getDrink()[choice].toString();
            }
            else if(choice == d.getDrinksCount()+1)
                d.settings();
            
        }while(choice != d.getDrinksCount()+2);
        
        
    }
    
    //Metodi di stampa
    public static void println(String string){
        System.out.println(string);
    }
    
    public static void print(String string){
        System.out.println(string);
    }
    
}
