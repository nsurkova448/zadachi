class Claws {
    private int sharpness;

    public Claws(int sharpness) {
        this.sharpness = sharpness;
    }

    public int getSharpness() {
        return sharpness;
    }
}

class Teeth {
    private int strength;

    public Teeth(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}

class Predator {
    private String name;
    private int age;
    private double weight;
    private Claws claws;
    private Teeth teeth;
    private boolean isHungry;
    private boolean isSleeping;
    private double speed;

    public Predator(String name, int age, double weight, Claws claws, Teeth teeth) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.claws = claws;
        this.teeth = teeth;
        this.isHungry = true;
        this.isSleeping = false;
        this.speed = 0;
    }

    public void roar() {
        System.out.println(name + " рычит!");
    }

    public void run() {
        if (!isSleeping) {
            speed = 20 + Math.random() * 10;
            System.out.println(name + " бежит со скоростью " + speed + " км/ч");
        } else {
            System.out.println(name + " спит и не может бежать");
        }
    }

    public void sleep() {
        isSleeping = true;
        System.out.println(name + " засыпает");
    }

    public void hunt() {
        if (!isSleeping) {
            if (isHungry) {
                System.out.println(name + " охотится за добычей");
                isHungry = false;
            } else {
                System.out.println(name + " не голоден и не хочет охотиться");
            }
        } else {
            System.out.println(name + " спит и не может охотиться");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Claws sharpClaws = new Claws(8);
        Teeth strongTeeth = new Teeth(9);
        Predator tiger = new Predator("Тигр", 5, 200, sharpClaws, strongTeeth);

        tiger.roar();
        tiger.run();
        tiger.hunt();
        tiger.sleep();
        tiger.run();
    }
}
