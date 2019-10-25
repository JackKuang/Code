package com.hurenjieee.code.pattern;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Jack
 * @date 2019/7/7 15:51
 */
public class PatternTest {

    //  ## 正则表达式的运用
    @Test
    public void patternCheck() {
        String str = "中文";
        String pattern = "^[\\u4e00-\\u9fa5]{0,}$";
        Assert.assertTrue(Pattern.matches(pattern, str));
    }

    @Test
    public void patternReplace() {
        String str = "test中文123🤝";
        System.out.println(str.replaceAll("[^\\u4e00-\\u9fa5]", ""));
    }

    @Test
    public void patternMatch() {
        String str = "1.first 2.second 3.third 11.eleventh";
        Pattern orderPattern = Pattern.compile("(?<=\\d\\.)\\w*(?=\\s?)");
        Matcher matcher = orderPattern.matcher(str);
        while (matcher.find()) {
            String title = matcher.group();
            System.out.println(title);
        }
    }


    @Test
    public void patternSplit() {
        String str = "1.first 2.second 3.third 11.eleventh";
        String[] strs = str.split("\\d*\\.");
        Arrays.stream(strs).forEach(System.out::println);
    }
    //
    //first
    //second
    //third
    //eleventh


    @Test
    public void group() {
        String url = "http://www.baidu.com:80/a/b.html";
        Pattern urlPattren = Pattern.compile("(\\w+):\\/\\/([^/:]+)(:\\d*)?([^# ]*)");
        // (\w+)  :\/\/   ([^/:]+)        (:\d*)   ?([^# ]*)
        // http   ://     www.baidu.com   :80      /a/b.html
        Matcher matcher = urlPattren.matcher(url);
        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(matcher.group(i));
            }
        }
    }
    //http://www.baidu.com:80/a/b.html
    //http
    //www.baidu.com
    //:80
    ///a/b.html

    @Test
    public void patternUrl() {
        String url = "https://www.baidu.com?name=jawil&age=23";
        Pattern urlPattren = Pattern.compile("(?<=[?&])(\\w+)=(\\w+)");
        Matcher matcher = urlPattren.matcher(url);
        while (matcher.find()) {
            System.out.println(matcher.group(1)+"="+matcher.group(2));
        }
    }
    // name=jawil
    // age=23

    @Test
    public void findDuplicate() {
        String str = "Is is the cost of of gasoline going up up";
        Pattern pattern = Pattern.compile("\\b([a-z]+) \\1\\b");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
    //of of
    //up up


    @Test
    public void replaceDuplicate() {
        String str = "Is is the cost of of gasoline going up up";
        String resultString = str.replaceAll("\\b([a-z]+) \\1\\b", "$1");
        System.out.println(resultString);
    }

    @Test
    public void hidePhone() {
        String phone = "15312341123";
        String resultString = phone.replaceAll("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])(?:\\d{4})(\\d{4})$", "$1****$2");
        System.out.println(resultString);
    }

    @Test
    public void foramtterMoney() {
        String money = "123456789000";
        String resultString = money.replaceAll("(?=(\\B)(\\d{3})+$)", ",");
        System.out.println(resultString);
        //123,456,789,000
    }


    @Test
    public void foramtterDate() {
        String date = "20190710";
        String resultString = date.replaceAll("(?=(\\d{2}|\\d{4})$)", "-");
        System.out.println(resultString);
        //2019-07-10
    }
}
