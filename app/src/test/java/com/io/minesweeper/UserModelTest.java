package com.io.minesweeper;

import org.junit.Test;

import static org.junit.Assert.*;

import com.io.minesweeper.UserModel;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserModelTest {

    @Test
    public void autoTestGettersSetters() {
        Object instance;
        try{
            instance = UserModel.getDeclaredConstructor().newInstance();
            Field[] declaredFields = UserModel.getDeclaredFields();
            for(Field f: declaredFields) {
                String name = f.getName();
              name = name.substring(0,1).toUpperCase() + name.substring(1);
              String getterName = "get" + name;
              String setterName = "set" + name;
              Method getterMethod = UserModel.getMethod(getterName);
              Method setterMethod = UserModel.getMethod(setterName, getterMethod.getReturnType());
              // prepare a test value based on the filed type 
              Object testVal = null;
              Class<?> fType = f.getType();
              if (fType.isAssignableFrom(Integer.class)) {
                  testVal = 1234;
              } else if (fType.isAssignableFrom(String.class)) {
                  testVal = "abcd";
              }
              // test by composing the setter and getter
              setterMethod.invoke(instance, testVal);
              Object result = getterMethod.invoke(instance);
              System.out.printf("Testing class=%s field=%s...\n", UserModel.getName(), f.getName());
              assertThat(result).as("in class %s fields %s", UserModel.getName(), f.getName()).isEqualTo(testVal);
            }
        } catch(Exception e) {
           System.out.println(e.toString());
        }
    }
}