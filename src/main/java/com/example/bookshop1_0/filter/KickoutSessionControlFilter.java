package com.example.bookshop1_0.filter;

import com.example.bookshop1_0.entity.ResponseResult;
import com.example.bookshop1_0.utils.IStatusMessage;
import com.example.bookshop1_0.utils.ShiroFilterUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

@Log4j2
public class KickoutSessionControlFilter extends AccessControlFilter {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private Cache<String, Deque<Serializable>> cache;
    private CacheManager cacheManager;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }


    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }
    public CacheManager getCacheManager(){
        return this.cacheManager;
    }
    public Cache<String, Deque<Serializable>> getCache(){
        return this.cache;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.info("isAccessAllowed--------------------------------------------");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        log.info("onAccessDenied--------------------------------------------");
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        // 没有登录授权 且没有记住我
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            // 如果没有登录，直接进行之后的流程
            //判断是不是Ajax请求，异步请求，直接响应返回未登录
            ResponseResult responseResult = new ResponseResult();
            if (ShiroFilterUtils.isAjax(request)) {
                log.info(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
                responseResult.setCode(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
                responseResult.setMessage("您已在别处登录，请您修改密码或重新登录");
                out(response, responseResult);
                return false;
            } else {
                log.info("第一次登陆return true");
                String username = request.getParameter("username");
                log.info("===当前用户username：==" + username);
                Serializable sessionId = session.getId();
                log.info("===当前用户sessionId：==" + sessionId);
                // 读取缓存用户 没有就存入
                Deque<Serializable> deque = cache.get(username);
                log.info("===当前deque：==" + deque);
                return true;
            }
        }

        log.info("==session时间设置：" + String.valueOf(session.getTimeout())
                + "===========");
        // 当前用户
        String username = (String) subject.getPrincipals().getPrimaryPrincipal();
        log.info("===当前用户username：==" + username);
        Serializable sessionId = session.getId();
        log.info("===当前用户sessionId：==" + sessionId);
        // 读取缓存用户 没有就存入
        Deque<Serializable> deque = cache.get(username);
        log.info("===当前deque：==" + deque);
        if (deque == null) {
            // 初始化队列
            deque = new ArrayDeque<Serializable>();
        }
        // 如果队列里没有此sessionId，且用户没有被踢出；放入队列
        if (!deque.contains(sessionId)
                && session.getAttribute("kickout") == null) {
            // 将sessionId存入队列
            deque.push(sessionId);
            // 将用户的sessionId队列缓存
            cache.put(username, deque);
        }

        //被踢出的人的session
        Serializable kickoutSessionId = null;
        // 如果队列里的sessionId数超出最大会话数，开始踢人
        while (deque.size() > maxSession) {
            log.info("===deque队列长度：==" + deque.size());
            // 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
            if (kickoutAfter) { // 如果踢出后者
                log.info("踢出后者");
                kickoutSessionId = deque.removeFirst();
            } else { // 否则踢出前者
                log.info("踢出前者");
                kickoutSessionId = deque.removeLast();
                log.info("===被踢用户kickoutSessionId：==" + kickoutSessionId);
            }
            // 踢出后再更新下缓存队列
            cache.put(username, deque);

            // 获取被踢出的sessionId的session对象
            Cache<Serializable, Session> sessionCache = cacheManager.getCache("sessionId-session");
            Session kickoutSession = sessionCache.get(kickoutSessionId);
            log.info(kickoutSession);
            if (kickoutSession != null) {
                // 设置会话的kickout属性表示踢出了
                log.info("被踢出了");
                kickoutSession.setAttribute("kickout", true);
                sessionCache.remove(kickoutSessionId);
            }
        }

        // 如果被踢出了，(前者或后者)直接退出，重定向到踢出后的地址
        if (session.getAttribute("kickout") != null
                && (Boolean) session.getAttribute("kickout") == true) {
            // 会话被踢出了
            try {
                subject.logout();
                log.info("以前的用户被踢出了");
            } catch (Exception e) { // ignore
            }
            saveRequest(request);
            log.info("==踢出后用户重定向的路径kickoutUrl:" + kickoutUrl);
            // ajax请求
            // 重定向
            //WebUtils.issueRedirect(request, response, kickoutUrl);
            return isAjaxResponse(request, response);
        }
        return true;

    }

    /**
     * @param response
     * @param result
     * @描述：response输出json
     * @创建人：wyait
     * @创建时间：2018年4月24日 下午5:14:22
     */

    public static void out(ServletResponse response, ResponseResult result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");//设置编码
            response.setContentType("application/json");//设置返回类型
            out = response.getWriter();
            out.println(objectMapper.writeValueAsString(result));//输出
            log.info("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息成功");
        } catch (Exception e) {
            log.error("用户在线数量限制【wyait-manager-->KickoutSessionFilter.out】响应json信息出错", e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    private boolean isAjaxResponse(ServletRequest request,
                                   ServletResponse response) throws IOException {
        // ajax请求
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        //判断是不是Ajax请求
        ResponseResult responseResult = new ResponseResult();
        if (ShiroFilterUtils.isAjax(request)) {
            log.debug(getClass().getName() + "当前用户已经在其他地方登录，并且是Ajax请求！");
            responseResult.setCode(IStatusMessage.SystemStatus.MANY_LOGINS.getCode());
            responseResult.setMessage("您已在别处登录，请您修改密码或重新登录");
            out(response, responseResult);
        } else {
            // 重定向
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }

}