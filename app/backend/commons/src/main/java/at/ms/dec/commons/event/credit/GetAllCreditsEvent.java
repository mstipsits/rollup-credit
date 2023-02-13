package at.ms.dec.commons.event.credit;

import at.ms.dec.commons.dto.CreditDto;
import at.ms.dec.commons.event.DecEvent;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class GetAllCreditsEvent implements DecEvent {
    private List<CreditDto> creditDtos;
}
