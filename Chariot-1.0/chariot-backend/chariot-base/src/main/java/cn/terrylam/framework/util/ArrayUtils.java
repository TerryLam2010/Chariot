package cn.terrylam.framework.util;


/**
 * 数组操作类
 * 继承org.apache.commons.lang3.ArrayUtils，
 * 如需使用一些工具方法时请仔细查看其api，其已有的方法可直接调用避免重复造轮子
 * http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/ArrayUtils.html
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils{
	
	
	public static void main(String[] args) {
		// 1.打印数组
	    ArrayUtils.toString(new int[] { 1, 4, 2, 3 });// {1,4,2,3}
	    ArrayUtils.toString(new Integer[] { 1, 4, 2, 3 });// {1,4,2,3}
	    ArrayUtils.toString(null, "I'm nothing!");// I'm nothing!
	    // 2.判断两个数组是否相等,采用EqualsBuilder进行判断
	    // 只有当两个数组的数据类型,长度,数值顺序都相同的时候,该方法才会返回True
	    // 2.1 两个数组完全相同
	    ArrayUtils.isEquals(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });// true
	    // 2.2 数据类型以及长度相同,但各个Index上的数据不是一一对应
	    ArrayUtils.isEquals(new int[] { 1, 3, 2 }, new int[] { 1, 2, 3 });// true
	    // 2.3 数组的长度不一致
	    ArrayUtils.isEquals(new int[] { 1, 2, 3, 3 }, new int[] { 1, 2, 3 });// false
	    // 2.4 不同的数据类型
	    ArrayUtils.isEquals(new int[] { 1, 2, 3 }, new long[] { 1, 2, 3 });// false
	    ArrayUtils.isEquals(new Object[] { 1, 2, 3 }, new Object[] { 1, (long) 2, 3 });// false
	    // 2.5 Null处理,如果输入的两个数组都为null时候则返回true
	    ArrayUtils.isEquals(new int[] { 1, 2, 3 }, null);// false
	    ArrayUtils.isEquals(null, null);// true
	    // 3.将一个数组转换成Map
	    // 如果数组里是Entry则其Key与Value就是新Map的Key和Value,如果是Object[]则Object[0]为KeyObject[1]为Value
	    // 对于Object[]数组里的元素必须是instanceof Object[]或者Entry,即不支持基本数据类型数组
	    // 如:ArrayUtils.toMap(new Object[]{new int[]{1,2},new int[]{3,4}})会出异常
	    ArrayUtils.toMap(new Object[] { new Object[] { 1, 2 }, new Object[] { 3, 4 } });// {1=2,
	    // 3=4}
	    ArrayUtils.toMap(new Integer[][] { new Integer[] { 1, 2 }, new Integer[] { 3, 4 } });// {1=2,
	    // 3=4}
	    // 4.拷贝数组
	    ArrayUtils.clone(new int[] { 3, 2, 4 });// {3,2,4}
	    // 5.截取数组
	    ArrayUtils.subarray(new int[] { 3, 4, 1, 5, 6 }, 2, 4);// {1,5}
	    // 起始index为2(即第三个数据)结束index为4的数组
	    ArrayUtils.subarray(new int[] { 3, 4, 1, 5, 6 }, 2, 10);// {1,5,6}
	    // 如果endIndex大于数组的长度,则取beginIndex之后的所有数据
	    // 6.判断两个数组的长度是否相等
	    ArrayUtils.isSameLength(new Integer[] { 1, 3, 5 }, new Long[] { 2L, 8L, 10L });// true
	    // 7.获得数组的长度
	    ArrayUtils.getLength(new long[] { 1, 23, 3 });// 3
	    // 8.判段两个数组的类型是否相同
	    ArrayUtils.isSameType(new long[] { 1, 3 }, new long[] { 8, 5, 6 });// true
	    ArrayUtils.isSameType(new int[] { 1, 3 }, new long[] { 8, 5, 6 });// false
	    // 9.数组反转
	    int[] array = new int[] { 1, 2, 5 };
	    ArrayUtils.reverse(array);// {5,2,1
	    // 10.查询某个Object在数组中的位置,可以指定起始搜索位置,找不到返回-1
	    // 10.1 从正序开始搜索,搜到就返回当前的index否则返回-1
	    ArrayUtils.indexOf(new int[] { 1, 3, 6 }, 6);// 2
	    ArrayUtils.indexOf(new int[] { 1, 3, 6 }, 2);// -1
	    // 10.2 从逆序开始搜索,搜到就返回当前的index否则返回-1
	    ArrayUtils.lastIndexOf(new int[] { 1, 3, 6 }, 6);// 2
	    // 11.查询某个Object是否在数组中
	    ArrayUtils.contains(new int[] { 3, 1, 2 }, 1);// true
	    // 对于Object数据是调用该Object.equals方法进行判断
	    ArrayUtils.contains(new Object[] { 3, 1, 2 }, 1L);// false
	    // 12.基本数据类型数组与外包型数据类型数组互转
	    ArrayUtils.toObject(new int[] { 1, 2 });// new Integer[]{Integer,Integer}
	    ArrayUtils.toPrimitive(new Integer[] { new Integer(1), new Integer(2) });// new int[]{1,2}
	    // 13.判断数组是否为空(null和length=0的时候都为空)
	    ArrayUtils.isEmpty(new int[0]);// true
	    ArrayUtils.isEmpty(new Object[] { null });// false
	    // 14.合并两个数组
	    ArrayUtils.addAll(new int[] { 1, 3, 5 }, new int[] { 2, 4 });// {1,3,5,2,4}
	    // 15.添加一个数据到数组
	    ArrayUtils.add(new int[] { 1, 3, 5 }, 4);// {1,3,5,4}
	    // 16.删除数组中某个位置上的数据
	    ArrayUtils.remove(new int[] { 1, 3, 5 }, 1);// {1,5}
	    // 17.删除数组中某个对象(从正序开始搜索,删除第一个)
	    ArrayUtils.removeElement(new int[] { 1, 3, 5 }, 3);// {1,5}
	    
	}
	  
}
