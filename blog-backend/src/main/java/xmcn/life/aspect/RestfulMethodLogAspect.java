package xmcn.life.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Component
@Aspect
@Slf4j
@Order(5)
public class RestfulMethodLogAspect {

	private static String[] types = {"java.lang.Integer", "java.lang.Double",
			"java.lang.Float", "java.lang.Long", "java.lang.Short",
			"java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
			"java.lang.String", "int", "double", "long", "short", "byte",
			"boolean", "char", "float"};

	@Pointcut(" @annotation(org.springframework.web.bind.annotation.GetMapping) || " +
			" @annotation(org.springframework.web.bind.annotation.PostMapping) || " +
			" @annotation(org.springframework.web.bind.annotation.PutMapping) || " +
			" @annotation(org.springframework.web.bind.annotation.DeleteMapping) ||"+
			" @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void restfulMethod() {
	}

	private ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();
	private ThreadLocal<String> uriThreadLocal = new ThreadLocal<>();

	@Before("restfulMethod()")
	public void logBeforeExecute(JoinPoint joinPoint) throws Throwable {
		startTimeThreadLocal.set(System.currentTimeMillis());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		StringBuilder logStr = new StringBuilder();
		assert attributes != null;
		HttpServletRequest request = attributes.getRequest();
		HttpServletResponse response = attributes.getResponse();
		String requestURI = request.getRequestURI();
		uriThreadLocal.set(requestURI);

		if (isAnonymousRequest(requestURI)) {
			return;
		}

		try {
			// 记录header

			// 记录下请求内容
			logStr.append("uri is : ").append(requestURI).append("\n").append("\n").append("class_method is : ").
					append(joinPoint.getSignature().getDeclaringTypeName()).append(".").
					append(joinPoint.getSignature().getName()).append("\n").append("args is :");
			// joinPoint获取参数名
			String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
			// joinPoint获取参数值
			Object[] args = joinPoint.getArgs();
			// 打印请求参数
			int i = 0;
			for (Object arg : args) {
				if (arg == request || arg == response) {
					i += 1;
					continue;
				}
				String typeName = "";
				try {
					typeName = arg.getClass().getTypeName();
				} catch (Exception e) {
				}

				if (!Arrays.asList(types).contains(typeName)) {
					// 把参数转成json格式, 参数太长的不打印
					logStr.append(params[i]).append("=").append(JSONObject.toJSONString(arg));
				} else {
					logStr.append(params[i]).append("=").append(arg);
				}
				i += 1;
				logStr.append(" & ");
			}

			log.info(logStr.toString());
		} catch (Throwable e) {
			log.error("beforeRestfulAdvice exception", e);
		}
	}

	@AfterReturning(returning = "ret", value = "restfulMethod()")
	public void logAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.debug("response is : " + ret);
		if (startTimeThreadLocal.get() != null) {
			log.info("uri is: {} , spent time is : {}" , uriThreadLocal.get(), (System.currentTimeMillis() - startTimeThreadLocal.get()) + " ms");
		} else {
			log.info("uri is: {} " , uriThreadLocal.get());
		}
		startTimeThreadLocal.remove();
	}

	private boolean isAnonymousRequest(String uri){
		for (String h5RequestUris : ANONYMOUS_URIS) {
			if (uri.startsWith(h5RequestUris)) {
				return true;
			}
		}
		return false;
	}
	private static final String[] ANONYMOUS_URIS = {
			"/api/app/payment/clear/cache","/api/app/payment/load/cache","api/app/account/registerDistributor","/api/paynotify/alipay/payCallback","/api/paynotify/wechatpay/payCallback",
	};
}

