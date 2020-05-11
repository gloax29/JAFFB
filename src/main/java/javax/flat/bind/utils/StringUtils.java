package javax.flat.bind.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * outil pour ne pas avoir la librairie StringUtile APACHE<br />
 * les code son issu de cette Librairie
 * 
 * @author Gloaguen Joel
 */
public class StringUtils {

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * suprime les espaces devant et deriere
     * 
     * @param str
     * @return
     */
    public static String strip(String str) {
        return strip(str, null);
    }

    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * suprime les espaces devant ou le cractere en parmetre
     * 
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * suprime les espaces deriere ou le cractere en parmetre
     * 
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != INDEX_NOT_FOUND) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String repeat(char ch, int repeat) {
        char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a String is not empty (""), not null and not whitespace only.
     * </p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {

        return isBlank(str) ? false : true;
    }

    public static String Empty() {

        return new String();
    }

    /**
     * Splits this string around matches of the given <a href="../util/regex/Pattern.html#sum">regular expression</a>.
     * <p>
     * The array returned by this method contains each substring of this string that is terminated by another substring that matches the given
     * expression or is terminated by the end of the string. The substrings in the array are in the order in which they occur in this string. If the
     * expression does not match any part of the input then the resulting array has just one element, namely this string.
     * <p>
     * The <tt>limit</tt> parameter controls the number of times the pattern is applied and therefore affects the length of the resulting array. If
     * the limit <i>n</i> is greater than zero then the pattern will be applied at most <i>n</i>&nbsp;-&nbsp;1 times, the array's length will be no
     * greater than <i>n</i>, and the array's last entry will contain all input beyond the last matched delimiter. If <i>n</i> is non-positive then
     * the pattern will be applied as many times as possible and the array can have any length. If <i>n</i> is zero then the pattern will be applied
     * as many times as possible, the array can have any length, and trailing empty strings will be discarded.
     * <p>
     * The string <tt>"boo:and:foo"</tt>, for example, yields the following results with these parameters: <blockquote>
     * <table cellpadding=1 cellspacing=0 summary="Split example showing regex, limit, and result">
     * <tr>
     * <th>Regex</th>
     * <th>Limit</th>
     * <th>Result</th>
     * </tr>
     * <tr>
     * <td align=center>:</td>
     * <td align=center>2</td>
     * <td><tt>{ "boo", "and:foo" }</tt></td>
     * </tr>
     * <tr>
     * <td align=center>:</td>
     * <td align=center>5</td>
     * <td><tt>{ "boo", "and", "foo" }</tt></td>
     * </tr>
     * <tr>
     * <td align=center>:</td>
     * <td align=center>-2</td>
     * <td><tt>{ "boo", "and", "foo" }</tt></td>
     * </tr>
     * <tr>
     * <td align=center>o</td>
     * <td align=center>5</td>
     * <td><tt>{ "b", "", ":and:f", "", "" }</tt></td>
     * </tr>
     * <tr>
     * <td align=center>o</td>
     * <td align=center>-2</td>
     * <td><tt>{ "b", "", ":and:f", "", "" }</tt></td>
     * </tr>
     * <tr>
     * <td align=center>o</td>
     * <td align=center>0</td>
     * <td><tt>{ "b", "", ":and:f" }</tt></td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * @param regex the delimiting regular expression
     * @param limit the result threshold, as described above
     * @return the List of strings computed by splitting this string around matches of the given regular expression
     * @throws PatternSyntaxException if the regular expression's syntax is invalid
     * @see java.util.regex.Pattern
     */
    public static List<String> splitinList(String regex, CharSequence input) {

        return splitinList(regex, input, 0);

    }

    public static List<String> splitinList(String regex, CharSequence input, int limit) {
        int index = 0;
        boolean matchLimited = limit > 0;
        List<String> matchList = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(input);
        // Add segments before each match found
        while (m.find()) {
            if (!matchLimited || matchList.size() < limit - 1) {
                String match = input.subSequence(index, m.start()).toString();
                matchList.add(match);
                index = m.end();
            } else if (matchList.size() == limit - 1) { // last one
                String match = input.subSequence(index, input.length()).toString();
                matchList.add(match);
                index = m.end();
            }
        }

        // si pas de resultat
        if (index == 0) {
            matchList.add(input.toString());
            return matchList;
        }

        // ajout du reste de segment
        if (!matchLimited || matchList.size() < limit) {
            matchList.add(input.subSequence(index, input.length()).toString());
        }

        return matchList;
    }

    public static String separateur() {
        return null;
    }

    /**
     * <p>
     * Checks if the String contains only unicode digits. A decimal point is not a unicode digit and returns false.
     * </p>
     * <p>
     * <code>null</code> will return <code>false</code>. An empty String (length()=0) will return <code>true</code>.
     * </p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = true
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if only contains digits, and is non-null
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Counts how many times the substring appears in the larger String.
     * </p>
     * <p>
     * A <code>null</code> or empty ("") String input returns <code>0</code>.
     * </p>
     *
     * <pre>
     * StringUtils.countMatches(null, *)       = 0
     * StringUtils.countMatches("", *)         = 0
     * StringUtils.countMatches("abba", null)  = 0
     * StringUtils.countMatches("abba", "")    = 0
     * StringUtils.countMatches("abba", "a")   = 2
     * StringUtils.countMatches("abba", "ab")  = 1
     * StringUtils.countMatches("abba", "xxx") = 0
     * </pre>
     *
     * @param str the String to check, may be null
     * @param sub the substring to count, may be null
     * @return the number of occurrences, 0 if either String is <code>null</code>
     */
    public static int countMatches(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    /**
     * Gets the convert time formtat.
     *
     * @param v the v
     * @return the convert time formtat
     */
    public static String getconvertTimeFormtat(Timestamp v) {
        StringBuffer buffer = new StringBuffer(repeat('0', 18));
        String str = v.toString().replaceAll("\\.", "").replaceAll(":", "").replaceAll("-", "").replaceAll(" ", "");
        buffer.replace(0, str.length(), str);

        return buffer.toString();

    }

    public static Timestamp getTimtampsValu(String v) {

        return Timestamp.valueOf(getconvertFormtatTime(v));

    }

    private static String getconvertFormtatTime(String string) {
        StringBuffer buffer = new StringBuffer(string);
        buffer.insert(14, ".").insert(12, ":").insert(10, ":").insert(8, " ").insert(6, "-").insert(4, "-");
        return buffer.toString();

    }
}
