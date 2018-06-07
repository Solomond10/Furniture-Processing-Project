package furniturelab;

import java.util.*;
import java.io.*;

public class FurnitureLab {

    /*  Programmer: Solomon Davis
        Due Date: Friday, February 9, 2018
        Project Description: This program will add and sell the inventory of a 
        furniture manufacturer.
        Project Number: Lab 01
     */
    public static void main(String[] args) throws IOException {

        FurnitureEntry[] furniture;
        furniture = new FurnitureEntry[1000];
        char userChoice = 'J';
        int numEntries = 0;
        Scanner kbd = new Scanner(System.in);
        String customerInfo[];
        customerInfo = new String[13];
        PrintWriter pw = new PrintWriter("Receipt.txt");

        System.out.println("What's your FULL name?");
        customerInfo[0] = kbd.nextLine();
        System.out.println("What's your address?");
        customerInfo[1] = kbd.nextLine();
        System.out.println("What's your number?");
        customerInfo[2] = kbd.nextLine();

        while (userChoice != 'Q' && userChoice != 'q') {

            userChoice = GetUserChoice();
            numEntries = PerformUserChoice(userChoice, furniture, numEntries,
                    customerInfo);

        }

    }

    public static char GetUserChoice() {

        char userChoice;
        Scanner kbd = new Scanner(System.in);
        System.out.println("What do you want to do to the inventory?\n"
                + "YOU MUST LOAD INVENTORY BEFORE YOU DO ANYTHING\n"
                + "A - Add to inventory\n"
                + "B - Buy furniture\n"
                + "M - Make comment about the furniture\n"
                + "Q - Quit\n"
                + "L - Load Inventory\n");

        userChoice = kbd.nextLine().charAt(0);
        return userChoice;
    }

    public static int PerformUserChoice(char userChoice,
            FurnitureEntry[] furniture,
            int numEntries, String[] customerInfo)
            throws IOException {

        switch (userChoice) {
            case 'A':
            case 'a':
                numEntries = Add(furniture, numEntries);
                break;

            case 'B':
            case 'b':
                numEntries = Buy(furniture, numEntries, customerInfo);
                break;

            case 'M':
            case 'm':
                numEntries = Comment(furniture, numEntries);

            case 'Q':
            case 'q':
                numEntries = Quit(furniture, numEntries);
                break;

            case 'L':
            case 'l':
                numEntries = Load(furniture);
                break;

        }
        return numEntries;

    }

    public static int Load(FurnitureEntry[] furniture) throws IOException {

        int numEntries;
        numEntries = 0;

        File furnitureFile = new File("Inventory.txt");
        Scanner fScanner = new Scanner(furnitureFile);

        while (numEntries < furniture.length && fScanner.hasNext()) {
            furniture[numEntries] = new FurnitureEntry();
            furniture[numEntries].Load(fScanner);
            numEntries++;

        }

        System.out.println("\nLOAD COMPLETE\n");
        fScanner.close();
        return numEntries;
    }

    public static int Comment(FurnitureEntry[] furniture, int numEntries)
            throws IOException {

        int loc;
        String itemName;
        itemName = GetFurnitureNameComment();
        loc = SearchFurnitureNamesComment(itemName, furniture, numEntries);
        numEntries = MakeComment(furniture, numEntries, loc);

        return numEntries;
    }

    public static String GetFurnitureNameComment() {
        String itemName;
        Scanner kbd = new Scanner(System.in);

        System.out.println("Which furniture item do you wish to comment on");
        itemName = kbd.nextLine();

        return itemName;

    }

    public static int SearchFurnitureNamesComment(String itemName,
            FurnitureEntry[] furniture,
            int numEntries)
            throws IOException {
        int loc;
        loc = 0;
        while (loc < numEntries && !itemName.equals(furniture[loc].fName())) {
            loc++;
        }

        return loc;
    }

    public static int MakeComment(FurnitureEntry[] furniture, int numEntries,
            int loc) {
        Scanner kbd = new Scanner(System.in);
        String nComment;

        System.out.println("What comment do you wish to make on the furniture");
        nComment = kbd.nextLine();

        if (loc < numEntries) {
            System.out.println("Comment Made");
            furniture[loc].CommentChange(nComment);

        } else {

            System.out.println("Furnitue not found");

        }

        return numEntries;
    }

    public static int Add(FurnitureEntry[] furniture, int numEntries)
            throws IOException {

        int loc;
        String itemName;
        itemName = GetFurnitureName();
        loc = SearchFurnitureNames(itemName, furniture, numEntries);
        numEntries = PerformAddition(furniture, numEntries, loc);

        return numEntries;
    }

    public static String GetFurnitureName() {
        String itemName;
        Scanner kbd = new Scanner(System.in);

        System.out.println("Which furniture inventory do you want to add to?");
        itemName = kbd.nextLine();

        return itemName;

    }

    public static int SearchFurnitureNames(String itemName,
            FurnitureEntry[] furniture,
            int numEntries) throws IOException {

        int loc;
        loc = 0;
        while (loc < numEntries && !itemName.equals(furniture[loc].fName())) {
            loc++;
        }

        return loc;
    }

