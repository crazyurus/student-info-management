package javacourse;

/**
 * 学生类
 *
 * @author Crazy Urus
 * @version 1.0
 */
public final class Student implements java.io.Serializable {

    private String sno, name, phone, email, college;
    private int age;
    private boolean isFemale;

    public Student(String sno, String name, int age, String phone, String email, String college, boolean isFemale) {
        set(sno, name, age, phone, email, college, isFemale);
    }

    public void set(String sno, String name, int age, String phone, String email, String college, boolean isFemale) {
        this.sno = sno;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.college = college;
        this.isFemale = isFemale;
    }

    public String getSNO() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCollege() {
        return college;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public String[] toStringArray(int index) {
        String[] sex = {"男", "女"};
        String[] result = {String.valueOf(index), this.sno, this.name, sex[this.isFemale ? 1 : 0], String.valueOf(this.age), this.phone, this.email, this.college};
        return result;
    }

}
