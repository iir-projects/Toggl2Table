package UnemployedVoodooFamily.Data;

import java.time.LocalDate;

public class WorkHours {

    private LocalDate from;
    private LocalDate to;
    private Double hours;
    private String note;

    public WorkHours(LocalDate from, LocalDate to, Double hours) {
        this.from = from;
        this.to = to;
        this.hours = hours;
        this.note = "";
    }

    public WorkHours(LocalDate from, LocalDate to, Double hours, String note) {
        this.from = from;
        this.to = to;
        this.hours = hours;
        this.note = note;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public Double getHours() {
        return hours;
    }

    public String getNote() {
        return note;
    }

    public DateRange getRange() {
        return DateRange.of(this.from, this.to);
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public void setNote(String note) {
        this.note = note;
    }
}