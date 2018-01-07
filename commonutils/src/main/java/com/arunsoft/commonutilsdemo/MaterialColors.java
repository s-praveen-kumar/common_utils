package com.arunsoft.commonutilsdemo;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class MaterialColors {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(Context c){
        return c.getColor(R.color.red500);
    }
}
