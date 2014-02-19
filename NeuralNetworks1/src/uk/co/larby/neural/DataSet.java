package uk.co.larby.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A container for a dataset.  Uses generics to specify the type of the attribute values that will be 
 * encapsulated in the members of the data set - I'm not sure about this approach and will probably
 * refactor this so that all data set members use a base type of double. I was tyring to add some additional
 * configuration options, but it has only really added complexity and no additional benefits so far!
 * @author larbyp
 */
public class DataSet<t> {
	
	//A collection of data set members.  Each data set member represents a single row of the source data file.
	private List<DataSetMember<t>> _data; 
	
	//A list of classifiers that exist across data set members.  
	//Note: Each classifier will appear only once in this list.  
	ArrayList<String> c;
	
	/**
	 * Create a DataSet from a file - expects a file formatted as per the iris dataset file at the moment
	 * @param Filename The file containing the data
	 */
	public DataSet(String Filename)
	{
		c = new ArrayList<String>();
		BuildFromFile(Filename);
	}
	
	private void BuildFromFile(String Filename)
	{
		//Use the data set builder to construct the data set members
		DataSetBuilder<t> dsb = new DataSetBuilder<t>("iris.data");
		_data = dsb.getDataMembers();
		
		//Set reference to null to queue for garbage collection and to ensure the source file gets closed
		dsb = null;
	}
	
	/**
	 * gets the number of records in the dataset
	 * @return the number of records in the dataset
	 */
	public int size()
	{
		return _data.size();
	}
	
	/**
	 * Gets the textual name of the classifier at the specified index position
	 * @param index
	 * @return the name of the classifier
	 */
	public String classifiers(int index)
	{
		return classifiers().get(index);
	}
	
	/**
	 * Returns the index of the supplied classifier, or -1 if the classifier is not known
	 * @param classifier
	 * @return
	 */
	public int getClassifierIndex(String classifier)
	{
		return classifiers().indexOf(classifier);
	}
	
	/**
	 * Gets a full list of the classifiers associated with the dataset.  Each class in the 
	 * @return
	 */
	public List<String> classifiers()
	{
		//If the classifier list has not yet been constructed then go ahead and do it
		if (c.isEmpty())
		{
			for(DataSetMember<t> member : _data)
			{
				if (!c.contains(member.getCategory()))
						c.add(member.getCategory().toString());
			}
		}
		
		return c;
	}
	
	/**
	 * get the full collection of data set members
	 * @return
	 */
	public List<DataSetMember<t>> getMembers()
	{
		return _data;
	}
	
	/**
	 * get a single randomly selected data set member
	 * @return a random data set member
	 */
	public DataSetMember<t> getRandomMember()
	{
		Random R = new Random();
		int i = R.nextInt(_data.size());
		return _data.get(i);
	}
	
	/**
	 * Get the data set member at the specified index position
	 * @param Index
	 * @return
	 */
	public DataSetMember<t> getMemberAt(int Index)
	{
		return _data.get(Index);
	}
}
