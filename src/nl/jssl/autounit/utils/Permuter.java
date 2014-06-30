package nl.jssl.autounit.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Permuter {

	public static List<Tuple> permute(List<List<?>> elements) {
		if (elements.size() > 2) {
			return permute(elements.get(0), elements.subList(1, elements.size() - 1));
		} else if (elements.size() == 2) {
			return permutePairs(elements.get(0), elements.get(1));
		} else
			throw new IllegalArgumentException("need at least 2");
	}

	private static List<Tuple> permute(List<?> list, List<List<?>> elements) {
		if (elements.size() > 2) {
			return permute(elements.get(0), elements.subList(1, elements.size() - 1));
		} else {
			return permutePairs(elements.get(0), elements.get(1));
		}
	}

	private static List<Tuple> permutePairs(List<?> list1, List<?> list2) {
		List<Tuple> pairs = new ArrayList<>();
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

		public Tuple(Object element1, Object element2) {
			super();
			this.element1 = element1;
			this.element2 = element2;
		}

		@Override
		public Iterator<Object> iterator() {
			List<Object> list = new ArrayList<>();
			add(element1, list);
			add(element2, list);
			return list.iterator();
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
			String string="";
			for (Object o:this){
				string+=o.toString()+",";
			}
			return string;
		}
	}
}
