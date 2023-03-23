package org.report;

public class Tariff {
    private long minutesLimit;
    private double incomingPrice;
    private double outgoingPrice;
    private double startPrice;

    private boolean changed;

    private final int type;



    public Tariff(int type) {
        this.type = type;
        setParams(type);
    }

    public double getStartPrice() {
        return this.startPrice;
    }

    public double getCallTotal(long duration, boolean isIncoming) {
        double price = isIncoming ? incomingPrice : outgoingPrice;
        double total = 0;

        if (duration < minutesLimit) {
            minutesLimit -= duration;
            total = price * duration;
        } else {
            total = price * minutesLimit;
            duration -= minutesLimit;
            if (!changed) {
                changeParams(type);
                price = isIncoming ? incomingPrice : outgoingPrice;
            }
            total += price * duration;
        }
        return total;
    }

    private void setParams(int type) {
        switch (type) {
            case 6 -> {
                this.minutesLimit = 300;
                this.startPrice = 100;
                this.incomingPrice = 0;
                this.outgoingPrice = 0;
            }
            case 3 -> {
                this.minutesLimit = 0;
                this.startPrice = 0;
                this.incomingPrice = 1.5;
                this.outgoingPrice = 1.5;
                this.changed = true;
            }
            case 11 -> {
                this.minutesLimit = 100;
                this.startPrice = 0;
                this.incomingPrice = 0;
                this.outgoingPrice = 0.5;
            }
        }
    }
    private void changeParams(int type) {
        switch (type) {

            case 6 -> {
                this.minutesLimit = 0;
                this.incomingPrice = 1;
                this.outgoingPrice = 1;
                this.changed = true;
            }

            case 11 -> setParams(11);
        }
    }
}
