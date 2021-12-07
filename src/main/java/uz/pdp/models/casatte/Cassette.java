package uz.pdp.models.casatte;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cassette {
    private String type;
    private BigDecimal amount;
}
