package com.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 1.@Target ：用于描述注解的使用范围，也就是说使用了@Target去定义一个注解，那么可以决定定义好的注解能用在什么地方
 * 2.@Retention：用于描述注解的生命周期，也就是说这个注解在什么范围内有效，注解的生命周期和三个阶段有关：源代码阶段、CLASS文件中有效、运行时有效，故其取值也就三个值，分别代表着三个阶段
 * 3.@Documented：表示该注解是否可以生成到 API文档中。在该注解使用后，如果导出API文档，会将该注解相关的信息可以被例如javadoc此类的工具文档化。 注意：Documented是一个标记注解，没有成员。
 * 4.@Inherited：使用@Inherited定义的注解具备继承性
 * 假设一个注解在定义时，使用了@Inherited，然后该注解在一个类上使用，如果这个类有子类，那么通过反射我们可以从类的子类上获取到同样的注解、
 */

@Documented
@Retention(RUNTIME)
public @interface SlAnnotation {
    String source() default "com.sl";

}
