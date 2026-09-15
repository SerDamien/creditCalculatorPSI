package org.productStar;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        double discount = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите сумму долга: ");
        double principal = sc.nextDouble();

        while  (principal <= 0) {
            System.out.println("Не верная сумма. Повторите:");
            principal = sc.nextDouble();
        }

        System.out.println("Введите срок кредите в годах: ");
        int years = sc.nextInt();

        while  (years <= 0 || years > 100) {
            System.out.println("Не срок кредита. Повторите:");
            years = sc.nextInt();
        }

        System.out.println("Введите процентную ставку:");
        double annualInterestRate = sc.nextDouble();

        while  (annualInterestRate <= 0) {
            System.out.println("Не верная процентная ставка. Повторите:");
            annualInterestRate = sc.nextDouble();
        }

        System.out.println("Выберите тип платежа (1 - аннуитетный, 2 - дифференцированный)");
        int paymentType = sc.nextInt();

        if(paymentType == 1) {
            System.out.println("Введите сумму первоначального платежа, от 0 до полной суммы:");
            discount = sc.nextDouble();
            if(discount < 0) {
                discount = 0;
            }
        }

        ICalculator calculator;

        switch (paymentType) {
            case 1: calculator = new AnnuityCalculator();
            break;
            case 2: calculator = new DifferentiatedCalculator();
            break;
            default: calculator = null;
                System.out.println("Вы ввели не верный тип платежа");
                System.exit(0);
        };

        calculator.setPrincipal(principal);
        calculator.setAnnualInterestRate(annualInterestRate);
        calculator.setYears(years);

        if (discount != 0){
            calculator.setDiscount(discount);
            calculator.calculateWithDiscount();
        }else{calculator.calculatePayment();}

        printShedule(calculator);
    }

    private static void printShedule(ICalculator calculator) {
        double totalInterestPayment = 0;
        System.out.println("График платежей:");
        for (Payment payment : calculator.getPaymentsSchedule()) {
            System.out.printf("Месяц: %d, Платёж по основному долгу: %.2f, Процентный платёж: %.2f, Общий платёж: %.2fn",
                    payment.getMonth(), payment.getPrincipalPayment(), payment.getInterestPayment(), payment.getTotalPayment());
            totalInterestPayment += payment.getInterestPayment();
            System.out.println("");
        }
        System.out.printf("Общая сумма выплат: %.2f", calculator.getTotalPayment());
        System.out.println("");
        System.out.printf("Общая сумма процентов: %.2f", calculator.getTotalInterest());
    }
}