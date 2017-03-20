// IWideRouterAIDL.aidl
package com.codo.amber_sleepeanuty.library;

import com.codo.amber_sleepeanuty.library.RouterRequest;
// Declare any non-default types here with import statements

interface IWideRouterAIDL {
    void respondAsync(RouterRequest request);
    String route(RouterRequest request);
    void stopWideRouter();

}
