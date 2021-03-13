public class Gladiator {

    // Гладіатор матиме здоров'я, щит, зброю, ім'я
    private byte health;
    private Shield shield;
    private Weapon weapon;
    private String name;

    // Геттери сеттери (поля у файлі ми заповнюємо через них, НЕ constructor injection
    public byte getHealth() {
        return health;
    }

    public Shield getShield() {
        return shield;
    }

    public String getName() {
        return name;
    }

    //сеттери (поля у файлі ми заповнюємо через них, НЕ constructor injection
    public void setHealth(byte health) {
        this.health = health;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Метод, щоб наносити шкоду супротивнику
    public void strike(Gladiator gladiator){
        double attack = ((Math.random()+1.0)*weapon.getDamage())/2.0;
        double block = ((Math.random()+1.0)*gladiator.getShield().getBlock())/2.0;
        double damage = block - attack;

        if (damage < 0) {
            System.out.println("Gladiator " + this.getName() +
                    " strikes " + gladiator.getName() + ".");
            gladiator.setHealth((byte) (gladiator.getHealth() + (byte) damage));
            System.out.println("He dealt " + Math.abs((byte)damage) + " damage.");
        }
    }
}
