package org.report;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        TrafficScanner ts = new TrafficScanner("cdr.txt");
        ts.getReport();
    }
}