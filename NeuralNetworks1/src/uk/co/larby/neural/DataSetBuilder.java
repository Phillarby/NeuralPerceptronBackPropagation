/**
 * 
 */
package uk.co.larby.neural;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Reads a structured dataset file and creates datasetmember objects
 * @author larbyp
 *
 */
public class DataSetBuilder<t> {
	
	private FileReader reader;
	private Scanner in;
	List<DataSetMember<t>> results;
	
	private void setDefaults()
	{
		results = new LinkedList<DataSetMember<t>>();
	}
	
	/**
	 * Constructor specifying a filename for the data set builder to operate on
	 * @param filename
	 */
	public DataSetBuilder(String filename)
	{
		setDefaults();
		
		//Open the specified file
		openFile(filename);
		
		//Read file
		while (in.hasNextLine())
		{
			try {
				DataSetMember<t> element = processDataFileLine(in.nextLine());
				results.add(element);
			} catch (Exception e) {
				System.out.println(String.format("An error was encountered while processing the data file: %s", e.getMessage()));
				e.printStackTrace();
			}
		}
			
		//Close the file once done.
		closeFile();
	}
	
	//Converts a line in the source data file to a datasetmember
	private DataSetMember<t> processDataFileLine(String line)
	{
		if (line.length() == 0)
			throw new RuntimeException("Data file contains empty rows");
		
		//Split the string into an array
		String[] split = line.split(",");
		
		double factor = 0.1; //A normalisation factor to apply to the values in the source file when constructing the dataset
		
		//Hard coded for the known format of the iris dataset file.  Can 
		//be generalised for other uses as needed in the future.  Includes hard coded
		//factor for max iris data set value - for normalisation
		DataSetMember<t> ret;
		ret = new DataSetMember<t>(split[4], split[0], split[1], split[2], split[3], factor);
		return ret;
	}
	
	//Open the file and create a scanner
	private void openFile(String filename)
	{
		try {
			reader = new FileReader(filename);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Close the file
	private void closeFile()
	{	
		try {
			in.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the collection of data set members created from the source file
	 * @return
	 */
	public List<DataSetMember<t>> getDataMembers()
	{
		return results;
	}
}
