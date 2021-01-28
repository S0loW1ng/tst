package TCPClasses;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import org.energy2d.model.*;
import org.json.simple.JSONObject;

public class TCPThermometers extends Thread {
    int port;
    InetAddress host;
    List<Thermometer> model = null;
    Socket socket;
    PrintStream out;
    boolean run = true;

    public TCPThermometers(int port, String host, List<Thermometer> model) throws IOException {
        this.port = port;
        this.host = InetAddress.getByName(host);
        this.model = model;
        this.socket = new Socket(host, port);
        this.out = new PrintStream(this.socket.getOutputStream());
    }

    public void run() {
        System.out.println("Connecting to server ");
        String data = "";
        String previousData = null;

        while(!Thread.currentThread().isInterrupted()) {
            data = this.toString(this.model);
            if (!data.equals(previousData)) {
                this.out.print(data);
                System.out.println(data);
                previousData = data;
            }
        }

    }

    public String toString(List<Thermometer> model) {
        String values = "";
        JSONObject js = new JSONObject();

        for(int i = 0; i < model.size(); ++i) {
            //values = values + "/" + i + ":" + ((Part)model.get(i)).;
            js.put(model.get(i).getName(),new Float(model.get(i).getCurrentData()));
        }

        return js.toJSONString() + "\n";
    }

}
