package br.com.cadastro.temperature.ipvigilante;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class IpVigilanteService {

    private final RestTemplate restTemplate;

    public IpVigilanteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IpVigilanteDTO findLocationByIp(HttpServletRequest request) {
        String ip = findUserIp(request);
        return restTemplate.getForObject("https://ipvigilante.com/json/" + ip, IpVigilanteDTO.class);
    }

    private String findUserIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null || "".equals(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}

