package UnemployedVoodooFamily.Data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.threeten.extra.YearWeek;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Used to structure data for the corresponding TableView
 */
public class DailyFormattedDataModel {

    private LocalDate date;
    private SimpleStringProperty weekday;
    private SimpleDoubleProperty workedHours;
    private SimpleDoubleProperty supposedHours;
    private SimpleDoubleProperty extraTime;
    private SimpleDoubleProperty accumulatedHours;
    private SimpleStringProperty note = new SimpleStringProperty("");
    private SimpleObjectProperty<YearWeek> weekNumber;

    /**
     * Creates a WeeklyTimeDataModel object
     * Used to structure data for the corresponding TableView
     * @param workedHours   String with hours worked
     * @param supposedHours String with supposed work hours
     */
    public DailyFormattedDataModel(Double workedHours, Double supposedHours, LocalDate date, Double accumulated,
                                   String note) {

        String weekDayStr = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        this.weekday = new SimpleStringProperty(weekDayStr);
        this.workedHours = new SimpleDoubleProperty(workedHours);
        this.supposedHours = new SimpleDoubleProperty(supposedHours);
        this.extraTime = new SimpleDoubleProperty(workedHours - supposedHours);
        this.accumulatedHours = new SimpleDoubleProperty(accumulated);
        if(null != note) {
            this.note = new SimpleStringProperty(note);
        }

        this.date = date;
        this.weekNumber = new SimpleObjectProperty<>(YearWeek.from(date));
    }

    /**
     * Returns the weekday
     * @return the weekday
     */
    public String getWeekday() {
        return this.weekday.get();
    }

    /**
     * Returns the amount worked
     * @return the amount worked
     */
    public Double getWorkedHours() {
        return this.workedHours.get();
    }

    /**
     * Returns the supposed amount worked
     * @return the supposed amount worked
     */
    public Double getSupposedHours() {
        return this.supposedHours.get();
    }

    /**
     * Returns the amount of extraTime
     * @return the amount of extraTime
     */
    public Double getExtraTime() {
        return this.extraTime.get();
    }

    /**
     * Returns the date
     * @return the date
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns the written note as a string
     * @return the written note as a string
     */
    public String getNote() {
        return this.note.get();
    }

    public double getAccumulatedHours() {
        return accumulatedHours.get();
    }

    /**
     * Returns the week number
     * @return the week number
     */
    public YearWeek getWeekNumber() {
        return weekNumber.get();
    }
}
