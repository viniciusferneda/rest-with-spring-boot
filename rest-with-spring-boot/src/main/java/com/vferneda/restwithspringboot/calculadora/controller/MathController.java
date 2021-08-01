package com.vferneda.restwithspringboot.calculadora.controller;

import com.vferneda.restwithspringboot.calculadora.helper.NumberHelpers;
import com.vferneda.restwithspringboot.exception.UnsuportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@RestController
public class MathController {

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public BigDecimal sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (!NumberHelpers.isNumeric(numberOne) || !NumberHelpers.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(numberOne).add(NumberHelpers.convertToBigDecimal(numberTwo)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public BigDecimal sub(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (!NumberHelpers.isNumeric(numberOne) || !NumberHelpers.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(numberOne).subtract(NumberHelpers.convertToBigDecimal(numberTwo)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public BigDecimal mult(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (!NumberHelpers.isNumeric(numberOne) || !NumberHelpers.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(numberOne).multiply(NumberHelpers.convertToBigDecimal(numberTwo)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public BigDecimal div(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (!NumberHelpers.isNumeric(numberOne) || !NumberHelpers.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(numberOne).divide(NumberHelpers.convertToBigDecimal(numberTwo)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/med/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public BigDecimal med(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {
        if (!NumberHelpers.isNumeric(numberOne) || !NumberHelpers.isNumeric(numberTwo)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(numberOne).add(NumberHelpers.convertToBigDecimal(numberTwo)).divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/sqrt/{number}", method = RequestMethod.GET)
    public BigDecimal square(@PathVariable("number") String number) throws Exception {
        if (!NumberHelpers.isNumeric(number)) {
            throw new UnsuportedMathOperationException("Please set a numeric value!");
        }
        return NumberHelpers.convertToBigDecimal(number).sqrt(MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_UP);
    }


}
