package com.t1t.t1c;

import com.t1t.t1c.configuration.LibConfig;
import com.t1t.t1c.configuration.T1cConfigParser;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1cClient {
    public T1cClient(LibConfig config) {
        init(config);
    }

    private void init(LibConfig config) {
        T1cConfigParser clientConfig = new T1cConfigParser(config);
    }
}

