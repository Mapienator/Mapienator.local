import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonTest {
    public static void main(String[] args) throws LoginException, URISyntaxException, InterruptedException, IOException {
        //https://secure.runescape.com/m=itemdb_rs/api/graph/1965.json
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://secure.runescape.com/m=itemdb_rs/api/graph/1965.json"))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());


    }
}