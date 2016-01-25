package free.halley.myn.test;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcat { 
	private static Tomcat tomcat = null; 
	private static String CONTEXT_PATH = "/carportal"; 
	private static String PROJECT_PATH = System.getProperty("user.dir"); 
	private static String WEB_APP_PATH = PROJECT_PATH + "/webapp/"; 
	private static String TOMCAT_HOME = PROJECT_PATH + "/Embedded/Tomcat"; 
	private static String ENCODING = "UTF-8"; 
	private static int START_TOMCAT_PORT = 8088; 
	private static int STOP_TOMCAT_PORT = 8090; 
	// 启动嵌入式Tomcat服务器
	public static void startTomcat() throws Exception { 
		try { 
			System.out.println("用户路径："+PROJECT_PATH);
			long startTime = System.currentTimeMillis(); 
			tomcat = new Tomcat(); 
			// 设置Tomcat的工作目录:工程根目录/Embedded/Tomcat 
			tomcat.setBaseDir(TOMCAT_HOME); 
			tomcat.setPort(START_TOMCAT_PORT); 
			
			
			tomcat.getHost().setAppBase(WEB_APP_PATH);
			// Add AprLifecycleListener
			StandardServer server = (StandardServer) tomcat.getServer();
			AprLifecycleListener listener = new AprLifecycleListener();
			server.addLifecycleListener(listener);
			//注册关闭端口以进行关闭        
			tomcat.getServer().setPort(STOP_TOMCAT_PORT); 
			
			StandardContext standardContext = new StandardContext();
			standardContext.setPath(CONTEXT_PATH);//contextPath
			standardContext.setDocBase(WEB_APP_PATH);//文件目录位置        
			standardContext.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
			//保证已经配置好了。
			standardContext.addLifecycleListener(new Tomcat.FixContextListener());
			standardContext.setSessionCookieName("t-session");
			tomcat.getHost().addChild(standardContext); 

			
			
			//tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH); 
			//tomcat.enableNaming();
			//执行这句才能支持JDNI查找 
			tomcat.getConnector().setURIEncoding(ENCODING); 
			tomcat.start(); 
			System.err.println("Tomcat started in " + (System.currentTimeMillis() - startTime) + " ms."); 
			tomcat.getServer().await();
			//让服务器一直跑 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
		} 
	public static void main(String[] args) { 
		try { 
			EmbeddedTomcat.startTomcat();
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
	}
	
	private static void shutdown() throws Exception { 
		String shutdown = "shutdown";
		Socket socket = new Socket("localhost", STOP_TOMCAT_PORT);        
		OutputStream stream = socket.getOutputStream();        
		for(int i = 0;i < shutdown.length();i++)  {          
			stream.write(shutdown.charAt(i));
		}
	    stream.flush();        
	    stream.close();        
	    socket.close();
   } 

}


