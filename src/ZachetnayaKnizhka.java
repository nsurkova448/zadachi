import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ZachetnayaKnizhka {
    private String studentName;
    private String studentId;
    private List<Session> sessions;

    public ZachetnayaKnizhka(String studentName, String studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public double calculateAverageGrade() {
        int totalGrades = 0;
        int count = 0;
        for (Session session : sessions) {
            for (Subject subject : session.subjects) {
                if (subject instanceof Exam) {
                    totalGrades += ((Exam) subject).grade;
                    count++;
                }
            }
        }
        return count > 0 ? (double) totalGrades / count : 0;
    }

    public class Session {
        private List<Subject> subjects;

        public Session() {
            this.subjects = new ArrayList<>();
        }

        public void addSubject(Subject subject) {
            subjects.add(subject);
        }
    }

    public abstract class Subject {
        protected String subjectName;
        protected LocalDate examDate;

        public Subject(String subjectName, LocalDate examDate) {
            this.subjectName = subjectName;
            this.examDate = examDate;
        }
    }

    public class Exam extends Subject {
        private int grade;

        public Exam(String subjectName, LocalDate examDate, int grade) {
            super(subjectName, examDate);
            this.grade = grade;
        }
    }

    public class Test extends Subject {
        private boolean isPassed;

        public Test(String subjectName, LocalDate examDate, boolean isPassed) {
            super(subjectName, examDate);
            this.isPassed = isPassed;
        }
    }

    // Метод для вывода информации о зачетной книжке
    public void printInfo() {
        System.out.println("Студент: " + studentName + " (ID: " + studentId + ")");
        System.out.println("Средний балл: " + String.format("%.2f", calculateAverageGrade()));
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println("Сессия " + (i + 1) + ":");
            for (Subject subject : sessions.get(i).subjects) {
                if (subject instanceof Exam) {
                    Exam exam = (Exam) subject;
                    System.out.println("  Экзамен: " + exam.subjectName + ", Дата: " + exam.examDate + ", Оценка: " + exam.grade);
                } else if (subject instanceof Test) {
                    Test test = (Test) subject;
                    System.out.println("  Зачет: " + test.subjectName + ", Дата: " + test.examDate + ", Сдан: " + (test.isPassed ? "Да" : "Нет"));
                }
            }
        }
    }

    public static void main(String[] args) {
        // Создаем зачетную книжку
        ZachetnayaKnizhka zachetka = new ZachetnayaKnizhka("Иванов Иван", "CS2023-01");

        // Создаем первую сессию
        ZachetnayaKnizhka.Session session1 = zachetka.new Session();
        session1.addSubject(zachetka.new Exam("Математика", LocalDate.of(2023, 1, 15), 4));
        session1.addSubject(zachetka.new Test("Информатика", LocalDate.of(2023, 1, 20), true));
        session1.addSubject(zachetka.new Exam("Физика", LocalDate.of(2023, 1, 25), 5));

        // Добавляем первую сессию в зачетку
        zachetka.addSession(session1);

        // Создаем вторую сессию
        ZachetnayaKnizhka.Session session2 = zachetka.new Session();
        session2.addSubject(zachetka.new Exam("Программирование", LocalDate.of(2023, 6, 10), 5));
        session2.addSubject(zachetka.new Test("Иностранный язык", LocalDate.of(2023, 6, 15), true));
        session2.addSubject(zachetka.new Exam("База данных", LocalDate.of(2023, 6, 20), 4));

        // Добавляем вторую сессию в зачетку
        zachetka.addSession(session2);

        // Выводим информацию о зачетной книжке
        zachetka.printInfo();
    }
}