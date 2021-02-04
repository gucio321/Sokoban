package pl.crystalek.sokoban.util;

enum Time {

    YEAR("rok", "lata", "lat", 31_536_000_000L),
    MONTH("miesiąc", "miesiace", "miesiecy", 2_592_000_000L),
    WEEK("tydzień", "tygodnie", "tygodni", 604_800_000L),
    DAY("dzień", "dni", "dni", 86_400_000L),
    HOUR("godzina", "godziny", "godzin", 3_600_000L),
    MINUTE("minuta", "minuty", "minut", 60_000),
    SECOND("sekunda", "sekundy", "sekund", 1_000);

    private final String text1;
    private final String text2;
    private final String text3;
    private final long millis;

    Time(final String text1, final String text2, final String text3, final long millis) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.millis = millis;
    }

    String getForm(final long number) {
        if (number == 0) {
            return "";
        }

        if (number == 1) {
            return text1;
        }

        final long onesDigit = number % 10;
        final long tensNumber = number % 100;

        if (onesDigit < 2 || onesDigit > 4 || tensNumber >= 12 && tensNumber <= 14) {
            return text3;
        }

        return text2;
    }

    long getMillis() {
        return millis;
    }
}
