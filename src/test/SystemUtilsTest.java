package test;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.SystemUtils;

public class SystemUtilsTest {
	public static void main(String[] args){
		boolean isLinux = SystemUtils.IS_OS_LINUX;
		boolean isMac = SystemUtils.IS_OS_MAC;
		boolean isUnix = SystemUtils.IS_OS_UNIX;
		
		System.out.println("is linux? :"+isLinux);
		System.out.println("is mac? :"+isMac);
		System.out.println("is_unix? :"+isUnix);
		
		System.out.println("java_home:"+SystemUtils.getJavaHome());
		System.out.println("java_version:"+SystemUtils.getJavaVersion());
		System.out.println("user_dir:"+SystemUtils.getUserDir());
		System.out.println("user_home:"+SystemUtils.getUserHome());
		
		System.out.println(CharEncoding.UTF_8);
	}
}
