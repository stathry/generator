package org.free.commons.generator.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 边界原子递增
 */
public class BoundAtomicInteger extends AtomicInteger {
    
    private static final long serialVersionUID = -8650032295035697897L;
    
    private int bound;

    public BoundAtomicInteger(int i, int bound) {
        super(i);
        this.bound = bound;
    }

    public int incrementWithBound() {
        for (;;) {
            int current = get();
            if(current >= bound) {
                set(1);
                return get();
            }
            int next = current + 1;
            if (compareAndSet(current, next))
                return next;
        }
    }

}
