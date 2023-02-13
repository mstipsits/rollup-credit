package at.ms.dec.commons.event.credit;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.commons.event.DecEvent;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreateCreditEvent implements DecEvent {
    private CreditDto creditDto;
}
