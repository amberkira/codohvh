// IWideRouterAIDL.aidl
package com.codo.amber_sleepeanuty.library;

// Declare any non-default types here with import statements

interface IWideRouterAIDL {
    void respondAsync(RouterRequest request);
    string route(Context context,RouterRequest request);
    void stopWideRouter();

}
