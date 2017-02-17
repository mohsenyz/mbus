# MBus (Android (java) event bus library)

very simple android eventbus library
feautures:

 * very very light, about ~ 1kb
 * support java and android
 * very fast and easy to use
 

its code styling is like [otto](square.github.io/otto/) eventbus (really thanks square cause their otto eventbus library)
i wrote it just for training!
download latest jar [here](https://github.com/mphj/mbus/releases/download/1.0.0/mbus.jar)

to register (when you need event handling):
```java
MBus.register(this);
```
to unregister (when you dont need event handling):
```java
MBus.unregister(this);
```
to subscribe new event:
```java
MBus.subscribe("hello");
MBus.subscribe(new MyObject());
```
to handle events:
```java
@RegisterBus
public void showMeString(String str){
}

@RegisterBus
public void customObj(MyObject obj){
}
```


### Thanks:

 * [otto](square.github.io/otto/) for its beautifull coding style
