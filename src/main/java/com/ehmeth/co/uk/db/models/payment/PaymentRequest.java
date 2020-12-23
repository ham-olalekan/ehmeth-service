package com.ehmeth.co.uk.db.models.payment;

import com.ehmeth.co.uk.Exceptions.BadRequestException;
import com.ehmeth.co.uk.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
public class PaymentRequest {
    private String token;
    private BigDecimal amount;

    public boolean isValid() {
        if (StringUtil.isBlank(token))
            throw new BadRequestException("payment token can not be null or empty");
        if (Objects.isNull(amount))
            throw new BadRequestException("payment amount can not be null or empty");
        return true;
    }
}
