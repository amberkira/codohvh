package com.codo.amber_sleepeanuty.library.base;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by amber_sleepeanuty on 2017/3/10.
 */

public abstract class BaseLocalRouter {
    public abstract void registerProvider(Context context,@NonNull BaseProvider provider);
}
