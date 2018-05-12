package com.gp.vaadin.vaadin_spring_jpa_demo.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

	public static long getTime(LocalDate localDate) {
		return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static LocalDate getLocalDate(long time) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
				ZoneId.systemDefault()).toLocalDate();
	}
}
