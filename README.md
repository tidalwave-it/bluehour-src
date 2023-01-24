![Maven Central](https://img.shields.io/maven-central/v/it.tidalwave.accounting/bluehour.svg)
[![Build Status](https://img.shields.io/jenkins/s/http/services.tidalwave.it/ci/job/blueHour_Build_from_Scratch.svg)](http://services.tidalwave.it/ci/view/blueHour)
[![Test Status](https://img.shields.io/jenkins/t/http/services.tidalwave.it/ci/job/blueHour.svg)](http://services.tidalwave.it/ci/view/blueHour)
[![Coverage](https://img.shields.io/jenkins/c/http/services.tidalwave.it/ci/job/blueHour.svg)](http://services.tidalwave.it/ci/view/blueHour)

blueHour
================================

blueHour is a small application for accounting and invoicing. Still under development in the early stage.

blueHour requires and is tested with JDKs in this range: [11,12).
It is released under the [Apache Licence v2](https://www.apache.org/licenses/LICENSE-2.0.txt).

Please have a look at the [project website](http://bluehour.tidalwave.it) for a quick introduction with samples, tutorials, JavaDocs and build reports.


Bootstrapping
-------------

In order to build the project, run from the command line:

```shell
mkdir bluehour
cd bluehour
git clone https://tidalwave@bitbucket.org/tidalwave/bluehour-src.git .
mvn -DskipTests
```

The project can be opened with a recent version of the [IntelliJ IDEA](https://www.jetbrains.com/idea/), 
[Apache NetBeans](https://netbeans.apache.org/) or [Eclipse](https://www.eclipse.org/ide/) IDEs.


Contributing
------------

Pull requests are accepted via [Bitbucket](https://tidalwave@bitbucket.org/tidalwave/bluehour-src.git) or [GitHub](). There are some guidelines which will make 
applying pull requests easier:

* No tabs: please use spaces for indentation.
* Respect the code style.
* Create minimal diffs — disable 'on save' actions like 'reformat source code' or 'organize imports' (unless you use the IDEA specific configuration for 
  this project).
* Provide [TestNG](https://testng.org/doc/) tests for your changes and make sure your changes don't break any existing tests by running
```mvn clean test```. You can check whether there are currently broken tests at the [Continuous Integration](http://services.tidalwave.it/ci/view/blueHour) page.

If you plan to contribute on a regular basis, please consider filing a contributor license agreement. Contact us for
 more information.


Additional Resources
--------------------

* [Issue tracking](http://services.tidalwave.it/jira/browse/BH)
* [Continuous Integration](http://services.tidalwave.it/ci/view/blueHour)
* [Tidalwave Homepage](http://tidalwave.it)
