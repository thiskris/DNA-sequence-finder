// This program reports information about DNA Genes. 
//	It lists the sequence of Nucleotides in the DNA gene
//	the count of each type of Nucleotide in the sequence
// computes the mass percentage occupied by each nucleotide type list
//	reports the codons (trios of nucleotides) present in each sequence
// Reports if the gene is a protein-coding gene


import java.io.*;
import java.util.*;

public class KM_DNA {
    public static void main(String[] args) 
      throws FileNotFoundException {
      
      Scanner console = new Scanner(System.in);
                 
      System.out.println("This program reports information about DNA");
      System.out.println("nucleotide sequences that may encode");
      System.out.print("proteins. Input file name?");
      String inputFile = console.next(); 
      System.out.print("Output file name?");
      String outputFile = console.next();
    
      Scanner input = new Scanner(new File(inputFile));
      PrintStream output = new PrintStream(new File(outputFile));
      
      while (input.hasNextLine()) {
         String regionName = input.nextLine();
         output.println("Region Name: " + regionName);
         String nucleotides = input.nextLine().toUpperCase();
         char[] nucArr = nucleotides.toCharArray();
         int[] countArr = countCodons(nucArr);
         double[] massArr = new double[MIN_CODONS];
         double totalMass = codonMass(countArr, massArr);
         String[] codonArr = codonList(nucleotides, nucArr, countArr);
         String isProtien = detectProtien(codonArr, massArr);                
         printOutput(output, nucleotides, countArr, massArr, totalMass, codonArr, isProtien);                          
   
      }   
  } // end of main
  
      public static void printOutput(PrintStream output, String nucleotides, int[] countArr, 
                                    double[] massArr, double totalMass, String[] codonArr, String isProtien) {
           
      output.println("Nucleotides: " + nucleotides);
      output.println("Nuc. Counts: " + Arrays.toString(countArr));
      output.println("Total Mass%: " + Arrays.toString(massArr) + " of " + Math.round(totalMass * 10) / 10.0);        
      output.println("Codons List: " + Arrays.toString(codonArr));
      output.println();
      output.println("Is Protien?: " + isProtien);
      output.println();   
   }
 
      static final int MIN_CODONS = 5;
      static final int PERCENT_C_G = 30;
      static final int UNIQUE_NUC = 4;
      static final int NUC_PER_CODON = 3;
      
      public static int[] countCodons(char[] nucleotides) {
         int[] countArr = new int[MIN_CODONS];
         for (int n : nucleotides) {
            if (n == 'A') {
               countArr[0]++;   
            } else if (n == 'C') {
               countArr[1]++;     
            } else if (n == 'G') {
               countArr[2]++;   
            } else if (n == 'T') {
               countArr[3]++;
            } else {
               countArr[4]++;
            }
         }
      return countArr;     
      }
      
      public static double codonMass(int[] countArr, double[] massArr) {  
         massArr[0] = 135.120 * countArr[0];
         massArr[1] = 111.103 * countArr[1];
         massArr[2] = 151.128 * countArr[2];
         massArr[3] = 125.107 * countArr[3];
         massArr[4] = 100.000 * countArr[4];
         
         double totalMass = 0;
         for (double n : massArr) {
            totalMass += n; 
         }
         
         for (int i = 0; i < massArr.length; i++) {
            massArr[i] /= totalMass;
            massArr[i] *= 100;
            massArr[i] = Math.round(massArr[i] * 10 ) / 10.0;
        }    
         return totalMass;
      }
      
      public static String[] codonList(String nucleotides, char[] nucArr, int[] countArr) {
         String[] codonArr = new String[(nucArr.length / NUC_PER_CODON) - countArr[UNIQUE_NUC]];
         for (int i = 0; i < codonArr.length; i++) {
            codonArr[i] = nucleotides.substring(0, NUC_PER_CODON);
            nucleotides = nucleotides.substring(NUC_PER_CODON, nucleotides.length());
         }
         return codonArr;
      }
      
      public static String detectProtien(String[] codonArr, double[] massArr) {
         String isProtien = "";
                   
         if (codonArr[0].equals("ATG") && codonArr.length >= MIN_CODONS) {
            if (massArr[1] + massArr[2] >= PERCENT_C_G) {
               if (codonArr[codonArr.length - 1].equals("TAA") ||
                   codonArr[codonArr.length - 1].equals("TAG") ||
                   codonArr[codonArr.length - 1].equals("TGA")) {
                   isProtien = "YES";
               } else {
                  isProtien = "NO";
                  }
            } else {
               isProtien = "NO";
               }
         } else {
            isProtien = "NO";
            }
         return isProtien;
      }                      
} // end of class
