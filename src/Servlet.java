

import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.InetAddress;  
import java.net.ServerSocket;  
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import container.Request;
import container.Response;
import container.ServletProcessor;
import container.StaticResourceProcessor;
import container.readXml;  
  
public class Servlet
{  
    //����ر�
    private static final String CLOSE_COMMAND = "/SHUTDOWN";
    private boolean close = false;  
  
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException  
    {  
    	//ʵ������������,��ȡxml�ļ�
    	Servlet servlet = new Servlet();
    	readXml.readX();
    	servlet.waiting();  
    }  
  
    public void waiting()  
    {  
    	//��ӷ���������
        ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}  
        
        //����δ�ر�ʱ���������� 
        while (!close)  
        {  
        	//�������
            Socket socket = null;  
            InputStream ins = null;  
            OutputStream os = null;  
            try  
            {
            	//�õ��������
                socket = serverSocket.accept();  
                ins = socket.getInputStream();  
                os = socket.getOutputStream();  
                //ʵ����������
                Request request = new Request(ins);  
                request.parse();  
                //ʵ����������  
                Response response = new Response(os);  
                response.setRequest(request);
                String uri = request.getUri();
                boolean flag = false;
                try{
                	if(uri.substring(uri.length()-4).equals(".jsp")){
                		flag = true;
                	}
                } catch(Exception e){}
                //�ж������Ƿ�Ϊservlet 
                if (readXml.ifServlet(uri))  
                {
                	//����servlet��ҳ
                    ServletProcessor processor = new ServletProcessor();  
                    processor.process(request, response);  
                }  
                else if(flag)
                {
                	//����һ���̶���ҳ
                	ServletProcessor processor = new ServletProcessor();  
                    processor.process2(request, response);  
                }  
                else
                {
                	//����һ���̶���ҳ
                    StaticResourceProcessor processor = new StaticResourceProcessor();  
                    processor.process(request, response);  
                }  
                //�ر�����
                socket.close();  
                //�ж��Ƿ���Ҫ�ر�����  
                close = request.getUri().equals(CLOSE_COMMAND);  
            }  
            catch (Exception e)  
            {  
                e.printStackTrace();  
                System.exit(0);
            }  
        }  
    }  
}  