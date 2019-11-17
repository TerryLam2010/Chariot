package cn.terrylam.framework.util;

/**
 * 命名转换工具类
 */
public class JavaNameUtil {
    public static String translate(String underscoreName,boolean isPascal){
        StringBuilder result = new StringBuilder();//返回字符结果
        if(underscoreName!=null && underscoreName.length()>0){
            boolean flag=false;
            char firstChar =underscoreName.charAt(0);//判断获取首字母
            if(isPascal){
                result.append(Character.toUpperCase(firstChar));//将首字母转换成大写
            }else{
                result.append(firstChar);
            }
            //从第二个字符开始循环
            for (int i=1;i<underscoreName.length();i++){
                char ch=underscoreName.charAt(i);
                //判断是否出现下划线
                if('_'==ch){
                    flag=true;
                }else {
                    if(flag){
                        result.append(Character.toUpperCase(ch));
                        flag=false;
                    }else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();//返回转换字符
    }

    /**
     * 骆驼命名法
     * @param str
     * @return
     */
    public static String toCamel(String str){
        return translate(str,false);
    }

    /**
     * 帕斯卡命名法
     * @param str
     * @return
     */
    public static String toPascal(String str){
        return translate(str,true);
    }

    /**
     * 转换数据类型
     * @param dbTypeName
     * @return
     */
    public static String dbtype2JavaType(String dbTypeName){
        String javaType=null;
        switch (dbTypeName){
            case "VARCHAR":javaType="String";break;
            case "BIGINT":javaType="long";break;
            case "INT":javaType="long";break;
            case "TINYINT":javaType="int";break;
            case "DATETIME":javaType="Date";break;
            default: javaType="String";break;
        }
        return javaType;
    }

}