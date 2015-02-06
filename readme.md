#PhoneCallCenter

Sample scenario using FutureTask and thread-safe.

* PhoneCallCenter: Main app, generates and starts execution a random list of PhoneCalls and check with PhoneCallManager if those calls took place.

* PhoneCallManager: In charge of PhoneCallRecords. Record the fact that a phone call happened 
and return whether a phone call has happened.

* PhoneCall: FutureTask to represent a call between 2 numbers with X ms of duration. After call ended reports to PhoneCallManager.

* PhoneCallRecord: A record of numbers that were in a call.

## Running on Maven

* mvn clean install
* java -jar target/PhoneCallCenter-0.0.1-SNAPSHOT.jar
