package utilities;

import java.util.Comparator;

import models.MedConsumption;

public class ConsumptionComparator implements Comparator<MedConsumption> {

	@Override
	public int compare(MedConsumption medCon1, MedConsumption medCon2) {
		return medCon2.getConsumedQty() - medCon1.getConsumedQty();
	}

}
