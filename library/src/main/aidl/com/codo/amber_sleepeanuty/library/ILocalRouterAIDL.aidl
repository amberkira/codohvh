
package com.codo.amber_sleepeanuty.library;

interface ILocalRouterAIDL {
    void respondAsync(RouterRequest request);
    string route(Context context,RouterRequest request);
}
