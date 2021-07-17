package com.diandian.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.diandian.util.TokenUtils;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authToken = request.getHeader(this.tokenHeader);
        if (authToken != null) {
            String usercode = TokenUtils.getUsercodeFromToken(authToken);
            logger.info("checking authentication " + usercode);
            if (usercode != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                /*SystemUserModel user = userService.getUserByUsercode(usercode);
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(usercode);

                if (TokenUtils.validateToken(authToken, userDetails) && userService.isPermissionApi(user.getId(), request.getRequestURI(), request.getMethod())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + usercode + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }*/
            }
        }
        filterChain.doFilter(request, response);
	}

	private String tokenHeader = "Authorization";
}
