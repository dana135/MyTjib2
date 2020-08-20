# Tjib #

# Start server - Spring Boot (Tjib_Spring)

Option 1 (recommended) -
Download Spring Tools for Eclipse https://spring.io/tools .
Import the project Tjib_Spring to the IDE, right click on the project Run As -> Spring Boot App.

If at any point you wish remove existing database and create a new empty database go to src/main/resources -> application. properties
and change at the last line "update" to "create". Don't forget to change it back to "update" before the next run to keep the data. 

Option 2 -
Go to Tjib_Spring-master\Tjib_Spring-master\target folder in command prompt.
Put the command java -jar Tjib-0.0.1-SNAPSHOT.jar and keep command promt open while server is running.

# Start client - Android Studio (Tjib_Android)
Download android studio https://developer.android.com/studio and import the folder "app" in Tjib_Android to the IDE.
Go to the class RetrofitInstance in folder 'network' and add your ip address to the url.
Download an AVD (Android Virtual Device), Pixel 2 API 30 is recommended.
Click run button to deploy the app on the device.
