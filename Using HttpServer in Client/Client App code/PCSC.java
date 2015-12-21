import java.io.Console;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.awt.Desktop;
import java.io.*;
import java.lang.*;
import java.net.URL;
import com.sun.net.httpserver.HttpsServer;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import com.sun.net.httpserver.*;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import java.net.InetAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsExchange;

public class PCSC {
        public static String response = "Card Reader not present please insert the card reader";
        public static String selectedOption1 = null;
        public static String selectedOption2 = null;
        private byte[] atr = null;
        private String protocol = null;
        private byte[] historical = null;
        
        public CardTerminal selectCardTerminal(HttpExchange t)
        {
                try
                {
                        // show the list of available terminals
                        TerminalFactory factory = TerminalFactory.getDefault();
                        List<CardTerminal> terminals = factory.terminals().list();
                        ListIterator<CardTerminal> terminalsIterator = terminals.listIterator();
                        CardTerminal terminal = null;
                        CardTerminal defaultTerminal = null;
												System.out.println(terminals.size()); 

                        if(terminals.size() >= 1)
                        {
                                response = "Card Reader Detected Please insert the card";
                                System.out.println("Please choose one of these card terminals (1-" + terminals.size() + "):");
                                response = response + "</br></br> " + "Please choose one of these card terminals (1-" + terminals.size() + "):";
																int i = 1;
                                while(terminalsIterator.hasNext())
                                {
                                        terminal = terminalsIterator.next();
                                        System.out.print("["+ i + "] - " + terminal + ", card present: "+terminal.isCardPresent());
                                        response = response + "</br></br> " + "["+ i + "] - " + terminal + ", card present: "+terminal.isCardPresent();
                                        if(terminal.isCardPresent())
                                        	response = response.replace("Please insert the card", "Card Present");
                                        if(i == 1) 
                                        {
                                                defaultTerminal = terminal;
                                                System.out.println(" [default terminal]");
                                                response = response + "</br></br> " + " [default terminal]";
                                        }
                                        else
                                        {
                                                System.out.println();
                                        }                                       
                                        i++;
                                }
                                /*try{
	                                t.sendResponseHeaders(200, response.length());
											            OutputStream os = t.getResponseBody();
											            os.write(response.getBytes());
											          	os.close();
										          	}
										          	catch(IOException eIo){
	            										eIo.printStackTrace();
	            									}*/
                                //Scanner in = new Scanner(System.in);
                                try
                                {
                                        //int option = in.nextInt();
                                        int option = 0;
                                        if(selectedOption1 != null) {
                                            option = Integer.parseInt(selectedOption1);
                                          }
                                        else {
                                            option = 1;
                                          }
                                        terminal = terminals.get(option-1);                                     
                                }
                                catch(Exception e2)
                                {
                                        //System.err.println("Wrong value, selecting default terminal!");
                                        terminal = defaultTerminal;
                                        
                                }
                                System.out.println("Selected: "+terminal.getName());
                                response = response + "</br></br> " + "Selected: "+terminal.getName();
                                //Console console = System.console(); 
                                return terminal;
                        }
                        

                }
                catch(Exception e)
                {
                        System.err.println("Error occured:");
                        response = response + "</br></br> " + "A problem in the communication or card Reader not detected , please wait ..... or insert the card Reader";
                        e.printStackTrace();
                }
                return null;
        }

        public String byteArrayToHexString(byte[] b) 
        {
            StringBuffer sb = new StringBuffer(b.length * 2);
            for (int i = 0; i < b.length; i++) {
              int v = b[i] & 0xff;
              if (v < 16) {
                sb.append('0');
              }
              sb.append(Integer.toHexString(v));
            }
            return sb.toString().toUpperCase();
         }

