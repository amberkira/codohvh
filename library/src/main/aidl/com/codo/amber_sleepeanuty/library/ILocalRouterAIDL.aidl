// ILocalRouterInterface.aidl
package com.codo.amber_sleepeanuty.library;

// Declare any non-default types here with import statements

interface ILocalRouterInterface {

    void respondAsync(RouterRequest request);
    RouteRespond route(Context context,RouterRequest request);
}
