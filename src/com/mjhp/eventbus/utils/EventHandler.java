package com.mjhp.eventbus.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dahlia on 2/17/17.
 */
public class EventHandler {
    private int hashCode;
    private String name;
    private List<Method> methods;
    private Object object;


    public EventHandler(Object object, int hashCode, String name, List<Method> methods){
        this.hashCode = hashCode;
        this.name = name;
        this.methods = methods;
        this.object = object;
    }


    public List<Method> getMethodsByParameter(String parameter){
        Class<?>[] parameters;
        List<Method> result = new ArrayList<>();
        for (Method method : methods){
            parameters = method.getParameterTypes();
            if (parameters.length == 1){
                Class<?> param = parameters[0];
                if (param.getName() == parameter)
                    result.add(method);
            }
        }
        return result;
    }

    public List<Method> getMethods(){
        return this.methods;
    }


    public int getHashCode(){
        return this.hashCode;
    }


    public String getName(){
        return this.name;
    }


    public Object getObject(){
        return this.object;
    }

}
