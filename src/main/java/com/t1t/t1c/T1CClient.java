package com.t1t.t1c;

import com.t1t.t1c.core.Core;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1CClient {
    public T1CClient(LibConfig config) {init(config);}
    private void init(LibConfig config) {
        T1CConfigParser clientConfig = new T1CConfigParser(config);
    }
}

