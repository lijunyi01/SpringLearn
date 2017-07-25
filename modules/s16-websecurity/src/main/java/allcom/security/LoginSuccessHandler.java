package allcom.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ljy on 17/7/14.
 * ok
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        String requestIp = request.getRemoteAddr();

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        //输出登录提示信息
        log.debug("管理员 " + userDetails.getUsername() + " 从 IP：" + requestIp + "  登录");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
