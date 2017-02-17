package com.mjhp.eventbus.utils;

import com.mjhp.eventbus.interfaces.RegisterBus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dahlia on 2/17/17.
 */
public class EventHandlerUtils {

    public static final Class<? extends Annotation> DEF_ANNOTATION_CLASS = RegisterBus.class;
    public static EventHandler newInstance(Object o){
        List<Method> methods = getAnnotatedMethods(o, DEF_ANNOTATION_CLASS);
        int hashCode = o.hashCode();
        String name = o.getClass().getName();
        return new EventHandler(o, hashCode, name, methods);
    }


    public static List<Method> getAnnotatedMethods(Object object, Class<? extends Annotation> annotation){
        Method[] methods = object.getClass().getDeclaredMethods();
        List<Method> result = new ArrayList<>();
        for (Method method : methods){
            method.setAccessible(true);
            if (method.isAnnotationPresent(annotation))
                result.add(method);
        }
        return result;
    }
}
