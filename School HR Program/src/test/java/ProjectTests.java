
import com.prog2.main.Staff;
import com.prog2.main.Teacher;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Seanzilla
 */
public class ProjectTests {

    Staff staff = new Staff(00, "test", "1999-02-02", "52 st.",  'M');
    Teacher teacher = new Teacher(00, "test", "1999-02-02", "52 st.", 'M');

    @Test
    void payRollTest1() {
        staff.setWorkload(32);
        double salary = 1536.0;

        assertEquals(staff.computePayRoll(), salary);
    }

    @Test
    void payRollTest2() {
        teacher.setWorkload(32);
        teacher.setDegree("bachelor");
        double salary = 2284.8;

        assertEquals(teacher.computePayRoll(), salary);
    }

    @Test
    void payRollTest3() {
        teacher.setWorkload(20);
        teacher.setDegree("phd");
        double salary = 3404.8;

        assertEquals(teacher.computePayRoll(), salary);
    }

    @Test
    void payRollTest4() {
        teacher.setWorkload(32);
        teacher.setDegree("master");
        double salary = 4460.8;

        assertEquals(teacher.computePayRoll(), salary);
    }

}
