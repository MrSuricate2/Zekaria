package fr.mrsuricate.zekaria.Trade;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class Reflection
{
    public static Class<?> getNMSClass(String name) {
        return getClass("net.minecraft.server." + getVersion() + "." + name);
    }

    public static Class<?> getCraftClass(String name) {
        return getClass("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public static String getRawVersion() {
        String pkg = Bukkit.getServer().getClass().getPackage().getName();
        return pkg.substring(pkg.lastIndexOf(".") + 1);
    }

    public static String getNMSVersion() {
        return "net.minecraft.server." + getRawVersion() + ".";
    }

    public static Class<?> wrapperToPrimitive(Class<?> clazz) {
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Integer.class) return int.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Short.class) return short.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Void.class) return void.class;
        if (clazz == Character.class) return char.class;
        return clazz;
    }

    public static Class<?>[] toParamTypes(Object... params) {
        Class<?>[] classes = new Class[params.length];
        for (int i = 0; i < params.length; i++)
            classes[i] = wrapperToPrimitive(params[i].getClass());
        return classes;
    }

    public static Enum<?> getEnum(String enumFullName) {
        String[] x = enumFullName.split("\\.(?=[^\\.]+$)");

        if (x.length == 2) {

            String enumClassName = x[0];
            String enumName = x[1];

            Class<Enum> cl = (Class)getClass(enumClassName);

            return Enum.valueOf((Class)cl, enumName);
        }

        return null;
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setValue(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    public static Object callMethod(Object object, String method, Object... params) {
        try {
            Class<?> clazz = object.getClass();
            Method m = clazz.getDeclaredMethod(method, toParamTypes(params));
            m.setAccessible(true);
            return m.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Object o, String methodName, Class<?>... params) {
        try {
            Method method = o.getClass().getMethod(methodName, params);
            if (!method.isAccessible()) method.setAccessible(true);
            return method;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    public static Field getField(Field field) {
        field.setAccessible(true);
        return field;
    }
}
