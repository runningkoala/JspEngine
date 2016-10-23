package container;
import java.io.File;
import java.io.IOException;
import java.net.URL;  
import java.net.URLClassLoader;  
import javax.servlet.Servlet;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;

import readJsp.readJsp;  
  
public class ServletProcessor
{  
    public void process(Request request, Response response)  
    {  
    	//�õ�·������ȡ����Ӧservlet����
        String uri = request.getUri();  
        String servletname = uri.substring(uri.lastIndexOf("/") + 1);  
        URLClassLoader loader = null;
        try  
        {  
            //����·������Ӧ������
            URL[] urls = new URL[1];  
            File classPath = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator);
            //������·��תΪ��Ӧ��ʽ
            URL url = classPath.toURI().toURL();
            urls[0]=url;
            //���ظ�·��
            loader = new URLClassLoader(urls);  
        }  
        catch (IOException e)  
        {  
            System.out.println(e.toString());  
        }  
        Class<?> myClass = null; 
        try  
        {  
            //������Ӧ·���µ���Ӧ��  
            myClass = loader.loadClass(readXml.getPath(servletname));  
        }  
        catch (ClassNotFoundException e)  
        {  
            System.out.println(e.toString());  
        }  
        Servlet servlet = null;  
        try  
        {  
            //������Ӧ���ಢʵ������Ȼ�����servlet��init,service������  
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) request, (ServletResponse) response);
        }  
        catch (Exception e)  
        {  
            System.out.println(e.toString());  
        }  
        catch (Throwable e)  
        {  
            System.out.println(e.toString());  
        }  
    }  
    
    public void process2(Request request, Response response)  
    {  
    	//�õ�·������ȡ����Ӧjsp����
        String uri = request.getUri();  
        String jspname = uri.substring(uri.lastIndexOf("/") + 1);
        String[] names = jspname.split("\\.");
        File file = new File(System.getProperty("user.dir") + File.separator + "WebRoot" + File.separator + jspname);
        
        //��jsp�ļ�����ʱת��Ϊservlet�ļ�
        if(file.exists()){
        	try {
				readJsp.readFile(jspname);
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
        }else{
        	//�Ҳ����ļ�ʱ����404��Ϣ
        	String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";  
            try {
				response.output.write(errorMessage.getBytes());
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
            return; 
        }

		File jsp1 = new File(System.getProperty("user.dir") + File.separator 
    			+ "bin" + File.separator + "JspServlet" + File.separator + names[0] + "_jsp.class");
		while(!jsp1.exists()){}
		
		File jsp2 = new File(System.getProperty("user.dir") + File.separator 
    			+ "src" + File.separator + "JspServlet" + File.separator + names[0] + "_jsp.java");
		while(!jsp2.exists()){}
		
        URLClassLoader loader = null;
        try  
        {  
            //����·������Ӧ������
            URL[] urls = new URL[1];  
            File classPath = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator);
            //������·��תΪ��Ӧ��ʽ
            URL url = classPath.toURI().toURL();
            urls[0]=url;
            //���ظ�·��
            loader = new URLClassLoader(urls);  
        }  
        catch (IOException e)  
        {  
            System.out.println(e.toString());  
        }  
        Class<?> myClass = null; 
        try  
        {
            //������Ӧ·���µ���Ӧ��  
            myClass = loader.loadClass("JspServlet." + names[0] + "_jsp");
        }  
        catch (ClassNotFoundException e)  
        {  
        	//�಻����������ʱ����404��Ϣ
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";  
            try {
				response.output.write(errorMessage.getBytes());
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
            System.out.println("meilei");
            return; 
        }  
        Servlet servlet = null;  
        try  
        {  
            //������Ӧ���ಢʵ������Ȼ�����servlet��init,service������  
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) request, (ServletResponse) response);
        }  
        catch (Exception e)  
        {  
            System.out.println(e.toString());  
        }  
        catch (Throwable e)  
        {  
            System.out.println(e.toString());  
        }  
    }  

}  