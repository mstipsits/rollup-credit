package at.ms.dec.creditservice.bom;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity(name = "CREDIT")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "LoanerAddress must not be null")
    @Column(name = "LOANER ADDRESS")
    private String loanerAddress;

    @Column(name = "DEBTOR_ADDRESS")
    private String debtorAddress;

    @Column(name = "SMART_CONTRACT_ADDRESS")
    private String smartContractAddress;

    @NotNull(message = "CreditAmount must not be null")
    @Column(name = "CREDIT_AMOUNT_WEI")
    private BigDecimal creditAmountWei;

    @NotNull(message = "InterestRatePercent must not be null")
    @Column(name = "INTEREST_RATE_PERCENT")
    private long interestRatePercent;

    @NotNull(message = "PeriodMonths must not be null")
    @Column(name = "PERIOD_MONTHS")
    private int periodMonths;
}