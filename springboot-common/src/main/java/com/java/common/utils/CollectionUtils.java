package com.java.common.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr.BH
 */
public class CollectionUtils<T> {

    /**
     * 去除重复
     * @param target
     * @return
     */
    public static<T>  Set<T> toSet(List<T> target){
        Set<T> set = new HashSet();
        set.addAll(target);
        return set;
    }

}
