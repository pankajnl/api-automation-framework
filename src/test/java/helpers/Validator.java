package helpers;

public class Validator {
    //This method validates the email format using a regular expression.
    public static boolean validateEmail(String email) {
        // This method validates the email format using a regular expression.
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
