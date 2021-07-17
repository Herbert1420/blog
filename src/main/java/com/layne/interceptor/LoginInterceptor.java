package com.layne.interceptor;

import com.layne.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 */

public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 目标方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查逻辑
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            //放行
            return true;
        }

        //拦截住。未登录。跳转到登录页
        request.setAttribute("msggage", "请先登录");
//        re.sendRedirect("/");
        request.getRequestDispatcher("/admin").forward(request, response);
        return false;
    }
}