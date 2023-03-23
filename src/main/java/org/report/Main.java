package org.report;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void addRecord(Map<String, User> map, String[] CDR_line) {
        String number = CDR_line[1];
        User user = map.computeIfAbsent(number, k -> new User(CDR_line[4], number));
        Call call = new Call(CDR_line[0], CDR_line[2], CDR_line[3], user.getTariff());
        user.addCall(call);
    }
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("cdr.txt"));
        String[] CDR_line = in.readLine().split(",\\s++");
        Map<String, User> reports = new HashMap<>();
        while (CDR_line != null) {
            addRecord(reports, CDR_line);

            try {
                CDR_line = in.readLine().split(",\\s++");
            } catch (NullPointerException e) {
                CDR_line = null;
            }
        }

        for (User user : reports.values()) {
            user.getReport();
        }
    }
}