package com.lin.common.utils;

import com.lin.common.constant.CommonConstant;
import com.lin.common.error.CustomRuntimeException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Base64;

public class CustomFileUtils {
    private CustomFileUtils() {
    }

    /**
     * 把文件转换成img src 的内容.
     *
     * @param path .
     * @return 返回字符串.
     * @throws IOException 异常.
     */
    public static String convertToBase64(String path) throws IOException {
        if (null == path || path.equals("")) {
            return null;
        }
        byte[] b = FileUtils.readFileToByteArray(new File(path));
        String suffix = path.substring(path.lastIndexOf(".") + 1, path.length());
        return String.format(CommonConstant.IMG_BASE64_PREFIX, suffix) + Base64.getEncoder().encodeToString(b);
    }

    /**
     * 通过图片转换成文件并返回路径.
     *
     * @param fileBase64 图片内容.
     * @param prefixPath 文件前缀.
     * @return 返回文件路径.
     * @throws IOException 异常.
     */
    public static String buildFile(String fileBase64, String suffix, String prefixPath, String serializableNo) throws IOException, CustomRuntimeException {
        if (null == fileBase64 || fileBase64.equals("")) {
            return null;
        }
        String path = prefixPath + File.separator + serializableNo + "." + suffix;
        FileUtils.writeByteArrayToFile(new File(path), Base64.getDecoder().decode(fileBase64));
        return path;
    }

    public static String buildFile(String fileBase64, String prefixPath, String serializableNo) throws IOException, CustomRuntimeException {
        if (null == fileBase64 || fileBase64.equals("")) {
            return null;
        }
        String[] img = fileBase64.split(CommonConstant.SPLIT_BASE64);
        final int startIndex = 11;
        String suffix = img[0].substring(startIndex);
        String path = prefixPath + File.separator + serializableNo + "." + suffix;
        FileUtils.writeByteArrayToFile(new File(path), Base64.getDecoder().decode(img[1]));
        return path;
    }

    /**
     * 获取远程URL的头像并存储到本地，返回本地URL.
     *
     * @param url
     * @param prefixPath
     * @return
     * @throws IOException
     * @throws CustomRuntimeException
     */
    public static String getBase64ByUrl(String url, String prefixPath, String serializableNo) throws IOException, CustomRuntimeException {
        URI u = URI.create(url);
        InputStream is = u.toURL().openStream();
        byte[] bytes = IOUtils.toByteArray(is);
        String path = prefixPath + File.separator + CommonConstant.CHATS + File.separator + serializableNo + "." + CommonConstant.DEFAULT_PREFIX;
        FileUtils.writeByteArrayToFile(new File(path), bytes);
        return path;
    }

    /**
     * @param url 文件（图片）的URL.
     * @return base的流
     * @throws IOException
     */
    public static String urlToBase(String url) throws IOException {
        if (null == url || url.equals("")) {
            return null;
        }
        URI u = URI.create(url);
        InputStream is = u.toURL().openStream();
        byte[] bytes = IOUtils.toByteArray(is);
//        return Base64.getEncoder().encodeToString(bytes);
        String suffix = url.substring(url.lastIndexOf(".") + 1, url.length());
        return String.format(CommonConstant.IMG_BASE64_PREFIX, suffix) + Base64.getEncoder().encodeToString(bytes);
    }

}
