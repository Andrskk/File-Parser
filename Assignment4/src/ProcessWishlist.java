import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//-------------------------------------
// Assignment 4 due to April 24, 2021
// Written by: Andrei Skachkou 40134189
// Comp 249
// Part I
// The program is File Parser, that processes all the words found in an input file and then creates three following txt files.
// 1. The file named "vowel_verbiage.txt" that contains all the words with more than 3 vowels.
// 2. The file named "obsessive_o.txt" that contains all the words starting with "o".
// 3. The file named "distinct_data.txt" that contains all distinct words from the input file.
// Part II The program creates a linked list of TVShows from txt file. Then creates arraylists of user watching/wishing
// TV shows from txt file as well. Finally, iterates through the linked list and display whether the user is able to watch
// TV shows from his/her wishlist or not according to time.
//-------------------------------------

/**
 * Driver class for program test.
 */
public class ProcessWishlist {
    /**
     * main() method for the driver program.
     * Prompt user to enter an input file name. If the file name is correct, creates 3 according output files.
     * @param args stores incoming command argument for the program.
     */
    public static void main(String[] args) throws FileNotFoundException
    {
                    // PART I

        //Arraylist of String instantiation
        ArrayList<String> vowel_verbiage = new ArrayList<>();
        ArrayList<String> obsessive_o = new ArrayList<>();
        ArrayList<String> distinct_data = new ArrayList<>();

        //String filename to store user input
        String fileName;
        //boolean variable to control user input
        boolean isCorrectUserInput2=false;

        System.out.print("Please enter an input file name to create 3 files(Distinct_Data, Obsessive_o" +
                "Vowel_verbiage) from: ");

        // loop to control user input and proceed input file
        while (!isCorrectUserInput2)
        {
            //try block to attempt to open input files
            try
            {
                //Scanner objects instantiation
                Scanner keyboard2 =new Scanner(System.in);
                //store user input
                fileName=keyboard2.next();

                //FileInputStream object instantiation to go throughout the file
                Scanner myFileReader = new Scanner(new FileInputStream(fileName + ".txt"));
                isCorrectUserInput2=true;
                System.out.println("Output files for file \""+fileName+"\" have been successfully created.");

                //loop to handle with a line
                while (myFileReader.hasNextLine())
                {
                    Scanner sc = new Scanner(myFileReader.nextLine());

                    //loop to handle with a word
                    while (sc.hasNext())
                    {
                        String word = sc.next();
                        // remove all non alphanumeric characters from the word
                        word = word.replaceAll("[^a-zA-Z0-9]", "");

                        //obsessive_o checking and storing to array
                        if (!word.isEmpty()&&(word.charAt(0)=='o'||word.charAt(0)=='O'))obsessive_o.add(word);

                        //4 or more vowels checking and storing to array
                        int vowelCounter=0;
                        for (int i=0;i<word.length();i++)
                        {
                            if(word.charAt(i)=='a'||word.charAt(i)=='e'||word.charAt(i)=='i'||word.charAt(i)=='o'||word.charAt(i)=='u'
                            || word.charAt(i)=='A'||word.charAt(i)=='E'||word.charAt(i)=='I'||word.charAt(i)=='O'||word.charAt(i)=='U')
                            {
                                vowelCounter++;
                            }
                        }
                        if (vowelCounter>3)
                        {
                            vowel_verbiage.add(word);
                        }

                        //distinct words checking and storing to array
                        boolean wordInArray=false;
                        //loop to check if a current word is already in array. Add, if not.
                        for (String element: distinct_data)
                        {
                            if (element.equals(word))
                            {
                                wordInArray = true;
                                break;
                            }
                        }
                        if (!wordInArray)distinct_data.add(word);
                    }
                }
                // Caling methods to create 3 output files
                vowelVerbiageFileOutput(vowel_verbiage,fileName);
                obsessiveOFileOutput(obsessive_o,fileName);
                distinctDataFileOutput(distinct_data,fileName);
            }
            //catch block in case the user attempt was unsuccessful and input file does not exist.
            catch (Exception e)
            {
                System.out.print("File with this name does not exist, please check the spelling. Enter a file name:");
            }
        }
                                    // PART II

        //LinkedList of TvShows objects initialization
        ShowList list =new ShowList();
        ShowList list2 =new ShowList(list);

        try
        {
            //Scanner objects instantiation
            Scanner myFileReader2 = new Scanner(new FileInputStream("TVGuide.txt"));
            String id;
            String name;
            String skip;
            double startTime;
            double endtime;
            TVShow tvShow;

            //Storing file containing to ShowList
            while (myFileReader2.hasNext())
            {
                id=myFileReader2.next();
                name=myFileReader2.next();
                skip=myFileReader2.next();
                startTime=myFileReader2.nextDouble();
                skip=myFileReader2.next();
                endtime=myFileReader2.nextDouble();

                tvShow=new TVShow(id,name,startTime,endtime);

                if (!(list.find(tvShow)))
                {
                    list.addToStart(tvShow);
                }
            }
            myFileReader2.close();
        }
        catch (Exception e)
        {
            System.out.println("File not found");
        }

        //Arraylist objects initialization
        ArrayList<String> watchingIDs = new ArrayList<>();
        ArrayList<String> wishListIDs = new ArrayList<>();

        boolean isCorrectUserInput=false;
        System.out.print("Please enter a name of user Watching/Wishlist file: ");

        //loop to control user input and storing Interest file to arraylists
        while (!isCorrectUserInput)
        {
            try
            {
                Scanner sc = new Scanner(System.in);
                String fileName2 = sc.next();
                Scanner interestReader = new Scanner(new FileInputStream(fileName2 + ".txt"));
                interestReader.next();
                while (interestReader.hasNext())
                {
                    watchingIDs.add(interestReader.next());
                    if (watchingIDs.get(watchingIDs.size()-1).equals("Wishlist"))
                    {
                        watchingIDs.remove(watchingIDs.size()-1);
                        while (interestReader.hasNext())
                        {
                            wishListIDs.add(interestReader.next());
                        }
                    }
                }
                isCorrectUserInput=true;
                interestReader.close();
            }

            catch (Exception e)
            {
                System.out.print("File not found, try again: ");
            }
        }

        //method invocation to proceed obtained information and output the result
        list.canWatch(watchingIDs,wishListIDs);

        //loop to prompt user to enter an ID to look for until "e" is entered
        boolean exit=false;
        while (!exit)
        {
            System.out.print("Enter an ID to search or enter \"e\" to exit: ");
            Scanner keyboard = new Scanner(System.in);
            String input = keyboard.next();

            if (!(input.equals("e")))
            {
                list.findID(input);
            }
            else
            {
                exit=true;System.out.println("Thank you for using program.");
            }
        }
    }

