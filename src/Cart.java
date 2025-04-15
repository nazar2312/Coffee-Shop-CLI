import java.util.*;

public class Cart{


    public static List<Coffee> cart = new ArrayList<>();


    public static void showCart() {

        if(!cart.isEmpty()) {
            System.out.println("    Your cart: ");
            for (Coffee item : cart) {
                System.out.println(item.getName() + " " + item.getPrice() + "$");
            }
        }else System.out.println("Your cart is empty!");
    }
    public static double total(){
        double total = 0.0;

        for(Coffee c : cart){
            total += c.getPrice();
        }
        return total;
    }




}