import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author Caroline Kirkconnell (CarolineKirkconnell8@gmail.com)
 * @version 2020-11-29
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   private String firstg;

   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      sourceText = sourceText.replaceAll("\n", " ");
      sourceText = sourceText.replaceAll("\r", "");
      int i = 0;
      firstg = sourceText.substring(i, i + K);
   
      while (i + K < sourceText.length() + 1) {
         String curr = sourceText.substring(i, i + K);
         String val;
         try {
            val = sourceText.substring(i + K, i + K + 1);
         } catch (StringIndexOutOfBoundsException e) {
            val = null;
         }
      
         if (model.putIfAbsent(curr, val) != null) {
            String newVal = model.get(curr);
            newVal += val;
            model.put(curr, newVal);
         }
         i++;
      }
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return firstg;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      Random rand = new Random();
      Object[] keySet = getAllKgrams().toArray();
      int index = rand.nextInt(model.size());
      String g = keySet[index].toString();
      return g;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String g) {
      String codes = model.get(g);
      Random rand = new Random();
      int index = rand.nextInt(codes.length());
      char character = codes.charAt(index);
      return character;
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
