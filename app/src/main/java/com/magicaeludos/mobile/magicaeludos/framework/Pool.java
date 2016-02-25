/*
 * Copyright (c) 2016 Anders Lunde
 */

package com.magicaeludos.mobile.magicaeludos.framework;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Anders on 28.01.2015.
 */
public class Pool<T> {

    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize){
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

    //Creates the new object that is being supplied by the factory
    //In our case its touch pointers.
    public T newObject(){
        T object = null;

        if (freeObjects.size() == 0){
            object = factory.createObject();
        }else{
            object = freeObjects.remove(freeObjects.size() -1);
        }
        return object;
    }

    //as long as there is space in the object list
    public void free(T object) {
        if (freeObjects.size() < maxSize){
            freeObjects.add(object);
        }
    }
}
