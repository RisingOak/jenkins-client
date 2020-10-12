# Release Notes

## Release 0.4.0 (NOT RELEASED YET)

 * [Fixed Issue 465][issue-465]
   
   * Remove asciidoctor site parts
     * AS preparation for user guide.

 * [Fixed Issue 464][issue-464]
   
   * Replaced xml-apis with xerces-xmlParserAPI.
   
 * [Fixed Issue 309][issue-309]
 
   * Added possibility to get mode detailed data from Maven Modules from Jobs/Builds
	
     Thanks for that to [Jakub Zacek](https://github.com/dawon).
	
 * [Fixed Issue 395][issue-395]
   
   * Remove google guava lib
   * Removed also the creation of the shaded artifact `stash`
     cause we do not rely on Guava anymore. So you 
     can use the original artifact directly.
   * This results in a bumping of the version
     number cause it is a change which is breaking 
     with previous version 0.3.8.

 * [Fixed Issue 405][issue-405]
   
	* CVE-2018-14718
	* CVE-2018-14719
	* CVE-2018-14720
	* CVE-2018-14721
	* CVE-2018-19360
	* CVE-2018-19361
	* CVE-2018-19362

 * [Fixed Issue 402][issue-402]
   
   Upgrade httpclient/httpmine/httpcore.

 * [Fixed Issue 401][issue-401]
   
   Upgrade JUnit

 * [Fixed Issue 400][issue-400]
   
   Upgrade assertj-core.

 * [Fixed Issue 399][issue-399]
   
   Upgrade Maven Plugins

 * [Fixed Issue 397][issue-397]
   
   Refactored Code Replaced UrlEscapers calls with EncodingUtils.

 * [Fixed Issue 396][issue-396]
   
   Add Unit Test for EncodingUtils.

 * [Fixed Issue 394][issue-394]
   
   Replace `Strings.isNullOrEmpty()` with self implemented code. 

 * [Pull Request #386][pull-386] 

   Add the crumbFlag as the 2nd parameter of getConsoleOutputText method 

 * [JENKINS-56186][jissue-56186]

   Added labels to computers

   ```java
    ComputerWithDetails computer = ...
    for (ComputerLabel assignedLabel : computer.getAssignedLabels()) {
      assignedLabel.getName()
    }
   ```
   
 * [JENKINS-56585][jissue-56585]
 
   Change request method of `QuietDown()` to POST


## Release 0.3.8

 * [Fixed Issue 289][issue-289]
   
   Added a build.stop() method which takes in a crumbFlag


 * [Fixed Issue 301][issue-301]
   
   Decoupled JenkinsServer and JenkinsHttpClient by extracting JenkinsHttpClient 
   methods into public interface so that different implementations can be plugged
   into JenkinsServer if required


 * [Fixed Issue 298][issue-298]
   
   Added Closeable support to JenkinsServer and JenkinsHttpClient so that
   underlying resources can be cleaned up when instances no longer required


 * [JENKINS-46472][jissue-46472]

   Added ability to modify offline cause for offline computers.

   ```java
    ComputerWithDetails computer = ...
    if (!computer.getOffline()){
      computer.toggleOffline();
      computer.changeOfflineCause("Scheduled for termination");
    }
   ```

 * [JENKINS-46445][jissue-46445]

   Add support for both client TLS and basic authentication.

   ```java
    HttpClientBuilder builder = HttpClientBuilder.create();
    builder.setSslcontext(sslContext);
    JenkinsHttpClient client = new JenkinsHttpClient(uri, builder, username, password);
    JenkinsServer jenkins = new JenkinsServer(client);
   ```

* [Refactor Issue 291][issue-291]
   
   Useful utility methods refactored into utility classes.

 * [Fixed Issue 282][issue-282]
   
   `NullPointerException` may be thrown if `upstreamUrl` is `null` when
   converting cause to `BuildCause` object.

* [Fixed Issue 45284][jissue-45284]

   JenkinsTriggerHelper can wait for too long if job allows concurrent builds.

 * [Fixed Issue 268][issue-268]
  
   NullPointerException is thrown unless isRunning() is called first.
 
 * [Fixed Issue 98][issue-98]
   
   Splitting fix made for jacoco reports from Jenkins #98.
   Thanks to Shah, Prince.
 
 * [Fixed Issue 217][issue-217]
   
   Added new api for streaming build logs

   ```java
    BuildWithDetails build = ...
    // Get log with initial offset
    int offset = 40;
    String output = build.getConsoleOutputText(offset);
    // Stream logs (when build is in progress)
    BuildConsoleStreamListener buildListener = ...
    build.streamConsoleOutput(listener, 3, 420);
   ```

   Thanks to Wojciech Trocki.

 * [Fixed Issue 222][issue-222]
   
   Fixed WARNING during build.

 * [Fixed Issue 220][issue-220]
   
   `getViews()` Do not use `api/json?depth=1` cause of timeout.

 * [Fixed Issue 244][issue-244]
   
   README.md Code grammar typos; Thanks to <dwlee@woowahan.com>.

 * [Pull Request #229][pull-229] Fix race condition in JenkinsTriggerHelper

   Thanks for that to [Veske](https://github.com/Veske).

 * [Pull Request #239][pull-239] Fixed Bug in build method to prevent
   building twice.

   Thanks for the pull request #239 from ladventure/master

 * [Pull Request #240][pull-240] Fixed code duplication.

   Thanks for the pull request #240 from Jonathan Bremer.

 * [Pull Request #247][pull-247] Add JavaDoc.

   Thanks to aisuke-yoshimoto

 * [Pull Request #262][pull-262] Fix typo.

   Thanks for Alberto Scotto.

### API Changes

 * [Fixed Issue 243](https://github.com/jenkinsci/java-client-api/issues/243) 
 
    Added new methods to JenkinsServer for stopping and restarting Jenkins. The methods are restart(Boolean crumbFlag), safeRestart(Boolean crumbFlag), exit(Boolean crumbFlag) and safeExit(Boolean crumbFlag)
	
	Thanks for that to [Chids](https://github.com/Chids-gs).

## Release 0.3.7

 * Changed Eclipse Formatting configuration.

[Fixed Issue 186][issue-186]

 * Correctly escaping `{` and `}` for range syntax.

[Fixed Issue 166][issue-166]

 * Added a supplemental package with classifier `apachehttp` which 
   includes the packages `org.apache.httpcomponents:httpclient` and
   `org.apache.httpcomponents:httpcore`.

### API Changes

  * [Fixed Issue 215][issue-215]
  
    The JenkinsServer class will return `JenkinsVersion` instead of String if you
    call `getJenkinsVersion()`.
    
```java
public class JenkinsVersion ... {
    public boolean isGreaterThan(String version);
    public boolean isGreaterOrEqual(String version);
    public boolean isLessThan(String version);
    public boolean isLessOrEqual(String version);
    public boolean isEqualTo(String version);
}
```

   The `JenkinsVersion` class can be used to compare different versions with
   each other.
   
```java
JenkinsVersion jv = new JenkinsVersion("1.651.1");
assertThat(jv.isGreaterThan("1.651.1")).isFalse();
``` 
    
    
  * [Fixed issue 184][issue-184]

    Based on the differences between getting a TestReport for a MavenJob type and 
    a freestyle job etc. you would have hit by getting 0 from `getTotalCount()` like
    in the following code snippet:

```java
JobWithDetails job = js.getJob("non-maven-test");
Build lastCompletedBuild = job.getLastCompletedBuild();
TestReport testReport = lastCompletedBuild.getTestReport();
```

   This is caused by the difference in the API of Jenkins which results in the following
   for a MavenJob type:

	{
	  "_class" : "hudson.maven.reporters.SurefireAggregatedReport",
	  "failCount" : 0,
	  "skipCount" : 0,
	  "totalCount" : 489,
	  "urlName" : "testReport",
	  "childReports" : [
	    {
	      "child" : {
	        "_class" : "hudson.maven.MavenBuild",
	        "number" : 2,
	        "url" : "http://localhost:27100/buildserver/job/maven-test/com.soebes.subversion.sapm$sapm/2/"
	      },
	      "result" : {
	        "_class" : "hudson.tasks.junit.TestResult",
	        "testActions" : [
	          
	        ],
	        "duration" : 0.009,
	        "empty" : false,
	        "failCount" : 0,
	        "passCount" : 489,
	        "skipCount" : 0,
	        "suites" : [
	          {
	            "cases" : [

   But for a non Maven job like freestyle job you will get the following:
    
	{
	  "_class" : "hudson.tasks.junit.TestResult",
	  "testActions" : [
	    
	  ],
	  "duration" : 0.01,
	  "empty" : false,
	  "failCount" : 0,
	  "passCount" : 489,
	  "skipCount" : 0,
	  "suites" : [
	    {
	      "cases" : [
	        {
	          "testActions" : [
	            
	          ],
	          "age" : 0,
	          "className" : "com.soebes.subversion.sapm.AccessRuleGroupTest",
	          "duration" : 0.003,

   This is exactly the cause for this result.
    
   The API has been enhanced to get the correct result. This can be achieved by calling
   the following in cases where you have a non Maven job type.
    
```java
TestResult testResult = lastCompletedBuild.getTestResult();
```

   This means you need to take care yourself if you are getting the test results from
   Maven job type or non Maven job. (Future releases of the lib hopefully handle that
   in a more convenient way).       
     
  

  * [Fixed issue 209][issue-209]
  
    Consider Returning null from the "getTestReport()" of Build.java class for builds that Never run.
    
    The type `BUILD_HAS_NEVER_RUN` has been enhanced to return `TestReport.NO_TEST_REPORT` if you
    call `getTestReport()` and return `BuildWithDetails.BUILD_HAS_NEVER_RUN` if you call `details()` on the 
    type.
    
    Furthermore `JobWithDetails` class has been enhanced with the following methods:
    
```java
public class JobWithDetails ... {
    public boolean hasFirstBuildRun();
    public boolean hasLastBuildRun();
    public boolean hasLastCompletedBuildRun();
    public boolean hasLastFailedBuildRun();
    public boolean hasLastStableBuildRun();
    public boolean hasLastSuccessfulBuildRun();
    public boolean hasLastUnstableBuildRun();
    public boolean hasLastUnsuccessfulBuildRun();
}
```
   to make checking more convenient. The following code snippet
   shows how you need to go before this change:
   
```java
JobWithDetails job = server.getJob(jobName);
if (Build.BUILD_HAS_NEVER_RUN.equals(job.getLastBuild()) {
  ...
} else {
  // Now we can get the TestReport
  job.getLastBuild().getTestReport();
}
```

   This can now be simplified to the following:
   
```java
JobWithDetails job = server.getJob(jobName);
if (job.hasLastBuildRun()) {
  // Now we can get the TestReport
  job.getLastBuild().getTestReport();
} else {
}
```

  * [Fixed issue 211][issue-211]
  
    Added methods to update/clear the description of a job.

```java
public class JobWithDetails .. {
    public void updateDescription(String description);
    public void updateDescription(String description, boolean crumbFlag);
    public void clearDescription();
    public void clearDescription(boolean crumbFlag);
```

  * [Fixed issue 188][issue-188]
  
    Added methods to update the description and the display name of a build.

```java
public class BuildWithDetails .. {
    public void updateDisplayNameAndDescription(String displayName, String description, boolean crumbFlag);
    public void updateDisplayNameAndDescription(String displayName, String description);
    public void updateDisplayName(String displayName, boolean crumbFlag);
    public void updateDisplayName(String displayName);
    public void updateDescription(String description, boolean crumbFlag);
    public void updateDescription(String description);
}
```

  * [Pull Request #206][pull-206] added runScript with `crumbFlag`.
    
    Thanks Rainer W. 
  
```java
public class JenkinsServer {
	public String runScript(String script,boolean crumb);
}
```

 * [Fixed issue 197][issue-197] Provide method for waiting until job has finished.
 
   Added a helper class to support this.
 
```java
public class JenkinsTriggerHelper {
	public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName);
	public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, boolean crumbFlag);
	public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, Map<String, String> params);
	public BuildWithDetails triggerJobAndWaitUntilFinished(String jobName, Map<String, String> params,boolean crumbFlag);
}
```

 * [Fixed Issue 104][issue-104] all build* methods now return consistently `QueueReference` 
   to make it possible to query for a queued build and if a build from the queue has
   been cancelled or to see if a build is running.
 
```java
 public class Job extends BaseModel {
   public QueueReference build();
   public QueueReference build(boolean crumbFlag);
   public QueueReference build(Map<String, String> params);
   public QueueReference build(Map<String, String> params, boolean crumbFlag);
}
```

 * [Fixed Issue 203][issue-203] with [pull request #204][pull-204]
   of RainerW <github@inforw.de>

   Added methods getJobXml and updateJob with folder parameter.

```java
public class JenkinsServer {
  String getJobXml(FolderJob folder, String jobName);
  void updateJob(FolderJob folder, String jobName, String jobXml, boolean crumbFlag);
}
``` 

 * [Fixed Issue 207][issue-207]

   Added 
```Java
public class QueueItem extends BaseModel {

  List<QueueItemActions> getActions();
}
```

  and the new `QueueItemActions` will offer access to the
  actions.

 * [Fixed Issue 38823][jissue-38823]

   Added an deleteJob method with a crumbFlag.

```java
public class JenkinsServer {
  deleteJob(FolderJob folder, String jobName, boolean crumbFlag);
}
``` 

 * [Fixed Issue 38816][jissue-38816]

   Added several methods for creating/updating views.

```java
public class JenkinsServer {
  void createView(String viewName, String viewXml);
  void createView(String viewName, String viewXml, Boolean crumbFlag);
  void createView(FolderJob folder, String viewName, String viewXml);
  void createView(FolderJob folder, String viewName, String viewXml, Boolean crumbFlag);
  void updateView(String viewName, String viewXml);
  void updateView(String viewName, String viewXml, boolean crumbFlag);
}
``` 

 * [Fixed Issue 38787][jissue-38787]

 Returning null instead of IOException if view is not found in JenkinsServer.getView


 * [Fixed Issue 201][issue-201]
 
 `MavenJobsWithDetails` is now in line with `JobWithDetails` and returns
 `MavenBuild.BUILD_HAS_NEVER_RUN` in cases where the run has not taken
 place yet. 

```java
public class MavenJobWithDetails { 
   public MavenBuild getLastBuild();
   public MavenBuild getLastCompletedBuild();
   public MavenBuild getLastFailedBuild();
   public MavenBuild getLastStableBuild();
   public MavenBuild getLastSuccessfulBuild();
   public MavenBuild getLastUnstableBuild();
   public MavenBuild getLastUnsuccessfulBuild();
}
``` 

 The `getBuilds()` method will return an empty list instead of `NULL` in cases no
 builds exists.

 Fixed grammar and changed `Build.BUILD_HAS_NEVER_RAN` into `Build.BUILD_HAS_NEVER_RUN`

 * [Fixed Issue 202][issue-202]

```java
public class MavenJobWithDetails { 
   public MavenBuild getFirstBuild();
}
```

 * [Fixed Issue 198][issue-198]
 
 Enhanced the `QueueItem` to add information about the task (QueueTask).

```java
public class QueueItem {
    public QueueTask getTask();
}
```

 * [Fixed Issue 200][issue-200]

 Added two methods to get all builds and a range of builds
 so we are more in line wiht JobWithDetails.

```java
public class MavenJobWithDetails { 
    public List<MavenBuild> getAllBuilds();
    public List<MavenBuild> getAllBuilds(Range range);
}
```

## Release 0.3.6

### General Changes

[Fixed #182 remove duplicated code][issue-182]

[Upgraded Maven Plugins][issue-167]

  * maven-release-plugin to 2.5.3
  * maven-site-plugin to 3.5.1
  * maven-shade-plugin to 2.4.3
  * maven-jar-plugin to 3.0.2
  * maven-source-plugin 3.0.1
  * maven-surefire/failsafe-plugin to 2.19.1
  * maven-resources-plugin to 3.0.1
  * Add missing maven-javadoc-plugin 2.10.4


[Upgraded Maven Plugins JENKINS-35108][jissue-35108]

  * maven-clean-plugin to 3.0.0
  * maven-resources-plugin to 3.0.0
  * maven-jar-plugin to 3.0.0

[Fixed issue 176 HttpResponseException: Not Found (createJob)][issue-176]
  Based on the wrong usage of the sub methods using crumbFlag=true instead
  of crumbFlag=false.

[Fixed issue 162][issue-162]

  JenkinsJob.details() produced NPE which has been got via view.getJob().

[Fixed issue 172][issue-172]

  The implementation `BuildWithDetails.getCauses()` could cause an 
  NPE which now has been imroved to prevent it. Thanks for hint
  which brought me to reconsider the implementation.

  Serveral issues fixed related to using logging framework 
  [issue-161][issue-161], [issue-113][issue-113] and
  [JENKINS-35002][jissue-35002]

  Added slf4j-api as foundation for logging. Using 
  log4j2-slf4j-impl in jenkins-client-it-docker for logging. 
  As a user you can now decide which logging framework
  you would like to use.

[Changed the structure and integrated Docker IT][issue-160]
  
### API Changes

  The attributes of the following classes have been made
  private. They are only accessible via getters/setters.
  
  `MavenArtifact`, `ComputerWithDetails`, `Executable`, `FolderJob`,
  `JobWithDetails`, `MavenJobWithDetails`, `MavenModule`, `MavenModuleRecord`,
  `PluginManager`  

  [Fixed issue 179][issue-179]

  The `getTestReport` has been moved up from `MavenBuild` into
  `Build` class. This makes the `TestReport` accessible 
  from any kind of build and not only from a Maven build.

  [Fixed issue 174][issue-174]

  `jenkins.getComputerSet().getComputer()` produced an error.
  Changed `getComputer()` into `getComputers()` cause it returns
  a list an not only a single computer.
  Based on the above problem the `Executor` needed to be changed to
  represent the correct data which is being returned.

```java
public class ComputerSet {
   public List<ComputerWithDetails> getComputers();
}
``` 

```java
public class Executor {
  public Job getCurrentExecutable();
  public Job getCurrentWorkUnit();
}
``` 

  [Fixed issue 169 Add crumbFlag to renameJob][issue-169]

  Added supplemental `renameJob` method which supports crumbFlag. Furthermore
  added `renameJob` which supports folder with and without `crumbFlag`.
  So we now have the following methods to rename jobs:

```java
public class JenkinsServer {
  public void renameJob(String oldJobName, String newJobName) throws IOException;
  public void renameJob(String oldJobName, String newJobName, Boolean crumbFlag) throws IOException;
  public void renameJob(FolderJob folder, String oldJobName, String newJobName) throws IOException;
  public void renameJob(FolderJob folder, String oldJobName, String newJobName, Boolean crumbFlag) throws IOException;
}
``` 

  [Fixed issue 168 deletejobs in folder][issue-168]

  Added new method to delete a job within a folder.

```java
public class JenkinsServer {
 public void deleteJob(FolderJob folder, String jobName) throws IOException;
}
```

  [Changing getLocalContext(), setLocalContext()][pull-163]

  The protected method `getLocalContext()` now returns
  `HttpContext` instead of `BasicHttpContext`.
  So the API has changed from the following:

```java
public class JenkinsServer {
  protected BasicHttpContext getLocalContext();
  protected void setLocalContext(BasicHttpContext localContext); 
  .
}
``` 

  into this:

```java
public class JenkinsServer {
  protected HttpContext getLocalContext();
  protected void setLocalContext(HttpContext localContext); 
  .
}
``` 

  Apart from that the visibility of the class `PreemptiveAuth` has been changed 
  from package private to public.

  [Get Jenkins Version from http header][issue-90]

```java
public class JenkinsServer {
  public String getVersion();
  .
}
``` 

  [Added description for Job][issue-165]

```java
public class JobWithDetails {
  public String getDescription();
  .
}
``` 
  

## Release 0.3.5

### API Changes

  [Fixed issue 159][issue-159]

  The following methods have been changed to return `Collections.emtpyList()` if 
  no builds exists or have ran before.

```java
getAllBuilds(Range range);
getAllBuilds(), 
getBuilds()
```

  [Fixed NPE][issue-147]

  The JobWithDetails class contains several methods which could
  have returned `null`. This has been changed to return non null values.

```java
     public List<Job> getDownstreamProjects();
     public List<Job> getUpstreamProjects();
```

  They will return `Collections.emptyList()`.

  The following methods will return `Build.BUILD_HAS_NEVER_RAN` in
  cases where no build has executed for the appropriate methods instead
  of the previous `null` value.

```java
    public Build getFirstBuild();
    public Build getLastBuild();
    public Build getLastCompletedBuild();
    public Build getLastFailedBuild();
    public Build getLastStableBuild();
    public Build getLastSuccessfulBuild();
    public Build getLastUnstableBuild();
    public Build getLastUnsuccessfulBuild();
```


  [Added getAllBuilds(), getAllBuilds(Range range) ][issue-148]

  The `JobWithDetails` has been enhanced with two methods
  to get all builds which exists or a range can be used
  to select.
  
  Note: There seemed to be no option to extract simply the number
  of existing builds of a particular job via the REST API.

```java
public class JobWithDetails {
    public List<Build> getAllBuilds() throws IOException;
    public List<Build> getAllBuilds(Range range) throws IOException;
}
```

 [Added renameJob(..)][pull-158]
 
```java
public class JenkinsServer {
  renameJob(String jobName, String newJobName) throws IOException;
  .
}
``` 


## Release 0.3.4


### API Changes

  [Added toggleOffline Node][issue-157]
  Added toggleOffline to `ComputerWithDetails` class.

```java
public class ComputerWithDetails {
  public void toggleOffline(boolean crumbFlag) throws IOException;
  public void toggleOffline() throws IOException;
}
```

  * [deleteJob throws exception but works anyway][issue-154]
  * [Some HTTP calls to jenkins result in a 302, which currently throws an HttpResponseException][issue-7]
  * [Create Job is failing - any idea on this error][issue-121]
  
  * Fixed. by changing call to client.post(, crumbFlag = true) into
    client.post(, crumbFlag = false).

  [Added getPluginManager() to JenkinsServer][issue-120]

```java
PluginManager getPluginManager() throws IOException;
```

  Added method to get `Plugin`.

```java
public class PluginManager extends BaseModel {
    public List<Plugin> getPlugins()
}
```

  `Plugin` class contains methods to get informations about
  the plugins plus a `PluginDependency` class.

  [Added getFileFromWorkspace() to Job][issue-119]
  Added method `getFileFromWorkspace()` to `Job` class to get a file 
  from workspace in Jenkins.

```java
String getFileFromWorkspace(String fileName)
```


  [Make jobNames case sensitive.][pull-149]

  [TestCase class enhanced][issue-155]
  Now the `TestCase` contains the information
  about the errorDetails and the errorStackTrace of 
  a failed test case.

```java
String getErrorDetails();
String getErrorStackTrace()
```

  [Added disableJob, enabledJob][pull-123]
  The following methods have been added to support enabling
  and disabling jobs.

```java
void disableJob(String jobName);
void disableJob(String jobName, boolean crumbFlag);
void enableJob(String jobName);
void enableJob(String jobName, boolean crumbFlag);
```

  [Fixed #128 Added OfflineCause as a real object in ComputerWithDetails][issue-128]
  Added `OfflineCause` class which can now being used 
  as a return type for the attribute `offlineCause` instead
  of `Object` in `ComputerWithDetails`. 

```java
public class ComputerWithDetails {
  Object offlineCause;
..
}
```

  into the following which also influenced the appropriate return types 
  of the getters:

into 
```java
public class ComputerWithDetails {
    OfflineCause offlineCause;

    public OfflineCause getOfflineCause() throws IOException;

}
```

  [Fixed #135 Created documentation area][issue-135]
  Started a documentation area under src/site/ for either Markdown
  or asciidoctor.

  [Fixed #130 org.jvnet.hudson:xstream][issue-130]
  I needed to remove the toString method from View class
  which uses the XStream classes which does not make sense
  from my point of view.

  [Fixed #144 Problems with spaces in paths and job names][issue-144]

  [Fixed #133 How do we find out which system the built was build on?][issue-133]

  Now `BuildWithDetails` API has been enhanced with the following method get
  the information on which node this build has ran.

```java
String getBuiltOn();
```

   [Fixed #146 duration / estimatedDuration are long][issue-146]

   The API in `BuildWithDetails`has been changed to represent the change
   in datatype.

```java
int getDuration();
int getEstimatedDuration();
```
   into:
```java
long getDuration();
long getEstimatedDuration();
```

## Release 0.3.3

  [Fixed #111 A missing dependency to jaxen caused problems][issue-111]
  The problem was that the dependency has missed from the compile scope
  which caused problems for users to use. The dependency was there via
  transitive dependency of a test scoped artifact.

  [Do not propagate IOException when GET for /CrumbIssuer fails][issue-116]

### API Changes

  [Added Cloudbees Folder support][issue-108]
  The following methods have been added to support `FolderJob` type.

```java
void createFolder(String jobName, Boolean crumbFlag);
Optional<FolderJob> getFolderJob(Job job);
Map<String, Job> getJobs(FolderJob folder);
Map<String, Job> getJobs(FolderJob folder, String view);
Map<String, View> getViews(FolderJob folder);
View getView(FolderJob folder, String name);
JobWithDetails getJob(FolderJob folder, String jobName);
MavenJobWithDetails getMavenJob(FolderJob folder, String jobName);
void createJob(FolderJob folder, String jobName, String jobXml);
void createJob(FolderJob folder, String jobName, String jobXml, Boolean crumbFlag);
```

## Release 0.3.2

### API Changes:

  [JobWithDetails has been enhanced with information about the first build][issue-91].

```java
Build getFirstBuild()
```

  [The `JenkinsServer` API has been enhanced to get information about the Queue][issue-104]

```java
QueueItem getQueueItem(QueueReference ref) throws IOException 
```

```java
Build getBuild(QueueItem q)  throws IOException 
```

 The `JenkinsHttpClient` API has been changed from the following:

```java
JenkinsHttpClient(URI uri, DefaultHttpClient defaultHttpClient);
```
 into

```java
JenkinsHttpClient(URI uri, CloseableHttpClient client);
```

  Furthermore the `JenkinsHttpClient` API has been enhanced with the following
  method which allows to create an unauthenticated Jenkins HTTP client.

```java
JenkinsHttpClient(URI uri, HttpClientBuilder builder);
```

  The `Build` class `Stop` method needed to be changed internally based on an
  inconsistencies in Jenkins versions. (This might be change in future). There
  are versions 1.565 which supports the stop method as a post call, version
  1.609.1 support it as a get call and 1.609.2 as a post call.


  The `Job` class has been enhanced to trigger parameterized builds.

```java
QueueReference build(Map<String, String> params, boolean crumbFlag) throws IOException;
```


## Release 0.3.1

### API Changes:


 Until to Release 0.3.0 the `getLoadStatistics()` method returned a simple `Map` which
 needed to be changed to represent the information which is returned.

 So the old one looked like this:

```java
Map getLoadStatistics();
```

 The new one looks like this:

```java
LoadStatistics getLoadStatistics() throws IOException
```

 You can see how it works by using the [JenkinsLoadStatisticsExample.java][2].

 The API for `getExecutors()` has been changed from a simple [`Map` into something
 more meaningful][issue-67].

 The old one looked like this:

```java
List<Map> getExecutors(); 
```

  where as the new API looks like this:


```java
List<Executor> getExecutors();
```

  This will result in a list of [Executor][4] which contain supplemental informations
  about the appropriate executor.


## Issues

### [Issue #53][issue-53]

  This release contains new functionality like support to get the list of existing views.
  This can be accomplished by using the following:

```java
Map<String, View> views = jenkins.getViews();
```

### [Issue #67][issue-67]

  The change for `getExecutors()` which is documented in API changes.

### [Issue #82][issue-82]

  The information about the `ChangeSet` and the `culprit` has been improved.

```java
JenkinsServer jenkins = new JenkinsServer(new URI("http://localhost:8080/jenkins"), "admin", "password");
MavenJobWithDetails mavenJob = js.getMavenJob("javaee");
BuildWithDetails details = mavenJob.getLastSuccessfulBuild().details();
```

  So now you can extract the causes which is related to the trigger which triggered the build.

```java
List<BuildCause> causes = details.getCauses();
```

  With the help of `getChangeSet()` you can extract the changeset information of your build:

```java
BuildChangeSet changeSet = details.getChangeSet();
```

  By using the `changeSet.getKind()` you would expect to get the kind of version control
  which has been used. If you use Git or Subversion this is true but unfortunately it 
  is not true for all version control systems (for example TFS). 

  The information about files which have been changed an other information can be extracted
  by using the following:

```java
List<BuildChangeSetItem> items = changeSet.getItems();
```

  If you like to get a better overview [check the example file in the project][1].


### [Issue #89][issue-89]

  Extract TestReport from Jenkins

```java
JenkinsServer js = new JenkinsServer(URI.create("http://jenkins-server/"));
MavenJobWithDetails mavenJob = js.getMavenJob("MavenJob");
BuildWithDetails details = mavenJob.getLastSuccessfulBuild().details();
TestReport testReport = mavenJob.getLastSuccessfulBuild().getTestReport(); 
```

  You can extract the information about the tests which have run which contains
  information about the number of tests, failed tests and skipped tests etc.
  [For a more detailed example take a look into the project][3].





[1]: https://github.com/jenkinsci/java-client-api/blob/master/src/test/java/com/offbytwo/jenkins/integration/JenkinsChangeSetExample.java
[2]: https://github.com/jenkinsci/java-client-api/blob/master/src/test/java/com/offbytwo/jenkins/integration/JenkinsLoadStatisticsExample.java
[3]: https://github.com/jenkinsci/java-client-api/blob/master/src/test/java/com/offbytwo/jenkins/integration/BuildJobTestReports.java
[4]: https://github.com/jenkinsci/java-client-api/blob/master/src/main/java/com/offbytwo/jenkins/model/Executor.java
[issue-7]: https://github.com/jenkinsci/java-client-api/issues/7
[issue-53]: https://github.com/jenkinsci/java-client-api/issues/53
[issue-67]: https://github.com/jenkinsci/java-client-api/issues/67
[issue-82]: https://github.com/jenkinsci/java-client-api/issues/82
[issue-89]: https://github.com/jenkinsci/java-client-api/issues/89
[issue-90]: https://github.com/jenkinsci/java-client-api/issues/90
[issue-91]: https://github.com/jenkinsci/java-client-api/issues/91
[issue-98]: https://github.com/jenkinsci/java-client-api/issues/98
[issue-104]: https://github.com/jenkinsci/java-client-api/issues/104
[issue-111]: https://github.com/jenkinsci/java-client-api/issues/111
[issue-116]: https://github.com/jenkinsci/java-client-api/issues/116
[issue-104]: https://github.com/jenkinsci/java-client-api/issues/104
[issue-108]: https://github.com/jenkinsci/java-client-api/issues/108
[issue-113]: https://github.com/jenkinsci/java-client-api/issues/113
[issue-119]: https://github.com/jenkinsci/java-client-api/issues/119
[issue-120]: https://github.com/jenkinsci/java-client-api/issues/120
[issue-121]: https://github.com/jenkinsci/java-client-api/issues/121
[issue-128]: https://github.com/jenkinsci/java-client-api/issues/128
[issue-130]: https://github.com/jenkinsci/java-client-api/issues/130
[issue-133]: https://github.com/jenkinsci/java-client-api/issues/133
[issue-135]: https://github.com/jenkinsci/java-client-api/issues/135
[issue-144]: https://github.com/jenkinsci/java-client-api/issues/144
[issue-146]: https://github.com/jenkinsci/java-client-api/issues/146
[issue-147]: https://github.com/jenkinsci/java-client-api/issues/147
[issue-148]: https://github.com/jenkinsci/java-client-api/issues/148
[issue-154]: https://github.com/jenkinsci/java-client-api/issues/154
[issue-155]: https://github.com/jenkinsci/java-client-api/issues/155
[issue-157]: https://github.com/jenkinsci/java-client-api/issues/157
[issue-159]: https://github.com/jenkinsci/java-client-api/issues/159
[issue-160]: https://github.com/jenkinsci/java-client-api/issues/160
[issue-161]: https://github.com/jenkinsci/java-client-api/issues/161
[issue-162]: https://github.com/jenkinsci/java-client-api/issues/162
[issue-165]: https://github.com/jenkinsci/java-client-api/issues/165
[issue-166]: https://github.com/jenkinsci/java-client-api/issues/166
[issue-167]: https://github.com/jenkinsci/java-client-api/issues/167
[issue-168]: https://github.com/jenkinsci/java-client-api/issues/168
[issue-169]: https://github.com/jenkinsci/java-client-api/issues/169
[issue-172]: https://github.com/jenkinsci/java-client-api/issues/172
[issue-174]: https://github.com/jenkinsci/java-client-api/issues/174
[issue-176]: https://github.com/jenkinsci/java-client-api/issues/176
[issue-179]: https://github.com/jenkinsci/java-client-api/issues/179
[issue-182]: https://github.com/jenkinsci/java-client-api/issues/182
[issue-184]: https://github.com/jenkinsci/java-client-api/issues/184
[issue-186]: https://github.com/jenkinsci/java-client-api/issues/186
[issue-188]: https://github.com/jenkinsci/java-client-api/issues/188
[issue-197]: https://github.com/jenkinsci/java-client-api/issues/197
[issue-198]: https://github.com/jenkinsci/java-client-api/issues/198
[issue-200]: https://github.com/jenkinsci/java-client-api/issues/200
[issue-201]: https://github.com/jenkinsci/java-client-api/issues/201
[issue-202]: https://github.com/jenkinsci/java-client-api/issues/202
[issue-203]: https://github.com/jenkinsci/java-client-api/issues/203
[issue-207]: https://github.com/jenkinsci/java-client-api/issues/207
[issue-209]: https://github.com/jenkinsci/java-client-api/issues/209
[issue-211]: https://github.com/jenkinsci/java-client-api/issues/211
[issue-215]: https://github.com/jenkinsci/java-client-api/issues/215
[issue-217]: https://github.com/jenkinsci/java-client-api/issues/217
[issue-220]: https://github.com/jenkinsci/java-client-api/issues/220
[issue-222]: https://github.com/jenkinsci/java-client-api/issues/222
[issue-244]: https://github.com/jenkinsci/java-client-api/issues/244
[issue-268]: https://github.com/jenkinsci/java-client-api/issues/268
[issue-289]: https://github.com/jenkinsci/java-client-api/issues/289
[issue-282]: https://github.com/jenkinsci/java-client-api/issues/282
[issue-291]: https://github.com/jenkinsci/java-client-api/issues/291
[issue-298]: https://github.com/jenkinsci/java-client-api/issues/298
[issue-301]: https://github.com/jenkinsci/java-client-api/issues/301
[issue-309]: https://github.com/jenkinsci/java-client-api/issues/309
[issue-394]: https://github.com/jenkinsci/java-client-api/issues/394
[issue-395]: https://github.com/jenkinsci/java-client-api/issues/395
[issue-396]: https://github.com/jenkinsci/java-client-api/issues/396
[issue-397]: https://github.com/jenkinsci/java-client-api/issues/397
[issue-399]: https://github.com/jenkinsci/java-client-api/issues/399
[issue-400]: https://github.com/jenkinsci/java-client-api/issues/400
[issue-401]: https://github.com/jenkinsci/java-client-api/issues/401
[issue-402]: https://github.com/jenkinsci/java-client-api/issues/402
[issue-405]: https://github.com/jenkinsci/java-client-api/issues/405
[issue-464]: https://github.com/jenkinsci/java-client-api/issues/464
[pull-123]: https://github.com/jenkinsci/java-client-api/pull/123
[pull-149]: https://github.com/jenkinsci/java-client-api/pull/149
[pull-158]: https://github.com/jenkinsci/java-client-api/pull/158
[pull-163]: https://github.com/jenkinsci/java-client-api/pull/163
[pull-204]: https://github.com/jenkinsci/java-client-api/pull/204
[pull-206]: https://github.com/jenkinsci/java-client-api/pull/206
[pull-229]: https://github.com/jenkinsci/java-client-api/pull/229
[pull-239]: https://github.com/jenkinsci/java-client-api/pull/239
[pull-240]: https://github.com/jenkinsci/java-client-api/pull/240
[pull-247]: https://github.com/jenkinsci/java-client-api/pull/247
[pull-262]: https://github.com/jenkinsci/java-client-api/pull/262
[pull-386]: https://github.com/jenkinsci/java-client-api/pull/386
[jissue-35002]: https://issues.jenkins-ci.org/browse/JENKINS-35002
[jissue-35108]: https://issues.jenkins-ci.org/browse/JENKINS-35108
[jissue-38787]: https://issues.jenkins-ci.org/browse/JENKINS-38787
[jissue-38816]: https://issues.jenkins-ci.org/browse/JENKINS-38816
[jissue-38823]: https://issues.jenkins-ci.org/browse/JENKINS-38823
[jissue-46445]: https://issues.jenkins-ci.org/browse/JENKINS-46445
[jissue-46472]: https://issues.jenkins-ci.org/browse/JENKINS-46472
[jissue-56186]: https://issues.jenkins-ci.org/browse/JENKINS-56186
[jissue-56585]: https://issues.jenkins-ci.org/browse/JENKINS-56585
