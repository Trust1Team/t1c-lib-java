package com.t1t.t1c;

import com.t1t.t1c.configuration.Environment;
import com.t1t.t1c.configuration.LibConfig;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1CClientExample {
    public static void main(String[] args) {
        LibConfig config = new LibConfig(Environment.DEV, "v0.1.0", "build.x");
        T1cClient client = new T1cClient(config);
    }
}
