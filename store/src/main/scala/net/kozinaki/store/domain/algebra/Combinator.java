package net.kozinaki.store.domain.algebra;

import java.util.function.Function;

public class Combinator {
    interface Y<F> extends Function<Y<F>, F> {}

    public static <A, B> Function<A, B> Z(Function<Function<A, B>, Function<A, B>> f) {
        Y<Function<A, B>> r = w -> f.apply(x -> w.apply(w).apply(x));
        return r.apply(r);
    }

    public static void main(String[] args) {
        Function<Integer, Integer> factorial = Z(f -> x -> (x == 1) ? 1 : (x * f.apply(x - 1)));
        System.out.println(factorial.apply(10));
    }
}
