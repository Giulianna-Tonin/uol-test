package br.com.cadastro.ipvigilante.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Component
public class IpVigilanteService {

    @Autowired
    private RestTemplate restTemplate;

    public IpVigilanteDTO getLocationByIp(HttpServletRequest request) {
        String ip = findUserIp(request);
        return findLocationByIp(ip);
    }

    private IpVigilanteDTO findLocationByIp(String ip) {
        ResponseEntity<IpVigilanteDTO> ipivigilanteDTO = restTemplate.getForEntity("https://ipvigilante.com/json/" + ip, IpVigilanteDTO.class);
        return ipivigilanteDTO.getBody();
    }

    private String findUserIp(HttpServletRequest request) {
        String ipAddress = "";
        if (request != null) {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || "".equals(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
        }
        return ipAddress;
    }
}

