public class ConsoleCaesar {

    public static void main(String[] args) {
        //0 - режим e - encrypt, d - decrypt, b - brute
        //1 - начальный символ
        //2 - конечный символ
        //3 - сдвиг
        String type = args[0];
        char start = args[1].charAt(0);
        char end = args[2].charAt(0);
        int k = Integer.parseInt(args[3]);
        String source = args[4];
        var caesar = new Caesar(start, end);
        switch (type) {
            case "e":
                System.out.println(caesar.encrypt(source, k));
                break;
            case "d":
                System.out.println(caesar.decrypt(source, k));
                break;
            case "b":
                System.out.println(caesar.tryDecrypt(source));
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип");
        }
    }
}
