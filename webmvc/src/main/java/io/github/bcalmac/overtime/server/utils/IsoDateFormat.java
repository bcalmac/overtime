package io.github.bcalmac.overtime.server.utils;

import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
public @interface IsoDateFormat {
}
