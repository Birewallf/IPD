package local.bwg.ipd;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IPAddrPublicTest {
    private IPAddrPublic ip;

    @Before
    public void initTest() {
        ip = new IPAddrPublic();
    }

    @Test
    public void getIpAddr() {
        System.out.print(ip.getIpAddr());
    }
}