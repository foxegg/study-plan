package org.liufree.xmindparser;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSON;

import org.apache.commons.compress.archivers.ArchiveException;
import org.dom4j.DocumentException;
import org.json.JSONException;
import org.liufree.xmindparser.pojo.JsonRootBean;

/**
 * @author liufree liufreeo@gmail.com
 * @Classname XmindParser
 * @Description 解析主体
 * @Date 2020/4/27 14:05
 */
public class XmindParser {
    public static final String xmindZenJson = "content.json";
    public static final String xmindLegacyContent = "content.xml";
    public static final String xmindLegacyComments = "comments.xml";

    /**
     * 解析脑图文件，返回content整合后的内容
     *
     * @param xmindFile
     * @return
     * @throws IOException
     * @throws ArchiveException
     * @throws DocumentException
     */
    public static String parseJson(InputStream xmindFile) throws IOException, ArchiveException, DocumentException, JSONException {
        String res = ZipUtils.extract(xmindFile);

        String content = null;
        if (isXmindZen(res, xmindFile)) {
            content = getXmindZenContent(xmindFile,res);
        } else {
            content = getXmindLegacyContent(xmindFile,res);
        }
        //System.out.println("content:"+content);

        //删除生成的文件夹
        File dir = new File(res);
        boolean flag = deleteDir(dir);
        if (flag) {
            // do something
        }
        JsonRootBean jsonRootBean = JSON.parseObject(content, JsonRootBean.class);
       return content;
    }

    public static Object parseObject(InputStream xmindFile) throws DocumentException, ArchiveException, IOException, JSONException {
        String content = parseJson(xmindFile);
        JsonRootBean jsonRootBean = JSON.parseObject(content, JsonRootBean.class);
        return jsonRootBean;
    }


    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * @return
     */
    public static String getXmindZenContent(InputStream xmindFile,String extractFileDir) throws IOException, ArchiveException {
        List<String> keys = new ArrayList<>();
        keys.add(xmindZenJson);
        Map<String, String> map = ZipUtils.getContents(keys, xmindFile,extractFileDir);
        String content = map.get(xmindZenJson);
       // content = content.substring(1, content.lastIndexOf("]"));

        content = XmindZen.getContent(content);
        return content;
    }

    /**
     * @return
     */
    public static String getXmindLegacyContent(InputStream xmindFile,String extractFileDir) throws IOException, ArchiveException, DocumentException, JSONException {
        List<String> keys = new ArrayList<>();
        keys.add(xmindLegacyContent);
        keys.add(xmindLegacyComments);
        Map<String, String> map = ZipUtils.getContents(keys, xmindFile,extractFileDir);

        String contentXml = map.get(xmindLegacyContent);
        String commentsXml = map.get(xmindLegacyComments);
        String xmlContent = XmindLegacy.getContent(contentXml, commentsXml);

        return xmlContent;
    }


    private static boolean isXmindZen(String res, InputStream xmindFile) throws IOException, ArchiveException {
        //解压
        File parent = new File(res);
        if (parent.isDirectory()) {
            String[] files = parent.list(new ZipUtils.FileFilter());
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].equals(xmindZenJson)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static JsonRootBean readFile(InputStream inputStream) {
        try {
            String res = XmindParser.parseJson(inputStream);
            Log("res", res);
            JsonRootBean jsonRootBean = JSON.parseObject(res, JsonRootBean.class);
            Log("jsonRootBean", JSON.toJSONString(jsonRootBean));
            return jsonRootBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void Log(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }


}
