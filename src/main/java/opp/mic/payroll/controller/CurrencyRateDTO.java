package opp.mic.payroll.controller;

import opp.mic.payroll.model.CurrencyRate;

import java.util.List;

public record CurrencyRateDTO(List<CurrencyRate> fx,int totalPages,int size,int pageNumber) {
}
