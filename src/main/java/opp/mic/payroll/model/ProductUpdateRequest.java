package opp.mic.payroll.model;

public record ProductUpdateRequest(String name, String description, String features,AttributeUpateRequest[] attributes,
                             double salePrice, Long quantity, double regularPrice) {
}

