package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User user1 = new User("Ivan", "Ivanov", "ivanovi@mail.ru");
        User user2 = new User("Petr", "Petrov", "petrovp@mail.ru");
        User user3 = new User("Lena", "Lenina", "lenal@mail.ru");
        User user4 = new User("Katya", "Katina", "katyak@mail.ru");

        Car car1 = new Car("Aurus", 101);
        Car car2 = new Car("Lada", 790);
        Car car3 = new Car("Moskvich", 306);
        Car car4 = new Car("UAZ", 149);

        userService.add(user1.setCar(car1).setUser(user1));
        userService.add(user2.setCar(car2).setUser(user2));
        userService.add(user3.setCar(car3).setUser(user3));
        userService.add(user4.setCar(car4).setUser(user4));

        // Пользователи с машинами
        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        // Выбрать пользователя, владеющего машиной по модели и серии
        System.out.println(userService.getUserByCar("Aurus", 101));

        // Нет такого пользователя с такой машиной
        try {
            User notFoundUser = userService.getUserByCar("Kamaz", 652);
        } catch (NoResultException e) {
            System.out.println("Нет такого пользователя");
        }

        context.close();
    }
}