    public static int PerformAddition(FurnitureEntry[] furniture,
            int numEntries, int loc) {

        Scanner kbd = new Scanner(System.in);
        int nQuantity;

        System.out.println("How many?");
        nQuantity = kbd.nextInt();

        if (loc < numEntries) {
            System.out.println("Invenotry Found");
            System.out.println(furniture[loc].fName());
            System.out.println(furniture[loc].fQuantity());
            furniture[loc].Set(nQuantity + furniture[loc].fQuantity());

        } else {

            System.out.println("Not found");

        }

        return numEntries;
    }

    public static int Buy(FurnitureEntry[] furniture, int numEntries,
            String[] customerInfo) throws IOException {

        int loc;
        String itemName;
        char buyersChoice = 'J';
        int nQuantity = 0;
        double receiptTotal = 0.0;
        int quantityTotal = 0;
        File receiptFile = new File("Receipt.txt");
        Scanner rScanner = new Scanner(receiptFile);
        Scanner kbd = new Scanner(System.in);

        itemName = GetFurnitureNameBuy();
        nQuantity = GetQuantity(nQuantity);
        loc = SearchFurnitureNamesBuy(itemName, furniture, numEntries);
        numEntries = PerformCalculations(furniture, numEntries, loc, nQuantity,
                receiptTotal, customerInfo, itemName,
                receiptFile, rScanner);
        buyersChoice = (char) Receipt(numEntries, furniture, customerInfo,
                nQuantity, itemName);

        return numEntries;
    }

    public static String GetFurnitureNameBuy() {
        String itemName;
        Scanner kbd = new Scanner(System.in);

        System.out.println("What furniture inventory do you want to buy from?");
        itemName = kbd.nextLine();

        return itemName;

    }

    public static int GetQuantity(int nQuantity) {

        Scanner kbd = new Scanner(System.in);

        System.out.println("How many?");
        nQuantity = kbd.nextInt();

        return nQuantity;
    }

    public static int SearchFurnitureNamesBuy(String itemName,
            FurnitureEntry[] furniture,
            int numEntries)
            throws IOException {

        int loc;
        loc = 0;
        while (loc < numEntries && !itemName.equals(furniture[loc].fName())) {
            loc++;
        }

        return loc;
    }

    public static int PerformCalculations(FurnitureEntry[] furniture,
            int numEntries, int loc,
            int nQuantity, double receiptTotal,
            String[] customerInfo, String itemName, File receiptFile,
            Scanner rScanner) throws IOException {

        double quantityPrice, totalPrice = 0.0;
        int quantity;
        String fName;
        FileWriter fiw;
        fiw = new FileWriter(receiptFile, true);

        if (loc < numEntries) {

            if (furniture[loc].fQuantity() < nQuantity) {

                nQuantity = furniture[loc].fQuantity();

                System.out.println("Not enough inventory");
            }

            furniture[loc].Set(furniture[loc].fQuantity() - nQuantity);
            furniture[loc].SetTotal(nQuantity * furniture[loc].fPrice());
            fiw.write(furniture[loc].customerPrice + "\n");
            fiw.write(nQuantity + "\n");
            fiw.write(itemName + "\n");
            fiw.close();

            System.out.printf("\nReceipt\n");
            System.out.printf("Customer Info\n");
            System.out.printf("%1s%30s%37s\n", "Name ", "Address", "Number");
            System.out.printf("%1s%30s%37s\n\n", customerInfo[0], customerInfo[1],
                    customerInfo[2]);
            System.out.printf("%1s%30s%37s\n\n", "Price ", "Quanitiy", "Item");
            while (rScanner.hasNext()) {
                quantityPrice = rScanner.nextDouble();
                quantity = rScanner.nextInt();
                rScanner.nextLine();
                fName = rScanner.nextLine();
                totalPrice = totalPrice + quantityPrice;
                System.out.printf("%.2f%30s%37s\n", quantityPrice, quantity,
                        fName);
                System.out.printf("%.2f\n", totalPrice);
            }

        } else {

            System.out.println("Not found");

        }
        rScanner.close();
        fiw.close();
        return numEntries;
    }

    public static int Receipt(int numEntries, FurnitureEntry[] furniture,
            String[] customerInfo, double nQuantity,
            String itemName) throws IOException {

        char buyersChoice = 'J';
        Scanner kbd = new Scanner(System.in);

        System.out.println("Do you want to buy more furniture? Type Y for Yes "
                + "or N for No");
        buyersChoice = kbd.nextLine().charAt(0);

        switch (buyersChoice) {
            case 'y':
            case 'Y':
                Buy(furniture, numEntries, customerInfo);
                break;
            case 'n':
            case 'N':
                break;
            default:
                System.out.println("Answer not an option try again");
                Receipt(numEntries, furniture, customerInfo, nQuantity,
                        itemName);
                break;
        }

        return numEntries;
    }

    public static int Quit(FurnitureEntry[] furniture, int numEntries)
            throws IOException {

        FileWriter fw;

        File furnitureFile = new File("Inventory.txt");
        Scanner fScanner = new Scanner(furnitureFile);

        fw = new FileWriter(furnitureFile, false);

        for (int cnt = 0; cnt < numEntries; cnt++) {
            fw.write(furniture[cnt].fName() + "\n");
            fw.write(furniture[cnt].fId() + "\n");
            fw.write(furniture[cnt].fQuantity() + "\n");
            fw.write(furniture[cnt].fPrice() + "\n");
            fw.write(furniture[cnt].fDescription() + "\n");

        }
        fScanner.close();
        fw.close();

        return numEntries;
    }
}
