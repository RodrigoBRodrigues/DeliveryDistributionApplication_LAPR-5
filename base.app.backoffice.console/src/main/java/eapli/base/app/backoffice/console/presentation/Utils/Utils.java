package eapli.base.app.backoffice.console.presentation.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Utils {

    public static int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                throw new NumberFormatException();
            }
        } while (true);
    }

    public static String readLineFromConsole(String prompt) {
        try {
            System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object showAndSelectOne(List list, String header) {
        showListNormal(list, header);
        return selectsObject(list);
    }

    public static Object showAndSelectOneWithMessage(List list, String header) {
        showListNormalUs402(list, header);
        System.out.println("Notice: If you wish to see more options type the option 36");
        return selectsObjectSpecial(list);
    }

    private static void showListNormal(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o);
        }
        System.out.println();
        System.out.println("0 - Cancel");
    }

    private static void showListNormalUs402(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o);
        }
        System.out.println();
        System.out.println("36 - Next List");
    }

    public static int showAndSelectIndex(List list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    public static void showList(List<MenuItem> list, String header) {
        System.out.println(header);

        int index = 0;
        for (MenuItem o : list) {
            index++;

            System.out.println(index + ". " + o.getDescription());
        }
        System.out.println();
        System.out.println("0 - Cancel");
    }

    public static Object selectsObject(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Type your option: (Enter a valid option!)");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    public static Object selectsObjectSpecial(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Type your option: (Enter a valid option!)");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size() + 1);

        if (value == 0) {
            return null;
        } else if (value == 36) {
            return null;
        } else {
            return list.get(value-1);
        }
    }

    public static int selectsIndex(List list) {
        String input;
        Integer value;
        do {

            input = Utils.readLineFromConsole("Type your option: ");
            value = detectNumberFormatException(input);
        } while (value < -1 || value > list.size());

        return value - 1;
    }

    public static Integer detectNumberFormatException(String s) {
        Integer n;
        try {
            n = Integer.valueOf(s);
            return n;
        } catch (NumberFormatException ex) {
            System.out.println("This is not an option!");
            return -2;
        }
    }
}