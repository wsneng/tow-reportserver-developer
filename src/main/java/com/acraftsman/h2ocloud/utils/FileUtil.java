package com.acraftsman.h2ocloud.utils;

import com.acraftsman.h2ocloud.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * File工具类，扩展 hutool 工具包
 * @author Zheng Jie
 * @date 2018-12-27
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;

    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * 获取文件扩展名，不带 .
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 将文件名解析成文件的上传路径
     */
    public static File upload(MultipartFile file) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String name = getFileNameNoEx(file.getOriginalFilename());
        String suffix = getExtensionName(file.getOriginalFilename());
//        System.out.println(name);
//        System.out.println(suffix);
        String nowStr = "-" + format.format(date);
        try {
            String fileName = name + nowStr + "." + suffix;
/*
            // 指定路径
            File directory = new File("");//参数为空
            String courseFile = directory.getCanonicalPath() ;
            System.out.println(courseFile);

            // 父级目录
            File ff = new File(System.getProperty("user.dir"));
            String dir = ff.getParent();
            System.out.println( dir );
            String courseFile1 = dir + "/webapps/webroot/WEB-INF/reportlets/" + fileName;*/
            String courseFile1 = "D:\\Soft2\\apache-tomcat-9.0.2\\apache-tomcat-9.0.2\\webapps\\webroot\\WEB-INF\\reportlets\\user\\" + fileName;
//            System.out.println(courseFile1);
            File dest1 = new File(courseFile1).getCanonicalFile();
//            System.out.println(dest1);
            // 检测是否存在目录
            if (!dest1.getParentFile().exists()) {
                dest1.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(dest1);
            return dest1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void checkSize(long maxSize, long size) {
        if(size > (maxSize * 1024 * 1024)){
            throw new BadRequestException("文件超出规定大小");
        }
    }
    /**
     * 判断两个文件是否相同
     */
    public static boolean check(File file1, File file2) {
        String img1Md5 = getMd5(file1);
        String img2Md5 = getMd5(file2);
        return img1Md5.equals(img2Md5);
    }

    /**
     * 判断两个文件是否相同
     */
    public static boolean check(String file1Md5, String file2Md5) {
        return file1Md5.equals(file2Md5);
    }

    private static byte[] getByte(File file) {
        // 得到文件长度
        byte[] b = new byte[(int) file.length()];
        try {
            InputStream in = new FileInputStream(file);
            try {
                in.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    private static String getMd5(byte[] bytes) {
        // 16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(bytes);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            // 移位 输出字符串
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMd5(File file) {
        return getMd5(getByte(file));
    }

}
