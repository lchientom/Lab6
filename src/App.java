public class App {
    public static void main(String[] args) throws Exception {
        String s = "auauauauauauau";
        String s2 = "ababababababaababa";
        String test = "true";
        String test3 = "(555)-444-3477";
        String test4 = "s2s4s6s8s10";
        String test4_2 = "s2s4sadasd";
        System.out.println("test part 4: " + twoNum(test4));
        System.out.println("test part 4: " + twoNum(test4_2));
        String test5 = "Password1!";
        System.out.println("test part 5: " + PasswordValidate(test5));
        String test5_1 = "12312"; // true
        String test5_2 = "a2bcka";
        System.out.println("test part 5: " + PasswordValidate(test5_1));
        System.out.println("test part 5: " + PasswordValidate(test5_2));
        String test6 = "aabbcc";
        String test6_2 = "abcdef";
        System.out.println("test part 6: " + doubleChecker(test6));
        System.out.println("test part 6: " + doubleChecker(test6_2));
    }

    public static boolean affirmative(String s) {
        if (s.matches("[tT]rue") || s.matches("[yY]es") || s.matches("[yY]")) {
            System.out.println("true");
            return true;
        } else if (s.matches("[fF]alse") || s.matches("[nN]o")) {
            System.out.println("false");
            return false;
        } else {
            System.out.println("Invalid input");
            return false;
        }
    }

    public static boolean conVolPattern(String s) {
        if (s.matches(".*[aeiouAEIOU].*")) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    /*
     * String test1 = "(555) 444 3477"; //true
     * String test2 = "(111) 111 1121"; //true
     * String test3 = "(555)-444-3477"; //true
     * String test4 = "(111) 111-1121"; //true
     * String test5 = "555 444 3477"; //false
     * String test6 = "111-111-1121"; //false
     */
    public static void checkPhoneFormat(String s) {
        // if
        // (s.matches("\\(\\d{3}\\)(?:\\s\\d{3}\\s\\d{4}|-\\d{3}-\\d{4}|\\s\\d{3}-\\d{4})"))
        if (s.matches("\\(\\d{3}\\)\\s|[-]\\d{3}\\s|[-]\\d{4}")) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    // part 4
    public static boolean twoNum(String s) {
        return s.matches("\\D*\\d\\D*\\d\\D*");
    }

    // part 5
    public static boolean PasswordValidate(String s) {
        return s.matches("^(?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%^&*]).{4,}$");
    }
    // part 6
    public static boolean doubleChecker(String s) {
        return s.matches(".*(.)\\1.*");
    }
}
