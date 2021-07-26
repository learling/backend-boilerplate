package de.boilerplate.springbootbackend.data.item.search;

public class IdRange {

    public Long from, to;

    public IdRange(String range) {
        String[] fromTo = range.split("-");
        try {
            if (fromTo.length == 2) {
                long from = Long.parseLong(fromTo[0].trim());
                long to = Long.parseLong(fromTo[1].trim());
                if (from > to) {
                    this.from = to;
                    this.to = from;
                } else {
                    this.from = from;
                    this.to = to;
                }
            } else {
                String id = range.trim();
                this.from = this.to = Long.parseLong(id);
            }
        } catch (NumberFormatException e) {
            this.from = this.to = 0L;
        }
    }
}