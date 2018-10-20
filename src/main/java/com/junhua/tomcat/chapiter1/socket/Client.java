package com.junhua.tomcat.chapiter1.socket;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        Socket client = null;
        OutputStream os = null;
        try {
            client = new Socket("127.0.0.1", 8080);

            if (client.isConnected()) {
                os = client.getOutputStream();
                Writer write = new PrintWriter(os);

                Reader read = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //send a request to server
                write.write("GET /index HTTP/1.1");
                write.write("Host:localhost:8080");
                write.flush();
                client.shutdownOutput();

                //read response
                StringBuffer sb = new StringBuffer(1024);
                boolean loop = true;
                while (loop) {
                    if (read.ready()) {
                        int i = 0;
                        while (i != -1) {
                            i = read.read();
                            sb.append((char) i);
                        }
                        loop = false;
                    }
                }

                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (client != null)client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
