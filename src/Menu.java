
import java.util.*;

public class Menu {

    public static void main(String...args){
        Scanner in = new Scanner(System.in);
        int mode = 0;
        do{

            //UPDATE MENU EVERY TIME PROGRAM IS STARTED;
            Admin.updateMenu();


            System.out.println(
                    "\n\n\n_____________________________________________________\n" +
                    "|          WELCOME TO THE COFFEE SHOP                 |\n" +
                    "|      SELECT MODE: |1| CUSTOMER , |2|ADMIN           |\n" +
                    " _____________________________________________________\n"+
                    "enter:"
            );

            try{
                mode = in.nextInt();
            }catch(InputMismatchException e){
                in.nextLine();
                System.out.println("try again...");
            }


            switch(mode){
                //Customer mode:
                case 1:
                    System.out.println(
                    " _____________________________________________________\n"+
                    "|                PLEASE SELECT COFFEE               | \n"+
                    " _____________________________________________________\n");
                    Coffee.showAllCoffees();
                    Coffee item;
                    System.out.println(
                            " _____________________________________________________\n"+
                            "enter:");
                    try {
                        item = Coffee.getObject(in.nextInt());
                        System.out.println(
                                " _____________________________________________________\n"+
                                "        YOU'VE SELECTED: '" + item.getName() + ", " + item.getPrice() +"$ '\n"+
                                " _____________________________________________________\n");

                    }catch (IndexOutOfBoundsException e){
                        System.out.print("Select coffee from existing! Try again: ");
                        item = Coffee.getObject(in.nextInt());
                        System.out.println(
                                " _____________________________________________________\n" +
                                "        YOU'VE SELECTED: '" + item.getName() + ", " + item.getPrice() +"$ '\n" );
                    }

                    System.out.println(
                            "SELECT PAYMENT METHOD:\n|1|VISA |2| MASTERCARD |3|CASH |4|CANCEL\n"+
                            "_____________________________________________________\n"+
                            "enter: ");
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
                            for(int i = 0; i < 10; i++){
                                System.out.println("\n");
                            }
                            System.out.println(
                                    "_________________________________\n"+
                                    "       Payment is successful! " +
                                    "\n         Item: " + item.getName() +
                                    "\n         Price: " + item.getPrice() +"$"+
                                    "\n\n         paid with VISA" +
                                    "\n+_______________________________+"
                            );
                            break;

                        case 2:
                            Receipt.addToHistory(new Receipt(item, item.getPrice(), "MASTERCARD",  new Date()));
                            for(int i = 0; i < 10; i++){
                                System.out.println("\n");
                            }
                            System.out.println(
                                    "_______________________________\n"+
                                            "       Payment is successful! " +
                                            "\n         Item: " + item.getName() +
                                            "\n         Price: " + item.getPrice() +"$"+
                                            "\n\n        paid with MASTERCARD" +
                                            "\n+_______________________________+"
                            );
                            break;


                        case 3:
                            System.out.print("|PLEASE ENTER AMOUNT: ");
                            double amount = in.nextDouble();

                            if(amount < item.getPrice()){
                                System.out.println(
                                        "Please enter right amount! Product price is: " + item.getPrice()+
                                        "\nenter: ");
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
                            for(int i = 0; i < 10; i++){
                                System.out.println("\n");
                            }
                            System.out.println(
                                    "_______________________________\n"+
                                            "       Payment is successful! " +
                                            "\n         Item: " + item.getName() +
                                            "\n         Price: " + item.getPrice() +"$"+
                                            "\n         Change: " + change +"$" +
                                            "\n\n        paid with Cash" +
                                            "\n+_______________________________+"

                            );
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
                    System.out.println("\n\n\n_____________________________________________________\n"+
                                    """
                                                WELCOME TO ADMIN PANEL
                                               SELECT OPTION FROM BELOW
                                    |1| ADD ITEM |2| REMOVE ITEM |3| CHANGE PRICE
                                    |4| VIEW TRANSACTION LIST
                                    |5| LOGOUT
                                    """ +
                                    " _____________________________________________________");

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

                            System.out.println("Please enter item to remove: ");


                            try {
                                String itemToDelete = in.next();
                                Admin.removeCoffeeFromTheMenu(itemToDelete);
                            }catch(Exception e){
                                System.out.println("Please enter existing id! ");
                                break;
                            }

                            System.out.println("Item removed");
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
