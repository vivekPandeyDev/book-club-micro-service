package org.loop.troop.domain.permitio;

import io.permit.sdk.Permit;
import io.permit.sdk.PermitConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermitIoConfiguration {

    @Bean
    public Permit permit(){
        return new Permit(
                new PermitConfig.Builder("permit_key_1KyxBXiAgIpgclEhtExBSB1gpCNTbjTNOHp8WC0AqLdBgyp4YrIFl4ojj78KZWl6BXwakXh7oudOp9MAhw03b6")
                        .withPdpAddress("http://localhost:7766")
                        .withDebugMode(false)
                        .build()
        );
    }

}
