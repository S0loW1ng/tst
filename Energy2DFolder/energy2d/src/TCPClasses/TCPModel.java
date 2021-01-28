package TCPClasses;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import org.json.JSONArray;

public class TCPModel extends Thread {
    int port;
    InetAddress host;
    Socket socket;
    PrintStream out;
    boolean run = true;
    float[][] model;

    public TCPModel(int port, String host, float[][] model) throws IOException {
        this.port = port;
        this.host = InetAddress.getByName(host);
        this.model = model;
        this.socket = new Socket(host, port);
        this.out = new PrintStream(this.socket.getOutputStream());
    }

    public void StopServer() {
        this.run = !this.run;
    }

    public void run() {
        System.out.println("Connecting to server ");
        String data = "";

        while(!Thread.currentThread().isInterrupted()) {
            data = this.toJSON(this.model);
            this.out.print(data);
            System.out.println(data);
        }

    }

    public String toJSON(float[][] model) {
        JSONArray jArr = new JSONArray();

        for(int i = 0; i < model.length; ++i) {
            jArr.put(Arrays.toString(model[i]));
        }

        return jArr.toString();
    }
}
