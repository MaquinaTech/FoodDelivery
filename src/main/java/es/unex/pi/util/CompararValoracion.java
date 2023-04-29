package es.unex.pi.util;

import java.util.Comparator;
import es.unex.pi.model.Restaurant;

public class CompararValoracion implements Comparator<Restaurant> {

	@Override
	public int compare(Restaurant r1, Restaurant r2) {
		if (r1.getGradesAverage() == r2.getGradesAverage())
			return r1.getName().compareTo(r2.getName());
		else if (r1.getGradesAverage() > r2.getGradesAverage())
			return 1;
		else
			return -1;
	}
}