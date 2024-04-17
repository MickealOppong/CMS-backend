package opp.mic.payroll.model;

import java.time.Instant;

public record CategoryDT0(long id, String name, String description, Long quantity, Long sale, Instant createAt) {
}