    /**
     * Method to create the file named "vowel_verbiage.txt" that contains all the words with more than 3 vowels.
     * @param arrayList Receives an arrayList of String type with words of more than 3 vowels.
     * @param fileName Receives a file name as a String to create an output file using this name.
     */
    public static void vowelVerbiageFileOutput(ArrayList<String> arrayList, String fileName) throws FileNotFoundException
    {
        //output stream object initializing to append words into the file
        PrintWriter myFileWriter = new PrintWriter(new FileOutputStream(fileName+"_Vowel_verbiage.txt",true));
        // Clear before writing
        PrintWriter pw = new PrintWriter(fileName+"_Vowel_verbiage.txt");
        pw.write("");
        pw.flush();
        pw.close();

        // printing words into output file
        myFileWriter.println("Word Count: "+arrayList.size());
        for (String element: arrayList)
        {
            myFileWriter.println(element);
        }
        myFileWriter.flush();
        myFileWriter.close();
    }

    /**
     * Method to create the file named "obsessive_o.txt" that contains all the words starting with "o".
     * @param arrayList Receives an arrayList of String type with words starting with "o".
     * @param fileName Receives a file name as a String to create an output file using this name.
     */
    public static void obsessiveOFileOutput(ArrayList<String> arrayList, String fileName) throws FileNotFoundException
    {
        //output stream object initializing to append words into the file
        PrintWriter myFileWriter = new PrintWriter(new FileOutputStream(fileName+"_Obsessive_o.txt",true));
        // Clear before writing
        PrintWriter pw = new PrintWriter(fileName+"_Obsessive_o.txt");
        pw.write("");
        pw.flush();
        pw.close();

        // printing words into output file
        myFileWriter.println("Word Count: "+arrayList.size());
        for (String element: arrayList)
        {
            myFileWriter.println(element);
        }
        myFileWriter.flush();
        myFileWriter.close();
    }

    /**
     * Method to create the file named "distinct_data.txt" that contains all distinct words from the input file.
     * @param arrayList Receives an arrayList of String type with distinct words.
     * @param fileName Receives a file name as a String to create an output file using this name.
     */
    public static void distinctDataFileOutput(ArrayList<String> arrayList, String fileName) throws FileNotFoundException
    {
        //output stream object initializing to append words into the file
        PrintWriter myFileWriter = new PrintWriter(new FileOutputStream(fileName+"_Distinct_Data.txt",true));
        // Clear before writing
        PrintWriter pw = new PrintWriter(fileName+"_Distinct_Data.txt");
        pw.write("");
        pw.flush();
        pw.close();

        // printing words into output file
        myFileWriter.println("Word Count: "+arrayList.size());
        for (String element: arrayList)
        {
            myFileWriter.println(element);
        }
        myFileWriter.flush();
        myFileWriter.close();
    }
}
