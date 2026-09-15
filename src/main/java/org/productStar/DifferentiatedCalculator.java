package org.productStar;

import java.util.ArrayList;
import java.util.List;

public class DifferentiatedCalculator implements ICalculator{
    private double principal;
    private double annualInterestRate;
    private int years;
    private List<Payment> payments;
    private double discount;

    @Override
    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    @Override
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void setYears(int years) {
        this.years = years;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public void calculatePayment() {
        double monthlyRate = annualInterestRate / 12 / 100;
        payments = new ArrayList<>();

        for (int month = 1; month <= years * 12; month++) {
            double principalPayment = principal / (years * 12);
            double interestPayment = principal * monthlyRate;
            principal -= principalPayment;

            payments.add(new Payment(month, principalPayment, interestPayment));
        }
    }

    @Override
    public void calculateWithDiscount() {
        principal = principal-discount;
        double monthlyRate = annualInterestRate / 12 / 100;
        payments = new ArrayList<>();

        for (int month = 1; month <= years * 12; month++) {
            double principalPayment = principal / (years * 12);
            double interestPayment = principal * monthlyRate;
            principal -= principalPayment;

            payments.add(new Payment(month, principalPayment, interestPayment));
        }
    }

    @Override
    public double getTotalPayment() {
        return payments.stream().mapToDouble(Payment::getTotalPayment).sum();
    }

    @Override
    public double getTotalInterest() {
        return payments.stream().mapToDouble(Payment::getInterestPayment).sum();
    }

    @Override
    public List<Payment> getPaymentsSchedule() {
        return payments;
    }
}
