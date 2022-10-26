import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Car Data Analysis: Project 3 Starter Code.
 * 
 * Allows user to input files of their choosing containing
 * data regarding cars' brands, prices, years, and mileages,
 * and the program can perform multiple types of analyses on  
 * the inputted data set which be outputted to any file 
 * of the user's choosing. 
 *
 * Gateway Programming: Java
 * Johns Hopkins University
 * Fall 2022
 *
 * @author Mark Faust (mfaust4 - 10/20/22)
 */
 
public class CarDataAnalysis {

   // menu options
   static final int BRAND_QUERY = 1;
   static final int TWO_HIGHEST_PRICES_QUERY = 2;
   static final int RANGE_QUERY = 3;
   static final int BEST_VALUE_QUERY = 4;
   static final int QUIT = 5;

   // column index constants for car data file
   static final int BRAND = 2;
   static final int YEAR = 4;
   static final int MILEAGE = 6;
   static final int PRICE = 1;

   /**
    * Counts the number of lines in a given plain-text file.
    * @param filename The file whose lines are to be counted.
    * @return the number of lines in the file.
    * @throws FileNotFoundException
    */
   public static int countFileLines(String filename)
                                    throws FileNotFoundException {

      int count = 0;
      FileInputStream file = new FileInputStream(filename);
      Scanner scan = new Scanner(file);
      while (scan.hasNext()) {
         scan.nextLine();
         count++;
      }
      scan.close();
      return count;

   }
   
   /**
    * Prints average price of the cars of the specified brand.
    * @param brand the brand of car which's average is being taken.
    * @param avgPrice the average price of the cars of the specified brand.
    * @param entries the number of times a car of the specified appears
    *        within the data.
    */
   public static void printAveragePrice(String brand, double avgPrice,
                                        int entries) {
      if (entries != 0) {
         System.out.printf("There are " + entries  
                            + " matching enties for brand " + brand 
                            + " with an average \nprice of $%.2f.\n",
                            avgPrice);
      }
      else {
         System.out.println("There are no matching entries "
                            + "for brand " + brand + ".");
      }
   }
   
   /**
    * Calculates the average price of the cars of the
    * specified brand.
    * @param brU the brand of cars that wants to be analyzed.
    * @param file the file that the brands' info is to be printed to. 
    * @param b the array containing all brands in the data set.
    * @param y the array containing all years in the data set. 
    * @param p the array containing all prices in the data set.
    * @param m the array containing all mileages in the data set.
    * @throws FileNotFoundException
    */
   public static void averagePrice(String brU, String file, 
                                     String[] b, String[] y, 
                                     String[] p, String[] m) 
                                     throws FileNotFoundException {
      String br = brU.toLowerCase();
      FileOutputStream outputFile = new FileOutputStream(file);
      PrintWriter printer = new PrintWriter(outputFile);
      int count = 0;
      double averagePrice = 0.0;
      double sum = 0.0;
      for (int i = 0; i < b.length; ++i) {
         if (b[i].equals(br)) {
            sum += Double.parseDouble(p[i]);
            printer.print(i + ", " + b[i] + ", "
                          + y[i] + ", " + m[i] + ", "
                          + p[i] + "\n");
            ++count;
         }
      }
      averagePrice = sum / count;
      printAveragePrice(brU, averagePrice, count);
      printer.close();
   }
   
   /**
    * Finds the two highest prices within the entered data set.
    * @param p the array conatining all of the prices within the data set
    * @return maxes an array of size 2 containing the max prices.
    */
   public static double[] twoHighestPrices(String[] p) {
      double maxes[] = new double[2];
      maxes[0] = Double.MIN_VALUE;
      maxes[1] = maxes[0];
      for (int i = 0; i < p.length; ++i) {
         if (Double.parseDouble(p[i]) > maxes[0]) {
            maxes[1] = maxes[0];
            maxes[0] = Double.parseDouble(p[i]);
         }
         if (Double.parseDouble(p[i]) > maxes[1] &&
             Double.parseDouble(p[i]) < maxes[0]) {
            maxes[1] = Double.parseDouble(p[i]);
         }
      }
      return maxes;
   }
   
   /**
    * Finds average price of cars within the given year and mileage ranges.
    * @param yLow the lowest year within the specified range.
    * @param yHigh the highest year within the specified range.
    * @param mLow the lowest year within the specified range.
    * @param mHigh the highest year within the specified range.
    * @param y the array containing all years in the data set. 
    * @param p the array containing all prices in the data set.
    * @param m the array containing all mileages in the data set.
    */
   public static void averagePriceInRange(int yLow, int yHigh,
                                          double mLow, double mHigh,
                                          String[] y, String[] p, String[] m) {
      int count = 0;
      double sum = 0;
      double averagePrice = 0;
      for (int i = 0; i < y.length; i++) {
         if (Integer.parseInt(y[i]) >= yLow &&
             Integer.parseInt(y[i]) <= yHigh &&
             Double.parseDouble(m[i]) >= mLow &&
             Double.parseDouble(m[i]) <= mHigh) {
            sum += Double.parseDouble(p[i]);
            count++;
         }
      }
      if (count != 0) {
         averagePrice = sum / count;
         System.out.printf("There are %d entries for year range "
                           + "[%d, %d] and \nmileage range "
                           + "[%d, %d] with an average price of "
                           + "$%.2f.\n", 
                           count,
                           yLow, yHigh,
                           (int) mLow, (int) mHigh,
                           averagePrice);
      }
      else {
         System.out.printf("There are no matching entries for "
                           + "year range [%d, %d] and \nmileage "
                           + "range [%d, %d].\n", 
                           yLow, yHigh,
                           (int) mLow, (int) mHigh);
      }
   }
   
