package at.ms.dec.commons.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreditDto implements Serializable {
    private Long id;
    private String loanerAddress;
    private String debtorAddress;
    private String smartContractAddress;
    private BigDecimal creditAmountWei;
    private long interestRatePercent;
    private int periodMonths;
}
