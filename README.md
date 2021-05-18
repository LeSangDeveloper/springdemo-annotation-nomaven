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
