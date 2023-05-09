class Hex2Str {
    public static byte[] Hex2Byte(String inHex) {
        String [] hex = inHex.split(" ");
        byte[] byteArray = new byte[hex.length];

        for (int i = 0; i < hex.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(hex[i], 16);
        }

        return byteArray;
    }

    public static String Byte2Hex(byte[] inByte) {
        StringBuilder sb = new StringBuilder();
        String hexString;

        for(int i = 0; i < inByte.length; i++) {
            String hex = Integer.toHexString(inByte[i]);

            if (hex.length() == 1) {
                sb.append("0");
            }

            if (hex.length() > 2) {
                hex = hex.substring(hex.length() - 2);
            }

            sb.append(hex);
            sb.append(" ");
        }

        hexString = sb.toString();
        hexString = hexString.toUpperCase();

        return hexString;
    }

    public static void main(String[] args) {
        String inHex = "1F 22 0B F5";
        
        byte[] byteArray = Hex2Byte(inHex);
        String hexString = Byte2Hex(byteArray);

        System.out.println(hexString);
    }
}