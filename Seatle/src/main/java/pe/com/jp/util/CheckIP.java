package pe.com.jp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckIP {

    static private final Logger LOGGER = Logger.getLogger("pe.com.sgv.model.CheckIP");

    public InetAddress host() {
        InetAddress addr=null;
        try {
             addr = InetAddress.getLocalHost();
            System.out.println("host:" + addr.getHostName());
            System.out.println("ip:" + addr.getHostAddress());
        } catch (UnknownHostException ex) {
            LOGGER.log(Level.SEVERE, "Error al consultar el Host");
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return addr;
    }
    
}
