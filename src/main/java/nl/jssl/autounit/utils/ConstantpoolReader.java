package nl.jssl.autounit.utils;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;

/**
 */
public class ConstantpoolReader {

	private ClassPool pool;

	public ConstantpoolReader(ClassPool pool) {
		this.pool = pool;
	}

	public List<String> scanStrings(Class<?> target) {
		CtClass ctClass;
		try {
			ctClass = pool.get(target.getName());
			List<String> strings = new ArrayList<String>();
			if (isScannable(ctClass)) {
				ConstPool constPool = ctClass.getClassFile().getConstPool();
				int size = constPool.getSize();
				for (int i = 1; i < size; i++) {
					int tag = constPool.getTag(i);
					if (tag == ConstPool.CONST_String) {
						strings.add(constPool.getStringInfo(i));
					}
				}
			}
			return strings;
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isScannable(CtClass ctClass) {
		return !ctClass.isFrozen();
	}

}
