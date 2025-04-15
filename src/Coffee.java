import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Coffee {

    private String name;
    private double price;

    //Default menu;
    public static ArrayList<Coffee> menu = new ArrayList<>();

    //Constructor method
    public Coffee(String n, double p){
        this.name = n;
        this.price = p;
    }
    public static Coffee getObject(int id){
        return menu.get(id);
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public static void showAllCoffees(){
        int i = 0;
        for(Coffee e : menu){
            System.out.println("|"+ i + "| " + e.toString());
            i++;
        }
    }

    public String toString(){
        return "[NAME: " + getName() + ", PRICE: " + getPrice()+ "$" + " ]";
    }







}
