package com.janinc;

/*
Programmerat av Jan-Erik "Janis" Karlsson 2020-02-19
Programmering i Java EMMJUH19, EC-Utbildning
CopyLeft 2020 - JanInc
*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.LocalDate.parse;

public class App {
    public void run() {
        uppgift1();
        uppgift2();
        uppgift3();
        uppgift4();
        uppgift5();
    } // run

    private void printHeader(int num) {
        System.out.printf("%n%s Uppgift %d %s%n%n", "-".repeat(30), num, "-".repeat(30));
    } // printHeader

    private long calculateSubtotal(float retiredPercentage, float time, Duration hoursInLife) {
        return (long) (time * hoursInLife.toDays() * (1 - retiredPercentage) + time * 2 * hoursInLife.toDays() * retiredPercentage);
    } // calculateSubtotal

    private void uppgift5() {
        printHeader(5);
        final int lifeSpan = 80;
        final int sleepHoursPerDay = 8;
        final int workAndSchoolYears = 65;
        final float retiredPercentage = (float)((lifeSpan - workAndSchoolYears) / lifeSpan);
        final int workdaysPerYear = 226;
        final int workAndSchoolTimeInHours = 9;
        final float toiletTime = 24 * .02f;
        final float hygieneTime = .5f;
        final int choreTime = 1;
        final float eatingTime = 0.5f;
        final int mobileHoursPerDay = 3;

        Duration hoursInLife = Duration.ofHours((long)(lifeSpan * 365.24 * 24));
        Duration sleepHours = Duration.ofHours((long)(lifeSpan * sleepHoursPerDay * 365.24));
        Duration workAndSchoolHours = Duration.ofHours(workdaysPerYear *  workAndSchoolYears * workAndSchoolTimeInHours);
        Duration toiletHours = Duration.ofHours(calculateSubtotal(retiredPercentage, toiletTime, hoursInLife));
        Duration hygieneHours = Duration.ofHours(calculateSubtotal(retiredPercentage, hygieneTime, hoursInLife));
        Duration choreHours = Duration.ofHours(calculateSubtotal(retiredPercentage, choreTime, hoursInLife));
        Duration eatingHours = Duration.ofHours(calculateSubtotal(retiredPercentage, eatingTime, hoursInLife));
        Duration mobileHours = Duration.ofHours(mobileHoursPerDay * hoursInLife.toDays());

        Duration leftForFreeTime = hoursInLife.minus(sleepHours).minus(workAndSchoolHours).minus(toiletHours).minus(hygieneHours).minus(choreHours).minus(eatingHours);
        System.out.printf("Total levnadstid i dagar: %,d eller i timmar: %,d%n", hoursInLife.toDays(), hoursInLife.toHours());
        System.out.printf("Tid sovandes: %,d dagar, %,d timmar%n", sleepHours.toDays(), sleepHours.toHours());
        System.out.printf("Tid i skola/arbete: %,d dagar, %,d timmar%n", workAndSchoolHours.toDays(), workAndSchoolHours.toHours());
        System.out.printf("Tid för toalett: %,d dagar, %,d timmar%n", toiletHours.toDays(), toiletHours.toHours());
        System.out.printf("Tid för hygien: %,d dagar, %,d timmar%n", hygieneHours.toDays(), hygieneHours.toHours());
        System.out.printf("Tid för hushållssysslor: %,d dagar, %,d timmar%n", choreHours.toDays(), choreHours.toHours());
        System.out.printf("Tid för att äta: %,d dagar, %,d timmar%n", eatingHours.toDays(), eatingHours.toHours());
        System.out.printf("Tid över till annat (exklusive mobilanvändning): %,d dagar (%,d timmar) eller %d%%%n",
                leftForFreeTime.toDays(),
                leftForFreeTime.toHours(),
                (int)(100 * ((float)leftForFreeTime.toHours() / hoursInLife.toHours())));
        System.out.printf("Tid pillandes på telefonen: %,d dagar, eller %,d timmar%n", mobileHours.toDays(), mobileHours.toHours());
        leftForFreeTime = leftForFreeTime.minus(mobileHours);
        System.out.printf("Tid över till annat (inklusive mobilanvändning): %,d dagar (%,d timmar) eller %d%%%n",
                leftForFreeTime.toDays(),
                leftForFreeTime.toHours(),
                (int)(100 * ((float)leftForFreeTime.toHours() / hoursInLife.toHours())));
    } // uppgift5

    private void uppgift4() {
        printHeader(4);
        float numdaysUntil80 = 365.24f * 80;
        float minutesOnLoo = 27 * numdaysUntil80;
        int minutesInADay = 24 * 60;
        int days = (int)(minutesOnLoo / minutesInADay);
        int hours = (int)(minutesOnLoo - days * minutesInADay) / 60;
        int minutes = (int) (minutesOnLoo % 60);
        System.out.printf("Tid på toalett vid 80 bast: %,d dagar, %d timmar, %d minuter (totalt tid i minuter: %,.0f)%n", days, hours, minutes, minutesOnLoo);
    }

    private void uppgift3() {
        printHeader(3);
        int n13th = 0;
        for (int i = 1900; i < LocalDate.now().getYear(); i++) {
            for (int j = 1; j < 13; j++) {
                LocalDate date = LocalDate.parse(String.format("%d-%02d-13", i, j));
                if (date.getDayOfWeek() == DayOfWeek.FRIDAY) ++n13th;
            } // for j...
        } // for i...

        System.out.printf("Antal fredagar den 13:e sedan 1900: %d, snitt per år: %.2f%n", n13th, (float)n13th / (LocalDate.now().getYear() - 1900));
    }

    private void uppgift2() {
        printHeader(2);
        DateTimeFormatter formatterUS = DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm a").withLocale(Locale.US);
        DateTimeFormatter formatterSwe = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime datetimeNY = LocalDateTime.parse("20-02-2020 09:32 AM", formatterUS);
        ZonedDateTime datetimeNYZoned = ZonedDateTime.of(datetimeNY, ZoneId.of("America/New_York"));
        ZonedDateTime datetimeSthlmZoned = datetimeNYZoned.toInstant().atZone(ZoneId.of("Europe/Stockholm"));

        System.out.println("Tid i NY: " + formatterUS.format(datetimeNY) + ", tid i Tokholm: " + formatterSwe.format(datetimeSthlmZoned));
    } // uppgift2

    private void uppgift1() {
        printHeader(1);
        long days = ChronoUnit.DAYS.between(parse("1969-03-07"), LocalDate.now());
        System.out.printf("Jag är %,d dagar gammal%n", days);
    } // uppgift1
} // class App
