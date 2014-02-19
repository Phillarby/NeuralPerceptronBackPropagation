/**
 * 
 */
package uk.co.larby.neural;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author larbyp
 *
 */
public class Test_Unit {

	/**
	 * Test method for {@link uk.co.larby.neural.Unit#getActivationFunction()}.
	 */
	@Test
	public void Unit_Correctly_Constructs_And_Reports_Specified_Activation_Function() {
		//Arrange
		ActivationFunction expected = ActivationFunction.ThresholdLessThanZero;
		Unit u = new Unit(expected);
		
		//Act
		ActivationFunction actual = u.getActivationFunction();
		
		//Assert
		assertEquals(expected, actual);
	}
}
