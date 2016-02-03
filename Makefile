make:
	javac -cp . -d ./bin/ ./src/*.java
run:
	java -cp ./ -cp ./bin/ Main
