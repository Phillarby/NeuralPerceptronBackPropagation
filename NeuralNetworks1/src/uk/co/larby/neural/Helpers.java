package uk.co.larby.neural;

import java.util.Iterator;

import uk.co.larby.app.Neural.Settings;

public final class Helpers {

	/**
	 * Converts a double typed value into an IValue interface, allowing it to be connected to the input of a neural network unit.
	 * @param value The value of the IValue
	 * @return an anonymously typed IValue object encapsulating the parameter value provided
	 */
	public static IValue getStaticValue(final double value)
	{
		return new IValue() {
			@Override
			public double getOutput(){
				return value;
			}
		};
	} 

	/**
	 * Gets an input object with the specified value and weight - used in percptron learning 
	 * @param staticValue The input value
	 * @param weight The input weight
	 * @return an input object that can be assigned to a unit
	 * @deprecated Now superceeded with functionlaity in the network class.  Originally created for perceptron learning process only
	 */
	public static Input getStaticInput(double staticValue, double weight)
	{
		return new Input(getStaticValue(staticValue), weight);
	}
	
	/**
	 * Load data from the Iris Data File.  We know we want to use double typed values for this dataset
	 * @return
	 */
	public static DataSet<Double> LoadIrisData()
	{
		DataSet<Double> ds = new DataSet<Double>("iris.data");
		return ds;
	}
}
