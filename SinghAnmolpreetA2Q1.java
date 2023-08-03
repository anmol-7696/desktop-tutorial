/**********************************
 * SinghAnmolpreetA2Q1
 *
 * COMP 1020 SECTION A01
 * INSTRUCTOR    HEATHER MATHESON
 * ASSIGNMENT    ASSIGNMENT 2, QUESTION 1
 * @author       ANMOLPREET SINGH, 7983556
 * @version      JULY 20, 2023
 *
 * PURPOSE: READING A FILE FOR PARTICULAR WORDS IN ANOTHER FILE
 ************************************/


import java.io.*;
import java.util.Scanner;

public class SinghAnmolpreetA2Q1{
    public static void main(String[] args) {
        Word[] words = new Word[100]; // creates array of word class to store words 
        int num = 0; // counter for words array
        int totalWords = 0; // counts total number of words in the book/file
        int uniqueWords = 0; // counts total number of uniquewords in the file
        float uniqueWordLength = 0; // measures length of the uniqueWords
        float weightedWordLength = 0; // measures weighted word length 
        int totalTimeSeen =0; // counts number of time a word appears in the file 

        try{
            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("Enter the name of the book file:"); // takes input of file in which we search for words 
                String fileName  = sc.nextLine(); 
                System.out.print("Enter the name of the file with words to find:"); // takes input of file containing words to search
                String findFileName = sc.nextLine();

                FileReader theFile = new FileReader(fileName);
                try (BufferedReader file = new BufferedReader(theFile)) {
                    BufferedReader find = new BufferedReader(new FileReader(findFileName)); // reader for the finding file 

                    String fileLine = file.readLine(); // reads the whole file 
                    String[] bookWords; // String array to store the words of the file 

                    do {
                        fileLine = fileLine.replaceAll("\\p{Punct}", "").toLowerCase(); // replaces the punctuation and converts them to lowerCase
                        bookWords = fileLine.split("\\s+"); // splits the line on basis of whitespace and stores them in array

                        for (int i = 0; i < bookWords.length; i++) {
                            String word = bookWords[i]; // adds the word to the word  array
                            totalWords++;   // increases the total word count 
                            if(words[i] != null){
                            if (sameWord(words, num, word)) {
                                words[i].increaseTimeSeen(); // increases the words timeSeen if already exists
                            }
                             } else {
                                Word newWord = new Word(word);
                                words[i] = newWord; // adds new word to the array
                                num ++;
                                if(num == words.length){
                                    resizeArray(words, num);
                                }
                                uniqueWords++; // increases the count for unique words 
                                uniqueWordLength += word.length(); // calculates total uniqueWordLength
                            } 
                        }
                            fileLine = file.readLine();
                    }while (fileLine != null);
                   
                  // this loops give us a count of number of times each word is seen in the file and weightedWordLength
                    for (int i = 0; i < words.length; i++) {
                        Word word = words[i];
                           if(word != null){
                            weightedWordLength += word.getTimesSeen() * word.getWord().length();
                            totalTimeSeen += word.getTimesSeen();
                           }
                    }
                

                    /*****
                     * whole couple of print statements to print the required outputs 
                     ***************/
                        System.out.println("The number of words is:" + totalWords);
                        System.out.println("The number of unique words is:" + uniqueWords);
                        System.out.printf("The average word length is: %.2f\n" , uniqueWordLength/uniqueWords);
                        System.out.printf("The weighted average word length is: %.2f\n" , weightedWordLength/totalTimeSeen );
                       
                        findWordsInBook(words,find); // Looks for words in the file
                }
            }
           }
                
        catch(IOException e){ // catches exception 
            System.out.println("OOPs! Something went wrong" + e.getMessage());
        }
    }
    /**********
     * this function find the provided words in the original file
     * @param words :takes the array of word
     * @param findFileName : file containing words to look for in other file 
     */
    public static void findWordsInBook(Word[] words, BufferedReader findFileName) {
        try {
            String[] wordsToFind = new String[100]; // Initial size of the array
            int num = 0; // counter for the array

            String findFile = findFileName.readLine(); // reads the file 
            do {
                findFile = findFile.replaceAll("\\p{Punct}", "").toLowerCase();  // converts the file to lowercase and all punctuation
                String[] wordStrings = findFile.split("\\s+");// splits the line on whitespaces 

                // iterates the wordString array and put the value inside the wordsToFind array
                for (int i = 0; i < wordStrings.length; i++) {
                    String word = wordStrings[i];
                    if (num == wordsToFind.length) {
                        wordsToFind = resize(wordsToFind, num); // increases the length of the array is size needs to be exceeded
                    }
                    wordsToFind[num] = word;
                    num++;
                }
                

             findFile = findFileName.readLine(); // Read the next line
            }while (findFile != null);

            findFileName.close(); // closes the file 

            System.out.println("The frequencies for words of interest are:");
            for (int i = 0; i < num; i++) {
                System.out.println(words[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *  this function checks if particular word already exists in the array before adding it the array 
     * @param words : array of type Word
     * @param num : counter of the array 
     * @param word : String to look for in the array 
     * @return : index where it exits and -1 if not
     */
    public  static boolean sameWord(Word[] words, int num, String word) {
        boolean same = false;
       
        for (int i = 0; i < num; i++) {
            if (words[i].getWord().equals(word)) {
                same = true;
            }
        }
    
        return same;
    }
/***
 * this function resizes the array  of Word when its counter reaches the entire length of the array 
 * @param words : array of Words
 * @param num : counter 
 * @return new array of Words 
 */
    public static Word[] resizeArray(Word[] words,int num) {
        int newSize = words.length * 2;
        Word[] newArray = new Word[newSize];
        for(int i=0;i<words.length;i++){
            newArray[i] = words[i];
        }
        num = newSize;
        return newArray;
    }
/***
 * this function resizes the array of String when its counter reaches the entire length of the array 
 * @param arr : array of String
 * @param num : counter 
 * @return new array of String 
 */
    public static String[] resize(String[] arr, int num ){
            int size = arr.length *2;
            String[] newArray = new String[size];
            for(int i=0;i<arr.length;i++){
                newArray[i] = arr[i];
            }
            num = size;
            return newArray;
        }
    
}
/**
 * Word class containg instance variables containing words and times Seen 
 */
class Word{
    private String word;
    private int timesSeen;

    public Word(String word){
        this.word = word;
        this.timesSeen =1;
    }

    public int getTimesSeen(){
        return timesSeen;
    }

    public String getWord(){
        return word;
    }

    public void increaseTimeSeen(){
       this.timesSeen++;
    }

    public String toString(){
        return "Word: " + word + ", Count: " + timesSeen;
    }

}