import java.util.Random;
import java.util.Scanner;

public class Praktikum {
    public static void main(String[] args) {
        CarProperties userCarProperties =
                new CarProperties(
                        "100",
                        0.7f,
                        2
                );

        Car userCar = createCarByProperties(userCarProperties);
        System.out.println("Давно тебя не было в Яндекс.Гонках!");
        System.out.println("Характеристики твоего автомобиля:");
        System.out.println("- Максимальная скорость: " + userCar.maxSpeed);
        System.out.println("- Ускорение: " + userCar.acceleration);
        System.out.println("- Закись азота: " + userCar.nitroLevel);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Что выберете?");
            System.out.println("1 - Устроить заезд");
            System.out.println("2 - Показать статистику");
            System.out.println("3 - Закончить игру");
            int command = scanner.nextInt();

            if (command == 1) {
                Car opponentCar = generateOpponentCar();
                System.out.println("Характеристики автомобиля соперника:");
                System.out.println("- Максимальная скорость: " + opponentCar.maxSpeed);
                System.out.println("- Ускорение: " + opponentCar.acceleration);
                System.out.println("- Закись азота: " + opponentCar.nitroLevel);

                int distance = generateInt(5, 70);
                System.out.println("Гонка будет проходить на дистанции: " + distance + " км.");

                int points = makeRace(userCar, opponentCar, distance);
                changePointAndDistance(userCar, points, distance);

            } else if (command == 2) {
                System.out.println("- Количество заработанных очков: " + userCar.score);
                System.out.println("- Пройдено километров на этом авто: " + userCar.kilometersTravelled);
            } else if (command == 3) {
                System.out.println("Увидимся!");
                break;
            }
        }
    }

    private static void changePointAndDistance(Car userCar, int points, int distance) {
        userCar.score += points;
        userCar.kilometersTravelled += distance;
    }

    private static int makeRace(Car userCar, Car opponentCar, int distance) {
        printFlag();
        boolean shortRaceWin = distance <= 15 && userCar.acceleration > opponentCar.acceleration;
        boolean longRaceWin = distance > 50 && userCar.maxSpeed > opponentCar.maxSpeed;

        if (shortRaceWin || longRaceWin) {
            System.out.println("Вы выиграли!");
            return (int) Double.max(userCar.maxSpeed, opponentCar.maxSpeed);
        } else if (userCar.acceleration == opponentCar.acceleration) {
            System.out.println("Ничья!");
            return 0;
        } else {
            if (userCar.nitroLevel > opponentCar.nitroLevel) {
                System.out.println("Вы проиграли, но благодаря закиси азота сохранили очки.");
                return 0;
            } else {
                System.out.println("Вы проиграли(");
                return -100;
            }
        }
    }
    private static void printFlag() {
        System.out.println("_\n" +
                "\\'-,,.\n" +
                " \\    \\\n" +
                "  \\-..,\\\n" +
                "   \\\n" +
                "    \\\n");
    }

    private static Car generateOpponentCar() {
        double maxSpeed = generateInt(75, 125);
        float acceleration = generateInt(4, 10) / 10.0f;
        int score = 0;
        Integer nitroLevel = generateInt(1, 5);

        return new Car(
                maxSpeed,
                acceleration,
                score,
                nitroLevel
        );
    }

    private static int generateInt(int from, int to) {
        int diapason = to - from;
        int offence = new Random().nextInt(diapason);
        return from + offence;
    }

    private static Car createCarByProperties(CarProperties carProperties) {
        double maxSpeed = Double.parseDouble(carProperties.maxSpeed);
        float acceleration = (float) carProperties.acceleration;
        int score = carProperties.initialScore;
        Integer nitroLevel = getNitroLevel(carProperties.nitroLevel);

        return new Car(
                maxSpeed,
                acceleration,
                score,
                nitroLevel
        );
    }

    private static Integer getNitroLevel(int nitroLevel) {
        if (nitroLevel == 0) {
            return null;
        }
        return nitroLevel;
    }
}