        public static byte[] hexStringToByteArray(String s) 
        {     
                int len = s.length();     
                byte[] data = new byte[len / 2];     
                for (int i = 0; i < len; i += 2) 
                {         
                        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));     
                }     
                return data; 
        }       

        public Card establishConnection(CardTerminal ct , HttpExchange t)
        {
                this.atr = null;
                this.historical = null;
                this.protocol = null;
                response = response + "</br></br> " + "To establish connection, please choose one of these protocols (1-4):";
                response = response + "</br></br> " + "[1] - T=0";
                response = response + "</br></br> " + "[2] - T=1";
                response = response + "</br></br> " + "[3] - T=CL";
                response = response + "</br></br> " + "[4] - * [default]";
                System.out.println("To establish connection, please choose one of these protocols (1-4):");
                System.out.println("[1] - T=0");
                System.out.println("[2] - T=1");
                System.out.println("[3] - T=CL");
                System.out.println("[4] - * [default]");
                
                String p = "*";
                /*try{
                t.sendResponseHeaders(200, response.length());
	             OutputStream os = t.getResponseBody();
	             os.write(response.getBytes());
	             os.close();
	            } catch(IOException eIo){
	            	eIo.printStackTrace();
	            }*/
                //Scanner in = new Scanner(System.in);
                
                try
                {
                        int option = Integer.parseInt(selectedOption2);
                        
                        if(option == 1) p = "T=0";
                        if(option == 2) p = "T=1";
                        if(option == 3) p = "T=CL";
                        if(option == 4) p = "*";                        
                }
                catch(Exception e)
                {
                        //System.err.println("Wrong value, selecting default protocol!");
                        p = "*";
                }
                
                System.out.println("Selected: "+p);
                response = response + "</br></br> " + "Selected: "+p; 
                
                Card card = null;
                try 
                {
                        card = ct.connect(p);
                } 
                catch (CardException e) 
                {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return null;
                }
                ATR atr = card.getATR();
                response = response + "</br></br> " + "connected";
                response = response + "</br></br>" + " - ATR:  "+ byteArrayToHexString(atr.getBytes());
                response = response + "</br></br>" + " - Historical: "+ byteArrayToHexString(atr.getHistoricalBytes());
								response = response + "</br></br>" + " - Protocol: "+card.getProtocol();
                System.out.println("Connected:");       
                System.out.println(" - ATR:  "+ byteArrayToHexString(atr.getBytes()));
                System.out.println(" - Historical: "+ byteArrayToHexString(atr.getHistoricalBytes()));
                System.out.println(" - Protocol: "+card.getProtocol());
                
                this.atr = atr.getBytes();
                this.historical = atr.getHistoricalBytes();
                this.protocol = card.getProtocol();
                
                return card;            
                
        }
        
        public static class MyHandler implements HttpHandler {
        @Override
	        public void handle(HttpExchange t) throws IOException {
	        	System.out.println("hai");
	            //HttpsExchange httpsExchange = (HttpsExchange) t;
	            t.setAttribute("Access-Control-Allow-Origin", "*");
	            //t.setAttribute("Access-Control-Allow-Origin", "*")
	            t.getResponseHeaders().add("Access-Control-Allow-Headers", "selectedoption1");
	            t.getResponseHeaders().add("Access-Control-Allow-Headers", "selectedoption2");
	            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
	            System.out.println(t.getRequestBody().read());
	            int i = 0;
								for (List<String> list : t.getRequestHeaders().values()) {
									//System.out.println(list.get(0));

								   // whatever
								   if(i==0)
								     selectedOption1 = list.get(0);
								   if(i==1)
								     //selectedOption2 = list.get(0);  
								   i++;
								}
	            PCSC pcsc = new PCSC();
	            CardTerminal ct = null;	
	            //String selectedOption1 =  "4";//t.getRequestHeaders("selectedoption1");           
	            	            try{
                ct = pcsc.selectCardTerminal(t);
              }
								catch (Exception e1) {
	            	e1.printStackTrace();
	            }
	            	        	System.out.println(selectedOption1);

                Card c = null;
                if(ct != null)
                {
                        if(/*t.getRequestHeaders("selectedoption2")*/ selectedOption2 != null) {
                        //selectedOption2 = Integer.parseInt("8");
                        //selectedOption2 = Integer.parseInt(t.getRequestHeaders("selectedoption2"));
                        c = pcsc.establishConnection(ct,t);
                        CardChannel cc = c.getBasicChannel();
                    //byte[] SELECT = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x09, (byte) 0x74, (byte) 0x69, (byte) 0x63, (byte) 0x6B, (byte) 0x65, (byte) 0x74, (byte) 0x69, (byte) 0x6E, (byte) 0x67, (byte) 0x00};
                        byte[] baCommandAPDU = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x08, (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00};
                        //byte[] baCommandAPDU = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x51, (byte) 0x00, (byte) 0x00};
                                                try 
                        {
                                System.out.println("TRANSMIT: "+pcsc.byteArrayToHexString(baCommandAPDU));
                                response = response + "</br></br>" + "TRANSMIT: "+pcsc.byteArrayToHexString(baCommandAPDU);       
                                ResponseAPDU r = cc.transmit(new CommandAPDU(baCommandAPDU));
                                System.out.println("RESPONSE: "+pcsc.byteArrayToHexString(r.getBytes()));
                                response = response + "</br></br>" + "RESPONSE: "+pcsc.byteArrayToHexString(r.getBytes()); 
                        } 
                        catch (CardException e) 
                        {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                response = response + "</br></br>" + "Something went wrong";
                        }
                      }
                }
	            t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
	            response = "";
	        }
    		}
        
        /**
         * @param args
         */
        public static void main(String argv[]) {
                // TODO Auto-generated method stub
                
                          try
														{        HttpServer server = HttpServer.create(new InetSocketAddress(8002), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
											}
											catch ( Exception exception )
											{
												exception.printStackTrace();
											  System.out.println( "Failed to create HTTP server on port " + 8002 + " of localhost" );
											}
            

        }

}
