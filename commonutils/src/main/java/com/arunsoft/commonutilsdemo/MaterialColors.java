package com.arunsoft.commonutilsdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Collections;
import java.util.HashMap;

public class MaterialColors {
    private static final HashMap<String, Integer> map = new HashMap<String, Integer>();
    static {
        map.put("red50",);
        Collections.unmodifiableMap(map);
    }

    public static int getColor(String color){
        return map.get(color);
    }
}
