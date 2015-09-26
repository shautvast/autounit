package nl.jssl.autounit.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PermuterTests {
	@Test
	public void testPairs() {
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(1);
		integers.add(2);

		List<String> strings = new ArrayList<String>();
		strings.add("A");
		strings.add("B");

		List<List<?>> outer = new ArrayList<List<?>>();
		outer.add(integers);
		outer.add(strings);
		List<LinkedList> permuted = Permuter.permute(outer);

		assertEquals("1-A-", permuted.get(0).toString());
		assertEquals("1-B-", permuted.get(1).toString());
		assertEquals("2-A-", permuted.get(2).toString());
		assertEquals("2-B-", permuted.get(3).toString());
	}

	@Test
	public void testTriplets() {
		List<Integer> integers = new ArrayList<Integer>();
		integers.add(1);
		integers.add(2);
		List<String> strings = new ArrayList<String>();
		strings.add("A");
		strings.add("B");
		List<Vogon> vogons = new ArrayList<Vogon>();
		vogons.add(new Vogon("Jeltz"));
		vogons.add(new Vogon("Gummbah"));

		List<List<?>> outer = new ArrayList<List<?>>();
		outer.add(integers);
		outer.add(strings);
		outer.add(vogons);

		List<LinkedList> permuted = Permuter.permute(outer);
		assertEquals("Vogon Jeltz-1-A-", permuted.get(0).toString());
		assertEquals("Vogon Jeltz-1-B-", permuted.get(1).toString());
		assertEquals("Vogon Jeltz-2-A-", permuted.get(2).toString());
		assertEquals("Vogon Jeltz-2-B-", permuted.get(3).toString());
		assertEquals("Vogon Gummbah-1-A-", permuted.get(4).toString());
		assertEquals("Vogon Gummbah-1-B-", permuted.get(5).toString());
		assertEquals("Vogon Gummbah-2-A-", permuted.get(6).toString());
		assertEquals("Vogon Gummbah-2-B-", permuted.get(7).toString());

	}

	public class Vogon {
		private String name;

		public Vogon(String name) {
			super();
			this.name = name;
		}

		@Override
		public String toString() {
			return "Vogon " + name;
		}
	}
}
