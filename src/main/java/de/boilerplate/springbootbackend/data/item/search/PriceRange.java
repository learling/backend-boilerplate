package de.boilerplate.springbootbackend.data.item.search;

public class PriceRange {

    public Double from, to;

    public PriceRange(String range) {
        range = range.replaceAll(",", ".");
        String[] fromTo = range.split("-");
        try {
            if (fromTo.length == 2) {
                double from = fromTo[0].length() > 0 ? Double.parseDouble(fromTo[0]) : 0;
                double to = fromTo[1].length() > 0 ? Double.parseDouble(fromTo[1]) : 0;
                if (from > to) {
                    this.from = to;
                    this.to = from;
                } else {
                    this.from = from;
                    this.to = to;
                }
            } else {
                String price = range.trim();
                this.from = this.to = price.length() > 0 ? Double.parseDouble(price) : 0;
            }
        } catch (NumberFormatException e) {
            this.from = this.to = 0.0;
        }
    }
}