package nl.jssl.autounit.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nl.jssl.autounit.utils.Permuter;
import nl.jssl.autounit.utils.Permuter.Tuple;

import org.junit.Test;

public class PermuterTests {
	@Test
	public void test(){
		List<Integer> integers=new ArrayList<>();
		integers.add(1);
		integers.add(2);
		List<String> strings=new ArrayList<>();
		strings.add("A");
		strings.add("B");
		List<List<?>> outer=new ArrayList<>();
		outer.add(integers);
		outer.add(strings);
		List<Tuple> permuted = Permuter.permute(outer);
		assertEquals("1,A,",permuted.get(0).toString());
		assertEquals("1,B,",permuted.get(1).toString());
		assertEquals("2,A,",permuted.get(2).toString());
		assertEquals("2,B,",permuted.get(3).toString());
	}
}
