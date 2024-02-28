package gamma;

import javax.xml.bind.DatatypeConverter;

public class ByteHexConverter {

    public static String bytesToHex(byte[] byteArray) {
        return DatatypeConverter.printHexBinary(byteArray);
    }

    // Метод для преобразования строки в шестнадцатеричном формате в массив байтов
    public static byte[] hexToBytes(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }
}
