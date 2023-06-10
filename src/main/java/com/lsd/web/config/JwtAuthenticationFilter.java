package com.lsd.web.config;

import com.lsd.lib.util.JwtUtil;
import com.lsd.web.exception.ErrorCode;
import com.lsd.web.exception.WebException;
import com.lsd.web.service.UserService;
import com.lsd.web.util.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);
            LOGGER.error("request: {}",request);
            LOGGER.error("jwt: {}",jwt);
            LOGGER.error("StringUtils.hasText: {}",StringUtils.hasText(jwt));
            LOGGER.error("validateToken: {}",jwtProvider.validateToken(jwt));
            if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                // Lấy id user từ chuỗi jwt
                String username = jwtProvider.getUsername(jwt);
                // Lấy thông tin người dùng từ id
                LOGGER.info("get username: {}",username);
                UserDetails userDetails = userService.loadUserByUsername(username);
                LOGGER.info("get userDetails: {}",userDetails);
                if(userDetails != null) {
                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    LOGGER.error("userDetails null");
                }
            }else {
                LOGGER.error("jwt invalid");
//                throw new WebException(ErrorCode.UN_AUTHORIZED);
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "jwt invalid");

            }
        } catch (Exception ex) {
            LOGGER.error("Failed on set user authentication", ex);
//            response.setStatus(401);
//            response.getWriter().print("Failed on set user authentication");
        }
        try {
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            LOGGER.error("Failed filterChain", ex);
            throw new WebException(ErrorCode.UN_AUTHORIZED);
        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        LOGGER.error("Check bearerToken: {}",bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            LOGGER.error("Check Bearer true");
            return bearerToken.substring(7);
        }
        return null;
    }

//    public void setUnauthorizedResponse(HttpServletResponse response) {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        Response unAuthorizedResponse = Response.unauthorized().build();
//        try {
//            PrintWriter out = response.getWriter();
//            out.println(unAuthorizedResponse.toJsonString());
//        } catch (IOException e) {
//            LOGGER.error("IOException ", e);
//        }
//    }
}
