//Francesco Scuriatti

package distributorebevande;

public class Bevanda{
    
    //ATTRIBUTI
    private String name;
    private double price;
    private int qProduct;
    private int porProduct;
    
    
    //COSTRUTTORE
    public Bevanda(String name, double price, int qProduct, int porProduct) {
        this.name = name;
        this.price = price;
        this.qProduct = qProduct;
        this.porProduct = porProduct;
    }

    @Override
    public String toString() {
        return "Bevanda{" + "name=" + name + ", price=" + price + ", qProduct=" + qProduct + ", porProduct=" + porProduct + '}';
    }
    
    //METODI
    
    //Getters

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getqProduct() {
        return qProduct;
    }

    public int getPorProduct() {
        return porProduct;
    }
    
    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setqProduct(int qProduct) {
        this.qProduct = qProduct;
    }

    public void setPorProduct(int porProduct) {
        this.porProduct = porProduct;
    }

}
