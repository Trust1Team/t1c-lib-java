package com.t1t.t1c;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1CClient {
    public T1CClient(ConfigObject config) {init(config);}
    private void init(ConfigObject config) {
        T1CConfigParser clientConfig = new T1CConfigParser(config);
    }
}

