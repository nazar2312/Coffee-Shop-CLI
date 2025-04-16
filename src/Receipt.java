
import java.io.*;
import java.util.*;

public class Receipt {
    //Class to construct and store receipts;
    Coffee item;
    String paymentMethod;
    double price;
    double amount;
    double change;
    Date date;
    static ArrayList<Receipt> historyOfReceipts = new ArrayList<>();
    public static final String receiptFile = "receipts.txt";

    //Constructor of receipt for CASH payment
    public Receipt(Coffee item, double price, String paymentMethod ,double amount, double change, Date date){
        this.item = item;
        this.price = price;
        this.amount = amount;
        this.change = change;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }
    //Constructor of receipt for CARD payment
    public Receipt(Coffee item, double price, String cardType, Date date){
        this.item = item;
        this.price = price;
        this.paymentMethod = cardType;
        this.date = date;
    }

    public static void addToHistory(Receipt r){
        historyOfReceipts.add(r);
        write(receiptFile, historyOfReceipts);
    }


    public String toString (){
        return "[ Item: " + item + "| Payment method: " + paymentMethod + "| Amount: " + amount + "| Change: " + change + "| Date " + date;
    }

    public static void write(String fileName, ArrayList<Receipt> data){

        try{
            FileWriter fw = new FileWriter(fileName, true);
            PrintWriter pw = new PrintWriter(fw);
            for (Receipt d : data) {
                pw.write(d.toString() + "\n");
            }
            pw.close();

        }catch(IOException e) {
            System.out.println("Something went wrong when writing file...");
        }

    }

    public static void printHistory() {
        try{
            FileReader fr = new FileReader(receiptFile);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while((line = br.readLine()) != null){
                System.out.println(line);
            }

        }catch(IOException e){
            System.out.println("Something went wrong while reading file...");
        }
    }




}
