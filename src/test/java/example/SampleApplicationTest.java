package example;

import org.apache.camel.CamelContext;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;
/**
 * Created by achalise on 5/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SampleApplicationTest {

    @Autowired
    private CamelContext camelContext;

    @Test
    public void testPrintToConsoleRoute() {
        ProducerTemplate template = camelContext.createProducerTemplate();
        template.sendBody("direct:print", new TestObject("myName", "myDesc"));
        NotifyBuilder notify = new NotifyBuilder(camelContext).whenDone(10).create();
    }

    private class TestObject {
        private String name;
        private String desc;
        public TestObject(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "Name: " + name + " , " + "desc: " + desc;
        }
    }

}
