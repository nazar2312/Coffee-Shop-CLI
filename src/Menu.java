
import java.util.*;

public class Menu {

    public static void main(String...args){

        Scanner in = new Scanner(System.in);

        int mode = 0;
        do{
            System.out.println("\n \n \n ");
            System.out.println(" _____________________________________________________");
            System.out.println("|          WELCOME TO THE COFFEE SHOP                 |");
            System.out.println("|      SELECT MODE: |1| CUSTOMER , |2|ADMIN             |");
            System.out.println(" _____________________________________________________");
            System.out.print("enter:");

            try{
                mode = in.nextInt();
            }catch(InputMismatchException e){
                in.nextLine();
                System.out.println("try again...");
            }


            switch(mode){
                //Customer mode:
                case 1:
                    System.out.println("\n");
                    System.out.println(" _____________________________________________________");
                    System.out.println("|                PLEASE SELECT COFFEE               | ");
                    System.out.println(" _____________________________________________________");
                    Coffee.showAllCoffees();
                    Coffee item;
                    System.out.println(" _____________________________________________________");
                    System.out.print("enter:");
                    try {
                        item = Coffee.getObject(in.nextInt());
                        System.out.println("\n");
                        System.out.println(" _____________________________________________________");
                        System.out.println("        YOU'VE SELECTED: '" + item.getName() + ", " + item.getPrice() +"$ '" );
                        System.out.println(" _____________________________________________________");

                    }catch (IndexOutOfBoundsException e){
                        System.out.print("Select coffee from existing! Try again: ");
                        item = Coffee.getObject(in.nextInt());
                        System.out.println(" _____________________________________________________");
                        System.out.println("        YOU'VE SELECTED: '" + item.getName() + ", " + item.getPrice() +"$ '" );
                    }

                    System.out.println("SELECT PAYMENT METHOD: \n|1|VISA |2| MASTERCARD |3|CASH |4|CANCEL");
                    System.out.println(" _____________________________________________________");
                    System.out.print("enter: ");
                    int type = in.nextInt();

                    if(type < 1 || type > 4){
                        System.out.println("Please enter correct option!");
                        type = in.nextInt();
                        if(type < 1 || type > 4){
                            System.out.println("Something went wrong...\nTransaction canceled.");
                            break;
                        }
                    }

                    switch (type){

                        case 1:
                            Receipt.addToHistory(new Receipt(item, item.getPrice(), "VISA",  new Date()));
                            break;

                        case 2:
                            Receipt.addToHistory(new Receipt(item, item.getPrice(), "MASTERCARD",  new Date()));
                            break;


                        case 3:
                            System.out.print("|PLEASE ENTER AMOUNT: ");
                            double amount = in.nextDouble();

                            if(amount < item.getPrice()){
                                System.out.println("Please enter right amount! Product price is: " + item.getPrice());
                                System.out.print("enter: ");
                                amount = in.nextDouble();

                                if(amount < item.getPrice()){
                                    System.out.println("Something went wrong...");
                                    break;
                                }
                                break;
                            }
                            //Calculating change and round it to 2 decimal places;
                            double change = amount - item.getPrice();
                            change = Double.parseDouble(String.format("%.2f", change));

                            Receipt.addToHistory(new Receipt(item, item.getPrice(),"CASH", amount, change,  new Date()));
                            break;

                        case 4:
                            System.out.println("Transaction canceled. ");
                            break;
                    }
                    break;


                    //Admin mode:
                case 2:
                    System.out.print("Please enter password: ");
                    int pass = in.nextInt();
                    boolean access = false;
                    int attempt = 0;

                    //Simple authentication to admin panel;
                    if(Admin.passwordCheck(pass)){
                        access = true;
                    }else do{
                        attempt++;
                        System.out.print("Incorrect password! Please enter correct password: ");
                        pass = in.nextInt();
                        access = Admin.passwordCheck(pass);


                    }while(!access && attempt < 3);

                    if(attempt >= 3) {
                        System.out.println("Authentication failed...");
                        break;
                    }


                    //Admin panel;
                    System.out.println(" _____________________________________________________");
                    System.out.println(
                                    """
                                                WELCOME TO ADMIN PANEL
                                               SELECT OPTION FROM BELOW
                                    |1| ADD ITEM |2| REMOVE ITEM |3| CHANGE PRICE |4| VIEW TRANSACTION LIST
                                    |5| LOGOUT
                                    """);
                    System.out.println(" _____________________________________________________");

                    int option = in.nextInt();
                    switch(option){

                        case 1:

                            System.out.println("Please enter item you'd like to add in the menu: [name _ price] ");
                            String n = in.next();
                            double p = in.nextDouble();
                            Admin.addCoffeeToTheMenu(n, p);
                            System.out.println("You've added new item :" + n + ", " + p);
                            break;

                        case 2:

                            System.out.println("Please enter id of item to remove: ");
                            int id;

                            try {
                                id = in.nextInt();
                                Admin.removeCoffeeFromTheMenu(id);
                            }catch(Exception e){
                                System.out.println("Please enter existing id! ");
                                id = in.nextInt();
                                if(id > Coffee.menu.size())
                                    System.out.println("Something went wrong...");
                                break;
                            }

                            System.out.println("Item: " + Coffee.menu.get(id).getName() + " - removed");
                            break;


                        case 3:
                            System.out.println("Please select item and enter new price [id _ new price]");
                            Coffee.showAllCoffees();
                            int Id = in.nextInt();
                            double newP = in.nextDouble();

                            try {
                                Admin.changePrice(Id, newP);
                            }catch (IndexOutOfBoundsException e){
                                System.out.println("ENTER CORRECT VALUES!");
                            }
                            Id = in.nextInt();
                            newP = in.nextDouble();
                            Admin.changePrice(Id, newP);

                            System.out.println("Item: '" + Coffee.menu.get(Id).getName() + "' has new price = " + newP + "$" );
                            break;


                        case 4:
                            Admin.viewReceipts();
                            break;
                        case 5:
                            break;
                    }




            }
        }while(true);

    }

}
