Korqie Android client
=============

Qiming Fang (qf26@cornell.edu)

##Installation
Make sure you have [Android Studio](https://developer.android.com/sdk/installing/studio.html) set up, as the build dependencies will use [gradle](https://www.gradle.org/) instead of ANT to resolve dependencies.

Import Korqie as an existing project, then go to `Build >> Make Project` to build gradle dependencies.

Attach an android device, enable USB debugging, and deploy.

##Testing
To run tests: `./gradlew test` Note that `BUILD SUCCESSFUL` in testing actually means that the tests have passed. 
