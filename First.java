import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class first {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DateFormat formatIn = new SimpleDateFormat("yyyy-MM", Locale.US);

        Date date = null;
        while (date == null) {
            String line = scanner.nextLine();
            try {
                date = formatIn.parse(line);
            } catch (ParseException e) {
                System.out.println("Sorry, that's not valid. Please try again.");
            }
        }

        Calendar calendar = Calendar.getInstance();

        System.out.println(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));

        String previousMonth = "";

        while (calendar.getTime().compareTo(date) <= 0) {
            String currentMonth = formatIn.format(calendar.getTime());
            if (!currentMonth.equals(previousMonth)) {
                if (!previousMonth.isEmpty()) {
                    System.out.println(); // Start a new line for a new month
                }
                System.out.print(currentMonth + ": ");
                previousMonth = currentMonth;
            }

            // Print the date
            System.out.print(calendar.get(Calendar.DATE) + " ");
            // Move to the next occurrence of the same day of the week (add 7 days)
            calendar.add(Calendar.DATE, 7);
        }
    }
}