package at.ms.dec.commons.event.smartcontract;

import at.ms.dec.commons.event.DecEvent;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DeployedSmartContractEvent implements DecEvent {
    private long creditId;
    private String smartContractAddress;
    private String debtorAddress;
}
