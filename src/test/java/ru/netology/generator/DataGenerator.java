package ru.netology.generator;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
	private DataGenerator() {

	}

	public static String randomDate() {
		Faker faker = new Faker(new Locale("ru"));
		long randomCount = faker.number().numberBetween(1, 9);
		String randomDate = LocalDate.now().plusDays(3).plusDays(randomCount).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		return randomDate;
	}

	public static String makeDate() {
		LocalDate today = LocalDate.now();
		LocalDate newDate = today.plusDays(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return formatter.format(newDate);
	}

	public static String makeCity() {
		String[] myCityList = new String[]{"Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Владикавказ", "Горно-Алтайск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Краснодар", "Магас", "Махачкала", "Нарьян-Мар", "Салехард", "Самара", "Саранск", "Саратов", "Хабаровск", "Ханты-Мансийск", "Южно-Сахалинск", "Великий Новгород", "Владивосток", "Владимир", "Вологда", "Рязань", "Биробиджан", "Чебоксары", "Москва", "Санкт-Петербург", "Ульяновск", "Симферополь", "Ростов-на-Дону"};
		int city = (int) Math.floor(Math.random() * myCityList.length);
		return myCityList[city];
	}


	public static String makeName() {
		Faker faker = new Faker(new Locale("ru"));
		return faker.name().lastName() + " " + faker.name().firstName();

	}

	public static String makeInvalidName() {
		Faker faker = new Faker(new Locale("en"));;
		return faker.name().lastName() + " " + faker.name().firstName();

	}

	public static String makePhone() {
		Faker faker = new Faker(new Locale("ru"));
		return faker.phoneNumber().phoneNumber();
	}

	public static String makeInvalidAboveMinPhoneNumber() {
		Faker faker = new Faker(new Locale("ru"));
		return faker.number().digits(6);
	}
}
