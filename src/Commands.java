import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Commands {
    PrintWriter out;
    InputStreamReader in;
    private Socket socket;

    private boolean init(){
        boolean state = false;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);        //Init output stream
            in = new InputStreamReader(socket.getInputStream());            //Init input stream
            if(out.checkError() || !in.ready()){
                state =  false;
            }
            else{
                state = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }
    private void write(String command){
            out.println(command);       //Send command
            out.flush();            //Clear the stream
    }

    private String read(){
        String response = null;
        try {
            //Init variables to receive response
            StringBuffer stringBuffer=new StringBuffer();

            int x;
            while(true)
            {
                x=in.read();        //Read bytes
                stringBuffer.append((char)x);       //Append byte to buffer
                if(x==62) break; // reads till the terminator

            }
            response=stringBuffer.toString();       //Convert buffer to string
//            System.out.println("Response: " + response);        //Print out response
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void close(){
        try {
            //Close input and output streams
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendAD4D(String IPAddress, String RFBand, String channel1Name, String channel1Freq, String channel2Name, String channel2Freq){
        try {
            socket = new Socket(IPAddress, 2202);     //Connecting to the device
        }
        catch (ConnectException c){
            return "Error Connecting to the Device";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(!init()){
            return "Error Connecting to the Device";
        }
        write("< SET RF_BAND " + RFBand + " >");
        write("< SET 1 CHAN_NAME " + channel1Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 1 Name";
        }
        write("< SET 1 FREQUENCY " + channel1Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 1 Frequency";
        }
        write("< SET 2 CHAN_NAME " + channel2Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 2 Name";
        }
        write("< SET 2 FREQUENCY " + channel2Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 1 Frequency";
        }
        close();

        return "Device Configured Successfully";
    }

    public String sendAD4Q(String IPAddress, String RFBand, String channel1Name, String channel1Freq, String channel2Name, String channel2Freq, String channel3Name, String channel3Freq, String channel4Name, String channel4Freq){
        try {
            socket = new Socket(IPAddress, 2202);     //Connecting to the device
        }
        catch (ConnectException c){
            return "Error Connecting to the Device";
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if(!init()){
            return "Error Connecting to the Device";
        }
        write("< SET RF_BAND " + RFBand + " >");
        write("< SET 1 CHAN_NAME " + channel1Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 1 Name";
        }
        write("< SET 1 FREQUENCY " + channel1Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 1 Frequency";
        }
        write("< SET 2 CHAN_NAME " + channel2Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 2 Name";
        }
        write("< SET 2 FREQUENCY " + channel2Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 2 Frequency";
        }
        write("< SET 3 CHAN_NAME " + channel3Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 3 Name";
        }
        write("< SET 3 FREQUENCY " + channel3Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 3 Frequency";
        }
        write("< SET 4 CHAN_NAME " + channel4Name + " >");
        if(read().length() != 53){
            return "Error setting Channel 4 Name";
        }
        write("< SET 4 FREQUENCY " + channel4Freq + " >");
        if(read().length() != 27){
            return "Error setting Channel 4 Frequency";
        }
        close();

        return "Device Configured Successfully";
    }

}
