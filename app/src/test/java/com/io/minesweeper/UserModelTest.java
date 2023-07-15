package com.io.minesweeper;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserModelTest {

    @Test
    public void autoTestGettersSetters() {
        Class<?> aClass = UserModel.class;
        Object instance;
        try {
            Class<?>[] parameterTypes = new Class<?>[] { int.class, String.class };
            Object[] parameters = new Object[] { 1, "test" };
            instance = aClass.getDeclaredConstructor(parameterTypes).newInstance(parameters);
            Field[] declaredFields = aClass.getDeclaredFields();

            for (Field f : declaredFields) if (!Modifier.isStatic(f.getModifiers())) {
                String name = f.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String getterName = "get" + name;
                String setterName = "set" + name;
                Method getterMethod = aClass.getMethod(getterName);
                Method setterMethod = aClass.getMethod(setterName, getterMethod.getReturnType());

                // prepare a test value based on the filed type 
                Object testVal = null;
                Class<?> fType = f.getType();
                if (fType.isAssignableFrom(Integer.class) || fType.isAssignableFrom(int.class)) {
                    testVal = Integer.valueOf("1234");
                } else if (fType.isAssignableFrom(Long.class) || fType.isAssignableFrom(long.class)) {
                    testVal = Long.valueOf("1234");
                } else if (fType.isAssignableFrom(String.class)) {
                    testVal = "abcd";
                }

                // test by composing the setter and getter
                setterMethod.invoke(instance, testVal);
                Object result = getterMethod.invoke(instance);
                System.out.printf("Testing class=%s field=%s...\n", aClass.getName(), f.getName());
                assertEquals("in class " + aClass.getName() + " fields " + f.getName(), result, testVal);
            }

            System.out.printf("Testing toString and equals of class=%s...\n", aClass.getName());
            assertFalse("toString in class " + aClass.getName(), instance.toString().isBlank());

            Set<Object> set = Set.of(instance);
            assertTrue("equals n hashCode in class " + aClass.getName(), set.contains(instance));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}