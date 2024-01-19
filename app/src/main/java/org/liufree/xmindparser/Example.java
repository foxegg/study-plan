package org.liufree.xmindparser;

import org.apache.commons.compress.archivers.ArchiveException;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liufree liufreeo@gmail.com
 * @Classname Example
 * @Description 测试例子
 * @Date 2020/4/28 13:12
 */
public class Example {

    public static void main(String[] args) throws DocumentException, ArchiveException, IOException {
        try {
            // String fileNae = "doc/XmindZen解析.xmind";
            InputStream fileName = null;
            String res = XmindParser.parseJson(fileName);
            System.out.println(res);

            Object root = XmindParser.parseObject(fileName);
            System.out.println(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
