package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Tuple<X> {
    private static AtomicInteger count = new AtomicInteger(0);

    final public X x;
    final public Integer y;

    public Tuple(X x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Tuple(X x){
        this.x =x;
        this.y = count.getAndIncrement();
    }


    @Override
    public String toString() {
        return "Tuple{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
