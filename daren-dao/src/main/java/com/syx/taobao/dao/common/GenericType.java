package com.syx.taobao.dao.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射类
 */
public class GenericType {

	/**
	 * 获取泛型的类型
	 * 
	 * @param clazz
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static Class getGenericType(Class clazz) {
		Type type = clazz.getGenericSuperclass();
		Type[] types = ((ParameterizedType) type).getActualTypeArguments();
		if (!(types[0] instanceof Class)) {
			return Object.class;
		}
		return (Class) types[0];
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager<Book>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager<Book>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index)
			throws IndexOutOfBoundsException {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 获取对象的类名称
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getGenericName(Class clazz) {
		return clazz.getSimpleName();
	}
}
