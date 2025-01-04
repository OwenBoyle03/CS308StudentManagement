import org.junit.Test;
import org.junit.jupiter.api.Assertions;

//test for dbConnect
public class ConnectTest {
    @Test
    public void checkConnection(){
        DbConnect testConnection = new DbConnect();
        Assertions.assertNotNull(testConnection.getDbConnect());
    }
}
