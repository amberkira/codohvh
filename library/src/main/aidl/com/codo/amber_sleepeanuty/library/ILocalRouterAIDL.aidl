
package com.codo.amber_sleepeanuty.library;

import com.codo.amber_sleepeanuty.library.RouterRequest;
import com.codo.amber_sleepeanuty.library.ActionResult;

interface ILocalRouterAIDL {
    void respondAsync(in RouterRequest request);
    ActionResult route(in RouterRequest request);
}
