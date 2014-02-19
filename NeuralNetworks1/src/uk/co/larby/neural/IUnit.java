package uk.co.larby.neural;

import java.util.Hashtable;
import java.util.List;

import uk.co.larby.neural.Perceptron.TestResult;

/**
 * @author Phil Larby
 * @version 1.0
 * Interface for Unit datatype declaring all public members.  Primarilly used to create
 * mocked up Unit objects for injection during unit testing, or to define specific one 
 * off behaviours for Unit objects if required on an ad-hoc basis
 */
public interface IUnit extends IValue {
	public ActivationFunction getActivationFunction();
	public void setActivationFunction(ActivationFunction Af);
	public List<Input> getInputs();
	public void setInputs(List<Input> Inputs);
	public void addInput(Input input);
	public double getLearningRate();
	public int getEpoch();
	public double Net();
	public double getY(double x);
	public Input getInputAt(int index);
	public <t> t getOutput(Class<t> c);
	public TestResult Test(DataSet<?> ds, Hashtable<String, Boolean> Classifier, List<Integer> Attributes);
	public void add(DataSetMember<Double> dsm, List<Integer> Attributes);
	public void subtract(DataSetMember<Double> dsm, List<Integer> Attributes);
}
