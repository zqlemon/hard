package tokeniz;


public class Tok implements TokenizImpl {
    @Override
    public String[] tokeizSqlkw(String str) {
        abc.sql(str);
        String [] strArray = abc.kW.toArray(new String[abc.kW.size()]);
//        for (String tmp : strArray ) {
//            System.out.println(tmp + " ");
//        }//可输出查看有什么关键字
        return strArray;
    }
    @Override
    public String[] tokeizSqlunkw(String str) {
        String [] strArray1 = abc.unkeyWords.toArray(new String[abc.unkeyWords.size()]);
        System.out.println("\n");
//        for (String tmp : strArray1 ) {
//            System.out.println(tmp + " ");
//        }//可输出查看有什么非关键字
        return strArray1;
    }
}
