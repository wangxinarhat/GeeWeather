package wang.wangxinarhat.geeweather.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fesen on 2015/7/4.
 */
public class StringUtils {


    /**
     * 判别手机是否为正确手机号码；
     * 号码段分配如下：
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((13)|(14)|(15)|(17)|(18))\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否输入内容是否包含字母
     *
     * @param password
     * @return
     */
    public static boolean haveLetter(String password) {
        String str = ".*[a-zA-Z]+.*";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 是否为vip用户
     *
     * @param s
     * @return
     */
    public static boolean isVip(String s) {
        return "1".equals(s) || "3".equals(s);
    }

    /**
     * 判断字符串是否有值
     * @param value
     * @return
     */
    public static boolean hasMeaningful(String value) {
        return value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim());
    }

    /**
     * 判断是否中文字符
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }


}
