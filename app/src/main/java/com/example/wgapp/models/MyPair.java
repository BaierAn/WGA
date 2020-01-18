package com.example.wgapp.models;

import android.util.Pair;

public class MyPair<F , S>  extends Pair<F , S> {
    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public MyPair(F first, S second) {
        super(first, second);
    }

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */


    public String toString(){
        return first.toString();
    }
}
