package com.delta.zf;

import com.delta.zf.sys.bean.EmBaseInfo;
import com.delta.zf.tools.BaseTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by Administrator on 2021/7/17.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        String path = System.getProperty("user.dir")+"//emkey.bin";
        System.out.println(path);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
        EmBaseInfo emBaseInfo = (EmBaseInfo) ois.readObject();
        System.out.println(emBaseInfo);
        ois.close();
    }
}
