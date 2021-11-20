package mz.unilurio.solidermed;

import org.junit.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mz.unilurio.solidermed.model.Measure;
import mz.unilurio.solidermed.model.Parturient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(6,4+2);
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    @Test
    public void testPredictedHour(){
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 4);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        Date expected = calendar.getTime();

        System.out.println(format(expected));
        System.out.println(format(measure.getPredictedExpulsionHour()));

        //assertEquals(format(expected), format(measure.getPredictedExpulsionHour()));
    }



    @Test
    public void testNotify(){

        Parturient bibo = new Parturient("Bibo", "Bubu", 20);
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 4);
        DeliveryService ds = new DeliveryService(bibo,measure);

        Queue queue = new Queue();
        queue.register(ds);

        System.out.println(format(measure.getInitialHour()));
        System.out.println(format(measure.getPredictedExpulsionHour()));

//        while (true) {
//            boolean flag = queue.nofify();
//            if (flag) {
//                break;
//            }
//        }
    }

    @Test
    public void fireAlertFalse(){

        Parturient bibo = new Parturient("Bibo", "Bubu", 20);
        Date current = Calendar.getInstance().getTime();
        Measure measure = new Measure(current, 4);
        DeliveryService ds = new DeliveryService(bibo,measure);
        assertFalse(ds.fireAlert());

    }

    @Test
    public void fireAlertTrue(){
        Parturient bibo = new Parturient("Bibo", "Bubu", 20);
        Calendar calendar = Calendar.getInstance();
        Date current = calendar.getTime();
        System.out.println(" hora atual : "+current);
        Measure measure = new Measure(current, 4);
        calendar.add(Calendar.MINUTE, 2);
        Date after = calendar.getTime();
        System.out.println(" hora esperado : "+after);
        DeliveryService ds = new DeliveryService(bibo,measure);
        ds.setFireble(new FireMockAlert(after, ds));
        System.out.println("resultados : "+ds.fireAlert());
        assertTrue(ds.fireAlert());
    }
}