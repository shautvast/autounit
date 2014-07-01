package nl.jssl.autounit.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.jssl.autounit.utils.Permuter;
import nl.jssl.autounit.utils.Permuter.Tuple;

import org.junit.Test;

public class PermuterTests {
	@Test
	public void testPairs() {
		List<Integer> integers = new ArrayList<>();
		integers.add(1);
		integers.add(2);
		List<String> strings = new ArrayList<>();
		strings.add("A");
		strings.add("B");
		List<List<?>> outer = new ArrayList<>();
		outer.add(integers);
		outer.add(strings);
		List<Tuple> permuted = Permuter.permute(outer);
		assertEquals("1-A-", permuted.get(0).toString());
		assertEquals("1-B-", permuted.get(1).toString());
		assertEquals("2-A-", permuted.get(2).toString());
		assertEquals("2-B-", permuted.get(3).toString());
	}

	@Test
	public void testTriplets() {
		List<Integer> integers = new ArrayList<>();
		integers.add(1);
		integers.add(2);
		List<String> strings = new ArrayList<>();
		strings.add("A");
		strings.add("B");
		List<Vogon> vogons = new ArrayList<>();
		vogons.add(new Vogon("Jeltz"));
		vogons.add(new Vogon("Gummbah"));

		List<List<?>> outer = new ArrayList<>();
		outer.add(integers);
		outer.add(strings);
		outer.add(vogons);

		List<Tuple> permuted = Permuter.permute(outer);
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
