package eapli.base.packet;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import eapli.base.clientusermanagement.domain.Address;
import eapli.base.clientusermanagement.domain.AddressType;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacketTest {




    @Test
    void ensurePacketisWellFormatted() {
        Packet packet = new Packet((byte) 0,(byte) 1,"data".getBytes(StandardCharsets.UTF_8));
       assertEquals(packet.getCode(),1);
       assertEquals("data",packet.data());
    }
}
