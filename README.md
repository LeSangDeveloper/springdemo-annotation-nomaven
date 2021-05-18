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