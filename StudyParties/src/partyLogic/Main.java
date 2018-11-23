package partyLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException 
	{
		// Initialize file read variables
        String line = "";	                                                                        // Holds the actual line read before being split by delimiters																	
        String[] inputs = new String[4];		                                                    // Holds line info during each line read - never more than 4 line elements to read
       
        // Loops through the folder /src/input to find any files we wish to process
        // So add new files to process there please! :)
        ArrayList<String> inputFiles = new ArrayList<String>();     
        ArrayList<String> inputFileNames = new ArrayList<String>();
        ArrayList<String> outputFiles = new ArrayList<String>();
        ArrayList<String> outputText = new ArrayList<String>();
        File folder = new File("src/input/");
        File[] listofFiles = folder.listFiles();
        for(File file : listofFiles) 
        { 
        	// inputFiles - strings like this: "src/input/example_in.txt"  || inputFileNames - strings like this: "example_in.txt"
            if(file.isFile()) { inputFiles.add(file.getPath()); inputFileNames.add(file.getName()); }
        }
        
        // Create array of output file paths to write to later
        for (String s : inputFileNames)
        {
        	String token[] = s.split("_");
        	for (String a : token) { if (!a.equals("in.txt")) { outputFiles.add("src/output/" + a + "_out.txt"); }}
        }
        
        // Loop through all input files, process each one fully and add all text to outputText array
        for (String s : inputFiles)
        {
        	
        	// Keep track of how many test cases in each file and which one is currently being processed
        	int testCases = 0;
        	int currentCase = 1;
        	try 																						
            {
                // Start reading input file here
                InputStream input = new FileInputStream(s);									
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(input));	
                
                // Go through every line in the input file
                while (inputStream.ready()) 														
                {			
                	// First line finds # of test cases
                    line = inputStream.readLine();														
                    inputs = line.split(" ");														
                    testCases = Integer.parseInt(inputs[0]);
                    
                    // Then we continue reading until we're out of test cases to check
                    // If there were more lines than test cases, they would be looped over in the outer loop but this loop would not process any more
                    while (testCases > 0)
                    {
                    	ArrayList<Integer> hostIDs = new ArrayList<Integer>();
                    	int numPeople, friendLinks, numParties, numHosts;
                    	
                    	// Start reading each test case within each file here
                    	line = inputStream.readLine();														
                        inputs = line.split("\\s+");														
                        numPeople = Integer.parseInt(inputs[0]);
                        friendLinks = Integer.parseInt(inputs[1]);
                        numParties = Integer.parseInt(inputs[2]);
                        numHosts = Integer.parseInt(inputs[3]);
                        
                        
                        // Loop through F more lines
                        for (int i = 0; i < friendLinks; i++)
                        {
                        	line = inputStream.readLine();                                                  
                            inputs = line.split("\\s+");
                            int vertA = Integer.parseInt(inputs[0]);
                            int vertB = Integer.parseInt(inputs[1]);
                            // Add edge between vertA and vertB
                        }
                        
                        // Loop over A more lines, assuming A is > 0
                        for (int i = 0; i < numHosts; i++)
                        {
                        	line = inputStream.readLine();
                        	inputs = line.split("\\s+");
                        	
                        	// Add all host IDs to array of integers
                        	hostIDs.add(Integer.parseInt(inputs[0]));
                        }
                        
                        // Add this text if A > 0
                        if (numHosts > 0)
                        {
                        	outputText.add("Test case " + currentCase + ".");
                        	outputText.add("Average social awkwardness = 0.00");
                        }
                        
                        // Add this text if A <= 0
                        else
                        {
                        	/* Add hosts info here */
                        	if (currentCase > 1) { outputText.add("\nTest case " + currentCase + "."); }
                        	else { outputText.add("Test case " + currentCase + "."); }
                        	outputText.add("Heuristic 1 hosts are ");
                        	outputText.add("Average social awkwardness = 0.00");
                        	outputText.add("Heuristic 2 hosts are ");
                        	outputText.add("Average social awkwardness = 0.00");
                        	outputText.add("My heuristic hosts are ");
                        	outputText.add("Average social awkwardness = 0.00\n");
                        }
                        
                        // Update test case status
                        currentCase++;
                        testCases--;
                    }
                }
                
                inputStream.close();
            }catch (IOException e){ e.printStackTrace(); }
        	
        	// Basically a delimiter within the outputText array so that when we write to files later it is clear what text came from which file
        	outputText.add("END OF FILE");
        }
 
        // Fill all outputFiles by reading through outputText array and switching to a new file each time "END OF FILE" is found
        int index = 0;
        for (String a : outputFiles)
        {
        	File file = new File(a);        	
        	PrintWriter writer = new PrintWriter(file, "UTF-8"); 
        	while (!outputText.get(index).equals("END OF FILE")) { writer.println(outputText.get(index)); index++; }
        	if (outputText.get(index).equals("END OF FILE")) { index++; }
        	writer.close(); 
        }
	}
}
