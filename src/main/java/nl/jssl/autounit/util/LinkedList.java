package nl.jssl.autounit.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedList implements Iterable<Object> {
	public final Object element1;
	public final Object element2;

	public LinkedList(Object element1) {
		this.element1 = element1;
		this.element2 = null;
	}

	public LinkedList(Object element1, Object element2) {
		super();
		this.element1 = element1;
		this.element2 = element2;
	}

	public int depth() {
		int d = 0;
		if (element2 != null) {
			if (element2 instanceof LinkedList) {
				d += 1 + ((LinkedList) element2).depth();
			} else {
				d += 1;
			}
		}
		if (element1 != null) {
			if (element1 instanceof LinkedList) {
				d += 1 + ((LinkedList) element1).depth();
			} else {
				d += 1;
			}
		}
		return d;
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
		if (element instanceof LinkedList) {
			LinkedList pair = (LinkedList) element;
			add(pair.element1, list);
			add(pair.element2, list);
		} else {
			list.add(element);
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