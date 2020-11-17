package com.porpit.ppcore.util;

import net.minecraftforge.fml.common.versioning.ComparableVersion;
import org.apache.logging.log4j.Logger;


public class VersionCompare {
    public static int compareVersion(String version1, String version2, Logger logger) {
        try {
            ComparableVersion v1 = new ComparableVersion(version1);
            ComparableVersion v2 = new ComparableVersion(version2);
            return v1.compareTo(v2);
        } catch (Exception e) {
            logger.error("版本比较失败~");
        }
        return 0;


       /* try{
            if (version1 == null || version2 == null) {
                return 0;
            }
            String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
            String[] versionArray2 = version2.split("\\.");
            int idx = 0;
            int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
            int diff = 0;
            while (idx < minLength
                    && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                    && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
                ++idx;
            }
            //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
            diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
            return diff;
        }catch (Exception e){
            PPCore.logger.error("版本比较失败~");
        }
        return 0;*/


    }

}
