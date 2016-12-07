# Twitter Aggregator With Camel and RxJava

A basic SpringBoot application that uses Camel to read twitter feed, only selects tweets containing a a given text
and routes them to be printed in console. It uses Reactive Camel to covert route into an
Observable stream so that messages can be operated on in reactive way using reactive operators
to for example map and filter.

## Twitter OAuth Set up

Following properties need to be provided in application.properties
file. These properties are used to connect to twitter feed using twitter4j library. See [Generating twitter access token](https://dev.twitter.com/oauth/overview/application-owner-access-tokens)
for details.

```
twitter4j.oauth.consumerKey=
twitter4j.oauth.consumerSecret=
twitter4j.oauth.accessToken=
twitter4j.oauth.accessTokenSecret=
```

### Running the application

It is a spring boot application and can be run by executing
```
$gradle bootRun
```
from within the application root directory.