package org.report;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class TrafficScanner {
    private final String filename;
    private final Map<String, User> reports;

    public TrafficScanner(String filename) {
        this.filename = filename;
        this.reports = new HashMap<>();
    }

    private void addCall(String[] line) {
        String number = line[1];
        User user = reports.computeIfAbsent(number, k -> new User(line[4], number));
        Call call = new Call(line[0], line[2], line[3], user.getTariff());
        user.addCall(call);
    }

    public void scan() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] cdrLine = br.readLine().split(",\\s+");
            while (cdrLine != null) {
                addCall(cdrLine);

                try {
                    cdrLine = br.readLine().split(",\\s++");
                } catch (NullPointerException e) {
                    cdrLine = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getReport() {
        if (reports.isEmpty()) {
            scan();
        }
        for (String number : reports.keySet()) {
            reports.get(number).getReport();
        }
    }

}
