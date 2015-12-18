import java.io.Console;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.awt.Desktop;
import java.io.*;
import java.net.URL;

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

public class PCSC {

    private byte[] atr = null;
    private String protocol = null;
    private byte[] historical = null;
    
   public static String sCurrentLine = null;
   CardTerminal terminal = null;

    public CardTerminal selectCardTerminal() {
    	for(int j=1;j>0;j++) {
        try {
            // show the list of available terminals
            TerminalFactory factory = TerminalFactory.getDefault();
            List < CardTerminal > terminals = factory.terminals().list();
            ListIterator < CardTerminal > terminalsIterator = terminals.listIterator();
            CardTerminal defaultTerminal = null;
            if (terminals.size() >= 1) {
            		BufferedReader br = null;

								try {
						
									
						
									br = new BufferedReader(new FileReader("CUSTOMURISCARDREADERREGISTERED.txt"));
									sCurrentLine = br.readLine();
									
										char a_char = sCurrentLine.charAt(0);
										if(sCurrentLine.charAt(0) == '\"') {
											sCurrentLine = sCurrentLine.replaceAll("(\"scardreader://)", "");
											sCurrentLine = sCurrentLine.replaceAll("\"\\s", "");
										}
										else {
											if(sCurrentLine.charAt(14) == '/') {
										  	sCurrentLine = sCurrentLine.replaceAll("(scardreader:///)", "");
										  } else {
									      sCurrentLine = sCurrentLine.replaceAll("(scardreader://)", "");
										  }
										  sCurrentLine = sCurrentLine.replaceAll("\"\\s", "");
										  //sCurrentLine = sCurrentLine.replace(Character.toString(sCurrentLine.charAt(sCurrentLine.length()-1)), "");
										}
										System.out.println(sCurrentLine);
									
						
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									try {
										if (br != null)br.close();
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								}
                /*
                 *  fix for
                 *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
                 *       sun.security.validator.ValidatorException:
                 *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
                 *               unable to find valid certification path to requested target
                 */
                TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}

                    }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                /*
                 * end of the fix
                 */
                InetAddress IP = InetAddress.getLocalHost();
                System.out.println("IP of my system is := " + IP.getHostAddress());
                URL url = new URL(sCurrentLine + "%20card-reader-detected");
                URLConnection con = url.openConnection();
                Reader reader = new InputStreamReader(con.getInputStream());
                while (true) {
                    int ch = reader.read();
                    if (ch == -1) {
                        break;
                    }
                    System.out.print((char) ch);
                }
                System.out.println("Please choose one of these card terminals (1-" + terminals.size() + "):");
                int i = 1;
                while (terminalsIterator.hasNext()) {
                    terminal = terminalsIterator.next();
                    System.out.print("[" + i + "] - " + terminal + ", card present: " + terminal.isCardPresent());
                    if(terminal.isCardPresent() == false){
                    	for(int z=1;z>0;z++)
                    	{
                    		if(z==1){
                    		url = new URL(sCurrentLine + "%20card-reader-detected-and-" + "Please-Insert-Your-Card");
                    	con = url.openConnection();
                    	reader = new InputStreamReader(con.getInputStream());
                    	while (true) {
                        int ch = reader.read();
                        if (ch == -1) {
                            break;
                        }
                        System.out.print((char) ch);
                    }
                  }
                    if(terminal.isCardPresent() == true){break;
                    }
                    	}
                    }
                    url = new URL(sCurrentLine + "%20card-reader-detected-and-" + "card-present:" + terminal.isCardPresent());
                    con = url.openConnection();
                    reader = new InputStreamReader(con.getInputStream());
                    while (true) {
                        int ch = reader.read();
                        if (ch == -1) {
                            break;
                        }
                        System.out.print((char) ch);
                    }
                    if (i == 1) {
                        defaultTerminal = terminal;
                        System.out.println(" [default terminal]");
                    } else {
                        System.out.println();
                    }
                    i++;
                }
                /*Scanner in = new Scanner(System.in);
                try {
                    int option = in .nextInt();
                    terminal = terminals.get(option - 1);
                } catch (Exception e2) {
                    //System.err.println("Wrong value, selecting default terminal!");
                    terminal = defaultTerminal;

                }
                System.out.println("Selected: " + terminal.getName());
                //Console console = System.console(); */
                break;
            }


        } catch (Exception e) {
            System.err.println("Error occured:");
            e.printStackTrace();
            if(j==1){
            BufferedReader br = null;

								try {
						
									
						
									br = new BufferedReader(new FileReader("CUSTOMURISCARDREADERREGISTERED.txt"));
									sCurrentLine = br.readLine();
										char a_char = sCurrentLine.charAt(0);
										if(sCurrentLine.charAt(0) == '\"') {
											sCurrentLine = sCurrentLine.replaceAll("(\"scardreader://)", "");
											sCurrentLine = sCurrentLine.replaceAll("\"\\s", "");
										}
										else {
										  sCurrentLine = sCurrentLine.replaceAll("(scardreader:///)", "");
										  sCurrentLine = sCurrentLine.replaceAll("\"\\s", "");
										  sCurrentLine = sCurrentLine.replace(Character.toString(sCurrentLine.charAt(sCurrentLine.length()-1)), "");
										}
										System.out.println(sCurrentLine);
									
						
								} catch (IOException e7) {
									e7.printStackTrace();
								} finally {
									try {
										if (br != null)br.close();
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								}
            try{
              /*
                 *  fix for
                 *    Exception in thread "main" javax.net.ssl.SSLHandshakeException:
                 *       sun.security.validator.ValidatorException:
                 *           PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException:
                 *               unable to find valid certification path to requested target
                 */
                TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}

                    }
                };
								 
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
                /*
                 * end of the fix
                 */
                InetAddress IP = InetAddress.getLocalHost();
                System.out.println("IP of my system is := " + IP.getHostAddress());
                URL url = new URL(sCurrentLine + "%20card-reader-not-detected");
                URLConnection con = url.openConnection();
                Reader reader = new InputStreamReader(con.getInputStream());
                while (true) {
                    int ch = reader.read();
                    if (ch == -1) {
                        break;
                    }
                    System.out.print((char) ch);
                }
            } catch (Exception e2) {
            	e2.printStackTrace();
            }
        }}
        }
        return terminal;
    }

    public String byteArrayToHexString(byte[] b) {
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

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public Card establishConnection(CardTerminal ct) {
        this.atr = null;
        this.historical = null;
        this.protocol = null;

        System.out.println("To establish connection, please choose one of these protocols (1-4):");
        System.out.println("[1] - T=0");
        System.out.println("[2] - T=1");
        System.out.println("[3] - T=CL");
        System.out.println("[4] - * [default]");

        String p = "*";
        Scanner in = new Scanner(System.in);

        try {
            int option = in .nextInt();

            if (option == 1) p = "T=0";
            if (option == 2) p = "T=1";
            if (option == 3) p = "T=CL";
            if (option == 4) p = "*";
        } catch (Exception e) {
            //System.err.println("Wrong value, selecting default protocol!");
            p = "*";
        }

        System.out.println("Selected: " + p);

        Card card = null;
        try {
            card = ct.connect(p);
        } catch (CardException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        ATR atr = card.getATR();
        System.out.println("Connected:");
        System.out.println(" - ATR:  " + byteArrayToHexString(atr.getBytes()));
        System.out.println(" - Historical: " + byteArrayToHexString(atr.getHistoricalBytes()));
        System.out.println(" - Protocol: " + card.getProtocol());

        this.atr = atr.getBytes();
        this.historical = atr.getHistoricalBytes();
        this.protocol = card.getProtocol();

        return card;

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        // The name of the file to open.
        String fileName = "CUSTOMURISCARDREADERREGISTERED.txt";

        // This will reference one line at a time
        String line = null;

        Boolean flagDocEmpty = false;
        try {
            FileReader fileReader =
                new FileReader(fileName);
        } catch (FileNotFoundException ex1) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");
            fileName = "bin/CUSTOMURISCARDREADERREGISTERED.txt";

            try {
                // FileReader reads text files in the default encoding.
                URL urlToDictionary = PCSC.class.getResource(fileName);
                InputStream stream = urlToDictionary.openStream();


                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(stream, "UTF-8"));

                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    flagDocEmpty = true;
                }
                if (flagDocEmpty == true) {} else {
                    String[] cmd = {
                        "regedit.exe",
                        "/s",
                        "bin/SCARDREADER.reg"
                    };
                    Process process = Runtime.getRuntime().exec(cmd);
                    process.waitFor();
                    try {
                        fileName = "CUSTOMURISCARDREADERREGISTERED.txt";
                        // Assume default encoding.
                        FileWriter fileWriter =
                            new FileWriter(fileName);

                        // Always wrap FileWriter in BufferedWriter.
                        BufferedWriter bufferedWriter =
                            new BufferedWriter(fileWriter);

                        // Note that write() does not automatically
                        // append a newline character.
                        bufferedWriter.write("Hello there,");
                        bufferedWriter.write(" here is some text.");
                        bufferedWriter.newLine();
                        bufferedWriter.write("We are writing");
                        bufferedWriter.write(" the text to the registery.");

                        // Always close files.
                        bufferedWriter.close();

                        fileName = "SCARDREADER.bat";
                        // Assume default encoding.
                        FileWriter fileWriter1 =
                            new FileWriter(fileName);

                        // Always wrap FileWriter in BufferedWriter.
                        BufferedWriter bufferedWriter1 =
                            new BufferedWriter(fileWriter1);

                        // Note that write() does not automatically
                        // append a newline character.
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("@echo off");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("call :isAdmin");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("if %errorlevel% == 0 (");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("goto :run");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(") else (");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("echo Requesting administrative privileges...");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("goto :UACPrompt");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(")");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("exit /b");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(":isAdmin");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("fsutil dirty query %systemdrive% >nul");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("exit /b");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(":run");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("C:");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("cd C://Program Files (x86)//SCARDREADER//CARDPRESENTORNOTAPP");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("@echo %1> CUSTOMURISCARDREADERREGISTERED.txt");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("start SCARDREADER.exe");
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("exit /b");
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(":UACPrompt");
                        bufferedWriter1.newLine();
                        String temp1 = "Shell.Application";
                        String temp2 = "%temp%\\getadmin.vbs";
                        String nextCommand = "echo Set UAC = CreateObject^(\""+ temp1 +"\"^) > \""+temp2+"\"";
                        bufferedWriter1.write(nextCommand);
                        bufferedWriter1.newLine();
                        temp1 = "cmd.exe";
                        temp2 = "/c %~s0 %~1";
                        String temp3 = "";
                        String temp4 = "runas";
                        String temp5 = "%temp%\\getadmin.vbs";
                        nextCommand = "echo UAC.ShellExecute \""+temp1+"\", \""+temp2+"\", \""+temp3+"\", \""+temp4+"\", 1 >> \""+temp5+"\"";
                        bufferedWriter1.write(nextCommand);
                        bufferedWriter1.newLine();
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("\""+temp5+"\"");
                        nextCommand = "del \"" + temp5 + "\"";
                        bufferedWriter1.newLine();
                        bufferedWriter1.write(nextCommand);
                        bufferedWriter1.newLine();
                        bufferedWriter1.write("exit /B");
                        // Always close files.
                        bufferedWriter1.close();


                    } catch (IOException ex) {
                        System.out.println(
                            "Error writing to file '" + fileName + "'");
                        // Or we could just do this:
                        // ex.printStackTrace();
                    }
                }

                // Always close files.
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file '" +
                    fileName + "'");
            } catch (IOException ex) {
                System.out.println(
                    "Error reading file '" + fileName + "'");
                // Or we could just do this: 
                // ex.printStackTrace();
            }
        }

        PCSC pcsc = new PCSC();
        CardTerminal ct = pcsc.selectCardTerminal();
        Card c = null;
        if (ct != null) {
            /*c = pcsc.establishConnection(ct);
            CardChannel cc = c.getBasicChannel();
            //byte[] SELECT = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x09, (byte) 0x74, (byte) 0x69, (byte) 0x63, (byte) 0x6B, (byte) 0x65, (byte) 0x74, (byte) 0x69, (byte) 0x6E, (byte) 0x67, (byte) 0x00};
            byte[] baCommandAPDU = {
                (byte) 0x00,
                (byte) 0xA4,
                (byte) 0x04,
                (byte) 0x00,
                (byte) 0x08,
                (byte) 0xA0,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x03,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00
            };
            //byte[] baCommandAPDU = {(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x51, (byte) 0x00, (byte) 0x00};
            try {
                System.out.println("TRANSMIT: " + pcsc.byteArrayToHexString(baCommandAPDU));
                ResponseAPDU r = cc.transmit(new CommandAPDU(baCommandAPDU));
                System.out.println("RESPONSE: " + pcsc.byteArrayToHexString(r.getBytes()));
            } catch (CardException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
        }
    }

}