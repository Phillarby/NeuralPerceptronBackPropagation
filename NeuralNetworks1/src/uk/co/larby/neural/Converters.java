package uk.co.larby.neural;

/**
 * Class containing some static helper functions that perform conversions of objects to a
 * a specified generic type.  This is not very clean and should be refactored or redesigned as
 * it has massive scope for errors
 * @author larbyp
 *
 */
public class Converters {
	//Hard coded for double values.  Can be generalised for other types as required
	public static <t> t typeConverter(Object s, Class<t> c)
	{
		//ToDo: This is messy! Can't cast double directly to t.  Find a better solution
		return (t)(Object)Double.parseDouble(s.toString());
	}
	
	//Hard coded for double values.  Can be generalised for other types as required
	public static <t> t typeConverter(Object s, Class<t> c, double Factor)
	{
		//ToDo: This is messy! Can't cast double directly to t.  Find a better solution
		return (t)(Object)(Double.parseDouble(s.toString()) * Factor);
	}
}
