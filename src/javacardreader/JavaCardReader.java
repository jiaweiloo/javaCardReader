package javacardreader;

import java.util.List;
import java.util.Scanner;
import javax.smartcardio.*;
import java.awt.Image;
import static javacardreader.Test.hexStringToByteArray;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class JavaCardReader {

    public String ic;
    public String gender;
    public String name;
    public String oldIc;
    public String dob;
    public String birthPlace;
    public String citizenship;
    public String race;
    public String religion;
    public String address1;
    public String address2;
    public String address3;
    public String poscode;
    public String city;
    public String state;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = new Scanner(System.in);
        byte[] file = {0x01, 0x00, 0x01, 0x00};
        byte[] file2 = {0x02, 0x00, 0x01, 0x00};
        byte[] file3 = {0x03, 0x00, 0x01, 0x00};
        byte[] file4 = {0x04, 0x00, 0x01, 0x00};
        byte[] file5 = {0x05, 0x00, 0x01, 0x00};

        //first two columns is length, last two columns is offsets
//        byte[][] all = {
//            {0x50, 0x00, (byte) 0x99, 0x00}, //name
//            {0x0D, 0x00, (byte) 0x11, 0x01},//id
//            {0x04, 0x00, (byte) 0x44, 0x01}, //JPN_BirthDate
//            {0x19, 0x00, (byte) 0x2B, 0x01}, //JPN_BirthPlace
//            {0x04, 0x00, (byte) 0x44, 0x01}, //JPN_DateIssued
//            {0x12, 0x00, (byte) 0x48, 0x01}, //JPN_Citizenship
//            {0x19, 0x00, (byte) 0x5A, 0x01}, //JPN_Race
//            {0x0B, 0x00, (byte) 0x73, 0x01}, //JPN_Religion
//            {0x01, 0x00, (byte) 0x8E, 0x01} //JPN_Category
//        };
        //mykid
        byte[][] all2 = {
            {(byte) 0x06, 0x00, (byte) 0x04, 0x00}, //NODAFTARKELAHIRAN
            {(byte) 0x0C, 0x00, (byte) 0x12, 0x00}, //IC
            {(byte) 0x96, 0x00, (byte) 0x1E, 0x00}, //NAME
            {(byte) 0x01, 0x00, (byte) 0xB4, 0x00}, //GENDER
            {(byte) 0x11, 0x00, (byte) 0xB5, 0x00}, //NATIONALITI
            {(byte) 0x1E, 0x00, (byte) 0xC6, 0x00}, //STATEBORN
            {(byte) 0x1E, 0x00, (byte) 0xE4, 0x00}, //ADDRESS1
            {(byte) 0x1E, 0x00, (byte) 0x02, 0x01}, //ADDRESS2
            {(byte) 0x1E, 0x00, (byte) 0x20, 0x01}, //ADDRESS3
            {(byte) 0x03, 0x00, (byte) 0x3E, 0x01}, //POSTCODE
            {(byte) 0x1E, 0x00, (byte) 0x41, 0x01}, //CITY
            {(byte) 0x1E, 0x00, (byte) 0x5F, 0x01}, //STATE
            {(byte) 0x0E, 0x00, (byte) 0x7D, 0x01}, //RELIGION
            {(byte) 0xFF, 0x00, (byte) 0x8B, 0x01} //RANDOM
        };

        byte[][] all3 = {
            {(byte) 0xFF, 0x00, (byte) 0x00, 0x00}, //NODAFTARKELAHIRAN
            {(byte) 0xFF, 0x00, (byte) 0xFE, 0x00}, //FF
            {(byte) 0xFF, 0x00, (byte) 0xFD, 0x01}, //FF
            {(byte) 0xFF, 0x00, (byte) 0xFE, 0x03}, //FF
            {(byte) 0xFF, 0x00, (byte) 0xFE, 0x04}, //FF
            {(byte) 0xFF, 0x00, (byte) 0xFE, 0x05} //FF
        };
        
        byte[][] all = {
            {(byte) 0x0A, 0x00, (byte) 0x00, 0x00}, //NODAFTARKELAHIRAN
            {(byte) 0x28, 0x00, (byte) 0xA, 0x00}, //POB HOSPITAL
            {(byte) 0x29, 0x00, (byte) 0x32, 0x00}, //POB
            {(byte) 0x03, 0x00, (byte) 0x5B, 0x00}, //??
            {(byte) 0x96, 0x00, (byte) 0x5E, 0x00}, //POB?
            {(byte) 0x99, 0x00, (byte) 0xF4, 0x00}, //FF
            {(byte) 0x4A, 0x00, (byte) 0x8D, 0x01}, //7FF
            {(byte) 0x96, 0x00, (byte) 0xDA, 0x01}, //877 MOTHER Name
            {(byte) 0x04, 0x00, (byte) 0x70, 0x02}, //9FF unknwon 4 digits
            {(byte) 0x17, 0x00, (byte) 0x74, 0x02}, //10FF MOTHER DETAILS
            {(byte) 0x1E, 0x00, (byte) 0x8B, 0x02}, //11FF MOTHER DETAILS
            {(byte) 0x4d, 0x00, (byte) 0xA9, 0x02}, //12MOTHER ic
            {(byte) 0x96, 0x00, (byte) 0xF6, 0x02}, //13FATHER
            {(byte) 0x39, 0x00, (byte) 0x8C, 0x03}, //14FATHER DETAILS
            {(byte) 0x32, 0x00, (byte) 0xC5, 0x03} //15REGISTER CENTRE
        };
        
        try {
            // Display the list of terminals
            TerminalFactory factory = TerminalFactory.getDefault();
            List<CardTerminal> terminals = factory.terminals().list();
            System.out.println("Terminals: " + terminals);

            // Use the first terminal
            CardTerminal terminal = terminals.get(0);

//            System.out.println("Ready card and press enter to proceed:");
//            String input = scanner.nextLine();
            // Connect with the card
            Card card = terminal.connect("*");
            System.out.println("card: " + card);
            CardChannel channel = card.getBasicChannel();

            System.out.println("6D 00	=  Instruction code not supported or invalid");
            System.out.println("6A 82	= File not found");
            System.out.println("6E 00	= Class not supported");
            System.out.println("90 00	= Command successfully executed (OK).");
            System.out.println("91 08	= Expecting 8 bytes data....");
            System.out.println("94 ??	= Total of ?? bytes is ready in buffer");

            System.out.println();

//          Send Select JPN Application Command
//          00 A4 04 00 0A A0 00 00 00 74 "4A 50 4E" 00 10
//          "4A 50 4E" represents "JPN"
            byte[] cmd1 = {(byte) 0x00, (byte) 0xA4, 0x04, 0x00, 0x0A, (byte) 0xA0, 0x00, 0x00, 0x00, 0x74, 0x4A, 0x50, 0x4E, 0x00, 0x10};
            byte[] cmd3 = {(byte) 0x00, (byte) 0xA4, 0x04, 0x00, 0x0A, (byte) 0xA0, 0x00, 0x00, 0x00, 0x74, 0x49, 0x4D, 0x4D, 0x00, 0x10};
            ResponseAPDU answer = channel.transmit(new CommandAPDU(cmd1));
            System.out.println("Select JPN Application, RETURN: " + answer.toString() + " SW1: " + Integer.toHexString(answer.getSW1()) + " SW2 : " + Integer.toHexString(answer.getSW2()));

            //Send Get Response Command
            //00 C0 00 00 05
            byte[] cmd2 = {(byte) 0x00, (byte) 0xC0, 0x00, 0x00, 0x05};
            answer = channel.transmit(new CommandAPDU(cmd2));
            System.out.println("Get Response, RETURN: " + answer.toString() + " SW1: " + Integer.toHexString(answer.getSW1()) + " SW2 : " + Integer.toHexString(answer.getSW2()));
            for (int row = 0; row < all.length; ++row) {
                process(all[row], file2, channel);
            }
            // Disconnect the card
            card.disconnect(true);

        } catch (Exception e) {
            System.out.println("Ouch: " + e.toString());
        }
    }

    public static String byteToString(byte[] returnData) {
        String combinedString = "";
        for (int row = 0; row < returnData.length; row++) {
            char ch = (char) returnData[row];
            combinedString += ch;
        }
        return combinedString;
    }

    public static void setLength(byte[] length, CardChannel channel) {

        byte[] lengthFixSection = {(byte) 0xC8, (byte) 0x32, 0x00, 0x00, 0x05, 0x08, 0x00, 0x00, 0x00, 0x00};
        lengthFixSection[8] = length[0];
        lengthFixSection[9] = length[1];
//        byte[] combine = (byte[])ArrayUtils.addAll(lengthFixSection, length);
        try {
            ResponseAPDU answer = channel.transmit(new CommandAPDU(lengthFixSection));
//            System.out.println("Set Length, RETURN: " + answer.toString() + " SW1: " + Integer.toHexString(answer.getSW1()) + " SW2 : " + Integer.toHexString(answer.getSW2()));

        } catch (Exception e) {
            System.out.println("Ouch: " + e.toString());
        }
    }

    public static void selectInfo(byte[] file, byte[] offset, byte[] length, CardChannel channel) {
        //Send Select Info Command
        //CC 00 00 00 08 "01 00 01 00" "E9 00" "28 00"
        //read jpn-1-1, offset 0x00E9, length 0x28
        try {
            byte[] selectinfobyte = {(byte) 0xCC, (byte) 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
            selectinfobyte[5] = file[0];
            selectinfobyte[6] = file[1];
            selectinfobyte[7] = file[2];
            selectinfobyte[8] = file[3];
            selectinfobyte[9] = offset[0];
            selectinfobyte[10] = offset[1];
            selectinfobyte[11] = length[0];
            selectinfobyte[12] = file[1];
            ResponseAPDU answer = channel.transmit(new CommandAPDU(selectinfobyte));
//            System.out.println("Select Info, RETURN: " + answer.toString() + " SW: " + Integer.toHexString(answer.getSW()));
        } catch (Exception e) {
            System.out.println("Ouch: " + e.toString());
        }
    }

    public static void readInfo(byte[] length, CardChannel channel) {
//        Send Read Info Command
//        CC 06 00 00 "28"
//        length 0x28
        try {
            byte[] readinfobyte = {(byte) 0xCC, (byte) 0x06, 0x00, 0x00, 0x00};
            readinfobyte[4] = length[0];
            ResponseAPDU answer = channel.transmit(new CommandAPDU(readinfobyte));
            byte[] returnanswer = answer.getData();
            System.out.println("Read Info, IN HEX :" + Arrays.toString(returnanswer));
            //System.out.println(printBytes(returnanswer, "utf8Bytes"));
            System.out.println("Read Info, Data   :" + new String(returnanswer, "UTF-8"));
        } catch (Exception e) {
            System.out.println("Ouch: " + e.toString());
        }
    }

    public static void process(byte[] all, byte[] file, CardChannel channel) {
        byte[] offset = {all[2], all[3]};
        byte[] length = {all[0], all[1]};
        setLength(length, channel);
        selectInfo(file, offset, length, channel);
        readInfo(length, channel);
    }
}
