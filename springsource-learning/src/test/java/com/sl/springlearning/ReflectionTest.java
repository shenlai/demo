package com.sl.springlearning;

import com.sl.springlearning.reflection.Person;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionTest {

    @Test
    public void testClass() {
        Class clazz = null;

        //1.得到Class对象
        clazz = Person.class;
        //2.返回所有字段的数组（public protected private）
        Field[] fields = clazz.getDeclaredFields();

        //返回public 属性
        Field[] fields2 = clazz.getFields();

        System.out.println();  //插入断点
    }

    /**
     * 获取Class对象的三种方式
     * <p>
     * 　　1.通过类名获取      类名.class
     * <p>
     * 　　2.通过对象获取      对象名.getClass()
     * <p>
     * 　　3.通过全类名获取    Class.forName(全类名)
     */
    @Test
    public void testClass2() throws ClassNotFoundException {
        Class clazz = null;

        //1.通过类名获取类对象
        clazz = Person.class;
        System.out.println(clazz.toString());

        //通过对象获取类对象
        //这种已经明确知道对象的类型，并无意义
        Person person = new Person();
        clazz = person.getClass();
        System.out.println(clazz.toString());
        //这种继承的方式
        Object object = new Person();
        clazz = object.getClass();
        System.out.println(clazz.toString());

        //3.通过全类名（不存在则会抛异常）:ClassNotFoundException
        //一般框架开发中使用这种方式，因为配置文件中基本配置的都是类全名，通过这种方式得到类实例
        String className = "com.sl.springlearning.reflection.Person";
        clazz = Class.forName(className);
        System.out.println(clazz.toString());

        //字符串
        clazz = String.class;
        clazz = "stringTest".getClass();
        clazz = Class.forName("java.lang.String");

        System.out.println(clazz.toString());


    }


    @Test
    public void testNewInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //1.创建class对象
        String className = "com.sl.springlearning.reflection.Person";
        Class clazz = Class.forName(className);

        //通过Class对象的newInstance方法创建一个类实例
        //实际调用的是类的无参构造函数，所以在定义一个类的时候，定义一个有参的构造器，作用是对属性进行初始化，还要写一个无参构造函数，作用是反射式使用
        Object object = clazz.newInstance();

        System.out.println(object.toString());

    }


    /**
     * 类装载器是用来把类class装载进JVM的，JVM规范定义了两种类型的类装载器：
     * 启动类装载器bootstrap和用户自定义装载器user-defined class loader
     * JVM在运行时会产生3个类加载器组成的初始化加载器层次结构，参考：https://www.cnblogs.com/tech-bird/p/3525336.html
     * 1.引导类加载器Bootstrap ClassLoader：使用C++编写的，是JVM自带的类装载器，负责Java平台核心库，用来装载核心类库，该加载器无法直接获取
     * 2.扩展类加载器Extension ClassLoader：负责jdk home/lib/ext目录下的jar包或 -D java.ext.dirs执行目录下的jar包装入工作库
     * 3.系统类加载器System ClassLoader:负责java -classpath或 -Djava.class.path所执行目录下的类与jar包装入工作
     */
    @Test
    public void testClassLoader() throws ClassNotFoundException {

        //1. 获取一个系统的类加载器（可以获取当前这个类ReflectionTest使用的加载器）
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

        //2. 获取系统加载器的父类加载器（扩展类加载器）
        classLoader = classLoader.getParent();
        System.out.println(classLoader); //sun.misc.Launcher$ExtClassLoader@10f87f48

        //3. 获取扩展类加载器的父加载器（引导类加载器，不可以获取）
        classLoader = classLoader.getParent();
        System.out.println(classLoader); //null

        //4. 测试当前类是由哪个类加载器加载的（系统类加载器）
        classLoader = Class.forName("com.sl.springlearning.ReflectionTest").getClassLoader();
        System.out.println(classLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

        //5. 测试JDK 提供的Object类是哪个来加载器加载的（引导类加载器）
        classLoader = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(classLoader);  // 输出null, 引导类加载器无法直接获取


    }

    /**
     * 使用类加载器获取当前类目录下的文件
     */
    @Test
    public void testLoaderResource() {

        //资源文件直接放在resources文件夹下
        InputStream stream = null;
        stream = this.getClass().getClassLoader().getResourceAsStream("test.txt");

        System.out.println(stream.toString());

    }

    //反射机制允许程序在执行期间借助Reflection API取得任何类的内部信息，并能直接操作任意对象的内部属性及方法
    //Java 反射机制主要提供了以下功能
    // 在运行时构造任意一个类的对象
    // 在运行时获取任意一个类所具有的成员量和方法
    // 在运行时调用任意一个对象的方法（属性）
    // 生成动态代理

    @Test
    public void testMethod() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        Class clazz = Class.forName("com.sl.springlearning.reflection.Person");

        //1. 获取方法
        //   getMethods不能获取private方法,且获取从父类继承来的所有方法
        Method[] methods = clazz.getMethods();
        //System.out.println(Arrays.asList(methods));
        for (Method method : methods) {
            //System.out.println(method.getName());
        }

        //   getDeclaredMethods可以获取类中定义的所有方法包括private,不能获取父类中的方法
        Method[] methods1 = clazz.getDeclaredMethods();

        //获取指定方法
        //参数是Integer
        Method method = clazz.getDeclaredMethod("setAge", Integer.class);
        System.out.println(method);
        //参数是int
        Method method2 = clazz.getDeclaredMethod("setSex", int.class);
        System.out.println(method2);

        //执行方法
        // invoke第一个参数表示执行哪个对象的方法，剩下的参数是执行方法时需要传入的参数
        Object object = clazz.newInstance();
        method.invoke(object, 20);
        //执行getAge
        Method method3 = clazz.getDeclaredMethod("getAge");
        System.out.println(method3.invoke(object));
        System.out.println(((Person) object).getAge());


        //如果一个方法是private方法，虽然可以通过getDeclaredMethod获取该方法信息，但是不能直接执行
        //如果需要执行私有方法，必须先修改其访问属性 ：method.setAccessible（true）;
    }


    @Test
    public void testField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        String className = "com.sl.springlearning.reflection.Person";
        Class clazz = Class.forName(className);

        //获取所有字段
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(Arrays.asList(fields));

        //获取指定字段
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getName());

        Person person = new Person("test", 20);
        //使用字段
        //如果字段是私有的，不管是读值还是写值，都必须先调用setAccessible（true）方法
        field.setAccessible(true);
        Object object = field.get(person);
        System.out.println(object);

    }

    //测试操作父类私有方法
    @Test
    public void testSupperPrivateField() throws Exception {
        String className = "com.sl.springlearning.reflection.Student";

        String fieldName = "school";
        Object fieldVal = "测试名称";

        Object object = null;
        Class clazz = Class.forName(className);
        Field field = getField(clazz, fieldName);
        object = clazz.newInstance();

        setFieldValue(object, field, fieldVal);

        //获取属性值
        Object val = getFieldValue(object, field);
        System.out.println(val);
    }

    public Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        for (Class c = clazz; c != Object.class; c = c.getSuperclass()) {
            //field = c.getDeclaredField(fieldName); 属性不存在则抛出异常
            Field[] fields = c.getDeclaredFields();
            for (Field fi : fields) {
                if (fi.getName() == fieldName) {
                    field = fi;
                    break;
                }
            }
            if (field != null)
                break;
            ;
        }
        return field;
    }

    public Object getFieldValue(Object obj, Field field) throws Exception {
        field.setAccessible(true);
        return field.get(obj);
    }

    public void setFieldValue(Object obj, Field field, Object val) throws Exception {
        field.setAccessible(true);
        field.set(obj, val);
    }


    /*构造函数https://www.cnblogs.com/tech-bird/p/3525336.html*/


}
