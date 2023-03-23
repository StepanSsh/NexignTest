package org.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

class Call implements Comparable<Call>{
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat OUT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat OUT_DURATION = new SimpleDateFormat("HH:mm:ss");

    static {
        OUT_DURATION.setTimeZone(TimeZone.getTimeZone("GMT"));
    }


    private final boolean incoming;
    private final long duration;
    private final Date start_time;
    private final Date end_time;
    private final Tariff tariff;
    private final double cost;

    public Call(String incoming, String start_time, String end_time, Tariff tariff) {
        this.incoming = incoming.equals("02");
        this.start_time = parseTime(start_time);
        this.end_time = parseTime(end_time);
        this.tariff = tariff;
        long diff = this.end_time.getTime() - this.start_time.getTime();
        this.duration = (diff + 60_000L - 1L) / 60_000L;

        this.cost = tariff.getCallTotal(duration, this.incoming);
    }


    public long getDuration() {
        return this.duration;
    }

    public boolean isIncoming() {
        return this.incoming;
    }

    private Date parseTime(String time) {
        Date res = new Date();
        try {
            res = DF.parse(time);
        } catch (ParseException e) {
            System.out.println("Failed to parse time");
        }
        return res;
    }

    @Override
    public String toString() {
        String type = isIncoming() ? "02" : "01";
        String startTime = OUT_TIME.format(start_time);
        String endTime = OUT_TIME.format(end_time);
        String duration = OUT_DURATION.format(this.end_time.getTime() - this.start_time.getTime());
        return String.format(Locale.US,"|\t%s\t| %s | %s | %s | %.2f |\n",
                type, startTime, endTime, duration, cost);
    }

    @Override
    public int compareTo(Call o) {
        return start_time.compareTo(o.start_time);
    }
}
