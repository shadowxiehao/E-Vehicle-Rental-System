/**package team.lc01.lb02.c.evss;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import team.lc01.lb02.c.evss.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EvssApplicationTests {

    @Autowired
    private VehicleService vehicleService;
    @Test
    void testgetLocationBySN() {
        System.out.println(Arrays.toString(vehicleService.getLocationBySN("AABBCC445568")));
    }

}*/
