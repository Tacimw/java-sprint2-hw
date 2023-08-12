public class Constants {

    /*
    лучше int
    байт это немного про другое)

    вопрос. зачем это менять если из байта в инт конвертится без проблем и автоматом?
    и второй в догонку. про что байт?
    */
    public final static int months = 3;
    public final static int year = 2021;
    public final static String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public static String getMonthName( int aMonth, int aYear ) {
        if ((aMonth >= 0) && (aMonth < Constants.monthNames.length)) {
            return Constants.monthNames[aMonth] + " " + aYear;
        } else {
            return "Неизвестный месяц";
        }
    }

}
