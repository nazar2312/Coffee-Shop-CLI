import java.io.*;
import java.util.Scanner;

public class Admin {

    private static final int password = 123;
    private static final String fileName = "src/data/menu.txt";

    //METHOD THAT READS FILE MENU.txt AND REWRITES IT OT ARRAYLIST IF IT'S DIFFERENT;
    public static void updateMenu(){

        //FIND OUT WHAT IS THE LAST LINE(ITEM) IN THE MENU.TXT
        String lineToCompare = null;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName)) ){
            String curr;
            while((curr = br.readLine()) != null){
                lineToCompare = curr;
            }

        }catch(IOException e){
            System.out.println("something went wrong while reaching eng of file");
        }
        //WRITE EVERY ITEM FROM MENU.TXT TO MENU LIST IN CASE IT'S EMPTY.
        if(Coffee.menu.isEmpty()){
            try (Scanner sc2 = new Scanner(new File(fileName))) {
                while (sc2.hasNext()) {
                    String name = sc2.next();
                    if (sc2.hasNextDouble()) {
                        double price = sc2.nextDouble();
                        Coffee.menu.add(new Coffee(name, price));
                    }
                }
            } catch (IOException e) {
                System.out.println("Something went wrong while reading file...");
            }
        }

        //COMPARING LAST ELEMENT IN THE LIST AND FILE.
        String currentMenuLast = Coffee.menu.getLast().getName() + " " + Coffee.menu.getLast().getPrice();
        if(!currentMenuLast.equals(lineToCompare)){
            try (Scanner sc3 = new Scanner(new File(fileName))) {
                Coffee.menu.clear(); //DELETE OUTDATED MENU AND UPDATE IT FROM THE FILE;
                while (sc3.hasNext()) {
                    String name = sc3.next();
                    if (sc3.hasNextDouble()) {
                        double price = sc3.nextDouble();
                        Coffee.menu.add(new Coffee(name, price));
                    }
                }
            } catch (IOException e) {
                System.out.println("Something went wrong while reading file...");
            }
        }
    }


    public static void addCoffeeToTheMenu(String n, double p){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true)) ){
            bw.write("\n" + n + " " + p);
        }catch(IOException e){
            System.out.println("something went wrong while adding new coffee...");
        }
    }

    public static void removeCoffeeFromTheMenu(String itemToDelete){
        //First remove item from current menu;
        Coffee.menu.removeIf(e -> e.getName().equals(itemToDelete));

        //Second, rewrite updated menu to a file;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for(Coffee e : Coffee.menu) {
                bw.write(e.getName() + " " + e.getPrice() + "\n");
            }
        } catch (IOException ex) {
            System.out.println("something went wrong while deleting item...");
        }

    }

    public static void changePrice(int id, double newPrice){
        Coffee.menu.get(id).setPrice(newPrice);//UPDATE PRICE FOR THE CURRENT MENU LIST;

        //Rewrite updated menu to a file;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for(Coffee e : Coffee.menu) {
                bw.write(e.getName() + " " + e.getPrice() + "\n");
            }
        } catch (IOException ex) {
            System.out.println("something went wrong while deleting item...");
        }

    }

    public static boolean passwordCheck(int p){
        return password == p;
    }

    public static void viewReceipts(){
       Receipt.printHistory();
    }

}
