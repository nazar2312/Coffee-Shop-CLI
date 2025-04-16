
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
            }catch(Exception e){
                System.out.println("Something went wrong...");
                break;
            }


            switch(mode){

                //CUSTOMER MODE
                case 1:

                    Coffee item = null;
                    boolean readyToPay = false;
                    double total = 0.0;
                    Cart.cart.clear();
                    do {
                        System.out.println(
                                " _____________________________________________________\n" +
                                        "|                PLEASE SELECT COFFEE               | \n" +
                                        " _____________________________________________________\n");
                        Coffee.showAllCoffees();
                        System.out.println("Select (-1) to progress to payments");
                        System.out.println(
                                " _____________________________________________________\n" +
                                        "enter:");
                        try {
                            var selector = in.nextInt();
                            if(selector == -1 && !Cart.cart.isEmpty()){ //If input is -1 -> progress to payment section.
                                readyToPay = true;
                                break;
                            }
                            item = Coffee.getObject(selector);
                            Cart.cart.add(item);
                            Cart.showCart();
                            total = Cart.total();
                            System.out.println("Your total " + total + "$");

                        }catch (IndexOutOfBoundsException e) {
                            System.out.print("Select coffee from existing! Try again: ");
                            item = Coffee.getObject(in.nextInt());
                            Cart.cart.add(item);
                            Cart.showCart();
                            total = Cart.total();
                            System.out.println("Your total " + total + "$");
                        }

                    }while(!readyToPay);


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
                            for(Coffee c : Cart.cart) {
                                Receipt.addToHistory(new Receipt(c, c.getPrice(), "VISA", new Date()));
                            }
                            System.out.println(
                                    "_________________________________\n"+
                                    "       Payment is successful! \n"
                            );
                            if(Cart.cart.size() == 1) {
                                System.out.println("        Products: "
                                        + Cart.cart.getFirst().getName() + " " + Cart.cart.getFirst().getPrice() + "$"
                                        +"\n        Total: " + Cart.total() + "$"
                                                +"\n        paid with VISA"
                                                +"\n+_______________________________+"
                                );
                            }else if(Cart.cart.size() > 1){
                                System.out.println("        Products: ");
                                for(Coffee c : Cart.cart){
                                    System.out.println(c.getName() + " " + c.getPrice() + "$");
                                }
                                System.out.println(
                                        "\n        Total: " + Cart.total() + "$"
                                        +"\n        paid with VISA"
                                        +"\n+_______________________________+"
                                );
                            }

                            break;

                        case 2:
                            for(Coffee c : Cart.cart) {
                                Receipt.addToHistory(new Receipt(c, c.getPrice(), "MASTERCARD", new Date()));
                            }
                            System.out.println(
                                    "_________________________________\n"+
                                            "       Payment is successful! \n"
                            );
                            if(Cart.cart.size() == 1) {
                                System.out.println("        Products: "
                                        + Cart.cart.get(0).getName() + " " + Cart.cart.get(0).getPrice() + "$"
                                        +"\n        Total: " + Cart.total() + "$"
                                        +"\n        paid with MASTERCARD"
                                        +"\n+_______________________________+"
                                );
                            }else if(Cart.cart.size() > 1){
                                System.out.println("        Products: ");
                                for(Coffee c : Cart.cart){
                                    System.out.println(c.getName() + " " + c.getPrice() + "$");
                                }
                                System.out.println(
                                        "\n        Total: " + Cart.total() + "$"
                                                +"\n        paid with MASTERCARD"
                                                +"\n+_______________________________+"
                                );
                            }

                            break;


                        case 3:
                            System.out.print("|PLEASE ENTER AMOUNT: ");
                            double amount = in.nextDouble();

                            if(amount < total){
                                System.out.println(
                                        "Amount is less that total price!");
                                amount = in.nextDouble();

                                if(amount < total){
                                    System.out.println("Payment's canceled.");
                                    break;
                                }
                                break;
                            }
                            //Calculating change and round it to 2 decimal places;
                            double change = amount - total;
                            change = Double.parseDouble(String.format("%.2f", change));

                            //
                            for(Coffee c : Cart.cart) {
                                Receipt.addToHistory(new Receipt(c, c.getPrice(), "CASH", amount, change, new Date()));
                            }
                            System.out.println(
                                    "_________________________________\n"+
                                            "       Payment is successful! \n"
                            );
                            if(Cart.cart.size() == 1) {
                                System.out.println("        Products: "
                                        + Cart.cart.getFirst().getName() + " " + Cart.cart.getFirst().getPrice() + "$"
                                        +"\n        Total: " + Cart.total() + "$"+
                                        "\n         Change: " + change +"$" +
                                        "\n\n        paid with Cash" +
                                        "\n+_______________________________+"
                                );
                            }else if(Cart.cart.size() > 1){
                                System.out.println("        Products: ");
                                for(Coffee c : Cart.cart){
                                    System.out.println(c.getName() + " " + c.getPrice() + "$");
                                }
                                System.out.println(
                                                "\n        Total: " + Cart.total() + "$"+
                                                "\n         Change: " + change +"$"+
                                                "\n\n        paid with Cash" +
                                                "\n+_______________________________+"
                                );
                            }

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
                    int option;
                    try{
                        option = in.nextInt();
                    }catch(InputMismatchException e){
                        System.out.println("Something went wrong...");
                        break;
                    }
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
                            int id = in.nextInt();
                            double newPrice = in.nextDouble();

                            try {
                                Admin.changePrice(id, newPrice);
                            }catch (Exception e){
                                System.out.println("Something went wrong...");
                                break;
                            }

                            System.out.println("Item: '" + Coffee.menu.get(id).getName() + "' has new price = " + newPrice + "$" );
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
