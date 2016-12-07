package example.twitter.router;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.rx.ReactiveCamel;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by achalise on 5/12/2016.
 */

@Component
public class TwitterFeedRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        ReactiveCamel reactiveCamel = new ReactiveCamel(getContext());

        from("direct:print").to("stream:out");

        from("twitter://streaming/sample?type=EVENT&consumerKey={{twitter4j.oauth.consumerKey}}&consumerSecret={{twitter4j.oauth.consumerSecret}}&accessToken={{twitter4j.oauth.accessToken}}&accessTokenSecret={{twitter4j.oauth.accessTokenSecret}}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        //System.out.println("Processed message!");
                        exchange.setProperty("test", Arrays.asList(1,2,3,4,5));
                    }
                })
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        //System.out.println("Read the property set by previous processor");
                        //System.out.println(exchange.getProperty("test", ArrayList.class));
                    }
                })
                .to("direct:twitterStream");


        Observable<String> observable = reactiveCamel.toObservable("direct:twitterStream")
                .map(m -> {return m.getBody().toString();})
                .doOnEach(m -> {System.out.println("Processed message - will be ignored if it doesn't contain 'happy'");})
                .filter(e->e.contains("happy"));

        reactiveCamel.sendTo(observable, "direct:print");
    }
}
