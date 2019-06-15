package com.bestbuses.bestmaintenancerecord;

public class RecordItems {
    private String dateFitted,dateRemoved,kilometers;

    public RecordItems(String dateFitted, String dateRemoved, String kilometers) {
        this.dateFitted = dateFitted;
        this.dateRemoved = dateRemoved;
        this.kilometers = kilometers;
    }

    public String getDateFitted() {
        return dateFitted;
    }

    public String getDateRemoved() {
        return dateRemoved;
    }

    public String getKilometers() {
        return kilometers;
    }
}
