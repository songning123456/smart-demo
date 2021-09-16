package com.sonin.ssmframework.springmvc.servlet;

import com.sonin.ssmframework.spring.annotation.Controller;
import com.sonin.ssmframework.spring.annotation.RequestMapping;
import com.sonin.ssmframework.spring.annotation.RequestParam;
import com.sonin.ssmframework.spring.entity.BeanWrapper;
import com.sonin.ssmframework.spring.factory.impl.WebApplicationContext;
import com.sonin.ssmframework.springmvc.adapter.HandlerAdapter;
import com.sonin.ssmframework.springmvc.entity.HandlerMapping;
import com.sonin.ssmframework.springmvc.entity.ModelAndView;
import com.sonin.ssmframework.springmvc.view.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author sonin
 * @date 2021/9/16 19:26
 */
public class DispatcherServlet extends HttpServlet {

    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private Map<HandlerMapping, HandlerAdapter> handlerAdapterMap = new HashMap<>();
    private List<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            doDispatch(req, resp);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 在web.xml中根据<param-name>获取<param-value>
        String LOCATION = "contextConfigLocation";
        String strLocation = config.getInitParameter(LOCATION);
        // 获取路径,初始化容器上下文
        WebApplicationContext applicationContext = new WebApplicationContext(strLocation);
        initStrategies(applicationContext);
    }

    private void initStrategies(WebApplicationContext context) {
        //通过HandlerMapping，将请求映射到处理器
        initHandlerMappings(context);
        //通过HandlerAdapter进行多类型的参数动态匹配
        initHandlerAdapters(context);
        //通过viewResolver解析逻辑视图到具体视图实现
        initViewResolvers(context);
    }

    private void initHandlerMappings(WebApplicationContext context) {
        String[] beanNames = context.getBeanDefinitions();
        for (String beanName : beanNames) {
            BeanWrapper beanWrapper = (BeanWrapper) context.getBean(beanName);

            if (!beanWrapper.getOriginalBean().getClass().isAnnotationPresent(Controller.class)) {
                continue;
            }
            Class<?> clazz = beanWrapper.getOriginalBean().getClass();
            String strBaseUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping classRM = clazz.getAnnotation(RequestMapping.class);
                strBaseUrl = classRM.value().trim();
            }

            // Controller扫描之后,接着扫描其Method
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping methodRM = method.getAnnotation(RequestMapping.class);
                String methodUrl = methodRM.value().trim();
                String strTotalUrl = ("/" + strBaseUrl + methodUrl.replaceAll("\\*", ".*")).replaceAll("/+", "/");
                this.handlerMappings.add(new HandlerMapping(beanWrapper.getOriginalBean(), method, Pattern.compile(strTotalUrl)));
                System.out.println("Mapping: " + strTotalUrl + " , " + method);
            }
        }
    }

    private void initHandlerAdapters(WebApplicationContext context) {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            Method method = handlerMapping.getMethod();
            Map<String, Integer> methodParamMapping = new HashMap<>(10);
            Annotation[][] paras = method.getParameterAnnotations();
            // 先处理命名参数
            for (int i = 0; i < paras.length; i++) {
                for (Annotation a : paras[i]) {
                    if (a instanceof RequestParam) {
                        String paraName = ((RequestParam) a).value().trim();
                        methodParamMapping.put(paraName, i);
                        break;
                    }
                }
            }
            // 再处理request, response参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] == HttpServletRequest.class || parameterTypes[i] == HttpServletResponse.class) {
                    // 记录下method形参中request,response两种参数的位置
                    methodParamMapping.put(parameterTypes[i].getName(), i);
                }
            }
            this.handlerAdapterMap.put(handlerMapping, new HandlerAdapter(handlerMapping, methodParamMapping));
            System.out.println("initHandlerAdapters: " + methodParamMapping + " , " + method);
        }
    }

    private void initViewResolvers(WebApplicationContext context) {
        String fileRootPath = context.getConfig().getProperty("templateRoot");
        String absolutePaths = Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileRootPath)).getFile();

        File viewDirectory = new File(absolutePaths);
        for (File file : Objects.requireNonNull(viewDirectory.listFiles())) {
            this.viewResolvers.add(new ViewResolver(file.getName(), file));
        }
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException {
        HandlerMapping handlerMapping = getHandler(request, response);
        if (handlerMapping == null) {
            try {
                response.getWriter().write("404 not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        HandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);
        ModelAndView viewAndModel = handlerAdapter.handler(request, response);
        processDispatcherResult(request, response, viewAndModel);
    }

    private void processDispatcherResult(HttpServletRequest request, HttpServletResponse response, ModelAndView viewAndModel) {
        try {
            if (viewAndModel == null) {
                response.getWriter().write("404 not found, please try again");
                return;
            }
            for (ViewResolver viewResolver : this.viewResolvers) {
                if (viewAndModel.getViewName().equals(viewResolver.getViewName())) {
                    String strResult = viewResolver.processViews(viewAndModel);
                    strResult = strResult == null ? "" : strResult;
                    response.getWriter().write(strResult);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handlerMapping) {
        if (handlerMapping == null) {
            return null;
        }
        return this.handlerAdapterMap.get(handlerMapping);
    }

    private HandlerMapping getHandler(HttpServletRequest request, HttpServletResponse response) {
        if (this.handlerMappings.size() < 1) {
            return null;
        }
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();
        url = url.replace(contextPath, "").replace("/+", "/");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            if (handlerMapping.getUrlPattern().matcher(url).matches()) {
                return handlerMapping;
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
    
}
