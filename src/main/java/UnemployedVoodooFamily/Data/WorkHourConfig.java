package UnemployedVoodooFamily.Data;

import UnemployedVoodooFamily.Logic.FileLogic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * A configuration for work-hours in different periods
 */
public class WorkHourConfig {
    private List<WorkHours> periods;

    /**
     * Create a configuration
     * @param periods Work hour periods to register in the configuration.
     */
    public WorkHourConfig(List<WorkHours> periods) {
        if (periods != null) {
            this.periods = periods;
        } else {
            this.periods = new LinkedList<>();
        }
    }

    public WorkHourConfig() {
        this.periods = new LinkedList<>();
    }

    /**
     * Load config from a JSON file
     * @param filePath Path to the JSON file
     * @return A work hour config object or null on error
     */
    public static WorkHourConfig loadFromJson(String filePath) {
        List<WorkHours> periods = FileLogic.loadWorkHourListFromJsonFile(filePath);
        if (periods != null) {
            return new WorkHourConfig(periods);
        } else {
            return null;
        }
    }


    /**
     * Get the corresponding work hours object for the given date
     * Performs a search over the WorkHours list, until it finds an object which contains the given date
     * Always ignores saturdays and sundays
     * @param date the date to check
     * @return a WorkHours object which is contains the given date, or null if no match is found.
     */
    public WorkHours getFor(LocalDate date) {
        // No work on weekend
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return null;
        }

        // Search through the list, return first period containing the date
        WorkHours wh = null;
        Iterator<WorkHours> it = this.periods.iterator();
        while (wh == null && it.hasNext()) {
            WorkHours period = it.next();
            if (period.getRange().contains(date)) {
                wh = period;
            }
        }
        return wh;
    }

    /**
     * Save configuration to a JSON file
     * @param filePath Path to the JSON file
     * @return True on success, false on error
     */
    public boolean saveToJson(String filePath) {
        return FileLogic.saveCollectionToJson(filePath, this.periods);
    }

    /**
     * Return true when the configuration does not contain any entries
     * @return True when empty, false when the config contains some entries.
     */
    public boolean isEmpty() {
        return this.periods.isEmpty();
    }

    /**
     * Get an iterator over the period list
     * @return List iterator
     */
    public ListIterator<WorkHours> listIterator() {
        return this.periods.listIterator();
    }

    /**
     * Add a work hour period to the configuration
     * @param workHourPeriod A period to save in the config
     */
    public void add(WorkHours workHourPeriod) {
        this.periods.add(workHourPeriod);
    }

    /**
     * Sort the work hour periods according to start date
     */
    public void sort() {
        this.periods.sort((o1, o2) -> {
            LocalDate d1 = o1.getFrom();
            LocalDate d2 = o2.getFrom();
            return d1.compareTo(d2);
        });
    }

    /**
     * Remove a work hour period from the configuration
     * @param workHours A work hour period to remove
     */
    public void remove(WorkHours workHours) {
        this.periods.remove(workHours);
    }
}