package pl.crystalek.sokoban.util;

public class TimeUtil {

    private TimeUtil() {
    }

    public static String getDateInString(long timeMilis, final String delimiter) {
        if (timeMilis < 1000) {
            return "0";
        }
        final StringBuilder result = new StringBuilder();

        for (final Time time : Time.values()) {
            final long divisionTime = timeMilis / time.getMillis();

            final String form = time.getForm(divisionTime);
            if (!form.isEmpty()) {
                result.append(delimiter).append(divisionTime).append(" ").append(form);
                timeMilis -= divisionTime * time.getMillis();
            }
        }

        return result.substring(delimiter.length());
    }
}
