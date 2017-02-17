package com.mjhp.eventbus;

import com.mjhp.eventbus.utils.EventHandler;
import com.mjhp.eventbus.utils.EventHandlerUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Created by dahlia on 2/17/17.
 * Simple eventBus
 * Thanks Otto eventBus cause its coding style
 * I think that they are similar just in style
 * Mbus might has very different structure to work
 * Created by mohsen yazdani just for fun!
 * if it was helpful please notify me in alimohammadi340@gmail.com
 * Finally, again and again thanks Otto eventBus (Square)
 */
public class MBus {
    private static List<EventHandler> eventsHandlers = new ArrayList<>();
    private static Queue<Object> events = new LinkedList<>();


    /**
     * Register new object
     * @param object object to register as event bus
     *               this object must have method(s) annotated with @RegisterBus
     *               until object is not unregistered, annotated methods in this object can get event
     */
    public static void register(Object object){
        if (!issetEventHandler(object))
            eventsHandlers.add(EventHandlerUtils.newInstance(object));
        subscribeQueue();
    }


    /**
     * Unregister registered object
     * @param object object to unregister,
     *               it should be registered before
     *               annotated methods in this object wont get any event after unRegistering
     */
    public static void unregister(Object object){
        removeEventHandler(object);
    }


    /**
     * Subscribe new event to registered object,
     * every method that has same parameter type with @param object will run
     * @param object a class to be subscribed
     */
    public static void subscribe(Object object){
        boolean isRunned = false;
        for (EventHandler eventHandler : eventsHandlers){
            List<Method> methods = eventHandler.getMethodsByParameter(object.getClass().getName());
            for (Method method : methods){
                method.setAccessible(true);
                try {
                    method.invoke(eventHandler.getObject(),
                            object);
                    isRunned = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if (!isRunned)
            events.add(object);
    }


    /**
     * Some events may not be subscribed successfully
     * they will be saved in a linked list
     * this method will try subscribing unSubscribed event
     */
    public static void subscribeQueue(){
        for (int i = 0; i < events.size(); i++){
            subscribe(events.poll());
        }
    }


    public static boolean issetEventHandler(Object object){
        int hashCode = object.hashCode();
        String name = object.getClass().getName();
        for (EventHandler eventHandler : eventsHandlers){
            if (eventHandler.getHashCode() == hashCode && eventHandler.getName().equals(name))
                return true;
        }
        return false;
    }


    public static void removeEventHandler(Object object){
        int hashCode = object.hashCode();
        String name = object.getClass().getName();
        for (EventHandler eventHandler : eventsHandlers){
            if (eventHandler.getHashCode() == hashCode && eventHandler.getName().equals(name)){
                eventsHandlers.remove(eventHandler);
                return;
            }
        }
    }
}

/**
 * -------- Very simple example --------
 *
 * public static void main(String[] args){
 *     new Test();
 * }
 *
 * public Test(){
 *     MBus.register(this);
 *     MBus.subscribe("hello"); // will run showMeMessage("hello");
 *     MBus.subscribe(2); // will run showMeInteger(2);
 *     MBus.unregister(this);
 * }
 *
 *
 * @RegisterBus
 * public void showMeMessage(String msg){
 *     System.out.println(msg);
 * }
 *
 *
 * @RegisterBus
 * public void showMeInteger(int a){
 *     System.out.println(a);
 * }
 *
 *
 *
 *
 *
 *
 */
