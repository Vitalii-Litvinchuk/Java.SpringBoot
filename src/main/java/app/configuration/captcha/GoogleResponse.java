package app.configuration.captcha;

import lombok.Data;

@Data
public class GoogleResponse {
    private boolean success;
    private String challengeTs;
    private String hostname;
}