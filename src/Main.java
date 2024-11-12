import java.util.ArrayList;
import java.util.List;

class RationalNumber {
    private int numerator;
    private int denominator;

    public RationalNumber(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
        if (this.denominator < 0) {
            this.numerator = -this.numerator;
            this.denominator = -this.denominator;
        }
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public RationalNumber add(RationalNumber other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new RationalNumber(newNumerator, newDenominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}

class Polynomial {
    private List<RationalNumber> coefficients;

    public Polynomial(List<RationalNumber> coefficients) {
        this.coefficients = new ArrayList<>(coefficients);
    }

    public Polynomial add(Polynomial other) {
        List<RationalNumber> resultCoefficients = new ArrayList<>();
        int maxDegree = Math.max(this.coefficients.size(), other.coefficients.size());

        for (int i = 0; i < maxDegree; i++) {
            RationalNumber thisCoeff = i < this.coefficients.size() ? this.coefficients.get(i) : new RationalNumber(0, 1);
            RationalNumber otherCoeff = i < other.coefficients.size() ? other.coefficients.get(i) : new RationalNumber(0, 1);
            resultCoefficients.add(thisCoeff.add(otherCoeff));
        }

        return new Polynomial(resultCoefficients);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            if (i != coefficients.size() - 1) {
                sb.append(" + ");
            }
            sb.append(coefficients.get(i)).append("x^").append(i);
        }
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        List<Polynomial> polynomials = new ArrayList<>();

        // Создаем несколько полиномов
        polynomials.add(new Polynomial(List.of(new RationalNumber(1, 2), new RationalNumber(3, 4))));
        polynomials.add(new Polynomial(List.of(new RationalNumber(1, 3), new RationalNumber(1, 5), new RationalNumber(2, 7))));
        polynomials.add(new Polynomial(List.of(new RationalNumber(2, 3), new RationalNumber(5, 6))));

        // Вычисляем сумму полиномов
        Polynomial sum = polynomials.get(0);
        for (int i = 1; i < polynomials.size(); i++) {
            sum = sum.add(polynomials.get(i));
        }

        // Выводим результат
        System.out.println("Сумма полиномов: " + sum);
    }
}
