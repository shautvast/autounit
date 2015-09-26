package nl.jssl.autounit.util;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.ConstPool;

/**
 * Reads the constantpool of a class (see jvm spec)
 */
public class ConstantpoolReader {

	private final static ClassPool pool;
	private final Class<?> classToRead;
	private final List<String> strings = new ArrayList<String>();

	static {
		pool = ClassPool.getDefault();
	}

	public ConstantpoolReader(Class<?> classToRead) {
		this.classToRead = classToRead;
	}

	public List<String> scanStrings() {
		CtClass ctClass;
		try {
			ctClass = pool.get(classToRead.getName());
			if (isScannable(ctClass)) {
				ConstPool constPool = ctClass.getClassFile().getConstPool();
				doScanStrings(constPool);
			}
			return strings;
		} catch (NotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void doScanStrings(ConstPool constPool) {
		for (int i = 1; i < constPool.getSize(); i++) {
			int tag = constPool.getTag(i);

			if (tag == ConstPool.CONST_String) {
				strings.add(constPool.getStringInfo(i));
			}
		}
	}

	private boolean isScannable(CtClass ctClass) {
		return !ctClass.isFrozen();
	}

}
