package uk.co.larby.neural;

import java.util.LinkedList;
import java.util.List;

/**
 * A generic class containing a category classifier and a collection of types objects.
 * This is used to build training data sets, typically vectors of varying lengths and 
 * and association classifier for use in categorisation learning algrothms
 * @author larbyp
 * @param <t> A type dictating the type of elements 
 */
public class DataSetMember<t> {
	
	private String _classifier;
	private List<t> _values;
	
	/**
	 * Default constructor creates object with default values
	 */
	public DataSetMember()
	{
		setDefaults();
	}
	
	/**
	 * 
	 * @param Class
	 * @param Value1
	 */
	public DataSetMember(String Class, t Value1)
	{
		this();
		_classifier = Class;
		_values.add(0, Value1);
	}
	
	/**
	 * 
	 * @param Class
	 * @param Value1
	 * @param Value2
	 */
	public DataSetMember(String Class, t Value1, t Value2)
	{
		this(Class, Value1);
		_values.add(1, Value2);
	}
	
	/**
	 * 
	 * @param Class
	 * @param Value1
	 * @param Value2
	 * @param Value3
	 */
	public DataSetMember(String Class, t Value1, t Value2, t Value3)
	{
		this(Class, Value1, Value2);
		_values.add(2, Value3);
	}
	
	/**
	 * 
	 * @param Class
	 * @param Value1
	 * @param Value2
	 * @param Value3
	 * @param Value4
	 */
	public DataSetMember(String Class, t Value1, t Value2, t Value3, t Value4)
	{
		this(Class, Value1, Value2, Value3);
		_values.add(3, Value4);
	}
	
	/**
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 * @param string5
	 */
	public DataSetMember(
			String string, 
			String string2, 
			String string3,
			String string4, 
			String string5) {
		this(
				string, 
				string2,
				string3,
				string4,
				string5,
				1d
			);
	}
	
	/**
	 * 
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 * @param string5
	 */
	public DataSetMember(
			String string, 
			String string2, 
			String string3,
			String string4, 
			String string5,
			double Factor) {
		this(
				string, 
				(t)Converters.typeConverter(string2, Double.class, Factor),
				(t)Converters.typeConverter(string3, Double.class, Factor),
				(t)Converters.typeConverter(string4, Double.class, Factor),
				(t)Converters.typeConverter(string5, Double.class, Factor)
			);
	}
	
	private void setDefaults()
	{
		_classifier = "";
		_values = new LinkedList<t>();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCategory()
	{
		return _classifier;
	}
	
	/**
	 * Get the number of items in the list
	 * @return
	 */
	public int ElementCount()
	{
		return _values.size();
	}
	
	/**
	 * Add a new element to the end of the elements list
	 * @param Element
	 */
	public void addElement(t Element)
	{
		_values.add(Element);
	}
	
	/**
	 * Replaces an existing element that exists in the elements collection
	 * @param Element
	 * @param index
	 */
	public void ReplaceElementAt(t Element, int index)
	{
		_values.remove(index);
		_values.add(index, Element);
	}
	
	public t getElementAt(int index)
	{
		return _values.get(index);
	}
	
	/**
	 * Get the element corresponding to the specified index 
	 * @param index
	 * @return the typed object at the specified index position
	 */
	public t getValueAt(int index)
	{
		return _values.get(index);
	}
	
	/**
	 * Return the value associated with a parameter as an IValue object 
	 * to allow it to be bound to an input
	 * @param index
	 * @return
	 */
	public IValue getIValueAt(final int index)
	{
		return new IValue() {
			@Override
			public double getOutput() {
				//ToDo: Cast will be invalid for data types other than double: Refactor and generalise
				return (Double)getValueAt(index);
			}
		};
	}
}
