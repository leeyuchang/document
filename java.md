package start;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import start.model.Customer;
import start.model.Size;
import start.model.Student;

public class StreamTest {

	public static void main(String[] args) {

		List<Student> students = new ArrayList<>();
		students.add(Student.builder().name("Ken").score(100).build());
		students.add(Student.builder().name("Shin").score(60).build());
		students.add(Student.builder().name("Takuya").score(80).build());
		students.add(Student.builder().name("Lee").score(90).build());

//		students.stream().filter(s -> s.getScore() >= 70).forEach(s -> System.out.println(s.getName()));

		LocalDate birthday = LocalDate.of(1980, 4, 16);

		System.out.println(birthday.toString());
		System.out.println(printBirthday(birthday));
		System.out.println(getAge(birthday));

		Customer customer = new Customer();

		List<Customer> customerList = customer.listCustomers();

		List<Customer> customer30List = customerList.stream().filter(c -> c.getAge() > 29).filter(c -> c.getAge() < 40)
				.collect(Collectors.toUnmodifiableList());

		customer30List.forEach(c -> System.out.println(c.getName() + ", " + c.getAge()));

		if (customer30List.stream().allMatch(c -> c.getAge() > 100)) {
			System.out.println("100際以下");
		}

		for (Size s : Size.values()) {
			long sizeCount = customerList.stream().filter(c -> c.getSize() == s).count();
			System.out.println(s.toString() + ": " + sizeCount);
		}

//		購入動向をの分析のために、商品のサイズにそれぞれポイントを付けたとします。
		Map<Size, Integer> point = new HashMap<>();
		point.put(Size.SMALL, 10);
		point.put(Size.MIDDLE, 20);
		point.put(Size.LARGE, 30);
		point.put(Size.XLARGE, 40);
		
		int allPoints = customerList.stream().mapToInt(c->point.get(c.getSize())).sum();
		System.out.println(allPoints);
	}

	public static String printBirthday(LocalDate localdate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.JAPAN);
		return localdate.format(formatter);
	}

	public static int getAge(LocalDate birthday) {
		return Period.between(birthday, LocalDate.now()).getYears();
	}
}
