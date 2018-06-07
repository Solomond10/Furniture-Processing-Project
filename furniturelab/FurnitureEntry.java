package furniturelab;

import java.util.Scanner;
import java.io.*;

public class FurnitureEntry {

    private String fName, fId, fComment;
    private double fPrice;
    private int fQuantity;
    public double customerPrice;

    public void Load(Scanner fScanner) throws IOException {

        fName = fScanner.nextLine();
        fId = fScanner.nextLine();
        fQuantity = fScanner.nextInt();
        fPrice = fScanner.nextDouble();
        fScanner.nextLine();
        fComment = fScanner.nextLine();

    }

    public String fName() {
        return fName;
    }

    public String fId() {
        return fId;
    }

    public int fQuantity() {
        return fQuantity;
    }

    public double fPrice() {
        return fPrice;
    }

    public String fDescription() {
        return fComment;
    }

    public void Set(int nQuantity) {

        fQuantity = nQuantity;

    }

    public double SetTotal(double reciptTotal) {

        customerPrice = reciptTotal;

        return customerPrice;
    }

    public String CommentChange(String nComment) {

        fComment = nComment;

        return fComment;
    }
}
