package nl.jssl.autounit.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutator {
	public static List<?> permute(List<? extends Object> arr) {
		List<Object> all = new ArrayList<>();
		permute(new ArrayList<Object>(arr), 0, all);
		return all;
	}

	private static void permute(List<?> arr, int k, List<Object> all) {
		for (int i = k; i < arr.size(); i++) {
			Collections.swap(arr, i, k);
			permute(arr, k + 1, all);
			Collections.swap(arr, k, i);
		}
		if (k == arr.size() - 1) {
			all.addAll(arr);
		}
	}

}