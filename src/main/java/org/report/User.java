package org.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class User {
    private final int type;
    private final String tariffIndex;
    private final SortedSet<Call> calls;
    private double total;
    private final Tariff tariff;
    private final String number;



    public User(String type, String number) {
        this.calls = new TreeSet<>();
        this.number = number;
        this.tariffIndex = type;
        this.type = Integer.parseInt(tariffIndex);
        this.tariff = new Tariff(this.type);
        this.total = 0;
    }

    public void addCall(Call call) {
        calls.add(call);
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void getReport() throws IOException {
        total = tariff.getStartPrice();
        try (FileWriter fw = new FileWriter("." + System.getProperty("file.separator") + "reports" + System.getProperty("file.separator") + number + ".txt")) {
            fw.write(String.format("Tariff index: %s\n", tariffIndex));
            fw.write("----------------------------------------------------------------------------\n");
            fw.write(String.format("Report for phone number %s:\n", this.number));
            fw.write("----------------------------------------------------------------------------\n");
            fw.write("| Call Type |\tStart Time\t\t|\tEnd Time\t\t| Duration | Cost  |\n");
            for (Call call : calls) {
                fw.write(call.toString());
                total += tariff.getCallTotal(call.getDuration(), call.isIncoming());
            }
            fw.write("----------------------------------------------------------------------------\n");
            fw.write(String.format(Locale.US, "|\t\t\t\t\t\t\t\t\t\t\tTotal Cost: |\t%.2f rubles |\n", total));
            fw.write("----------------------------------------------------------------------------\n");
        }
    }
}