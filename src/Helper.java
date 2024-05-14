import java.util.Scanner;

public class Helper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        try{
            return Integer.parseInt(scanner.nextLine());
        }catch (Exception e) {
            System.err.println("Vui lòng nhập số!");
            return getInt();
        }
    }

    public static long getLong() {
        try{
            return Long.parseLong(scanner.nextLine());
        }catch (Exception e) {
            System.err.println("Vui lòng nhập số!");
            return getLong();
        }
    }


    public static String getText() {
        return scanner.nextLine();
    }

}
