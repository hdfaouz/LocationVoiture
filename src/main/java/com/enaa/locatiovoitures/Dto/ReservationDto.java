package com.enaa.locatiovoitures.Dto;

import com.enaa.locatiovoitures.Model.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private ReservationStatus status;

    public ReservationDto() {
    }

    public ReservationDto(LocalDate startDate, LocalDate endDate, double totalPrice, ReservationStatus status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
