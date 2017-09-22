package be.maximvdw.placeholderapi.internal.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class ReflectionUtil {
	private static volatile Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<Class<?>, Class<?>>();

	public static Class<?> obcPlayer = ReflectionUtil.getOBCClass("entity.CraftPlayer");
	public static Method methodPlayerGetHandle = getMethod("getHandle", obcPlayer);

	static {
		CORRESPONDING_TYPES.put(Byte.class, byte.class);
		CORRESPONDING_TYPES.put(Short.class, short.class);
		CORRESPONDING_TYPES.put(Integer.class, int.class);
		CORRESPONDING_TYPES.put(Long.class, long.class);
		CORRESPONDING_TYPES.put(Character.class, char.class);
		CORRESPONDING_TYPES.put(Float.class, float.class);
		CORRESPONDING_TYPES.put(Double.class, double.class);
		CORRESPONDING_TYPES.put(Boolean.class, boolean.class);
	}

	public enum DynamicPackage {
		MINECRAFT_SERVER {
			@Override
			public String toString() {
				return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
			}
		},
		CRAFTBUKKIT {
			@Override
			public String toString() {
				return Bukkit.getServer().getClass().getPackage().getName();
			}
		}
	}

	public static class FieldEntry {
		String key;
		Object value;

		public FieldEntry(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return this.key;
		}

		public Object getValue() {
			return this.value;
		}
	}

	private static Class<?> getPrimitiveType(Class<?> clazz) {
		return CORRESPONDING_TYPES.containsKey(clazz) ? CORRESPONDING_TYPES.get(clazz) : clazz;
	}

	private static Class<?>[] toPrimitiveTypeArray(Object[] objects) {
		int a = objects != null ? objects.length : 0;
		Class<?>[] types = new Class<?>[a];
		for (int i = 0; i < a; i++)
			types[i] = getPrimitiveType(objects[i].getClass());
		return types;
	}

	private static Class<?>[] toPrimitiveTypeArray(Class<?>[] classes) {
		int a = classes != null ? classes.length : 0;
		Class<?>[] types = new Class<?>[a];
		for (int i = 0; i < a; i++)
			types[i] = getPrimitiveType(classes[i]);
		return types;
	}

	private static boolean equalsTypeArray(Class<?>[] a, Class<?>[] o) {
		if (a.length != o.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (!a[i].equals(o[i]) && !a[i].isAssignableFrom(o[i]))
				return false;
		return true;
	}

	public static Class<?> getClass(String name, DynamicPackage pack, String subPackage) throws Exception {
		return Class
				.forName(pack + (subPackage != null && subPackage.length() > 0 ? "." + subPackage : "") + "." + name);
	}

	public static Class<?> getClass(String name, DynamicPackage pack) {
		try {
			return getClass(name, pack, null);
		} catch (Exception e) {
		}
		return null;
	}

	public static Class<?> getClass(String name, String namespace) throws Exception {
		return Class.forName(namespace + "." + name);
	}

	public static Object getHandle(Object obj) {
		try {
			return getMethod("getHandle", obj.getClass()).invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    public static Object getHandle(Player player) {
        try {
            return methodPlayerGetHandle.invoke(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... paramTypes) {
		Class<?>[] t = toPrimitiveTypeArray(paramTypes);
		for (Constructor<?> c : clazz.getConstructors()) {
			Class<?>[] types = toPrimitiveTypeArray(c.getParameterTypes());
			if (equalsTypeArray(types, t))
				return c;
		}
		return null;
	}

	public static Object newInstance(Class<?> clazz, Object... args) throws Exception {
		return getConstructor(clazz, toPrimitiveTypeArray(args)).newInstance(args);
	}

	public static Object newInstance(String name, DynamicPackage pack, String subPackage, Object... args)
			throws Exception {
		return newInstance(getClass(name, pack, subPackage), args);
	}

	public static Object newInstance(String name, DynamicPackage pack, Object... args) throws Exception {
		return newInstance(getClass(name, pack, null), args);
	}

	public static Method getMethod(String name, Class<?> clazz, Class<?>... paramTypes) {
		Class<?>[] t = toPrimitiveTypeArray(paramTypes);
		for (Method m : clazz.getMethods()) {
			Class<?>[] types = toPrimitiveTypeArray(m.getParameterTypes());
			if (m.getName().equals(name) && equalsTypeArray(types, t))
				return m;
		}
		return null;
	}

	public static Object invokeMethod(String name, Class<?> clazz, Object obj, Object... args) throws Exception {
		return getMethod(name, clazz, toPrimitiveTypeArray(args)).invoke(obj, args);
	}

	public static Field getField(String name, Class<?> clazz) throws Exception {
		return clazz.getDeclaredField(name);
	}

	public static Object getValue(String name, Object obj) throws Exception {
		Field f = getField(name, obj.getClass());
		if (!f.isAccessible())
			f.setAccessible(true);
		return f.get(obj);
	}

	public static Object getValueFromClass(String name, Object obj, Class<?> clazz) throws Exception {
		Field f = getField(name, clazz);
		if (!f.isAccessible())
			f.setAccessible(true);
		return f.get(obj);
	}

	public static Object getValue(String name, Class<?> clazz) throws Exception {
		Field f = getField(name, clazz);
		if (!f.isAccessible())
			f.setAccessible(true);
		return f.get(clazz);
	}

	public static void setValue(Object obj, FieldEntry entry) throws Exception {
		Field f = getField(entry.getKey(), obj.getClass());
		if (!f.isAccessible())
			f.setAccessible(true);
		f.set(obj, entry.getValue());
	}

	public static void setValue(String name, Object value, Object obj) throws Exception {
		Field f = getField(name, obj.getClass());
		if (!f.isAccessible())
			f.setAccessible(true);
		f.set(obj, value);
	}

	public static void setFinalValue(String name, Object value, Object obj) throws Exception {
		Field f = obj.getClass().getDeclaredField(name);
		if (!f.isAccessible())
			f.setAccessible(true);
		f.set(obj, value);
	}

	public static void setValues(Object obj, FieldEntry... entrys) throws Exception {
		for (FieldEntry f : entrys)
			setValue(obj, f);
	}

	public static String getVersion() {
		String name = Bukkit.getServer().getClass().getPackage().getName();
		String version = name.substring(name.lastIndexOf('.') + 1) + ".";
		return version;
	}

	public static Class<?> getNMSClass(String className) {
		String fullName = "net.minecraft.server." + getVersion() + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}

	public static Class<?> getOBCClass(String className) {
		String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clazz;
	}


	public static Class<?> getNMSClassWithException(String className) throws Exception {
		String fullName = "net.minecraft.server." + getVersion() + className;
		Class<?> clazz = Class.forName(fullName);
		return clazz;
	}


	public static Field getField(Class<?> clazz, String name) {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
		for (Method m : clazz.getMethods())
			if (m.getName().equals(name) && (args.length == 0 || ClassListEqual(args, m.getParameterTypes()))) {
				m.setAccessible(true);
				return m;
			}
		return null;
	}

	/**
	 * Set a specified Field accessible
	 *
	 * @param f
	 *            Field set accessible
	 */
	public static Field setAccessible(Field f)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
		return f;
	}

	/**
	 * Set a specified Method accessible
	 *
	 * @param m
	 *            Method set accessible
	 */
	public static Method setAccessible(Method m)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		m.setAccessible(true);
		return m;
	}

	public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
		boolean equal = true;
		if (l1.length != l2.length)
			return false;
		for (int i = 0; i < l1.length; i++)
			if (l1[i] != l2[i]) {
				equal = false;
				break;
			}
		return equal;
	}

}