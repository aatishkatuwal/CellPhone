public class CellPhone
{
    // Fields
    private String model;
    private String manufacturer;    // Manufacturer
    private double retailPrice; // Retail price

    public CellPhone(String m, String man, double price)
    {
        setModel(m);
        setManufacturer(man);
        setRetailPrice(price);
    }
    public CellPhone()
    {
        this("","",0.0);
    }

    public void setModel(String m)   {
        model = m;
    }
    public void setManufacturer(String man)
    {
        manufacturer = man;
    }

    public void setRetailPrice(double price)
    {
        retailPrice = price;
    }

    public String getModel(){return model;}
    public String getManufacturer()
    {
        return manufacturer;
    }
    public double getRetailPrice()
    {
        return retailPrice;
    }

    @Override
    public String toString(){
        return String.format("Model: %-20sManufacturer: %-20sretail price: %10.2f%n",getModel(), getManufacturer(),
                getRetailPrice());

    }
}
