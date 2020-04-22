package com.sl.luban.proxyCustom.proxy;


import com.sl.luban.proxyCustom.dao.CoustomInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 实现动态代理
 * InvocationHandler作用:
 * 官方解释比较拗口，InvocationHandler的作用其实就是关联目标类与代理类，
 * InvocationHandler实现中传入目标类对象，在动态生成的代理类中传入InvocationHandler实例（看一下以下生成的代理类代码一目了然）
 * 为什么不 直接在代理类中传入目标类？  => 代理通常实现逻辑增强，增强逻辑怎么传？
 */
public class ProxyUtilV2 {


    /**
     * 生成的代理类：
     * <p>
     * package com.google;
     * import com.sl.luban.proxyCustom.dao.UserDao;
     * import com.sl.luban.proxyCustom.dao.CoustomInvocationHandler;
     * import java.lang.Exception;import java.lang.reflect.Method;
     * public class $Proxy implements UserDao{
     * <p>
     * private CoustomInvocationHandler h;
     * <p>
     * public $Proxy (CoustomInvocationHandler h){
     * this.h =h;
     * }
     * public void query() {
     * Method method = Class.forName("com.sl.luban.proxyCustom.dao.UserDao").getDeclaredMethod("query");
     * return h.invoke(method);
     * }
     * public String query2()throws Exception {
     * Method method = Class.forName("com.sl.luban.proxyCustom.dao.UserDao").getDeclaredMethod("query2");
     * return (String)h.invoke(method);
     * }
     * }
     *
     * @param targetInf
     * @param h
     * @return
     */
    public static Object newInstance(Class targetInf, CoustomInvocationHandler h) {
        Object proxy = null;
        //Class targetInf = target.getClass();
        Method methods[] = targetInf.getDeclaredMethods();
        String line = "\n";
        String tab = "\t";
        String infName = targetInf.getSimpleName();
        String content = "";
        String packageContent = "package com.google;" + line;
        String importContent = "import " + targetInf.getName() + ";" + line
                + "import com.sl.luban.proxyCustom.dao.CoustomInvocationHandler;" + line
                + "import java.lang.Exception;"
                + "import java.lang.reflect.Method;" + line;
        String clazzFirstLineContent = "public class $Proxy implements " + infName + "{" + line;
        String filedContent = tab + "private CoustomInvocationHandler h;" + line;
        String constructorContent = tab + "public $Proxy (CoustomInvocationHandler h){" + line
                + tab + tab + "this.h =h;"
                + line + tab + "}" + line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName = method.getName();

            Class args[] = method.getParameterTypes();
            String argsContent = "";
            String paramsContent = "";
            int flag = 0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();

                argsContent += temp + " p" + flag + ",";
                paramsContent += "p" + flag + ",";
                flag++;
            }
            if (argsContent.length() > 0) {
                argsContent = argsContent.substring(0, argsContent.lastIndexOf(",") - 1);
                paramsContent = paramsContent.substring(0, paramsContent.lastIndexOf(",") - 1);
            }

            //void
            if (returnTypeName.equalsIgnoreCase("void")) {
                methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ")throws Exception {" + line
                        + tab + tab + "Method method = Class.forName(\"" + targetInf.getName() + "\").getDeclaredMethod(\"" + methodName + "\");" + line
                        + tab + tab + "h.invoke(method);" + line;
                methodContent += tab + "}" + line;
            } else {
                methodContent += tab + "public " + returnTypeName + " " + methodName + "(" + argsContent + ")throws Exception {" + line
                        + tab + tab + "Method method = Class.forName(\"" + targetInf.getName() + "\").getDeclaredMethod(\"" + methodName + "\");" + line
                        + tab + tab + "return (" + returnTypeName + ")h.invoke(method);" + line;   //返回类型强转
                methodContent += tab + "}" + line;
            }


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
            //不在项目工程中，所以使用URLClassLoader加载
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");

            Constructor constructor = clazz.getConstructor(CoustomInvocationHandler.class);

            //构造代理类的对象，将目标对象传入
            proxy = constructor.newInstance(h);

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
