package com.sl.luban.proxyCustom.proxy;

import com.sun.jndi.toolkit.url.UrlUtil;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 实现动态代理
 * <p>
 * 补充两个原则：
 * 1:开闭原则
 * 2:单一职责
 * 参考：2018-11-4(20)-手写动态代理，动态代理逻辑的实现
 */
public class ProxyUtil {
    //region
    /**
     * 生成的$Proxy.java 代理类文件内容
     *
     * package com.google;
     * import com.sl.luban.proxyCustom.dao.LubanDao;
     * public class $Proxy implements LubanDao{
     * 	private LubanDao target;
     * 	public $Proxy (LubanDao target){
     * 		this.target =target;
     *        }
     * 	public void query() {
     * 		System.out.println("log");
     * 		target.query();
     *    }
     * }
     */
    //endregion

    /**
     * content --->string
     * .java  io
     * .class
     * .new   反射----》class
     *
     * @return
     */
    public static Object newInstance(Object target) {
        Object proxy = null;
        Class targetInf = target.getClass().getInterfaces()[0];
        //Class targetInf = target.getClass();
        Method methods[] = targetInf.getDeclaredMethods();
        String line = "\n";
        String tab = "\t";
        String infName = targetInf.getSimpleName();
        String content = "";
        String packageContent = "package com.google;" + line;
        String importContent = "import " + targetInf.getName() + ";" + line;
        String clazzFirstLineContent = "public class $Proxy implements " + infName + "{" + line;
        String filedContent = tab + "private " + infName + " target;" + line;
        String constructorContent = tab + "public $Proxy (" + infName + " target){" + line
                + tab + tab + "this.target =target;"
                + line + tab + "}" + line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();
            // Sting.class String.class
            Class args[] = method.getParameterTypes();
            String argsContent = "";
            String paramsContent = "";
            int flag = 0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();
                //String
                //String p0,Sting p1,
                argsContent += temp + " p" + flag + ",";
                paramsContent += "p" + flag + ",";
                flag++;
            }
            if (argsContent.length() > 0) {
                argsContent = argsContent.substring(0, argsContent.lastIndexOf(",") - 1);
                paramsContent = paramsContent.substring(0, paramsContent.lastIndexOf(",") - 1);
            }

            methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ") {" + line
                    + tab + tab + "System.out.println(\"log\");" + line
                    + tab + tab + "target." + methodName + "(" + paramsContent + ");" + line
                    + tab + "}" + line;

        }

        content = packageContent + importContent + clazzFirstLineContent + filedContent
                + constructorContent + methodContent + "}";
        //代理
        File file = new File("d:\\com\\google\\$Proxy.java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }


            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();

            //编译代理类
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            //加载到虚拟机
            URL[] urls = new URL[]{new URL("file:D:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");

            Constructor constructor = clazz.getConstructor(targetInf);

            //构造代理类的对象，将目标对象传入
            proxy = constructor.newInstance(target);


            //clazz.newInstance();
            //Class.forName()
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * public UserDaoLog(UserDao target){
         * 		this.target =target;
         *
         *        }
         */
        return proxy;
    }
}
