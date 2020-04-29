## Running SonarQube

The easiest way to start SonarQube is in a Docker container with the following command:

```bash
$ docker run -d --name sonarqube -p 9000:9000 sonarqube
```

SonarQube will be available after a couple of minutes at this address http://localhost:9000/ and there you can login with the default user `admin`/`admin`.

Run the following command to scan your project and push the reports to SonarQube:

```bash
$ ./gradlew sonarqube
```

Now you should be able to analyse the results in SonarQube.