   /**
    * Finds the car with the best value with a mileage and price above
    * the specified threshold.
    * @param mThresh the lowest mileage accepted.
    * @param pThresh the lowest pice accepted. 
    * @param b the array containing all brands in the data set.
    * @param y the array containing all years in the data set. 
    * @param p the array containing all prices in the data set.
    * @param m the array containing all mileages in the data set.
    */
   public static void bestValue(double mThresh, double pThresh,
                                String[] b, String[] y, 
                                String[] p, String[] m) {
      double maxVal = Double.MIN_VALUE;
      int index = 0;
      for (int i = 0; i < p.length; i++) {
         if (Double.parseDouble(m[i]) > mThresh && 
             Integer.parseInt(p[i]) > (int) pThresh) {
            double value = Double.parseDouble(y[i]) - 
                        (Double.parseDouble(m[i]) / 13500) -
                        (Double.parseDouble(p[i]) / 1900);
            if (value > maxVal) {
               maxVal = value;
               index = i;
            }
         }
      }
      if (maxVal != Double.MIN_VALUE) {
         System.out.printf("The best-value entry with more than "
                           + "%.1f miles and a price \nhigher than "
                           + "$%d is a %s %s with %s miles for "
                           + "a price \nof $%s.\n",
                           mThresh, 
                           (int) pThresh,
                           y[index], 
                           b[index],
                           m[index],
                           p[index]);
      }
      else {
         System.out.printf("There is no best-value entry with "
                            + "more than %.1f miles and a price "
                            + "higher than $%d.\n",
                            mThresh,
                            (int) pThresh);
      }
   }

   /**
    * Print the program menu to the console.
    */
   public static void printMenu() {

      System.out.printf("[%d]: Average price of brand.\n", BRAND_QUERY);
      System.out.printf("[%d]: Two highest prices.\n",
             TWO_HIGHEST_PRICES_QUERY);
      System.out.printf("[%d]: Average price in year and mileage range.\n",
             RANGE_QUERY);
      System.out.printf("[%d]: Best value.\n", BEST_VALUE_QUERY);
      System.out.printf("[%d]: Quit.\n", QUIT);
      System.out.print("Please select an option: ");

   }

   /**
    * Drive the Car Data Analysis program.
    * @param args This program does not take commandline arguments.
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {

      // output purpose
      System.out.println("Welcome to the car dataset analysis program.");

      // get input filename (e.g. "USA_cars_datasets.csv")
      System.out.print("Please enter input csv filename: ");
      Scanner keyboard = new Scanner(System.in);
      String filename = keyboard.nextLine();

      // count the number of rows in the file (ignore headers line)
      int rowCount = countFileLines(filename) - 1;
      System.out.println("File has " + rowCount + " entries.");
      System.out.println();

      // declare and allocate parallel arrays for each column of interest
      String[] brands   = new String[rowCount];
      String[] years    = new String[rowCount];
      String[] prices   = new String[rowCount];
      String[] mileages = new String[rowCount];

      // load columns from file
      FileInputStream inputFile = new FileInputStream(filename);
      Scanner scan = new Scanner(inputFile);
      scan.nextLine();
      while (scan.hasNextLine()) {
         for (int i = 0; i < rowCount; ++i) {
            String line = scan.nextLine();
            String[] array = line.split(",");
            brands[i] = array[BRAND];
            years[i] = array[YEAR];
            prices[i] = array[PRICE];
            mileages[i] = array[MILEAGE];
         }
      }

      // while the user doesn't choose to quit...
      int option = 0;
      while (option != QUIT) {

         // display the menu and get an option
         printMenu();
         option = keyboard.nextInt();

         // handle chosen option
         switch (option) {
            case BRAND_QUERY: //////////////////////////////////////////////
               System.out.print("Please enter a car brand: ");
               String brandU = keyboard.next();
               String brand = brandU.toLowerCase();
               System.out.print("Please enter an output filename: ");
               String printToFile = keyboard.next();
               averagePrice(brandU, printToFile, brands, years, prices,
                            mileages);
               break;
            case TWO_HIGHEST_PRICES_QUERY: //////////////////////////////////
               double maxPrices[] = twoHighestPrices(prices);
               System.out.printf("The two highest prices are $%.2f "
                                 + "and $%.2f.\n", maxPrices[0], maxPrices[1]);
               break;
            case RANGE_QUERY: ////////////////////////////////////////////
               System.out.print("Please enter the year lower bound: ");
               int yearLow = keyboard.nextInt();
               System.out.print("Please enter the year upper bound: ");
               int yearHigh = keyboard.nextInt();
               System.out.print("Please enter the mileage lower bound: ");
               double mileageLow = keyboard.nextDouble();
               System.out.print("Please enter the mileage upper bound: ");
               double mileageHigh = keyboard.nextDouble();
               averagePriceInRange(yearLow, yearHigh, mileageLow, mileageHigh,
                                   years, prices, mileages);
               break;
            case BEST_VALUE_QUERY: ///////////////////////////////////////
               System.out.print("Please enter lower mileage threshold: ");
               double mileageThresh = keyboard.nextDouble();
               System.out.print("Please enter lower price threshold: ");
               double priceThresh = keyboard.nextDouble();
               bestValue(mileageThresh, priceThresh, brands, years, prices,
                         mileages);
               break;
            case QUIT: ///////////////////////////////////////////////////
               System.out.println("Thank you for using the program!");
               break;
            default:
               System.out.println("Invalid option.");

         }

         // leave empty line for next printing of menu
         System.out.println();

      }

   }

}
