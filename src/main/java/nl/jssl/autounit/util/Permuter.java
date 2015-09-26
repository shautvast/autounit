package nl.jssl.autounit.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes permutations of n lists of m elements, ie:
 * 
 * [A,B], [1,2] => [A,1], [A,2], [B,1], [B,2]
 */
public class Permuter {

	public static List<Pair> permute(List<List<?>> elements) {
		if (elements.size() >= 2) {
			List<Pair> result = permutePairs(elements.remove(0), elements.remove(0));

			for (List<?> element : elements) {
				result = permutePairs(element, result);
			}
			return result;
		} else {
			throw new IllegalArgumentException("need at least 2");
		}
	}

	private static List<Pair> permutePairs(List<?> list1, List<?> list2) {
		List<Pair> pairs = new ArrayList<Pair>();
		for (Object element1 : list1) {
			for (Object element2 : list2) {
				pairs.add(new Pair(element1, element2));
			}
		}
		return pairs;
	}

}
