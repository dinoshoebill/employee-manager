package employeemanager.security.payload.response;

public class JwtResponse {

    private final String type = "Bearer";
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
