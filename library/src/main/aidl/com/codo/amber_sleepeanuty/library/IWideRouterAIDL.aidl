// IWideRouterAIDL.aidl
package com.codo.amber_sleepeanuty.library;

import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.ActionResult;


interface IWideRouterAIDL {
    void respondAsync(in RouterRequest request);
    ActionResult route(in RouterRequest request);
    void stopWideRouter();

}
