# Spring 5 demo (using annotation and without maven)

## Setup
Because this example don't have maven so we need add all jar file in /libs for running program correctly. 

## Configure in XML for component Scan
For use annotation get bean, we must config in XML file like this:
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:context="http://www.springframework.org/schema/context"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context.xsd">

	<context:component-scan base-package="com.sang" />
	
</beans>
```

And in Class which we want to inject, we add annotation @Component("id"), if we use @Component without param, the default id will be used with name of the class which lower first character like tennisCoach:
```
package com.sang;

import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Practice your backhand volley";
    }
}
```

Then we get bean in main of App:
```
...
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Coach theCoach = context.getBean("tennisCoach", Coach.class);

        System.out.println(theCoach.getDailyWorkout());
...
```

## Inject Dependency by annotation @Autowire

### Setup
#### Create Interface
We will create an interface which we will use for injecting to Client class
```
package com.sang;

public interface FortuneService {
    String getFortune();
}
```

#### Create Class
Then we create class which implements FortuneService and mark as Component for scanning by annotation @Component
```
package com.sang;

import org.springframework.stereotype.Component;

@Component
public class FortuneServiceImpl implements FortuneService{
    @Override
    public String getFortune() {
        return "Today is your lucky day";
    }
}
```
### In constructor
We create constructor in class which has field need inject dependency and mark annotation @Autowire in constructor, it will get the class which implement:

```
package com.sang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach{
    private FortuneService fortuneService;

    @Autowired
    public TennisCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Practice your backhand volley";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
```

Note: As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary if the target bean only defines one constructor to begin with. However, if several constructors are available, at least one must be annotated to teach the container which one to use.

### In setter

Like constructor, we can mark as setter method annotation @Autowire and Object Factory will find FotuneService and assign it to field of object which need FortuneService as dependency.
```
    public TennisCoach() {}

    @Autowired
    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }
```

### In any method

Like setter, we can mark any method as @Autowire and Object Factory work as inject constructor or setter.
```
...
@Autowire
public void anyMethod(FortuneService fortuneService) {
    this.fortuneService = fortuneService;
}
...
```
### In a field
Width field injection, we don't need any constructor inject tion or setter injection.
```
...
@Autowire
private FortuneService fortuneService;
...
```

## With multiple bean can be injected to dependency
If we have many Class for inject to interface Injection we can use annotation @Qualifier("id") under @Autowire with id of bean we want to inject.
```
...
    @Autowired
    @Qualifier("fortuneServiceImpl")
    private FortuneService fortuneService;
...
```

Noted: If we use @Qualifier in constructor, we must place it inside the constructor argument. Reference: https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-autowired-annotation-qualifiers 

## Default bean name
In general, when using Annotations, for the default bean name, Spring uses the following rule.

If the annotation's value doesn't indicate a bean name, an appropriate name will be built based on the short name of the class (with the first letter lower-cased).

However, for the special case of when BOTH the first and second characters of the class name are upper case, then the name is NOT converted.

For the case of RESTFortuneService

RESTFortuneService --> RESTFortuneService

Read more: https://docs.oracle.com/javase/8/docs/api/java/beans/Introspector.html#decapitalize(java.lang.String).

## Inject value from properties file by annotation
We can use annotation @Value("${properties.field}") to inject value. Ex: In properties file:
```
foo.email=test@gmail.com
foo.team=arsenal
```

In the class we want to inject:
```
...
@Value("${foo.email}")
private String email;
    
@Value("${foo.team}")
private String team;
...
```


