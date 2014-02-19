package uk.co.larby.neural;

/**
 * An enumeration of activation function names, purely for simplification of syntax and to make code more readable
 * @author Phil Larby
 */
public enum ActivationFunction {
	ThresholdLessThanZero,
	ThresholdLessThanEqualToZero,
	ThresholdGreaterThanZero,
	ThresholdGreaterThanEqualToZero,
	Sigmoid
}
