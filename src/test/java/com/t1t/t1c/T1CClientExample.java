package com.t1t.t1c;

import com.t1t.t1c.core.pojo.Environment;

/**
 * Created by michallispashidis on 02/11/2017.
 */
public class T1CClientExample {
    public static void main (String []args){
        ConfigObject config = new ConfigObject(Environment.DEV,"v0.1.0","build.x");
        T1CClient client = new T1CClient(config);
    }
}
