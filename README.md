# w4engineping
W4 BPMN+ Engine Ping Application

to install W4 Engine Ping : 
* Clone the repository or download zip
* (If necessary) change build.gradle 
<pre>
  w4home = installation folder of W4 BPMN+ Engine (default value is 'c:/w4/bpmnplus')
  w4Server = the host name or ip address of W4 BPMN+ Engine (default value is 'localhost')
  w4Port = the tcp port of W4 BPMN+ Engine (default value is '7707')
  w4Login = a user login (default value is 'admin')
  w4Password = the related user password  (default value is 'admin')
</pre>
* Type following command line : 
<pre>
  gradlew war
</pre>
* Deploy the pingDef.bpmn file on your W4 BPMN+ Engine (using web administration console available at http://yourserver/w4BpmnplusAdmin or W4 BPMN+ Composer)
* Go to build\libs and get w4engineping.war (change configuration in ping.jsp if necessary)
