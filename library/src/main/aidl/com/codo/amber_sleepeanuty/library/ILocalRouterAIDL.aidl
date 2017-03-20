
package com.codo.amber_sleepeanuty.library;

import com.codo.amber_sleepeanuty.library.RouterRequest;

interface ILocalRouterAIDL {
    void respondAsync(RouterRequest request);
    String route(RouterRequest request);
}
