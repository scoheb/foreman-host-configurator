package com.scoheb.foreman.cli.model;

/**
 * Created by shebert on 17/01/17.
 */
public class Reservation {
    public final String reason;

    public Reservation(String reservation) {
        this.reason = reservation;
    }

    public static Reservation none() {
        return new EmptyReservation("");
    }

    public static class EmptyReservation extends Reservation {
        public EmptyReservation(String reservation) {
            super(reservation);
        }
    }
}
