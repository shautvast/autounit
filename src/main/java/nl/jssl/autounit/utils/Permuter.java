package nl.jssl.autounit.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Makes permutations of n lists of m elements, ie:
 * 
 * [A,B], [1,2] => [A,1], [A,2], [B,1], [B,2]
 */
public class Permuter {

	public static List<Tuple> permute(List<List<?>> elements) {
		if (elements.size() >= 2) {
			List<Tuple> result = permutePairs(elements.remove(0), elements.remove(0));

			for (List<?> element : elements) {
				result = permutePairs(element, result);
			}
			return result;
		} else {
			throw new IllegalArgumentException("need at least 2");
		}
	}

	private static List<Tuple> permutePairs(List<?> list1, List<?> list2) {
		List<Tuple> pairs = new ArrayList<Tuple>();
		for (Object element1 : list1) {
			for (Object element2 : list2) {
				pairs.add(new Tuple(element1, element2));
			}
		}
		return pairs;
	}

	public static class Tuple implements Iterable<Object> {
		public final Object element1;
		public final Object element2;

		public Tuple(Object element1) {
			this.element1 = element1;
			this.element2 = null;
		}

		public Tuple(Object element1, Object element2) {
			super();
			this.element1 = element1;
			this.element2 = element2;
		}

		@Override
		public Iterator<Object> iterator() {
			return asList().iterator();
		}

		private List<Object> asList() {
			List<Object> list = new ArrayList<Object>();
			if (element1 != null) {
				add(element1, list);
			} else {
				add("autounit:[NULL]", list);
			}
			if (element2 != null) {
				add(element2, list);
			}
			return list;
		}

		private void add(Object element, List<Object> list) {
			if (!(element instanceof Tuple)) {
				list.add(element);
			} else {
				Tuple pair = (Tuple) element;
				add(pair.element1, list);
				add(pair.element2, list);
			}
		}

		@Override
		public String toString() {
			String string = "";
			for (Object o : this) {
				string += o.toString() + "-";
			}
			return string;
		}

		public Object[] toArray() {
			return asList().toArray();
		}
	}
}
