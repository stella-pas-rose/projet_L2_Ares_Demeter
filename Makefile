
clean:
	$ rm -rf classes/*

doc: 
	$ javadoc -d docs -sourcepath src src/game/listchooser/*.java
	$ javadoc -d docs -sourcepath src src/game/listchooser/util/*.java
	$ javadoc -d docs -sourcepath src src/game/tuile/*.java
	$ javadoc -d docs -sourcepath src src/game/util/*.java
	$ javadoc -d docs -sourcepath src src/game/*.java
	$ javadoc -d docs -sourcepath src src/game/tuile/building/*.java
	$ javadoc -d docs -sourcepath src src/game/action/*.java

cls:
	$ javac -sourcepath src src/game/tuile/*.java -d classes
	$ javac -sourcepath src src/game/util/*.java -d classes
	$ javac -sourcepath src src/game/*.java -d classes
	$ javac -sourcepath src src/game/tuile/building/*.java -d classes
	$ javac -sourcepath src src/game/action/*.java -d classes

ares.jar: 
	$ jar cvfe ares.jar game.Ares -C classes .

demeter.jar: 
	$ jar cvfe demeter.jar game.Demeter -C classes .

aresRandom.jar: 
	$ jar cvfe aresRandom.jar game.AresRandom -C classes .

demeterRandom.jar: 
	$ jar cvfe demeterRandom.jar game.DemeterRandom -C classes .

