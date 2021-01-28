package TCPClasses;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import org.energy2d.model.Part;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TCPRXTX extends Thread {
    int port;
    InetAddress host;
    List<Part> model = null;
    Socket socket;
    PrintStream out;


    public TCPRXTX(int port, String host, List<Part> model) throws IOException {
        this.port = port;
        this.host = InetAddress.getByName(host);
        this.model = model;
        this.socket = new Socket(host, port);
        this.out = new PrintStream(this.socket.getOutputStream());
    }

    public void run() {
        System.out.println("Connecting to server ");

        try {
            DataInputStream inp = new DataInputStream(this.socket.getInputStream());

            while(!Thread.currentThread().isInterrupted()) {
                String line = inp.readLine();
                if (line != null) {
                    System.out.println("Received from server: " + line);
                }
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }


    public String toString(List<Part> model) {
        return "Recieving!";
    }
    public void JSONtoModel(String str){
        JSONParser parser = new JSONParser();


        try {
            /* Fix this!! */
            //TODO: GET Paerser to work
            Object obj = parser.parse(str);
            JSONArray arr = (JSONArray) obj;
            JSONObject par = (JSONObject) arr.get(1);




        } catch (ParseException | JSONException e) {
            e.printStackTrace();
        }
    }
    public String getPartsPower(List<Part> model){
        JSONObject js = new JSONObject();

        for (int i = 0; i < model.size(); i++) {
            js.put(model.get(i).getUid(),new Float(model.get(i).getPower()));

        }

        return null;
    }
}

