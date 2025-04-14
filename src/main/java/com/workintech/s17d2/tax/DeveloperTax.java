package com.workintech.s17d2.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{
    @Override
    public Double getSimpleTaxRate() {
        return 15d;
    }

    @Override
    public Double getMiddleTaxRate() {
        return 25d;
    }

    @Override
    public Double getUpperTaxRate() {
        return 35d;
    }

    public double applyTax(double salary, String experience){
        switch (experience){
            case "JUNIOR":
                return salary - salary*getSimpleTaxRate();
            case "MID":
                return salary - salary*getMiddleTaxRate();
            case "SENIOR":
                return salary - salary*getUpperTaxRate();
            default:
                return salary;
        }
    }
}
