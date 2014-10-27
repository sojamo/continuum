cd "`dirname "$0"`"
pwd
java -Djava.library.path="./lib/native" -cp ./target/classes:./lib/communication/*:./lib/processing/*:./lib/sound/*:./lib/visual/* sojamo.continuum.Continuum
