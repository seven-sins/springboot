## 项目说明
	前后端分离开发模式，前后端使用json数据交互

	一、后端：springMVC、mybatis、mysql、redis、druid、thymeleaf
		细节说明：
			1. 功能权限验证: com.springboot.config.interceptor.HandlerInterceptor
			2. Bean数据验证：
				a. 验证规则： com.springboot.config.annotation.Rule
				b. 方法参数前添加自定义注解@Valid
					eg:
						@PostMapping("/role")
						public Object create(@Valid @RequestBody Role role){
							roleService.insert(role);
							
							return super.resultMsg(0, "操作成功");
						}
				c. 使用aspectj前置通知拦截实现验证， 具体实现类：com.springboot.config.annotation.AspectConfig
					相关代码：
						@Pointcut("execution(* com.springboot.controller..*.*(..))")
						public void init() {
						}

						@Before("init()")
						public void beforeAdvice(JoinPoint joinPoint) {
							MethodSignature sign = (MethodSignature) joinPoint.getSignature();
							Method method = sign.getMethod();
							Annotation[][] annotations = method.getParameterAnnotations();
							Object[] objects = joinPoint.getArgs();
							int i = 0;
							for (Annotation[] annotation : annotations) {
								Object object = objects[i++];
								for (Annotation obj : annotation) {
									if (obj instanceof Valid) {
										new Validator(object);
									}
								}
							}
						}

	二、前端：(兼容ie10及以上)react、redux、webpack、babel、es6、fetch、less
		1. 项目源代码：同仓库下的 react-redux
		2. 少数dom操作使用到seven.js(之前开发用来替代jquery的js工具库)，工具源代码：同仓库下的seven

	三、其他：nginx、swagger2等


## 待解决问题 


    









## run
    1. gradle assemble
    2. java -jar xx.jar

## 应用监控
    a. 引入依赖
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    b. 默认url
        /autoconfig
        /beans
        /dump
        /configprops
        /health
        /info
        /metrics
        /mappings
        /env
        /shutdown
        配置application.properties
        endpoints.enabled=false
        endpoints.env.enabled=true
    c. 通过jconsole查看
	