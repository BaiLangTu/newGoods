package red.mlz.module.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetImageUrl {

    // ar的获取
    public static ImageInfo getImageInfo(String imageUrl) {
        // 正则匹配宽和高
        Pattern pattern = Pattern.compile("_(\\d+)x(\\d+)");  // 假设宽高格式是 _宽x高
        Matcher matcher = pattern.matcher(imageUrl);

        if (matcher.find()) {
            int width = Integer.parseInt(matcher.group(1));  // 宽度
            int height = Integer.parseInt(matcher.group(2));  // 高度
            double ar = (double) width / height;  // 计算AR值


            // 返回 ImageInfo 对象
            return new ImageInfo(imageUrl, ar);
        }

        // 如果没有匹配到宽高，返回默认值
        return new ImageInfo(imageUrl, 1.0);  // 默认值
    }

}
