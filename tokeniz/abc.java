package tokeniz;

import com.sun.istack.internal.localization.NullLocalizable;

import java.util.*;

public class abc{
    //关键字
    static String[] keyWord = {"into", "use", "show", "database", "create",
            "table", "drop", "alter", "database", "NAME", "put", "LIMIT",
            "METHOD", "get", "list", "delete", "deleteall", "STARTROW",
            "ENDROW", "enable", "disable", "scan", "count", "exsits",
            "describle", "truncate", "{", "}", ",","=>",":", ";"};
    static List<String> kW = new ArrayList<String>();
    static  List<String> unkeyWords = new ArrayList<String>();
    static ArrayList<String> keyWords = null;
    //put values{}分支
    static String[] keyWord1 ={"put","values"};
    static ArrayList<String> keyWords1 = null;
    //指向当前所读到字符串的位置的指针
    static int p, lines;

    public static void main(String[] args) {
     sql( "put values {infp1:id,xxx,info2:name,yyy,info1:age,12};");
        for (String tmp : kW) {
            System.out.println(tmp + " ");
        }
        System.out.println("\n");
        for (String tmp : unkeyWords) {
            System.out.println(tmp + " ");
        }//可输出查看有什么关键字或者非关键字
  }
    public static void sql(String ac) {

        if(findcode(ac)){
            change1();
            Scanner input = new Scanner(ac);
            String str = input.nextLine();
            analyze1(str);
    }
        else{
            change();
            Scanner input = new Scanner(ac);
            String str = input.nextLine();
            analyze(str);}
        }
    public static boolean findcode(String str) {
       p = 0;
        char ch;
        str = str.trim();
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            if (Character.isDigit(ch)) {
                digitCheck(str);
            }
            else if (ch == ' '&&ch == ':'&&ch == ',') {
                continue;
            } else if (Character.isLetter(ch) || ch == '_'||ch=='='||ch=='>') {
                String toke = String.valueOf(str.charAt(p++));
                char c;
                for (; p < str.length(); p++) {
                    c = str.charAt(p);
                    if (!Character.isLetterOrDigit(c) && c != '_' && c != '=' && c != '>') {
                        break;
                    } else {
                        toke += c;
                    }
                }
                if (toke.equals("values")) {
                    return true;
                }
            }
            else {
                symbolCheck1(str);
            }}
        return false;
    }
    //初始化把数组转换为ArrayList
    public  static void change() {
        keyWords = new ArrayList<>();
        Collections.addAll(keyWords, keyWord);
    }
    public  static void change1() {
        keyWords = new ArrayList<>();
        Collections.addAll(keyWords, keyWord1);
    }
    public static void analyze(String str) {
        p = 0;
        char ch;
        str = str.trim();
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            if (Character.isDigit(ch)) {
                digitCheck(str);
            } else if (Character.isLetter(ch) || ch == '_'||ch=='='||ch=='>') {
                letterCheck(str);
            }
            else if (ch == '"') {
                stringCheck(str);
            } else if (ch == '\'') {
                stringCheck1(str);
            }
            else if (ch == ' ') {
                continue;
            } else if (ch == ';') {
                break;
            }
            else {
                symbolCheck(str);
            }
        }
    }
//    put values分支
    public static void analyze1(String str) {
        p = 0;
        char ch;
     str = str.trim();
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            if (Character.isDigit(ch)) {
                digitCheck(str);
            }
            else if (ch == ' '&&ch == ':'&&ch == ',') {
                continue;
            } else if (Character.isLetter(ch) || ch == '_'||ch=='='||ch=='>') {
                letterCheck1(str);
            }
            else if (ch == '"') {
                stringCheck(str);
            }
            else if (ch == '\'') {
                stringCheck1(str);
            }
            else if (ch == ' ') {
                continue;
            }
        else if (ch == ';') {
                break;
            }
            else {
                symbolCheck1(str);
            }
        }
    }
    public static void symbolCheck1(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        if (keyWords.contains(toke)) {
            p--;
            kW.add(toke);}
        else {
            if (!keyWords.contains(toke)){
                p--;
            }else {
                p--;
                System.out.println(lines + "line" + ": " + toke + " is wrong");
            }
        }
    }

    public static void digitCheck(String str) {
        String toke = String.valueOf(str.charAt(p++));//转换成字符串
        //判断数字的小数点是否有且是否大于1
        int flag = 0;
        boolean err = false;
        char ch;
        for (; p < str.length(); p++) {
            ch = str.charAt(p);//获取字符
            if (ch == ' ' || (!Character.isLetterOrDigit(ch) && ch != '.')) {
                break;
            } else if (err) {
                toke += ch;
            } else {
                toke += ch;
                if (ch == '.') {
                    if (flag == 1) {
                        err = true;
                    } else {
                        flag++;
                    }
                } else if (Character.isLetter(ch)) {
                    err = true;
                }
            }
        }
        if (toke.charAt(toke.length() - 1) == '.') {
            err = true;
        }
        if (err) {
            System.out.println(lines + "line" + ": " + toke + " is wrong");
        } else {
            unkeyWords.add(toke);
        }
        if (p != str.length() - 1 || (p == str.length() - 1 && !Character.isDigit(str.charAt(p)))) {
            p--;
        }
    }

    //标识符，关键字的识别
    public static void letterCheck(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
          if (!Character.isLetterOrDigit(ch) && ch != '_' && ch!='=' && ch!='>'){
                break;
            }
                else {
                toke += ch;
            }
        }
        if (keyWords.contains(toke)) {
            kW.add(toke);
            }
         else {
            unkeyWords.add(toke);
        }
        if (p != str.length() - 1 || (p == str.length() - 1 && (!Character.isLetterOrDigit(str.charAt(p)) && str.charAt(p) != '_'))) {
            p--;
        }
    }
    public static void letterCheck1(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            if (!Character.isLetterOrDigit(ch) && ch != '_' && ch!='=' && ch!='>'){
                break;
            }
            else {
                toke += ch;
            }
        }
        if (toke.equals("values")) {//输出固定关键字
            kW.add("put");
            kW.add(":");
            kW.add("{");
            kW.add("}");
        }
        else if (!keyWords.contains(toke)) {
            unkeyWords.add(toke);
        }
        if (p != str.length() - 1 || (p == str.length() - 1 && (!Character.isLetterOrDigit(str.charAt(p)) && str.charAt(p) != '_'))) {
            p--;
        }
    }


    //    符号的识别
    public static void symbolCheck(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        if (keyWords.contains(toke)) {
            p--;
            kW.add(toke);}
        else {
            if (!keyWords.contains(toke)){
                p--;
                unkeyWords.add(toke);
            }else {
                p--;
                System.out.println(lines + "line" + ": " + toke + " is wrong");
            }
        }

    }

    //字符串检查
    public static void stringCheck(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            toke += ch;
            if (ch == '"') {
                break;
            }
        }
        if (toke.charAt(toke.length() - 1) != '"') {
            System.out.println(lines + "line" + ": " + toke + " is wrong");
        } else {
           toke=toke.replace("\"","");
            unkeyWords.add(toke);
        }
    }
    public static void stringCheck1(String str) {
        String toke = String.valueOf(str.charAt(p++));
        char ch;
        for (; p < str.length(); p++) {
            ch = str.charAt(p);
            toke += ch;
            if (ch == '\'') {
                break;
            }
        }
        if (toke.charAt(toke.length() - 1) != '\'') {
            System.out.println(lines + "line" + ": " + toke + " is wrong");
        } else {
            toke=toke.replace("\'","");
            unkeyWords.add(toke);
        }
    }
}
