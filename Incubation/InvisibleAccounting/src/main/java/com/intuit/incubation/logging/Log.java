package com.intuit.incubation.logging;

/**
 * Indicates Logger of appropriate type to
 * be supplied at runtime to the annotated field.
 *
 * The injected logger is an appropriate implementation
 * of org.slf4j.Logger.
 * @author Amaresh Mourya
 * @date May 19, 2014
 **/



import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * A marker for {@link LoggerInjector}  to know that Logger injection is to be performed.
 * @author Amaresh Mourya
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface Log
{
}