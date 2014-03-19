package utilities;

public class MedicineUtility {
	
	/**
	 * This operation checks whether medical supply is available after transaction.
	 * @param medRequest - number of requested medical supply
	 * @param medCount - current count of medical supply
	 */
	public boolean isMedAvailable(int medRequest, int medCount)
	{
		boolean isAvailable = true;
		if (medCount - medRequest <= 0)
		{
			isAvailable = false;
		}
		else
		{
			isAvailable = true;
		}
		
		return isAvailable;
	}

}
