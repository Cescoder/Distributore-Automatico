//Francesco Scuriatti

package distributorebevande;

//Librerie matematiche
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Distributore {
    //ATTRIBUTI
    //Password nel caso di modifica
    private String password;
    
    //Conteggio delle bevande
    private final int maxDrinks = 10;
    private int drinksCount;
    
    //Quantità iniziali di monete in pz
    private int coin10;
    private int coin20;
    private int coin50;
    private int coin1;
    
    //Oggetto per input
    static Scanner input = new Scanner(System.in);
    
    //Vettore di oggetti bevanda
    Bevanda[] drink = new Bevanda[maxDrinks]; 
    
    
    //COSTRUTTORE
    public Distributore(String password) {
        //Inizializzazione variabile conta drink
        this.drinksCount = 0;
        
        //Pezzi per moneta
        this.coin10 = 20;
        this.coin20 = 10;
        this.coin50 = 5;
        this.coin1 = 2;
        
        //Imposta la password passata per parametro
        this.password = password;
    }
    
    //METODI
    
    public int menu(){ //DEVI RISOLVERE IL FATTO DELLA DISPONIBILITà DEI PRODOTTI
        int scelta;
        
        println("\nDISTRIBUTORE DI BEVANDE");
        println("Scegli cosa prendere:");
        
        for(int i = 0; i<drinksCount; i++){
            if(drink[i].getPorProduct()<= drink[i].getqProduct()){ //Verifica della disponibilità del prodotto
                println("\t"+drink[i].getName()+"["+(i)+"]");
            }
        }
            
        
        println("\tModifiche["+(drinksCount+1)+"]");
        println("\tEsci["+(drinksCount+2)+"]");
        
        do{
            print("\nScelta: ");
            scelta = input.nextInt();
            
        }while(scelta < 0 || scelta >drinksCount+2);
        
        return scelta;
        
    }
    
    //Metodo di gestione pagamento di un prodotto 
    public void product(Bevanda prodotto){ 
        if(prodotto.getqProduct() < prodotto.getPorProduct() ){
            println("Prodotto non disponibile!");
            return;
        }
        
        println("Prodotto disponibile!");
        
        pay(prodotto.getPrice());
        
        prodotto.setqProduct(prodotto.getqProduct()-prodotto.getPorProduct()); 
    }
    
    //Metodo di pagamento
    private void pay(double price){
        double money = 0;
        double insert;
        boolean coin;
        
        println("Importo dovuto: "+price+" euro");
        
        do{
            coin = true; //flag
            
            money = round(money, 1);
            print("Inserisci moneta(ancora "+(round(price-money, 2))+ " euro): ");
            
            insert = input.nextDouble();
            
            if(insert == 0.1)
                coin10++;
      
            else if(insert == 0.2)
                coin20++;
                
            else if(insert == 0.5)
                coin50++;
                
            else if(insert == 1)
                coin1++;
            
            else coin = false;
            
            if(coin)
                money += insert;
            
        }while(money < price);
        
        if(money>price)
            resto(round(money-price, 2));
    }
    
    //Metodo per restituire il resto
    private void resto(double change){
        println("\nRESTO: "+change);
        
        boolean noChange;
        int f;
        
        int[] changeMoney = {0, 0, 0, 0};
        double[] coin = {1, 0.5, 0.2, 0.1};
        
        do{
            f = 0;
            noChange = false;
            
            change = round(change,2);
            
            if(change/1 >= 1 && this.coin1>0) {
                change -= 1;
                changeMoney[0]++;
                coin1--;
                f = 1;
            }
            else if(change/0.5 >= 1 && this.coin50>0) {
                change -= 0.5;
                changeMoney[1]++;
                coin50--;
                f = 1;
            }
            else if(change/0.2 >= 1 && this.coin20>0) {
                change -= 0.2;
                changeMoney[2]++;
                coin20--;
                f = 1;
            }
            else if(change/0.1 >= 1 && this.coin10>0) {
                change -= 0.1;
                changeMoney[3]++;
                coin10--;
                f = 1;
            }
            
            else if(f==0){
                noChange = true;
            }
            
        }while(change>0 || !noChange);
        
        println("Resto in monete:");
        
        for(int i = 0; i<4; i++)
            if(changeMoney[i] != 0)
                println("\tMonete da "+coin[i]+" euro :"+changeMoney[i]);
            
    }
    
    //Metodo di impostazioni per modificare il distributore
    public void settings() {

        print("Inserire la password: ");
        
        if(!(input.next().equals(this.password))){
            println("Password errata");
            return;
        }
        
        int scelta;
        
        do{
            println("\nIMPOSTAZIONI: ");
            println("\tAggiungi bevanda[1]");
            println("\tRimuovi bevanda[2]");
            println("\tModifica quantita ad un prodotto[3]");
            println("\tModifica quantita di porzione di un prodotto[4]");
            println("\tAggiungere delle monete[5]");
            println("\tPrendere delle monete[6]");
            println("\tCambiare password[7]");
            println("\tEsci[8]");

            do{
                print("\nScelta: ");
                scelta = input.nextInt();
            }while(scelta < 1 || scelta>8);
            
            switch(scelta){
                case 1:
                    addDrink();
                    break;
                
                case 2:
                    removeDrink();
                    break;
                              
                case 3:
                    changeProductQuantity();
                    break;
                
                case 4:
                    changeProductPortion();
                    break;
                
                case 5:
                    putCoin();
                    break;
                    
                case 6:
                    getCoin();
                    break;
                
                case 7:
                    setPassword();
                    break;
            }
            
        }while(scelta != 8);
        
    }
    
    //Metodo per aggiungere una bevanda al distributore
    private void addDrink(){
        
        if(drinksCount>= maxDrinks-1){
            println("Non c'è posto per altre bevande...");
            return;
        }
        
        String name;
        double price;
        int qProduct;
        int porProduct;
        
        println("\nAGGIUNTA DI UN PRODOTTO!");
        
        //Input dei dati
        do{
            print("Inserire il nome del prodotto: ");
            name = input.next();
        }while(name.equals(null));
        
        do{
            print("Inserire il prezzo del prodotto "+name+": ");
            price = input.nextDouble();
        }while(price<0);
        
        do{
            print("Inserire la quantita iniziale del prodotto "+name+": ");
            qProduct = input.nextInt();
        }while(qProduct < 0);
        
        do{
            print("Inserire la porzionde del prodotto "+name+": ");
            porProduct = input.nextInt();
        }while(porProduct> qProduct);
        

        //Creazione dell'oggetto
        drink[drinksCount] = new Bevanda(name, price, qProduct, porProduct);
        
        println("Prodotto "+drink[drinksCount].getName()+" aggiunto!");
        
        //Incremento del conteggio
        drinksCount++;

    }
    
    //Metodo di rimozione bevanda
    private void removeDrink() {
        
        int choice;
    
        println("\nRIMOZIONE DI UN PRODOTTO");
        
        if(drinksCount == 0){
            println("Non e' presente alcun prodotto...");
            return;
        }
       
        println("Quale bevanda vuoi rimuovere?");
        
        for(int i = 0; i<drinksCount; i++)
            println("\t"+drink[i].getName()+"["+(i)+"]");
        
        println("\tEsci["+drinksCount+"]");
        
        do{
            print("Svelta: ");
            choice = input.nextInt();
        }while(choice<0 || choice>drinksCount);
        
        if(choice == drinksCount)
            return;
        
        println("Prodotto "+drink[choice].getName()+" rimosso!");
        drink[choice] = null;
        
        //Shift degli oggetti per compattare l'elenco
        for(int i = choice; i<drinksCount-1; i++)
            drink[i] = drink[i+1];
        
        drink[drinksCount] = null;
        
        //Decremento del conteggio
        this.drinksCount--;
        
    }
    
    //Metodo di modifica quantità di un prodotto
    private void changeProductQuantity() {
        int choice;
        int qProduct;
        
        println("CAMBIO QUANTITà DI PRODOTTO");
        
        if(drinksCount == 0){
            println("Non e' presente alcun prodotto...");
            return;
        }
        
        println("Di quale bevanda vuoi cambiare la quantità?");
        
        for(int i = 0; i<drinksCount; i++)
            println("\t"+drink[i].getName()+"["+(i)+"]");
        
        println("\tEsci["+drinksCount+"]");
        
        do{
            print("Svelta: ");
            choice = input.nextInt();
        }while(choice<0 || choice>drinksCount);
        
        if(choice == drinksCount)
            return;
        
        do{
            print("Nuova quantità (min "+drink[choice].getPorProduct()+"): ");
            qProduct = input.nextInt();
        }while(qProduct<drink[choice].getPorProduct());
        
        drink[choice].setqProduct(qProduct);
        
        println("Modifica effettuata!");
        
    }
    
    //Metodo di modifica quantitò per porzione di vendita
    private void changeProductPortion() {
        int choice;
        int porProduct;
        
        println("CAMBIO QUANTITA DI PORZIONE PRODOTTO");
        
        if(drinksCount == 0){
            println("Non e' presente alcun prodotto...");
            return;
        }
        
        println("Di quale bevanda vuoi cambiare la porzione?");
        
        for(int i = 0; i<drinksCount; i++)
            println("\t"+drink[i].getName()+"["+(i)+"]");
        
        println("\tEsci["+drinksCount+"]");
        
        do{
            print("Svelta: ");
            choice = input.nextInt();
        }while(choice<0 || choice>drinksCount);
        
        if(choice == drinksCount)
            return;
        
        do{
            print("Nuova quantità di porzione (max "+drink[choice].getqProduct()+"): ");
            porProduct = input.nextInt();
        }while(porProduct<drink[choice].getPorProduct());
        
        drink[choice].setPorProduct(porProduct);
        
        println("Modifica effettuata!");
    }

    //Metodo per l'inserimento di monete all'interno del distributore
    private void putCoin(){
        int choice, pz;
        
        
        String[] names = {"10 centesimi", "20 centesimi", "50 centesimi", "1 euro" };
        int[] pezzi = {this.coin10, this.coin20, this.coin50, this.coin1};
        
        do{
            println("Quali pezzi vuoi aggiungere?");
            for(int i = 0; i<4; i++)
               println("\tMonete da "+names[i]+" ["+i+"]: "+pezzi[i]);

            println("Esci[5]");

            do{
                print("Svelta: ");
                choice = input.nextInt();
            }while(choice<0 || choice>5);

            if(choice == 5)
                return;

            do{
                print("N pezzi da aggiungere: ");
                pz = input.nextInt();
            }while(choice<1);

            switch(choice){

                case 0:
                    this.coin10 += pz;
                    break;

                case 1:
                    this.coin20 += pz;
                    break;

                case 2:
                    this.coin50 += pz;
                    break;

                case 3:
                    this.coin1 += pz;
                    break;
            }

            println("Pezzi aggiunti!");
        }while(true);
    }
    
    //Metodo per il ritiro di denaro
    private void getCoin() {
        double money;
        boolean noChange;
        int f;
        
        println("RITIRO DI DENARO");
        
        do{
            print("Inserire somma di denaro da ritirare (max "+sumCoin()+"): ");
            money = input.nextDouble();
        }while(money%0.1 == 0);
        
        int[] changeMoney = {0, 0, 0, 0};
        double[] coin = {1, 0.5, 0.2, 0.1};
        
        do{
            f = 0;
            noChange = false;
            
            money = round(money,2);
            
            if(money/1 >= 1 && this.coin1>0) {
                money -= 1;
                changeMoney[0]++;
                coin1--;
                f = 1;
            }
            else if(money/0.5 >= 1 && this.coin50>0) {
                money -= 0.5;
                changeMoney[1]++;
                coin50--;
                f = 1;
            }
            else if(money/0.2 >= 1 && this.coin20>0) {
                money -= 0.2;
                changeMoney[2]++;
                coin20--;
                f = 1;
            }
            else if(money/0.1 >= 1 && this.coin10>0) {
                money -= 0.1;
                changeMoney[3]++;
                coin10--;
                f = 1;
            }
            
            else if (f == 0){
                noChange = true;
            }
            
        }while(money>0 || !noChange);
        
        println("Resto in monete:");
        
        for(int i = 0; i<4; i++)
            if(changeMoney[i] != 0)
                println("\tMonete da "+coin[i]+" euro :"+changeMoney[i]);
    }
    
    //Metodo per la modifica della password
    private void setPassword() {
        String password;
        do{
            print("Inserire nuova password: ");
            password = input.next();
        }while(password.equals(null) || password.equals(this.password));
        
        this.password = password;
    }
    
    
    
    //Getters
    public String getPassword() {
        return password;
    }

    public int getMaxDrinks() {
        return maxDrinks;
    }

    public int getDrinksCount() {
        return drinksCount;
    }

    public int getCoin10() {
        return coin10;
    }

    public int getCoin20() {
        return coin20;
    }

    public int getCoin50() {
        return coin50;
    }

    public int getCoin1() {
        return coin1;
    }

    public static Scanner getInput() {
        return input;
    }

    public Bevanda[] getDrink() {
        return drink;
    }
    
    //Setters
    public void setDrinksCount(int drinksCount) {
        this.drinksCount = drinksCount;
    }

    public void setCoin10(int coin10) {
        this.coin10 = coin10;
    }

    public void setCoin20(int coin20) {
        this.coin20 = coin20;
    }

    public void setCoin50(int coin50) {
        this.coin50 = coin50;
    }

    public void setCoin1(int coin1) {
        this.coin1 = coin1;
    }

    //Metodi di stampa
    public static void println(String string){
        System.out.println(string);
    }
    
    public static void print(String string){
        System.out.print(string);
    }
    
    //Metodo per arrotondare i double
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    //Metodo per la somma delle monete
    private double sumCoin(){
        return round(coin1+coin10+coin20+coin50, 2);
    }

}
