package eapli.base.packet;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Packet implements Serializable {
    private byte version;
    private byte code;
    private byte d_length1;
    private byte d_length2;
    private byte[] data;

    public Packet(byte version, byte code, byte[] data) {
        this.version = version;
        this.code = code;
        this.data = data;
        this.d_length1 = (byte) calculateDlength1(data.length);
        this.d_length2 = (byte) calculateDlength2(data.length);
    }

    private static int calculateDlength1(int size) {
        return size - (calculateDlength2(size) * 256);
    }

    private static int calculateDlength2(int size) {
        if (size < 256) {
            return 0;
        } else return size / 256;
    }

    public byte getCode() {
        return this.code;
    }

    public String data() {
        String str = new String(data, StandardCharsets.UTF_8);
        return str;
    }

 //   public static void main(String[] args) {
  //      System.out.println(Packet.calculateDlength1(1000));
    //    System.out.println(Packet.calculateDlength2(1000));

    //}
}
