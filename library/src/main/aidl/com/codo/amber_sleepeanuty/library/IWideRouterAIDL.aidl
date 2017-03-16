// IWideRouterAIDL.aidl
package com.codo.amber_sleepeanuty.library;

// Declare any non-default types here with import statements

interface IWideRouterAIDL {
    void respondAsync(String request);
    String route(String request);
    void stopWideRouter();

}
