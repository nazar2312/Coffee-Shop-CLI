public class Admin {

    private static final int password = 123;


    public static void addCoffeeToTheMenu(String n, double p){
        Coffee.menu.add(new Coffee(n, p));
    }

    public static void removeCoffeeFromTheMenu(int id){
        Coffee.menu.remove(id);
    }

    public static void changePrice(int id, double p){
        Coffee.menu.get(id).setPrice(p);
    }

    public static boolean passwordCheck(int p){
        return password == p;
    }

    public static void viewReceipts(){
       Receipt.printHistory();
    }

}
