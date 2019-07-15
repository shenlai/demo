package com.generics;

import com.annotations.SlAnnotation;
import com.tests.AnnotationTest;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 泛型方法
 * https://github.com/waylau/essential-java/blob/master/docs/generics.md
 * <p>
 * 泛型方法是引入其自己类型参数的方法 。这类似于声明泛型类型，但是类型参数的范围仅限于声明它的方法。允许使用静态和非静态泛型方法，以及泛型类构造函数。
 * 泛型方法的语法包括一个类型参数列表，在尖括号内，它出现在方法返回类型之前。对于静态泛型方法，类型参数部分必须出现在方法的返回类型之前。
 */
public class GenericMethodUtil {

    //泛型方法的语法包括一个类型参数列表，在尖括号内，它出现在方法返回类型之前。
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) && p1.getValue().equals(p2.getValue());
    }
    /**
     * 通配符
     * 通配符（?）通常表示未知类型。通配符可以用于各种情况：
     * 1：作为参数，字段或局部变量的类型；
     * 2：作为返回类型
     * 在泛型中，通配符不用与泛型方法调用，泛型类实例创建或超类型的类型参数。
     */

    /**
     * 【1.1 上线有界通配符】
     * <p>
     * 可以使用上限通配符来放宽对变量的限制。
     * 例如： 要编写一个使用于List<Integer>、List<Double>和List<Number>的方法，可以使用上限有界通配符来实现这一点
     *
     * @param list
     * @return
     */
    public static double sumOfList(List<? extends Number> list) {
        double s = 0.0;
        for (Number n : list) {
            s += n.doubleValue();
        }
        return s;
    }

    /**
     * 无界通配符
     *
     * 无界通配符类型通常用于定义未知类型 ：List<?>
     * 无界通配符用法 1,2
     *
     * 注意：要区分场景来选择使用List<Object>还是List<?>. 如果想插入一个Object或者是任意Object的子类，就可以使用List<Object>,
     *       但是只能在List<?>中插入null。
     *
     */

    /**
     * 1:需用使用Object类中提供过的功能实现的方法
     *
     * @param list
     */
    public static void printList(List<Object> list) {
        for (Object elem : list) {
            System.out.println(elem + " ");
        }
    }

    /**
     * 2. 当代码使用泛型类中不依赖于类型参数的方法
     *
     * @param list
     */
    public static void printList2(List<?> list) {
        for (Object elem : list)
            System.out.print(elem + " ");
        System.out.println();
    }


    /**
     * 下限有界通配符
     * <p>
     * 下限有界通配符将未知类型限制为该类的特定类型或超类型。使用下限有界通配符语法为<? super A>
     * 假设要编写一个将Integer对象放入列表的方法。为了最大限度地提高灵活性，
     * 希望该方法可以处理List<Integer>、List<Number>或者是List<Object>等可以保存Integer值的方法。
     *
     * @param list
     */
    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
    }


    public static void main(String[] args) {
        Pair<Integer, String> p1 = new Pair<Integer, String>(1, "apple");
        Pair<Integer, String> p2 = new Pair<Integer, String>(2, "pear");
        boolean isSame = compare(p1, p2);
        System.out.print("p1 compare p2:" + isSame);


    }


}
